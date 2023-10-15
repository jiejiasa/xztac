package com.xzt.system.service.crk;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.system.domain.crk.InInventory;
import com.xzt.system.domain.vo.crk.InventoryManagementSelectVO;

public interface InInventoryService extends IService<InInventory> {
    PageInfo<InInventory> selectList(InventoryManagementSelectVO vo);
}
