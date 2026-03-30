package com.cesco.fs.serviceStatement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceStatementDtlDto {

    /**
     * 서비스견적서 상세
     */
    private String sys_id;                // 시스템id
    private String fs_no;                 // 컨설팅번호
    private String fs_seq;                // 컨설팅번호순번
    private String est_seq;               // 
    private String fs_bill_no;            // 청구번호
    private String cst_id;                // 고객사
    private String cst_nm;                // 고객사명
    private String receive_date;          // 접수일자
    private String bill_cst_id;           // 정산거래처
    private String bill_cst_nm;           // 정산거래처명
    private String fs_mng_id;             // 세스코담당자
    private String fs_mng_nm;             // 세스코담당자명
    private String prod_cd;               // 제품
    private String prod_nm;               // 제품명
    private String prod_tp_cd;            // 내용물
    private String prod_tp_cust_nm;       // 
    private String prod_item;             // 품목번호
    private String prod_ser_cd;           // 정기 비정정기 서비스구분
    private String prod_qty_nm;           // 내용물
    private String prod_unit_cd;          // 견적금액구분
    private String prod_unit_nm;          // 견적금액구분명
    private String prod_mafa_nm;          // 제조업체명
    private String status;                // 상태
    private String ssmm;                  // 비고
    private String cst_mng_id;            // 고객담당자
    private String cst_mng_nm;            // 고객담당자명
    private String cst_mng_mobile;        // 담당자연락처
    private String fs_bill_yn;            // 세금계산서 발행구분(정산여부)
    private String est_tp_cd;             // 견적금액구분
    private String est_tp_nm;             // 견적금액구분명
    private String est_amt;               // 견적금액
    private String est_mdfr_id;           // 견적수정자
    private String est_mdfr_nm;           // 견적수정자명
    private String est_mdfr_dt;           // 견적수정일자
    private String est_ssmm;              // 견적ssmm
    private String fs_status_cd;          // 진행상태
    private String fs_status_nm;          // 진행상태명
    private String mem_cnt;               // 참여자 수
    private String mdfr_dt;               // 최종처리일시

    private String fs_bill_seq;
    private String fs_bill_nm;
  
}
