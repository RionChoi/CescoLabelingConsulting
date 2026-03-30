package com.cesco.hc.consultMgt_hc.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConCompleteDto {
    
    private String sys_id; //시스템구분
    private String fs_no; //컨설팅 번호
    private String cst_nm; //업체명
    private String con_prod; //컨설팅 분야
    private String con_com_nm; //컨설팅 기관
    private String con_start_date;
    private String con_end_date;
    private String con_text11;
    private String con_text12;
    private String con_text21;
    private String con_text22;
    private String con_text31;
    private String con_text32;
    private String con_text41;
    private String con_text42;
    private String con_text51;
    private String con_text52;
    private String con_text61;
    private String con_text62;
    private String con_text71;
    private String con_text72;
    private String con_text81;
    private String con_text82;
    private String con_text91;
    private String con_text92;
    private String con_text101;
    private String con_text102;
    private String con_complet_date; //완료일
    private String cst_mng_id;
    private String cst_mng_nm; //고객사 담당자
    private String cst_mng_sig_yn;
    private String cst_mng_sig_date;
    private String fs_mng_id;
    private String fs_mng_nm; //담당 컨설턴트
    private String fs_mng_sig_yn;
    private String fs_mng_sig_date;
    private String status;
    private String rgstr_id;
    private String rgstr_dt;
    private String mdfr_id;
    private String mdfr_dt;
    private String ssmm;
    private String write_auth; //권한부여
}
