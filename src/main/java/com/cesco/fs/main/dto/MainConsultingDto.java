package com.cesco.fs.main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainConsultingDto {

    private String sys_id;           // 시스템id
    private String fs_no;            // 컨설팅번호
    private String fs_seq;           // 컨선팅번호순번
    private String fs_sub_seq;       // 
    private String reg_channel;      // 의뢰등록구분FS012
    private String cst_id;           // 고객사번호
    private String cst_nm;           // 고객사명
    private String prod_str_nm;      // 설탕/발효식품
    private String receive_date;     // 접수일시
    private String issue_date;       // 컨설팅확인등록
    private String close_date;       // 최종종료일시
    private String bill_cst_id;      // 정산고객아이디
    private String fs_bill_yn;       // 세금계산서 발행여부
    private String fs_status_cd;     // 진행상태코드 FS004
    private String mem_cnt;          // CHAT 인원
    private String fs_mng_id;        // 세스코담당자
    private String fs_mng_nm;        // 세스코담당자명
    private String prod_cd;          // 식품코드
    private String prod_nm;          // 상품명(제품명)
    private String prod_tp_cd;       // 식품유형 FS001
    private String prod_tp_cust_nm;  // 식품유형명
    private String prod_item;        // 품목보고번호
    private String prod_mafa_nm;     // 제조업체명
    private String prod_ser_cd;      // 서비스구분FS005
    private String prod_qty_nm;      // 
    private String prod_unit_cd;     // 단가구분FS002 (신규,리뉴얼)
    private String status;           // 상태
    private String rgstr_id;         // 
    private String rgstr_dt;         // 
    private String mdfr_id;          // 
    private String mdfr_dt;          // 
    private String ssmm;             // 
    private String cst_mng_id;       // 고객담당자
    private String cst_mng_nm;       // 고객담당자명
    
}
