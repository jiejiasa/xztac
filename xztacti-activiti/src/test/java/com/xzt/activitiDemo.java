package com.xzt;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class activitiDemo {



    @Test
    public void testActivitiDe(){

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();


        Deployment deploy = repositoryService.createDeployment()
                .name("出库申请")
                .addClasspathResource("BPMN/outInventory.bpmn")
                .addClasspathResource("BPMN/outInventory.png")
                .deploy();
        System.out.println("流程部署id" + deploy.getId());
        System.out.println("key" + deploy.getKey());
        System.out.println(deploy);
    }


}
