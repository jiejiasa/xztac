package com.xzt.inventory.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xzt.inventory.domain.InInventory;
import com.xzt.inventory.rvo.InInventoryRVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InInventoryManagementMapper extends BaseMapper<InInventory> {
    List<InInventoryRVO> selectByParam(@Param(Constants.WRAPPER) QueryWrapper<InInventory> queryWrapper);
}
