package com.xzt.inventory.importPackage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InventoryManagementOut implements Serializable {



    @JsonProperty(value = "客户名称")
    private String customerName;

    @JsonProperty(value = "地区")
    private String region;

    @JsonProperty(value = "业务类型")
    private String businessType;

    @JsonProperty(value = "车牌号")
    private String licensPlateNumber;

    @JsonProperty(value = "审核状态")
    private String status;

    @JsonProperty(value = "入库日期")
    private String inboundDate;

    @JsonProperty(value = "清收团队")
    private String clearanceTeam;

    @JsonProperty(value = "清收费用")
    private String pickUpFee;

    @JsonProperty(value = "是否支付")
    private String settleStatus;

    @JsonProperty(value = "出库日期")
    private String outDate;

    @JsonProperty(value = "在库天数")
    private Integer inDay;

    @JsonProperty(value = "停车费用")
    private Integer inDayMony;


    @JsonProperty(value = "入库人")
    private String nickName;



    @JsonProperty(value = "停车费是否支付")
    private String paid;




    @JsonProperty(value = "停放车库")
    private String garageTypeOrName;


    @JsonProperty(value = "车钥匙是否正常")
    private String isNormal;

}
