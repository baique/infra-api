package tech.hljzj.infrastructure.vo.SysDept;

import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDept;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SysDeptAncestors {
    private SysDept current;
    private SysDept parent;
    private List<SysDept> nodes;

    public Vo toVo() {
        Vo vo = new Vo();
        if (current != null) {
            vo.setCurrent(new SysDeptListVo().fromDto(current));
        }
        if (parent != null) {
            vo.setParent(new SysDeptListVo().fromDto(parent));
        }
        if (nodes != null) {
            vo.setNodes(nodes.stream()
                .map(f -> new SysDeptListVo().<SysDeptListVo>fromDto(f))
                .collect(Collectors.toList()));
        }
        return vo;
    }

    @Getter
    @Setter
    public static class Vo {
        /**
         * 当前部门
         */
        private SysDeptListVo current;
        /**
         * 上级部门
         */
        private SysDeptListVo parent;
        /**
         * 所有上级（排序不定
         */
        private List<SysDeptListVo> nodes;
    }
}
