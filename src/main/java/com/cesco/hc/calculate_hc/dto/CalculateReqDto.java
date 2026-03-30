package com.cesco.hc.calculate_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateReqDto {
 
    /**
     * 정산 param / jdw
     */
    private String sys_id;              // 시스템id
    private String fs_no;               // 컨설팅번호
    private String fs_seq;              // 컨설팅번호순번
    private String est_seq;             // 견적단가 SEQ
    private String cst_id;              // 고객사id
    private String cst_nm;              // 고객사명
    private String status;
    private String est_use_yn;
    private String rgstr_id;
    private String mdfr_id;
    private String est_chg_id;
    private String prod_unit_cd;
    private String prod_unit_nm;
    private String prod_nm;
    private String prod_tp_cust_nm;
    private String est_amt;
    private String est_ssmm;

    
}
