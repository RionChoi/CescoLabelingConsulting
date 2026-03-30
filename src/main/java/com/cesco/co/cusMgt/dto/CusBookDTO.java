package com.cesco.co.cusMgt.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CusBookDTO {

    private String sys_id;
    private Integer book_seq;       // 순번
    private String book_tp_cd;      // 즐겨찾기구분 01 고객사즐겨찾기 02 협력사즐겨찾기FS014
    private String fs_mng_id;       // 사용자 ID
    private String cst_id;          // 고객사 ID
    private String cst_ref_id;      // 
    private String book_nm;         // 즐겨찾기 명
    private String status;          // 상태
    private String rgstr_id;
    private Date rgstr_dt;
    private String mdfr_id;
    private Date mdfr_dt;
    private String ssmm;

    public CusBookDTO(String sys_id, Integer book_seq, String book_tp_cd, String fs_mng_id,
                String cst_id, String cst_ref_id, String book_nm, String status, String rgstr_id,
                Date rgstr_dt, String mdfr_id, Date mdfr_dt, String ssmm){

        this.sys_id         = sys_id;
        this.book_seq       = book_seq;
        this.book_tp_cd     = book_tp_cd;
        this.fs_mng_id      = fs_mng_id;
        this.cst_id         = cst_id;
        this.cst_ref_id     = cst_ref_id;
        this.book_nm        = book_nm;
        this.status         = status;
        this.rgstr_id       = rgstr_id;
        this.rgstr_dt       = rgstr_dt;
        this.mdfr_id        = mdfr_id;
        this.mdfr_dt        = mdfr_dt;
        this.ssmm           = ssmm;
                        
    }
    
}
