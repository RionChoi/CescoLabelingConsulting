package com.cesco.sys.comm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommDtlCodeDTO {    
    private String cmm_dtl_cd;
    private String cmm_dtl_nm;
    private String etc_cd1;
    private String etc_cd2;
    private String etc_cd3;
    private String etc_cd4;
    private String etc_cd5;
    private int etc_cd1_amt;
}
