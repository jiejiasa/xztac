package com.xzt.inventory.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzt.inventory.domain.InInventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InInventoryManagementMapper extends BaseMapper<InInventory> {
}
