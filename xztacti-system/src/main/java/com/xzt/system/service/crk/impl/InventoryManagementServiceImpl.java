package com.xzt.system.service.crk.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.common.utils.SecurityUtils;
import com.xzt.system.domain.crk.InInventory;
import com.xzt.system.domain.crk.InventoryManagement;
import com.xzt.system.domain.vo.crk.InventoryManagementSelectVO;
import com.xzt.system.domain.vo.crk.InventoryManagementUpdateVO;
import com.xzt.system.mapper.crk.InventoryManagementMapper;
import com.xzt.system.service.crk.InInventoryService;
import com.xzt.system.service.crk.InventoryManagementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InventoryManagementServiceImpl extends ServiceImpl<InventoryManagementMapper, InventoryManagement> implements InventoryManagementService {

    @Resource
    private InventoryManagementMapper inventoryManagementMapper;

    @Resource
    private InInventoryService inInventoryService;
    @Override
    public PageInfo<InventoryManagement> selectList(InventoryManagementSelectVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<InventoryManagement> inventoryManagementList = inventoryManagementMapper.selectByParam();
        PageInfo<InventoryManagement> inventoryManagementPageInfo = new PageInfo<>(inventoryManagementList);
        return inventoryManagementPageInfo;
    }

    @Override
    public Boolean addOrg(InventoryManagementUpdateVO vo) {

        InventoryManagement inventoryManagement = inventoryManagementMapper.selectById(vo.getId());
        inventoryManagement.setCarNum(inventoryManagement.getCarNum()+vo.getInNum());
        InInventory inInventory = new InInventory();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(new Date());
        inInventory.setTime( format);
        inInventory.setCarNum(vo.getInNum());
        inInventory.setCarInformation(inventoryManagement.getCarInformation());
        inInventory.setPeopleId(SecurityUtils.getUserId());
        inInventory.setInventoryCode(inventoryManagement.getInventoryCode());
        inInventoryService.save(inInventory);
        inventoryManagementMapper.updateById(inventoryManagement);
        return true;

    }


}
