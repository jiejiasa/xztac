package com.xzt.inventory.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.DeployActiviti;
import com.xzt.inventory.domain.InInventory;
import com.xzt.inventory.mapper.InInventoryManagementMapper;
import com.xzt.inventory.service.InInventoryService;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InInventoryServiceImpl extends ServiceImpl<InInventoryManagementMapper, InInventory> implements InInventoryService {


    @Resource
    private DeployActiviti deployActiviti;


    /**
     * 查询入库记录
     * @param vo
     * @return
     */
    @Override
    public PageInfo<InInventory> selectList(InventoryManagementSelectVO vo) {

        QueryWrapper<InInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("time");
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<InInventory> inInventories = baseMapper.selectList(queryWrapper);
        PageInfo<InInventory> inInventoryPageInfo = new PageInfo<>(inInventories);
        return inInventoryPageInfo;
    }
}
