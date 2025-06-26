package tech.hljzj.infrastructure.vo.SysApp;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.*;

import java.util.List;

@Getter
@Setter
public class SysAppExport {
    private SysApp sysApp;
    private List<SysConfig> configList;
    private List<SysRole> roleList;
    private List<SysMenu> menuList;
    private List<SysDictType> dictTypeList;
    private List<SysDictData> dictDataList;
    private List<SysRoleMenu> roleMenuGrantList;
}
