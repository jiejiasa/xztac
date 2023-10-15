package com.xzt.system.service.crk.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.system.domain.crk.InInventory;
import com.xzt.system.domain.vo.crk.InventoryManagementSelectVO;
import com.xzt.system.mapper.crk.InInventoryManagementMapper;
import com.xzt.system.service.crk.InInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InInventoryServiceImpl extends ServiceImpl<InInventoryManagementMapper, InInventory> implements InInventoryService {



    @Override
    public PageInfo<InInventory> selectList(InventoryManagementSelectVO vo) {

        QueryWrapper<InInventory> inInventoryQueryWrapper = new QueryWrapper<>();
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<InInventory> inInventories = baseMapper.selectList(inInventoryQueryWrapper);
        PageInfo<InInventory> inInventoryPageInfo = new PageInfo<>(inInventories);
        return inInventoryPageInfo;
    }
}
