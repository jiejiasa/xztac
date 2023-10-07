package com.ruoyi.system.mapper.crk;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.crk.InventoryManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryManagementMapper extends BaseMapper<InventoryManagement> {
    List<InventoryManagement> selectByParam();
}
