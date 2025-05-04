package tech.hljzj.activiti.pojo.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import tech.hljzj.activiti.pojo.condition.FilterRules;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: ConditionNode
 * @description：条件(分支)
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConditionNode extends Node {
    private Boolean def;
    private FilterRules conditions;
    @JsonIgnore
    private Map<String, String> operatorMap = new HashMap<>();

    {
        // 等于
        operatorMap.put("eq", "%s == %s");
        // 不等于
        operatorMap.put("ne", "%s != %s");
        // 包含
        operatorMap.put("in", "%s.contains(%s)");
        // 不包含
        operatorMap.put("ni", "!(%s.contains(%s))");
        // 为空
        operatorMap.put("ul", "%s == null");
        // 不为空
        operatorMap.put("nu", "%s != null");
        // 字符包含
        operatorMap.put("lk", "%s.contains(%s)");
        // 大于
        operatorMap.put("gt", "%s > %s");
        // 小于
        operatorMap.put("lt", "%s < %s");
        // 小于或等于
        operatorMap.put("lte", "%s <= %s");
        // 大于或等于
        operatorMap.put("gte", "%s >= %s");
    }

    protected String stringVal(Object val) {
        if (val instanceof String) {
            return String.format("'%s'", val);
        } else {
            return String.valueOf(val);
        }
    }

    public String toConditionExpression(FilterRules filterRules) {
        String expression = filterRules.getConditions().stream().map(e -> {
            String operator = operatorMap.get(e.getOperator());
            if (StringUtils.isNotBlank(operator)) {
                if (e.getValue() instanceof Collection) {
                    e.setValue(
                            ((Collection<?>) e.getValue())
                                    .stream()
                                    .map(this::stringVal)
                                    .collect(Collectors.joining(","))
                    );
                } else if (e.getValue() instanceof Object[]) {
                    e.setValue(
                            Arrays.stream((Object[]) e.getValue())
                                    .map(this::stringVal)
                                    .collect(Collectors.joining(","))
                    );
                } else if (e.getValue() instanceof String) {
                    e.setValue(String.format("'%s'", e.getValue()));
                }
                return String.format(operator,
                        e.getField(),
                        e.getValue()
                );
            } else {
                return "";
            }
        }).collect(Collectors.joining("and".equals(filterRules.getOperator()) ? " && " : " ||"));
        if (CollectionUtils.isEmpty(filterRules.getGroups())) {
            return expression;
        } else {
            String collect = filterRules
                    .getGroups()
                    .stream()
                    .map(this::toConditionExpression)
                    .collect(Collectors.joining("and".equals(filterRules.getOperator()) ? " && " : " || "));
            return String.format("(%s) %s (%s)", expression, "and".equals(filterRules.getOperator()) ? " && " : " || ", collect);
        }
    }

    @Override
    public List<FlowElement> convert() {
        ArrayList<FlowElement> elements = new ArrayList<>();
        // 条件节点
        SequenceFlow sequenceFlow = this.buildSequence(this);
        sequenceFlow.setId(this.getId());
        sequenceFlow.setName(this.getName());
        sequenceFlow.setTargetRef(
                Optional.ofNullable(this.getChild()).map(Node::getId).orElse(this.getBranchId())
        );
        String expression = this.toConditionExpression(this.getConditions());
        if (StringUtils.isNotBlank(expression)) {
            sequenceFlow.setConditionExpression(String.format("${%s}", expression));
        }
        elements.add(sequenceFlow);
        // 下一个节点
        Node child = this.getChild();
        if (Objects.nonNull(child)) {
            child.setBranchId(this.getBranchId());
            List<FlowElement> flowElements = child.convert();
            elements.addAll(flowElements);
        }
        return elements;
    }
}
