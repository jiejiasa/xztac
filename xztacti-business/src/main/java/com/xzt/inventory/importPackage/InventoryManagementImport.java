package com.xzt.inventory.importPackage;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class InventoryManagementImport {

    @ExcelProperty("客户名称")
    private String customerName;

    @ExcelProperty("地区")
    private String region;

    @ExcelProperty("业务类型")
    private String businessType;

    @ExcelProperty("车牌号")
    private String licensPlateNumber;

    @ExcelProperty("入库日期")
    private String inboundDate;

    @ExcelProperty("清收团队")
    private String clearanceTeam;


    @ExcelProperty("收车费用")
    private String pickUpFee;


    @ExcelProperty("是否支付")
    private String settleStatusName;


    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("停放车库")
    private String parkingGarageName;


    @ExcelProperty("品牌型号")
    private String makeAndModel;


    @ExcelProperty("车钥匙是否好用")
    private String isNormalName;
}
