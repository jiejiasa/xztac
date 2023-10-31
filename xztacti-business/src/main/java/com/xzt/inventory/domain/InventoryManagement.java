package com.xzt.inventory.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "inventory_management")
public class InventoryManagement {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String inventoryCode;

    private String carInformation;

    private Integer carNum;

    private Long creatorId;

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

    private String parkingGarage;

    private String makeAndModel;

    @TableField(exist = false)
    private Integer inDay;

    @TableField(exist = false)
    private String inDayMony;

}
