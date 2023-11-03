package com.xzt.inventory.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "out_inventory")
public class OutInventory {

    private Integer id;
    private Integer inManId;

    private String outboundReason;

    private Integer parkingFees;

    private Integer paid;



    private String outDate;

    private String vehicleRecipient;


    private String outRemark;



    private Integer creatorId;

    private String createTime;

    private Integer delFlag;
}
