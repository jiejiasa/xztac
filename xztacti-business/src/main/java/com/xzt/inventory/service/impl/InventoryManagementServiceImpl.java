package com.xzt.inventory.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.common.utils.SecurityUtils;
import com.xzt.inventory.domain.InInventory;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.mapper.InventoryManagementMapper;
import com.xzt.inventory.service.InInventoryService;
import com.xzt.inventory.service.InventoryManagementService;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import com.xzt.inventory.vo.InventoryManagementUpdateVO;
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


    /**
     * 库存信息列表
     * @param vo
     * @return
     */
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

    /**
     * 查询库存详细信息
     * @param id
     * @return
     */
    @Override
    public AjaxResult getInventoryInfo(Integer id) {

        InventoryManagement inventoryManagement = inventoryManagementMapper.selectById(id);
        return AjaxResult.success(inventoryManagement);
    }

    @Override
    public AjaxResult delInventory(Integer id) {

        return AjaxResult.success(inventoryManagementMapper.deleteById(id));
    }


}
