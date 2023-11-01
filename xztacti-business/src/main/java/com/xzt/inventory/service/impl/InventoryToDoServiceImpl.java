package com.xzt.inventory.service.impl;

import com.github.pagehelper.PageInfo;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.mapper.InventoryManagementMapper;
import com.xzt.inventory.service.InventoryManagementService;
import com.xzt.inventory.service.InventoryToDoService;
import com.xzt.service.IProcessService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class InventoryToDoServiceImpl implements InventoryToDoService {


    @Resource
    private IProcessService processService;

    @Resource
    private InventoryManagementService inventoryManagementService;

    @Resource
    private InventoryManagementMapper inventoryManagementMapper;

    @Override
    public PageInfo<InventoryManagement> getToDoInventory() {

        List<String> strings = processService.selectAuditTodoList();
        List<Integer> collect = strings.stream().map(Integer::valueOf).collect(Collectors.toList());
        if(ObjectUtils.isEmpty(collect)){
            return new PageInfo<>();
        }
        List<InventoryManagement> inventoryManagements = inventoryManagementMapper.selectBatchIds(collect);

        PageInfo<InventoryManagement> inventoryManagementPageInfo = new PageInfo<>();
        inventoryManagementPageInfo.setTotal(collect.size());
        inventoryManagementPageInfo.setList(inventoryManagements);
        return inventoryManagementPageInfo;
    }
}
