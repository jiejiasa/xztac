package com.xzt.inventory.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.InInventory;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.rvo.InInventoryRVO;
import com.xzt.inventory.vo.InventoryManagementSelectVO;

public interface InInventoryService extends IService<InInventory> {
    PageInfo<InInventoryRVO> selectList(InventoryManagementSelectVO vo);

    Boolean inster(InventoryManagement inventoryManagement);
}
