package com.xzt.inventory.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.rvo.AuditHistoryRVO;
import com.xzt.inventory.rvo.GoOutInventoryRVO;
import com.xzt.inventory.rvo.PriceAllInfoRVO;
import com.xzt.inventory.vo.GoOutInfo;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import com.xzt.inventory.vo.UpdateInventoryVO;
import com.xzt.vo.HandleAuditParam;

public interface InventoryManagementService extends IService<InventoryManagement> {
        PageInfo<InventoryManagement> selectList(InventoryManagementSelectVO vo);


        AjaxResult getInventoryInfo(Integer id);

        AjaxResult delInventory(Integer id);

        GoOutInventoryRVO getGoOut(Integer id);

        Boolean goOut(GoOutInfo goOutInfo);


         Boolean auditFlow(HandleAuditParam param);

        Boolean inster(InventoryManagement inventoryManagement);

        AjaxResult deleteById(Integer id);

    Boolean updateInventory(UpdateInventoryVO vo);

    PriceAllInfoRVO getAllInfo(Integer id);


     Boolean updatePriceStatus(GoOutInfo goOutInfo);

    AuditHistoryRVO getAuditHistoryRVO(Integer id);
}
