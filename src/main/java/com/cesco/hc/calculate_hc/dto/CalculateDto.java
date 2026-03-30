package com.cesco.hc.calculate_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateDto {
    
    /**
     * 정산 /  jdw
     */
    private String sys_id;              // 시스템id
    private String cst_id;              // 고객사id
    private String cst_nm;              // 고객사명
    private String fs_no;               // 컨설팅번호
    private String fs_seq;              // 컨설팅번호순번

    private String prod_cd;             // + 상품코드
    private String prod_nm;             // 제품명 -> 상품명
    private String prod_tp_nm;          // 식품유형 -> 상품유형명

    private String corp_nm;             // + 참여자명
    private String cst_mng_id;          // 고객담당자
    private String cst_mng_nm;          // 고객담당자명
    private String cst_mng_mobile;      // 담당자연락처

    private String bill_cst_id;         // 정산거래처 :  신청시 등록시 넣는다.
    private String bill_cst_nm;         // 정산거래처명
    private String receive_date;        // 접수일자

    private String tax_bill_id;         // 정산담당자
    private String tax_bill_nm;         // 정산담당자명
    private String tax_bill_email;      // 정산담당자이메일
    private String fs_bill_item;        // + 세금계산서 발행여부/청구번호

    private String fs_mng_id;           // 세스코담당자
    private String cst_cesco_mng_nm;    // + 세스코담당자명

    private String md_day;              // + 계약일수
    private String mw_day;              // + 진행일수

    private String fs_status_cd;        // 진행상태
    private String fs_status_nm;        // 진행상태명

    private String mem_cnt;             // 참여자수

    private int est_amt;                // 견적금액
    private int bill_ar_amt;            // + 납입금액(수금금액)
    private int bill_amt;               // + 청구금액
    private int bill_net_amt;           // + 미청구금액
    private String mdfr_dt;             // 수정일시

    private String p_type;


    // private String sys_id;              // 시스템id
    // private String fs_no;               // 컨설팅번호
    // private String fs_seq;              // 컨설팅번호순번
    // private String est_seq;             // 견적단가 SEQ
    // private String cst_id;              // 고객사id
    // private String cst_nm;              // 고객사명
    // private String prod_nm;             // 제품명 
    // private String prod_str_nm;         // 설탕/발효식품
    // private String prod_nm1;            // 설탕/발효식품
    // private String prod_nm2;            // 설탕/발효식품
    // private String prod_tp_cd;          // 
    // private String prod_tp_cust_nm;     // 식품유형
    // private String prod_item;           // 품목번호
    // private String prod_qty_nm;         // 내용물
    // private String cst_mng_id;          // 고객담당자
    // private String cst_mng_nm;          // 고객담당자명
    // private String cst_mng_mobile;      // 담당자연락처
    // private String bill_cst_id;         // 정산거래처 :  신청시 등록시 넣는다.
    // private String bill_cst_nm;         // 정산거래처명
    // private String receive_date;        // 접수일자
    // private String prod_ser_cd;         // 정기 비정정기 서비스구분  
    // private String prod_ser_nm;         // 서비스구분명
    // private String prod_unit_cd;        // 단가구분(의뢰구분)
    // private String prod_unit_nm;        // 의뢰구분
    // private String tax_bill_id;         // 정산담당자
    // private String tax_bill_nm;         // 정산담당자명
    // private String tax_bill_email;      // 정산담당자이메일
    // private String tax_bill_tp_cd;      // 세금계산서 발행구분
    // private String fs_mng_id;           // 세스코담당자
    // private String fs_mng_nm;           // 세스코담당자명
    // private String mdfr_id;             // 수정일시
    // private String mdfr_dt;             // 수정일시
    // private String ssmm;                // 수정일시
    // private String fs_bill_yn;          // 세금계산서 발행구분(정산여부)
    // private String fs_bill_no;          // 청구번호
    // private String est_tp_cd;           // 견적금액구분
    // private String est_tp_nm;           // 견적금액구분명
    // private int est_amt;                // 견적금액
    // private int est_samt;               // 견적금액합
    // private String fs_bill_nm;          // 청구명
    // private String tax_bill_make_date;  // 견적서 생성일자
    // private String est_mdfr_id;         // 견적수정자
    // private String est_mdfr_nm;         // 견적수정자명
    // private String est_mdfr_dt;         // 견적수정일자
    // private String est_ssmm;            // 견적SSMM
    // private String fs_status_cd;        // 진행상태
    // private String fs_status_nm;        // 진행상태명
    // private String mem_cnt;             // 참여자수

    // private String p_type;


}
