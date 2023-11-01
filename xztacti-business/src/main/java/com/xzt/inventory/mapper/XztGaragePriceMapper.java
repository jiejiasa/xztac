package com.xzt.inventory.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xzt.inventory.domain.XztGaragePrice;
import com.xzt.inventory.rvo.GaragePiceListRVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface XztGaragePriceMapper extends BaseMapper<XztGaragePrice> {
    List<GaragePiceListRVO> geList(@Param(Constants.WRAPPER) QueryWrapper<XztGaragePrice> queryWrapper);
}
