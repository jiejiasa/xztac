package com.ruoyi.system.service.crk;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.domain.crk.InInventory;
import com.ruoyi.system.domain.vo.crk.InventoryManagementSelectVO;

public interface InInventoryService extends IService<InInventory> {
    PageInfo<InInventory> selectList(InventoryManagementSelectVO vo);
}
