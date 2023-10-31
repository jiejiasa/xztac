package com.xzt.inventory.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "in_inventory")
public class InInventory {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Long peopleId;

    private String carInformation;


    private String inventoryCode;
    private Integer carNum;

    private String time;


    private Integer inMaId;
}
