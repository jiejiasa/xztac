package com.xzt;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DeployActiviti {


    @Resource
    private RepositoryService repositoryService;




    public void testde(){

        Deployment deploy = repositoryService.createDeployment()
                .name("出库申请")
                .addClasspathResource("BPMN/outInventory.bpmn")
                .addClasspathResource("BPMN/outInventory.png")
                .deploy();

        System.out.println(deploy);
        System.out.println("success");

    }










}
