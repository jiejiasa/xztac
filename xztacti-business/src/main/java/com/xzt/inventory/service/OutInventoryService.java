package com.xzt.inventory.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.vo.InventoryManagementSelectVO;

public interface OutInventoryService extends IService<OutInventory> {
    PageInfo<OutInventory> selectList(InventoryManagementSelectVO vo);
}
