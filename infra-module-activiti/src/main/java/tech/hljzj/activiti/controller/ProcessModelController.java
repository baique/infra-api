package tech.hljzj.activiti.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.hljzj.activiti.config.Loader;
import tech.hljzj.activiti.config.Registry;
import tech.hljzj.activiti.manager.SimpleProcessRegister;
import tech.hljzj.activiti.pojo.ProcessModel;
import tech.hljzj.activiti.pojo.Result;
import tech.hljzj.activiti.register.ActStoreLoader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/activiti/model")
public class ProcessModelController {
    @Autowired
    private RepositoryService repositoryService;
    @Qualifier("httpServletResponse")
    @Autowired
    private HttpServletResponse httpServletResponse;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired(required = false)
    private ActStoreLoader actStoreLoader;
    @Autowired
    private Registry registry;

    /**
     * 转为bpmn，并下载
     *
     * @return
     */
    @PostMapping("/save")
    @Transactional
    public Result save(@RequestBody Loader model) throws IOException {
        String jsonStr = objectMapper.writeValueAsString(model);

        ProcessModel modelBase = model.getModelBase();
        BpmnModel bpmnModel = modelBase.toBpmnModel();
        new BpmnAutoLayout(bpmnModel).execute();
        byte[] xmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel);

        //将xml保存到模型
        Model m = repositoryService.createModelQuery().modelKey(modelBase.getCode()).singleResult();
        if (m == null) {
            m = repositoryService.newModel();
            m.setKey(modelBase.getCode());
        }
        repositoryService.saveModel(m);
        repositoryService.addModelEditorSource(m.getId(), xmlBytes);
        repositoryService.addModelEditorSourceExtra(m.getId(), jsonStr.getBytes(StandardCharsets.UTF_8));
        publish(modelBase.getCode());

        return Result.builder().build();
    }


    @PostMapping("/detail/{id}")
    public Result editor(@PathVariable String id) throws JsonProcessingException {
        Model mInfo = repositoryService.createModelQuery().modelKey(id).singleResult();
        httpServletResponse.setContentType("application/json;utf-8");
        byte[] d = repositoryService.getModelEditorSourceExtra(mInfo.getId());
        Loader loader = objectMapper.readValue(new String(d), Loader.class);
        SimpleProcessRegister processor = registry.get(loader.getModelBase().getCode());
        loader.setFields(processor.props());
        loader.setAssignMethods(processor.assignMethod());
        loader.setTriggers(processor.actionTriggerMethod());
        return Result.builder().data(loader).build();
    }


    @Transactional
    public void publish(@PathVariable String id) throws IOException {
        //发布流程
        ModelQuery modelQuery = repositoryService.createModelQuery();
        Model model = modelQuery.modelKey(id).singleResult();
        byte[] modelJsonByte = repositoryService.getModelEditorSourceExtra(model.getId());
        byte[] xmlBytes = repositoryService.getModelEditorSource(model.getId());
        if (xmlBytes == null) {
            httpServletResponse.sendError(500, "模型数据为空，请先设计流程并成功保存，再进行发布");
            return;
        }
        //部署标识
        DeploymentEntityImpl deployment = (DeploymentEntityImpl) repositoryService.createDeployment()
            .name(model.getName())
            .key(id)
            .addString(model.getKey() + ".define.json", new String(modelJsonByte, StandardCharsets.UTF_8))
            .addString(model.getKey() + ".bpmn20.xml", new String(xmlBytes, StandardCharsets.UTF_8))
            .deploy();
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
    }


    @GetMapping("/users")
    public Result users(@RequestParam String[] ids) {
        if (actStoreLoader == null) {
            return Result.builder().data(Collections.emptyList()).build();
        }
        if (ids == null) {
            ids = new String[0];
        }
        return Result.builder().data(actStoreLoader.users(Arrays.asList(ids))).build();
    }

    @GetMapping("/user/info")
    public Result users(String id) {
        if (actStoreLoader == null) {
            return Result.builder().data(Collections.emptyList()).build();
        }
        return Result.builder().data(actStoreLoader.users(Collections.singletonList(id))).build();
    }


    @GetMapping("/roles")
    public Result roles(@RequestParam(required = false) String[] ids) {
        if (actStoreLoader == null) {
            return Result.builder().data(Collections.emptyList()).build();
        }
        if (ids == null) {
            ids = new String[0];
        }
        return Result.builder().data(actStoreLoader.roles(Arrays.asList(ids))).build();
    }

    @GetMapping("/role/info")
    public Result roles(String id) {
        if (actStoreLoader == null) {
            return Result.builder().data(Collections.emptyList()).build();
        }
        return Result.builder().data(actStoreLoader.roles(Collections.singletonList(id))).build();
    }

}
