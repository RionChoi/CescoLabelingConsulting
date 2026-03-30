package com.cesco.hc.main_hc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRecordDto {
   
    private String sys_id;
    private String fs_no;
    private String fs_seq;
    private String cst_id;
    private String cst_mng_nm;
    private String con_prod;
    private String cst_nm;
    private String md_day;
    private String md_date;
    private String con_text11;
    private String con_text12;
    private String con_text21;
    private String con_text22;
    private String con_text31;
    private String cst_mng_sig_date;
    private String user_tp_cd;
    private String contents; //메일
    private String cst_mng_sign;
    private String fs_mng_sign;
    private String user_id;
    private String fs_mng_nm2;
    private String cst_mng_nm2;
    private String fs_mng_id;
}
