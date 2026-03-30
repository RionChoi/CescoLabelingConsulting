package com.cesco.co.cusMgt.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CusInfoDTO {

    private String sys_id;          // 시스템 코드 : FS 표시 컨설팅
    private String cst_id;          // 고객ID
    private String cst_nm;          // 고객명
    private String cst_tp_cd;       // 고객구분FS008
    private String cst_mng_biz;     // 종 사업자 번호
    private String cst_biz_no;      // 사업자 번호
    private String cst_tel_no;      // 전화번호
    private String cst_fax_no;      // 팩스번호
    private String cst_email;       // 이메일
    private String cst_sys_id;      // ERP 고객 번호
    private String status;          // 상태
    private String rgstr_id;
    private String rgstr_nm;
    private Date rgstr_dt;
    private String mdfr_id;
    private String mdfr_nm;
    private Date mdfr_dt;
    private String ssmm;    
    private Integer book_seq;       // 즐겨찾기 등록 번호(미사용)
    
}
