package com.xzt.inventory.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "inventory_management")
public class InventoryManagement {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String time;

    private Integer delFlag;

    private String customerName;

    private String region;

    private String businessType;

    private String licensPlateNumber;

    private Integer status;

    private String inboundDate;

    private String clearanceTeam;

    private String pickUpFee;

    private Integer settleStatus;

    private String remark;

    private Integer parkingGarage;

    private String makeAndModel;

    private Integer isNormal;

    private Long creatorId;

    @TableField(exist = false)
    private Integer inDay;

    @TableField(exist = false)
    private Integer inDayMony;


    @TableField(exist = false)
    private String nickName;


    @TableField(exist = false)
    private Integer pId;

    @TableField(exist = false)
    private Integer dayOrMonthPrice;

    @TableField(exist = false)
    private Integer fixedPrice;

    @TableField(exist = false)
    private List<XztGaragePrice> garagePriceList;

}
