package com.xzt.web.crk;


import com.github.pagehelper.PageInfo;
import com.xzt.DeployActiviti;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.rvo.*;
import com.xzt.inventory.service.InInventoryService;
import com.xzt.inventory.service.InventoryManagementService;
import com.xzt.inventory.service.OutInventoryService;
import com.xzt.inventory.vo.GoOutInfo;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import com.xzt.inventory.vo.UpdateInventoryVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Resource
    private InventoryManagementService inventoryManagementService;

    @Resource
    private InInventoryService inInventoryService;

    @Resource
    private OutInventoryService outInventoryService;


    @Resource
    private DeployActiviti deployActiviti;


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
    public PageInfo<InInventoryRVO> getInList(@RequestBody InventoryManagementSelectVO vo){
        return inInventoryService.selectList(vo);
    }

    @PostMapping("/getOutList")
    public PageInfo<OutInventoryRVO> getOutList(@RequestBody InventoryManagementSelectVO vo){
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

        Boolean id = inventoryManagementService.inster(inventoryManagement);
        return inInventoryService.inster(inventoryManagement);

    }


    @GetMapping("/getGoOut")
    public GoOutInventoryRVO getGoOut(@RequestParam("id") Integer id){
        GoOutInventoryRVO goOutInventoryRVO = inventoryManagementService.getGoOut(id);
        return goOutInventoryRVO;
    }


    @GetMapping("/delInventory")
    public AjaxResult delInventory(@RequestParam("id") Integer id){
        return inventoryManagementService.deleteById(id);
    }


    @PostMapping("/goOut")
    public Boolean goOut (@RequestBody GoOutInfo goOutInfo){
        return inventoryManagementService.goOut(goOutInfo);
    }




    @PostMapping("/updateInventory")
    public Boolean updateInventory(@RequestBody UpdateInventoryVO vo){
        return inventoryManagementService.updateInventory(vo);
    }


    @GetMapping("/getAllInfo")
    public PriceAllInfoRVO getAllInfo(@RequestParam("id") Integer id){
        return inventoryManagementService.getAllInfo(id);
    }



    @PostMapping("/updatePriceStatus")
    public Boolean updatePriceStatus(@RequestBody GoOutInfo goOutInfo){
        return inventoryManagementService.updatePriceStatus(goOutInfo);
    }





    @GetMapping("/getAuditHistoryRVO")
    public AuditHistoryRVO getAuditHistoryRVO(@RequestParam("id") Integer id){

          return inventoryManagementService.getAuditHistoryRVO(id);


    }



    @PostMapping("/InventoryImport")
    public Boolean getAuditHistoryRVO(@RequestBody MultipartFile file) throws Exception {

        System.out.println("takls");
        return inventoryManagementService.inventoryManagementImport(file);


    }


    @GetMapping("/importTemplate")
    public void getAuditHistoryRVO(HttpServletResponse response) throws IOException {

        inventoryManagementService.getTemplate( response);

    }


    @PostMapping("/export")
    public void export(  HttpServletResponse response,@RequestBody InventoryManagementSelectVO vo) throws Exception {

        inventoryManagementService.exportExcle(vo,response);

    }

}
