package com.xzt.inventory.vo;

import lombok.Data;

@Data
public class GoOutInfo {


    private String id;

    private String firstPeopleId;


    private String twoPeopleId;


    private String outboundReason;

    private String parkingFees;


    private Integer paid;


    private String vehicleRecipient;


    private String outRemark;


    private String outDate;


    private String inDate;


    private Integer parkingGarage;


}
