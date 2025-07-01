
package tech.hljzj.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tech.hljzj.infrastructure.domain.SysDept;

/**
 * 组织管理 sys_dept
 * DTO操作层
 * 
 * @author wa
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    @Update("update sys_dept_ set node_path_ = replace(node_path_,#{old},#{new}) where node_path_ like concat(#{old},'%')")
    void updateNodePath(@Param("old") String currentOldNodePath, @Param("new") String currentNewNodePath);
}