package tech.hljzj.infrastructure.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.framework.exception.UserException;
import tech.hljzj.framework.util.excel.ExcelUtil;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptImportVo;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptNewVo;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Service
@Scope(value = SCOPE_PROTOTYPE)
public class SysDeptImportService {
    public static final String KEY = "k:";
    public static final String NAME = "n:";
    private final SysDeptService sysDeptService;
    /**
     * 查询缓存
     */
    private final Map<String, SysDept> deptQueryCache = new HashMap<>();
    /**
     * 已经处理完的数据
     */
    private final Map<SysDeptImportVo, SysDept> processedDataCache = new HashMap<>();
    /**
     * 部门标识到部门数据的映射
     */
    private final Map<String, SysDeptImportVo> deptIndexMap = new HashMap<>();

    public SysDeptImportService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void read(ExcelTypeEnum type, InputStream inputStream) {
        List<SysDeptImportVo> deptList = ExcelUtil.readExcel(type, inputStream, SysDeptImportVo.class);
        deptList.forEach(f -> {
            if (StrUtil.isNotBlank(f.getName())) {
                deptIndexMap.put(NAME + f.getName(), f);
            }
            if (StrUtil.isNotBlank(f.getKey())) {
                deptIndexMap.put(KEY + f.getKey(), f);
            }
        });
        deptList.forEach(data -> {
            if (StrUtil.isAllBlank(data.getKey(), data.getName())) {
                return;
            }
            resolveRowData(data, new LinkedHashSet<>(deptList.size()));
        });
    }

    private SysDept resolveRowData(SysDeptImportVo node, Set<SysDeptImportVo> unionNode) {
        if (!unionNode.add(node)) {
            String link = unionNode.stream().map(SysDeptImportVo::getName).collect(Collectors.joining("/"));
            throw UserException.defaultError(
                "[" + link + "/" + node.getName() + "]上下级关系设置错误，可能导致组织树出现死循环"
            );
        }
        // 代表已经处理过了
        if (processedDataCache.containsKey(node)) {
            return processedDataCache.get(node);
        }
        // 查找已存在的部门

        boolean findParent = !StrUtil.isAllBlank(node.getParentKey(), node.getParentName());
        SysDept parent = null;
        if (findParent) {

            SysDeptImportVo parentNode;
            if (StrUtil.isNotBlank(node.getParentKey())) {
                parentNode = deptIndexMap.get(KEY + node.getParentKey());
            } else {
                parentNode = deptIndexMap.get(NAME + node.getParentName());
            }

            if (parentNode == null) {
                parentNode = new SysDeptImportVo();
                parentNode.setKey(node.getParentKey());
                parentNode.setName(StrUtil.blankToDefault(node.getParentName(), node.getParentKey()));

                // 增加到索引，方便后续查找
                deptIndexMap.put(KEY + parentNode.getKey(), parentNode);
                deptIndexMap.put(NAME + parentNode.getName(), parentNode);
            }

            parent = resolveRowData(parentNode, unionNode);
        }
        // 现在是没有上级的情况
        SysDept curr = getByInfo(node);
        if (curr == null || (parent != null && !isOwnParent(curr, parent))) {
            SysDeptNewVo sysDept = new SysDeptNewVo();
            // 这个步骤只能在插入前完成，因为可能用户会通过key或者name来定位部门，如果提前设置会导致无法正确查询部门
            sysDept.setKey(StrUtil.blankToDefault(node.getKey(), node.getName()));
            sysDept.setName(StrUtil.blankToDefault(node.getName(), node.getKey()));

            if (parent != null) {
                sysDept.setParentId(parent.getId());
            } else {
                sysDept.setParentId("0");
            }

            sysDept.setAlias(sysDept.getAlias());
            sysDept.setType(sysDept.getType());
            sysDept.setAddr(sysDept.getAddr());
            sysDept.setAddrNo(sysDept.getAddrNo());
            sysDept.setDuty(sysDept.getDuty());
            sysDept.setEnable("1");
            sysDept.setStatus("1");
            curr = sysDept.toDto();
            sysDeptService.entityCreate(curr);
        } else {
            // 反之，如果部门已经存在了，就更新一下名
            curr.setName(StrUtil.blankToDefault(node.getName(), curr.getName()));
            curr.setShortName(StrUtil.blankToDefault(node.getShortName(), curr.getShortName()));
            curr.setAlias(StrUtil.blankToDefault(node.getAlias(), curr.getAlias()));
            curr.setDuty(StrUtil.blankToDefault(node.getDuty(), curr.getDuty()));
            curr.setType(StrUtil.blankToDefault(node.getType(), curr.getType()));
            curr.setAddr(StrUtil.blankToDefault(node.getAddr(), curr.getAddr()));
            curr.setAddrNo(StrUtil.blankToDefault(node.getAddrNo(), curr.getAddrNo()));

            sysDeptService.entityUpdate(curr);
        }
        // 创建完毕
        deptQueryCache.put(KEY + curr.getKey(), curr);
        deptQueryCache.put(NAME + curr.getName(), curr);

        processedDataCache.put(node, curr);
        return curr;
    }

    private boolean isOwnParent(SysDept curr, SysDept parent) {
        if (parent == null) {
            // 部门上级是否是顶级
            return ObjUtil.equals(curr.getParentId(), 0);
        }
        // 是否是同一个上级
        return curr.getParentId().equals(parent.getId());
    }

    private SysDept getByInfo(SysDeptImportVo data) {
        SysDept findedDept = null;
        if (StrUtil.isNotBlank(data.getKey())) {
            findedDept = deptQueryCache.compute(KEY + data.getKey(), (key, v) -> {
                if (v == null) {
                    v = sysDeptService.getOne(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getKey, data.getKey()));
                }
                return v;
            });
        } else if (StrUtil.isNotBlank(data.getName())) {
            findedDept = deptQueryCache.compute(NAME + data.getName(), (key, v) -> {
                if (v == null) {
                    v = sysDeptService.getOne(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getName, data.getName()));

                }
                return v;
            });
        }
        if (findedDept != null) {
            deptQueryCache.put(KEY + findedDept.getKey(), findedDept);
            deptQueryCache.put(NAME + findedDept.getName(), findedDept);
            return findedDept;
        } else {
            return null;
        }
    }
}
