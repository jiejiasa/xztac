package com.xzt.service.impl;

import com.xzt.common.utils.PageUtils;
import com.xzt.common.utils.SecurityUtils;
import com.xzt.service.IProcessService;
import com.xzt.vo.HandleAuditParam;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl implements IProcessService {


    @Resource
    private RuntimeService runtimeService;


    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;





    public String startProcessInstance(String definitionKey, String businessKey, Map<String, Object> variables) {
        // 1. 调用 `RuntimeService` 开始流程实例（流程定义 key，版本号，业务标识，流程变量）
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(definitionKey, businessKey, variables);
        // 2. 返回新增的流程实例 id
        return instance.getId();
    }


    /**
     * 查询待办
     * @return
     */
    @Override
    public List<String> selectAuditTodoList() {

        // 2. 通过 Activiti API 查询业务标识列表
        List<String> businessKeyList = selectBusinessKeyList("outInventory", SecurityUtils.getUserId());

        if (CollectionUtils.isEmpty(businessKeyList)) {
            // 如果查不到业务标识，返回空数据
            return Collections.emptyList();
        }

        PageUtils.startPage(); // 开始分页
        // 3. 通过业务标识列表查到审核信息列表
        return businessKeyList;
    }

    @Override
    public Boolean handleAudit(HandleAuditParam param) {


        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(param.getAuditId().toString()).singleResult();
        // 2. Activiti API 处理任务
        boolean ended = handleTaskByInstanceIdAndAssignee(processInstance.getId(), SecurityUtils.getUserId(), param);
        // 3. 根据当前审核状态更新套餐信息、服务项的状态

        return true;
    }


    public List<String> selectBusinessKeyList(String processDefinitionKey, Long userId) {

        String type = "TODO";
        // 1. 根据流程信息对象 + 当前用户查询已完成的历史任务
        HistoricTaskInstanceQuery taskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(userId + "");
        if (type.equals("TODO")) {
            // 如果查不到待办任务，直接返回空数据
            taskInstanceQuery.unfinished();
        }else {
            taskInstanceQuery.finished();
        }

        List<HistoricTaskInstance> list = taskInstanceQuery.list();


        // 2. 如果查不到，直接返回空集合
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        Set<String> collect = list.stream()
                .map(HistoricTaskInstance::getProcessInstanceId)
                .collect(Collectors.toSet());

        // 3. 基于流程实例 id 列表，查询流程实例列表，得到业务标识列表 => RuntimeService
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .processInstanceIds(collect)
                .list();

        // 4. 将流程实例集合转换为 businessKey 集合
        List<String> businessKey = new ArrayList<>();
        for (ProcessInstance instance : instances) {
            businessKey.add(instance.getBusinessKey());
        }
        return businessKey;
    }


    public boolean handleTaskByInstanceIdAndAssignee(String instanceId, Long userId, HandleAuditParam param) {
        // 1. 基于流程实例 id + 当前用户 id 查询待办任务
        Task task = taskService.createTaskQuery()
                .processInstanceId(instanceId)
                .taskAssignee(userId + "")
                .singleResult();
        Assert.notNull(task, "处理失败，任务不存在");

        // 2. 处理任务（流程变量），添加批注（根据状态添加）
        Map<String, Object> variables = new HashMap<>();
        // 是否通过
        variables.put("shopOwnerAudit", param.isPassed());
        // 添加批注
        String username = SecurityUtils.getUsername();
        String msg = username + "[" + (param.isPassed() ? "通过" : "拒绝") + "], " + param.getInfo();
        taskService.addComment(task.getId(), instanceId, msg);
        // 完成任务
        taskService.complete(task.getId(), variables);
        // 3. 查询是否还有下一个节点
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        return instance == null;
    }

}
