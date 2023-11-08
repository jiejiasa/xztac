package com.xzt.inventory.vo;

import com.xzt.inventory.common.PageUtil;
import lombok.Data;

import java.util.List;

@Data
public class InventoryManagementSelectVO extends PageUtil {




    private String customerName;

    private String region;

    private String businessType;


    private List<String> inboundDates;


    private String clearanceTeam;

    private Integer settleStatus;


    private Integer status;


    private List<String> outDates;


    private Integer paid;



}
