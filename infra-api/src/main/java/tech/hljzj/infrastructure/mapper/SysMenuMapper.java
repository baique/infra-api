
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tech.hljzj.infrastructure.domain.SysMenu;

/**
 * 菜单管理 sys_menu
 * DTO操作层
 *
 * @author wa
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    @Update("update sys_menu_ set node_path_ = replace(node_path_,#{old},#{new}) where node_path_ like concat(#{old},'%')")
    void updateNodePath(@Param("old") String currentOldNodePath, @Param("new") String currentNewNodePath);
}