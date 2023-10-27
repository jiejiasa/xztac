package com.xzt.inventory.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.mapper.OutInventoryMapper;
import com.xzt.inventory.service.OutInventoryService;
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
    public PageInfo<OutInventory> selectList(InventoryManagementSelectVO vo) {

        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<OutInventory> outInventories = outInventoryMapper.selectList(new QueryWrapper<>());
        PageInfo<OutInventory> outInventoryPageInfo = new PageInfo<>(outInventories);
        return outInventoryPageInfo;
    }
}
