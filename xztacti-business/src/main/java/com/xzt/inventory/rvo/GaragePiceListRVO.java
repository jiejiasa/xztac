package com.xzt.inventory.rvo;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GaragePiceListRVO {


    private Integer id;

    private Integer pId;

    private String garageTypeOrName;

    private Integer dayOrMonthPrice;

    private Integer fixedPrice;

    private Integer createrId;

    private String createTime;

    private Integer delFlag;

    private List<GaragePiceListRVO> chileds = new ArrayList<>();
}
