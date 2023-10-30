package com.xzt.vo;


import lombok.Data;

@Data
public class HandleAuditParam {

    private Long auditId;
    private boolean passed;
    private String info;

}
