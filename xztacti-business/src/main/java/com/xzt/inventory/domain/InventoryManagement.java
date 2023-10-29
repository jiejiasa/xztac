package com.xzt.inventory.domain;


import com.baomidou.mybatisplus.annotation.IdType;
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


    private Long peopleId;


    private String time;

    private Integer delFlag;


    private String customerName;

    private String region;


    private String businessType;

    private String License_plateNumber;

    private Integer status;


}
