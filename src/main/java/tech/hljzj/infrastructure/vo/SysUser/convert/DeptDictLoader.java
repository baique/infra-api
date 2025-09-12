package tech.hljzj.infrastructure.vo.SysUser.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.hljzj.framework.util.excel.bind.DictDataLoader;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.service.SysDeptService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeptDictLoader implements DictDataLoader {
    private final SysDeptService sysDeptService;

    @Override
    public Map<String, String> load() {
        List<SysDept> list = sysDeptService.list();
        Map<String, String> dict = new HashMap<>();
        for (SysDept sysDept : list) {
            dict.put(sysDept.getId(), sysDept.getName() + "(" + sysDept.getKey() + ")");
        }
        return dict;
    }
}
