package tech.hljzj.infrastructure.compatible.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.hljzj.infrastructure.compatible.controller.bsae.MController;
import tech.hljzj.infrastructure.compatible.vo.department.Department;
import tech.hljzj.framework.security.an.Anonymous;
import tech.hljzj.framework.util.tree.TreeUtil;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.SysUser;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.service.SysUserService;
import tech.hljzj.infrastructure.vo.SysDept.SysDeptQueryVo;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("app")
@RestController
@Anonymous
public class AppDepartmentAction extends MController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取部门树状列表
     *
     * @param departmentId
     * @return
     */
    @PostMapping("/departmentsTree")
    @ResponseBody
    public Object findAllByTree(String departmentId) {
        SysDeptQueryVo sq = new SysDeptQueryVo();
        if (!StrUtil.isBlank(departmentId)) {
            SysDept dept = sysDeptService.getById(departmentId);
            if (dept == null) {
                return success(Collections.emptyList());
            }
            sq.setNodePathPrefix(dept.getNodePath());
            //兼容旧统一认证
            sq.setIdNot(dept.getId());
        }
        List<Department> deps = sysDeptService.list(sq).stream().map(Department::from).collect(Collectors.toList());
        return success(TreeUtil.listToTree(deps));
    }

    /**
     * 获取部门列表
     *
     * @param department 动态条件
     * @return 允许用户查看的字段组成的bean集合
     */

    @PostMapping("/departments")
    @ResponseBody
    public Object findAll(Department department) {
        SysDeptQueryVo queryVo = department.toQuery();
        List<Department> deps = sysDeptService.list(queryVo).stream().map(Department::from).collect(Collectors.toList());
        if (StrUtil.isNotBlank(department.getDepartmentId())) {
            if (deps.size() != 0) {
                return success(deps.get(0));
            } else {
                return success(null);
            }
        }
        return success(deps).addProperty("count", deps.size());
    }

    /**
     * 获取专案组成员
     *
     * @param departmentId 部门标识
     * @return 用户信息
     */
    @PostMapping("/admin/departments/dp/get")
    @ResponseBody
    public Object users(String departmentId) {
        VSysUserQueryVo query = new VSysUserQueryVo();
        query.setDeptId(departmentId);
        return success(sysUserService.list(query).stream().map(SysUser::getId).collect(Collectors.toSet()));
    }


    /**
     * 按照ID查询部门
     *
     * @param departmentId
     * @return
     */

    @PostMapping("/department")
    @ResponseBody
    public Object findByid(String departmentId) {
        return success(Department.from(sysDeptService.getById(departmentId)));
    }


    /**
     * 按照ID查询部门
     *
     * @param departmentId
     * @return
     */

    @PostMapping("/departmentIds")
    @ResponseBody
    public Object findByIds(String departmentId) {
        return success(sysDeptService.listByIds(Arrays.asList(departmentId.split(","))).stream().map(Department::from).collect(Collectors.toList()));
    }

    @PostMapping("/departmentsnochildren")
    @ResponseBody
    public Object finddepartmentsnochildren(Department department) {
//        List<Department> departments = new ArrayList<>();
        SysDept dept = sysDeptService.getById(department.getDepartmentId());

        if (dept == null) {
            return success(Collections.emptyList());
        }
        SysDeptQueryVo sq = department.toQuery();
        // 清空id条件！
        sq.setId(null);
        sq.setNodePathPrefix(dept.getNodePath());
        //兼容旧统一认证
        sq.setIdNot(dept.getId());
        List<Department> deptList = new ArrayList<>();
        deptList.add(Department.from(dept));
        deptList.addAll(sysDeptService.list(sq).stream().map(Department::from).collect(Collectors.toList()));
        return success(deptList).addProperty("count", deptList.size());
    }

}
