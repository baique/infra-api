package tech.hljzj.infrastructure.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import tech.hljzj.framework.base.BaseController;
import tech.hljzj.framework.bean.R;
import tech.hljzj.framework.logger.BusinessType;
import tech.hljzj.framework.logger.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.hljzj.infrastructure.domain.SysUserAllowIp;
import tech.hljzj.infrastructure.service.SysUserAllowIpService;
import tech.hljzj.infrastructure.vo.SysUserAllowIp.*;
import tech.hljzj.framework.util.excel.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 控制层
 *
 * @author wa
 */
@RestController
@RequestMapping("/sys_user_allow_ip")
public class SysUserAllowIpController extends BaseController {
    public static final String MODULE_NAME = "用户IP绑定信息";
    private final SysUserAllowIpService service;

    @Autowired
    public SysUserAllowIpController(SysUserAllowIpService service) {
        this.service = service;
    }
    
    /**
     * 获取数据
     *
     * @param id 数据id
     * @return 数据详情
     */
    //@PreAuthorize("auth({'sys_user_allow_ip_:query','sys_user_allow_ip_:edit'})")
    @GetMapping("/{id}")
    @Log(title = MODULE_NAME, operType = BusinessType.DETAIL)
    public R<SysUserAllowIpDetailVo> entityGet(@PathVariable Serializable id) {
        SysUserAllowIp dto = this.service.entityGet(id);
        return R.ok(new SysUserAllowIpDetailVo().fromDto(dto));
    }

    
    /**
     * 新增数据
     *
     * @param entity 数据体
     * @return 新增
     */
    //@PreAuthorize("auth('sys_user_allow_ip_:add')")
    @PostMapping("/insert")
    @Log(title = MODULE_NAME, operType = BusinessType.INSERT)
    public R<SysUserAllowIpDetailVo> entityCreate(@RequestBody @Validated SysUserAllowIpNewVo entity) {
        SysUserAllowIp dto = entity.toDto();
        this.service.entityCreate(dto);
        return R.ok(new SysUserAllowIpDetailVo().fromDto(dto));
    }

    
    /**
     * 修改数据
     *
     * @param entity 数据体
     * @return 修改后
     */
    //@PreAuthorize("auth('sys_user_allow_ip_:edit')")
    @PostMapping("/update")
    @Log(title = MODULE_NAME, operType = BusinessType.UPDATE)
    public R<SysUserAllowIpDetailVo> entityUpdate(@RequestBody @Validated SysUserAllowIpUpdateVo entity) {
        SysUserAllowIp dto = entity.toDto();
        this.service.entityUpdate(dto);
        return R.ok(new SysUserAllowIpDetailVo().fromDto(dto));
    }

    
     /**
     * 删除数据
     *
     * @param ids 数据ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    //@PreAuthorize("auth('sys_user_allow_ip_:delete')")
    @Log(title = MODULE_NAME, operType = BusinessType.DELETE)
    public R<Boolean> entityDelete(@RequestBody @Validated List<Serializable> ids) {
        return R.ok(this.service.entityBatchDelete(ids));
    }

    
  /**
   * 导出数据
   *
   * @param query 数据查询
   * @return 导出结果
   */
  @PostMapping("/export")
  //@PreAuthorize("auth('sys_user_allow_ip_:export')")
  @Log(title = MODULE_NAME, operType = BusinessType.EXPORT)
  public void export(@RequestBody SysUserAllowIpQueryVo query, HttpServletResponse response) {
      List<SysUserAllowIp> listDto = this.service.list(query);

      List<SysUserAllowIpListVo> listVo = new ArrayList<>();
      for (SysUserAllowIp item : listDto) {
          SysUserAllowIpListVo vo = new SysUserAllowIpListVo();
          listVo.add(vo.fromDto(item));
      }

      ExcelUtil.exportExcel(response, listVo, "数据", SysUserAllowIpListVo.class);
  }
  
    
    /**
     * 导入数据
     *
     * @param file 文件
     * @return 导入失败的数据
     */
    @PostMapping("/import")
    //@PreAuthorize("auth('sys_user_allow_ip_:import')")
    @Log(title = MODULE_NAME, operType = BusinessType.IMPORT)
    public R<List<ExcelUtil.FailRowWrap<SysUserAllowIpListVo>>> importData(@RequestPart MultipartFile file) throws IOException {
        return R.ok(ExcelUtil.readExcel(file.getInputStream(), SysUserAllowIpListVo.class, item -> {
            this.service.entityCreate(item.toDto());
        }));
    }

    
    /**
     * 查询数据
     *
     * @param query 数据查询
     * @return 查询结果
     */
    @PostMapping("/list")
    //@PreAuthorize("auth('sys_user_allow_ip_:list')")
    public R<R.PageResult<SysUserAllowIpListVo>> page(@RequestBody SysUserAllowIpQueryVo query) {
        Page<SysUserAllowIp> page = this.service.page(query);
        Page<SysUserAllowIpListVo> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<SysUserAllowIpListVo> list = page.getRecords().stream().map((Function<SysUserAllowIp, SysUserAllowIpListVo>) entity -> {
            SysUserAllowIpListVo vo = new SysUserAllowIpListVo();
            return vo.fromDto(entity);
        }).collect(Collectors.toList());

        pageVo.setRecords(list);
        return R.ok(pageVo);
    }

}