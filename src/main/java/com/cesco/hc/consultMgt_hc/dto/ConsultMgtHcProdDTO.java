package com.cesco.hc.consultMgt_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultMgtHcProdDTO {

    private String sys_id;              // 시스템id
    private String fs_no;               // 컨설팅번호
    private String fs_seq;              // 컨설팅번호순번
    private String cst_id;              // 고객사id
    private String cst_nm;              // 고객사명		
    private String prod_cd;             // 상품코드 
    private String prod_nm;             // 상품명
    private String prod_tp_cd;          // 상품유형
    private String prod_tp_nm;          // 상품유형명
    private String receive_date;        // 접수일자
    private String est_amt1;            // 선금
    private String est_amt2;            // 중도금 
    private String est_amt3;            // 잔금
    private String cont_no;             // 계약번호
    private String cont_id;             // 계약자
    private String cst_mng_id;          // 주 담당자
    private String fs_mng_id;           // 세스코 주 담당자
    private String md_day;              // 계약 MD
    private String reg_channel;         // 의뢰 경로 01: 고객 02:관리자
    // "bill_cst_id": "", 
    
}