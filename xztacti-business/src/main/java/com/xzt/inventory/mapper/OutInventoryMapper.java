package com.xzt.inventory.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.rvo.OutInventoryRVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutInventoryMapper extends BaseMapper<OutInventory> {
    List<OutInventoryRVO> selectByParam(@Param(Constants.WRAPPER) QueryWrapper<OutInventory> objectQueryWrapper);
}
