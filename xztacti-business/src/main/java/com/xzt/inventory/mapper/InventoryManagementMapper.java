package com.xzt.inventory.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InventoryManagementMapper extends BaseMapper<InventoryManagement> {
    List<InventoryManagement> selectByParam(@Param(Constants.WRAPPER) QueryWrapper<InventoryManagementSelectVO> queryWrapper);
}
