package com.xzt.inventory.service;

import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.InventoryManagement;

public interface InventoryToDoService {
    PageInfo<InventoryManagement> getToDoInventory();
}
