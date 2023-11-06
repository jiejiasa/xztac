package com.xzt.inventory.rvo;

import lombok.Data;

@Data
public class PriceAllInfoRVO extends GoOutInventoryRVO{



    private String outboundReason;

    private String parkingFees;

    private Integer paid;


    private String outDate;

    private String vehicleRecipient;


    private String outRemark;





    private Integer firstPeopleId;


    private Integer twoPeopleId;






}
