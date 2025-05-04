package tech.hljzj.activiti.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.ProcessModel;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Registry implements ApplicationRunner {
    private final Map<String, SimpleProcessRegister> processMap = new LinkedHashMap<>(16);
    @Autowired(required = false)
    @Getter
    private List<SimpleProcessRegister> processors = new ArrayList<>();
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;


    public SimpleProcessRegister get(String key) {
        return processMap.get(key);
    }

    //    @Transactional(rollbackFor = Exception.class)
    public void registry(SimpleProcessRegister SimpleProcessRegister) throws Exception {
        Model model = repositoryService.createModelQuery().modelKey(SimpleProcessRegister.processCode()).singleResult();
        if (model == null) {
            model = repositoryService.newModel();
        }
        model.setKey(SimpleProcessRegister.processCode());
        model.setName(SimpleProcessRegister.processName());
        byte[] jsonBytes = repositoryService.getModelEditorSourceExtra(model.getId());
        String metaInfo = null;
        if (jsonBytes != null) {
            metaInfo = new String(jsonBytes, StandardCharsets.UTF_8);
        }
        Loader loader;
        ProcessModel modelBase = null;
        if (StrUtil.isNotBlank(metaInfo)) {
            loader = objectMapper.readValue(metaInfo, Loader.class);
            modelBase = loader.getModelBase();
        } else {
            loader = new Loader();
        }
        if (modelBase == null) {
            modelBase = new ProcessModel();
        }
        modelBase.setCode(model.getKey());
        modelBase.setName(model.getName());

        loader.setModelBase(modelBase);
        loader.setFields(SimpleProcessRegister.props());
        loader.setAssignMethods(SimpleProcessRegister.assignMethod());
        loader.setTriggers(SimpleProcessRegister.actionTriggerMethod());


        //保留流程
        repositoryService.saveModel(model);
        repositoryService.addModelEditorSourceExtra(model.getId(), objectMapper.writeValueAsBytes(loader));
        processMap.put(model.getKey(), SimpleProcessRegister);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(ApplicationArguments args) throws Exception {
        this.initProcess();
    }

    public void initProcess() throws Exception {
        for (SimpleProcessRegister processRegister : processors) {
            this.registry(processRegister);
        }
    }
}
