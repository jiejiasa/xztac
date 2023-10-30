package com.xzt.service;

import com.xzt.vo.HandleAuditParam;

import java.util.List;
import java.util.Map;

public interface IProcessService {





    /**
     * 开始流程实例
     *
     * @param definitionKey 流程定义 key
     * @param businessKey   业务标识
     * @param variables     流程变量
     * @return 流程实例 id
     */
    String startProcessInstance(String definitionKey, String businessKey, Map<String, Object> variables);


    /**
     * 待办审核列表查询
     */
    List<String> selectAuditTodoList();

    Boolean handleAudit(HandleAuditParam param);
}
