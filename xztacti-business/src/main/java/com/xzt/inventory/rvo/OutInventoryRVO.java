package com.xzt.inventory.rvo;

import lombok.Data;

@Data
public class OutInventoryRVO {

    private Integer id;

    private Integer inManId;

    private String outboundReason;

    private Integer parkingFees;

    private Integer paid;

    private String createTime;

    private String vehicleRecipient;

    private String nickName;

    private Integer status;

    private String customerName;



    private String outDate;



}
