package com.xzt.inventory.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzt.inventory.domain.InventoryManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryManagementMapper extends BaseMapper<InventoryManagement> {
    List<InventoryManagement> selectByParam();
}
