package com.xzt.web.crk;


import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.service.InventoryManagementService;
import com.xzt.inventory.service.InventoryToDoService;
import com.xzt.vo.HandleAuditParam;
import com.xzt.service.IProcessService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inventoryFlow")
public class InventoryToDoController {


    @Resource
    private InventoryManagementService inventoryManagementService;


    @Resource
    private IProcessService processService;

    @Resource
    private InventoryToDoService inventoryToDoService;



    @GetMapping("/ToDoInventory")
    public PageInfo<InventoryManagement> getToDoInventory(){

       return inventoryToDoService.getToDoInventory();

    }

    @PostMapping("/handle")
    public Boolean handleAudit( @RequestBody  HandleAuditParam param) {
        inventoryManagementService.auditFlow(param);
        return true;
    }







}
