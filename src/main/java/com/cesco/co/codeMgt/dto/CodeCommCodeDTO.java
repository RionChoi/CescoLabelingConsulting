package com.cesco.co.codeMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeCommCodeDTO {

    private String sys_id;      // 시스템 코드
    private String cmm_cd;      // 그룹 코드
    private String up_cmm_cd;   // 상위그룹코드
    private String ref_cmm_cd;  // 관계그룹코드
    private String cmm_nm;      // 그룹 코드명
    private String cmm_desc;    // 그룹코드설명
    private String status;      // 상태코드 'Y' 사용 'N' 미사용 
    private String rgstr_id;
    private String rgstr_dt;
    private String mdfr_id;
    private String mdfr_dt;
    
}
