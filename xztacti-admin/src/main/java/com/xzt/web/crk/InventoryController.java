package com.xzt.web.crk;


import com.github.pagehelper.PageInfo;
import com.xzt.system.domain.crk.InInventory;
import com.xzt.system.domain.crk.InventoryManagement;
import com.xzt.system.domain.vo.crk.InventoryManagementSelectVO;
import com.xzt.system.domain.vo.crk.InventoryManagementUpdateVO;
import com.xzt.system.service.crk.InInventoryService;
import com.xzt.system.service.crk.InventoryManagementService;
import com.xzt.system.service.crk.OutInventoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Resource
    private InventoryManagementService inventoryManagementService;

    @Resource
    private InInventoryService inInventoryService;

    @Resource
    private OutInventoryService  outInventoryService;


    @PostMapping("/getList")
    public PageInfo<InventoryManagement> getList(@RequestBody InventoryManagementSelectVO vo){

       return inventoryManagementService.selectList(vo);

    }

    @PostMapping("/save")
    public Boolean save(@RequestBody InventoryManagement inventoryManagement){
       return inventoryManagementService.save(inventoryManagement);
    }


    @PostMapping("/addOrg")
    public Boolean addOrg(@RequestBody InventoryManagementUpdateVO vo){
        inventoryManagementService.addOrg(vo);
        return true;
    }



    @PostMapping("/getInList")
    public PageInfo<InInventory> getInList(@RequestBody InventoryManagementSelectVO vo){

        return inInventoryService.selectList(vo);

    }




}
