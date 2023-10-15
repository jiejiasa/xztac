package com.xzt.system.service.crk.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzt.system.domain.crk.OutInventory;
import com.xzt.system.mapper.crk.OutInventoryMapper;
import com.xzt.system.service.crk.OutInventoryService;
import org.springframework.stereotype.Service;

@Service
public class OutInventoryServiceImpl extends ServiceImpl<OutInventoryMapper, OutInventory> implements OutInventoryService {
}
