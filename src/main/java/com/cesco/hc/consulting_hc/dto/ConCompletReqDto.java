package com.cesco.hc.consulting_hc.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConCompletReqDto {
    
    private String sys_id;
    private String cst_id;
    private String cst_nm;
    private String fs_no;
    private String fs_seq;
    private String prodnm;
    private String corpnm;
    private String cst_mng_id;
    private String cst_mng_nm;
    private String cst_mng_mobile;
    private String bill_cst_id;
    private String bill_cst_nm;
    private String receive_date;
    private String rod_ser_cd;
    private String prod_ser_nm;
    private String prod_unit_cd;
    private String prod_unit_nm;
    private String tax_bill_id;
    private String tax_bill_nm;
    private String tax_bill_email;
    private String est_tp_nm;
    private String fs_bill_item;
    private String fs_bill_no;
    private String fs_mng_id;
    private String cst_cesco_mng_nm;
    private String prod_qty_nm;
    private String fs_status_cd;
    private String fs_status_nm;
    private String mem_cnt;
    private String est_amt;
    private String mdfr_dt;
    private String md_day;
    private String complet_stt;
    private String complet_tp_cd;
    private String write_auth;
    private String fs_sign_image;
    private String fs_cst_image;
}
