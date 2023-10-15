package com.xzt.system.service.crk;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.system.domain.crk.InventoryManagement;
import com.xzt.system.domain.vo.crk.InventoryManagementSelectVO;
import com.xzt.system.domain.vo.crk.InventoryManagementUpdateVO;

public interface InventoryManagementService extends IService<InventoryManagement> {
    PageInfo<InventoryManagement> selectList(InventoryManagementSelectVO vo);

    Boolean addOrg(InventoryManagementUpdateVO vo);
}
