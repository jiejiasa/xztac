package com.xzt.inventory.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "xzt_garage_price")
public class XztGaragePrice {


    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer pId;

    private String garageTypeOrName;

    private Integer dayOrMonthPrice;

    private Integer fixedPrice;

    private Integer createrId;

    private String createTime;

    private Integer delFlag;



}
