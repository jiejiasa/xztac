package com.xzt.inventory.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import com.xzt.inventory.vo.InventoryManagementUpdateVO;

public interface InventoryManagementService extends IService<InventoryManagement> {
    PageInfo<InventoryManagement> selectList(InventoryManagementSelectVO vo);

    Boolean addOrg(InventoryManagementUpdateVO vo);

    AjaxResult getInventoryInfo(Integer id);

    AjaxResult delInventory(Integer id);
}
