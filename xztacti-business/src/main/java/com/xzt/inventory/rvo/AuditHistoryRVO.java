package com.xzt.inventory.rvo;

import com.xzt.rvo.AuditUserInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuditHistoryRVO extends PriceAllInfoRVO {

    private List<AuditUserInfo> auditUserInfoList = new ArrayList<>();

}
