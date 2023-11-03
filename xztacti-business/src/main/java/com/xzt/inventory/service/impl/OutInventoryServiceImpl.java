package com.xzt.inventory.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.mapper.OutInventoryMapper;
import com.xzt.inventory.rvo.OutInventoryRVO;
import com.xzt.inventory.service.OutInventoryService;
import com.xzt.inventory.vo.GoOutInfo;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import org.springframework.stereotype.Service;

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
        queryWrapper.eq("o.del_flag",0);

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
