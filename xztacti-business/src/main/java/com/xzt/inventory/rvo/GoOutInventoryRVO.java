package com.xzt.inventory.rvo;


import com.xzt.common.core.domain.entity.SysUser;
import com.xzt.inventory.domain.InventoryManagement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoOutInventoryRVO {


    private InventoryManagement inventoryManagement;



    private List<SysUser> firstPeople = new ArrayList<>();


    private List<SysUser> twoPeople = new ArrayList<>();







}
