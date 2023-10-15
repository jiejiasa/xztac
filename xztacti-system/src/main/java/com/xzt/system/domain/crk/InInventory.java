package com.xzt.system.domain.crk;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "in_inventory")
public class InInventory {

    private Integer id;

    private Long peopleId;

    private String carInformation;


    private String inventoryCode;
    private Integer carNum;

    private String time;
}
