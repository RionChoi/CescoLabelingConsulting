package com.cesco.hc.consultMgt_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultMgtHcMdayDTO {

    private String sys_id;                  //  시스템구분
    private String fs_no;                   //  컨설팅번호
    private String md_day;                  //  컨설팅MD
    private String md_yn;                   //  수행여부
    private String md_date;                 //  수행일자
    private String con_text11;              //  컨설팅내역
    private String con_text12;              //  컨설팅내역
    private String con_text21;              //  컨설팅내역
    private String con_text22;              //  컨설팅내역
    private String con_text31;              //  컨설팅내역
    private String con_text32;              //  컨설팅내역
    private String con_text41;              //  컨설팅내역
    private String con_text42;              //  컨설팅내역
    private String cst_mng_id;              //  고객사담당자
    private String cst_mng_sig_yn;          //  
    private String cst_mng_sig_date;        //  
    private String fs_mng_id;               //  세스코담당자
    private String fs_mng_sig_yn;           //  
    private String fs_mng_sig_date;         //  
    private String atch_nm;                 //  
    private String atch_size;               //  
    private String atch_ref_id;             //  
    private String url;                     //  
    private String org_file_nm;             //  
    private String ser_file_nm;             //  
    private String status;                  //  상태
    private String rgstr_id;                //  
    private String rgstr_dt;                //  
    private String mdfr_id;                 //  
    private String mdfr_dt;                 //  
    private String ssmm;                    //

    public ConsultMgtHcMdayDTO(String sys_id, String fs_no, String md_day, String cst_mng_id, String fs_mng_id
                                , String atch_nm, String url, String org_file_nm, String ser_file_nm, String status
                                , String rgstr_id, String mdfr_id){
        this.sys_id         = sys_id;
        this.fs_no          = fs_no;
        this.md_day         = md_day;
        this.cst_mng_id     = cst_mng_id;
        this.fs_mng_id      = fs_mng_id;
        this.atch_nm        = atch_nm;
        this.url            = url;
        this.org_file_nm    = org_file_nm;
        this.ser_file_nm    = ser_file_nm;
        this.status         = status;
        this.rgstr_id       = rgstr_id;
        this.mdfr_id        = mdfr_id;
    }
    
}
