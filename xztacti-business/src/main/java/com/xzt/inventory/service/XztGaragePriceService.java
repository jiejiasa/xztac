package com.xzt.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xzt.inventory.common.PageUtil;
import com.xzt.inventory.domain.XztGaragePrice;
import com.xzt.inventory.rvo.GaragePiceListRVO;

public interface XztGaragePriceService extends IService<XztGaragePrice> {
    PageInfo<GaragePiceListRVO> getList(PageUtil pageUtil);

    Boolean insert(XztGaragePrice xztGaragePrice);
}
