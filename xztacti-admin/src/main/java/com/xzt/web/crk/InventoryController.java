package com.xzt.web.crk;


import com.github.pagehelper.PageInfo;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.common.utils.SecurityUtils;
import com.xzt.common.utils.bean.BeanUtils;
import com.xzt.inventory.domain.InInventory;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.rvo.GoOutInventoryRVO;
import com.xzt.inventory.service.InInventoryService;
import com.xzt.inventory.service.InventoryManagementService;
import com.xzt.inventory.service.OutInventoryService;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Resource
    private InventoryManagementService inventoryManagementService;

    @Resource
    private InInventoryService inInventoryService;

    @Resource
    private OutInventoryService outInventoryService;


    /**
     * 库存列表
     * @param vo
     * @return
     */
    @PostMapping("/getList")
    public PageInfo<InventoryManagement> getList(@RequestBody InventoryManagementSelectVO vo){
       return inventoryManagementService.selectList(vo);
    }


    /**
     * 入库记录查询
     * @param vo
     * @return
     */
    @PostMapping("/getInList")
    public PageInfo<InInventory> getInList(@RequestBody InventoryManagementSelectVO vo){
        return inInventoryService.selectList(vo);
    }

    @PostMapping("/getOutList")
    public PageInfo<OutInventory> getOutList(@RequestBody InventoryManagementSelectVO vo){
        return outInventoryService.selectList(vo);
    }

    /**
     * 获取库存详细信息
     */
    @GetMapping("/getInventoryInfo")
    public AjaxResult getInventoryInfo(@RequestParam("id") Integer id){

        return inventoryManagementService.getInventoryInfo(id);
    }

    /**
     * 新增库存信息
     * @param inventoryManagement
     * @return
     */
    @PostMapping("/save")
    public Boolean save(@RequestBody InventoryManagement inventoryManagement){
        InInventory inInventory = new InInventory();
        BeanUtils.copyProperties(inventoryManagement,inInventory);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(new Date());
        inInventory.setTime( format);
        inInventory.setPeopleId(SecurityUtils.getUserId());
        inInventoryService.save(inInventory);
        return inventoryManagementService.save(inventoryManagement);
    }


    @GetMapping("/getGoOut")
    public GoOutInventoryRVO getGoOut(@RequestParam("id") Integer id){



        GoOutInventoryRVO goOutInventoryRVO = inventoryManagementService.getGoOut(id);

        return goOutInventoryRVO;


    }


    @GetMapping("/delInventory")
    public AjaxResult delInventory(@RequestParam("id") Integer id){
        return inventoryManagementService.delInventory(id);
    }




}
