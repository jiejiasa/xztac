package com.xzt.inventory.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.rvo.OutInventoryRVO;
import com.xzt.inventory.vo.GoOutInfo;
import com.xzt.inventory.vo.InventoryManagementSelectVO;

public interface OutInventoryService extends IService<OutInventory> {
    PageInfo<OutInventoryRVO> selectList(InventoryManagementSelectVO vo);

    Boolean updatePriceStatus(GoOutInfo goOutInfo);
}
