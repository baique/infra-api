package tech.hljzj.infrastructure.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hljzj.activiti.config.Loader;
import tech.hljzj.activiti.pojo.ProcessModel;
import tech.hljzj.infrastructure.domain.SysActiviti;
import tech.hljzj.infrastructure.mapper.SysActivitiMapper;
import tech.hljzj.infrastructure.service.SysActivitiService;
import tech.hljzj.infrastructure.vo.SysActiviti.SysActivitiQueryVo;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;


/**
 * 流程管理 sys_activiti_
 * 业务实现
 *
 * @author wa
 */
@Service
public class SysActivitiServiceImpl extends ServiceImpl<SysActivitiMapper, SysActiviti> implements SysActivitiService {
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public SysActiviti entityGet(SysActiviti entity) {
        return getOne(Wrappers.query(entity));
    }

    @Override
    public SysActiviti entityGet(Serializable id) {
        return getById(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityCreate(SysActiviti entity) {
        save(entity);

        saveOrUpdate(entity);
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityUpdate(SysActiviti entity) {
        SysActiviti existsEntity = getById(entity.getId());
        existsEntity.updateForm(entity);

        saveOrUpdateModel(entity);
        return updateById(existsEntity);
    }

    private void saveOrUpdateModel(SysActiviti entity) {
        ProcessModel model = new ProcessModel();
        model.setCode(entity.getId());
        model.setName(entity.getName());
        Model m = repositoryService.createModelQuery().modelKey(entity.getId()).singleResult();
        if (m == null) {
            Model nm = repositoryService.newModel();
            nm.setKey(model.getCode());
            nm.setName(model.getName());
            m = nm;
            repositoryService.saveModel(m);
        } else {
            repositoryService.saveModel(m);
        }
    }


    @Override
    public boolean entityDelete(SysActiviti entity) {
        return removeById(entity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean entityBatchDelete(Collection<Serializable> ids) {
        return removeBatchByIds(ids);
    }


    @Override
    public Page<SysActiviti> page(SysActivitiQueryVo query) {
        Page<SysActiviti> pageConfig = query.buildPagePlus();
        // add default order
        pageConfig.addOrder(OrderItem.desc("id_"));
        // add default order
        return super.page(pageConfig, query.buildQueryWrapper());
    }

    @Override
    public List<SysActiviti> list(SysActivitiQueryVo query) {
        query.setEnablePage(false);
        return this.page(query).getRecords();
    }

    @Override
    public Loader getModelDetail(String id) {
        byte[] modelInfo = repositoryService.getModelEditorSourceExtra(id);
        if (modelInfo == null) {
            return null;
        } else {
            return JSONUtil.toBean(new String(modelInfo), Loader.class);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndPublishModel(String id, Loader model) {
        // 这里保存并发布模型
        Model m = repositoryService.createModelQuery().modelKey(id).singleResult();
        String modelConfigJsonStr = JSONUtil.toJsonStr(model);
        repositoryService.addModelEditorSourceExtra(m.getId(), modelConfigJsonStr.getBytes(StandardCharsets.UTF_8));
        ProcessModel modelBase = model.getModelBase();
        BpmnModel bpmnModel = modelBase.toBpmnModel();
        new BpmnAutoLayout(bpmnModel).execute();
        byte[] xmlByte = new BpmnXMLConverter().convertToXML(bpmnModel);
        repositoryService.addModelEditorSource(m.getId(), xmlByte);

        DeploymentEntityImpl deployment = (DeploymentEntityImpl) repositoryService.createDeployment()
            .name(modelBase.getName())
            .key(id)
            .addString(m.getKey() + ".define.json", modelConfigJsonStr)
            .addString(m.getKey() + ".bpmn20.xml", new String(xmlByte, StandardCharsets.UTF_8))
            .deploy();
        m.setDeploymentId(deployment.getId());
    }
}