package com.ruoyi.system.domain.crk;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "out_inventory")
public class OutInventory {

    private Integer id;

    private Long peopleId;

    private String carInformation;

    private Integer carNum;

    private String time;
}
