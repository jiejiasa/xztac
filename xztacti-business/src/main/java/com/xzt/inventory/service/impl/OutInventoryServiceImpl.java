package com.xzt.inventory.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.common.utils.StringUtils;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.mapper.OutInventoryMapper;
import com.xzt.inventory.rvo.OutInventoryRVO;
import com.xzt.inventory.service.OutInventoryService;
import com.xzt.inventory.vo.GoOutInfo;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OutInventoryServiceImpl extends ServiceImpl<OutInventoryMapper, OutInventory> implements OutInventoryService {


    @Resource
    private OutInventoryMapper outInventoryMapper;


    /**
     * 查询出库记录
     * @param vo
     * @return
     */
    @Override
    public PageInfo<OutInventoryRVO> selectList(InventoryManagementSelectVO vo) {


        QueryWrapper<OutInventory> queryWrapper = new QueryWrapper<>();

        if (!"null".equals(vo.getPaid()) && !ObjectUtils.isEmpty(vo.getPaid()))
            queryWrapper.eq("o.paid",vo.getPaid());
        if (!"null".equals(vo.getSettleStatus()) && !ObjectUtils.isEmpty(vo.getPaid()))
            queryWrapper.eq("im.settle_status",vo.getSettleStatus());
        if (!"null".equals(vo.getCustomerName()) && StringUtils.isNotBlank(vo.getCustomerName()) && !vo.getCustomerName().isEmpty())
            queryWrapper.like("im.customer_name",vo.getCustomerName());
        if (!ObjectUtils.isEmpty(vo.getOutDates()))
            queryWrapper.ge("o.out_date",vo.getOutDates().get(0)).le("o.out_date",vo.getOutDates().get(1));
        queryWrapper.eq("o.del_flag",0).orderByDesc("o.create_time");

        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<OutInventoryRVO> outInventories = outInventoryMapper.selectByParam(queryWrapper);
        return new PageInfo<>(outInventories);
    }

    @Override
    public Boolean updatePriceStatus(GoOutInfo goOutInfo) {

        UpdateWrapper<OutInventory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("in_man_id",goOutInfo.getId())
                .set("out_remark",goOutInfo.getOutRemark())
                .set("paid",goOutInfo.getPaid());

        outInventoryMapper.update(null, updateWrapper);
        return true;
    }

















}
