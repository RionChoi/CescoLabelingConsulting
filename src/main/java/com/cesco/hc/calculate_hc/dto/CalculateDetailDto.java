package com.cesco.hc.calculate_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateDetailDto {
    
    /* 정산 Detail Result List */ 

    private String sys_id;              // 시스템id
    private String fs_no;               // 컨설팅번호
    private String fs_seq;              // 컨설팅번호순번

    private String est_seq;             // 견적단가 SEQ
    private String est_tp_cd;           // 견적금액 구분
    private String est_tp_nm;           // 견적금액 구분명

    private String cst_id;              // 고객사id
    private String cst_nm;              // 고객사명

    private String prod_nm;             // 제품명 -> 상품명
    private String prod_nm2;            // 식품유형

    private String cst_mng_id;          // 고객담당자
    private String cst_mng_nm;          // 고객담당자명
    private String cst_mng_mobile;      // 담당자연락처

    private String bill_cst_id;         // 정산거래처 :  신청시 등록시 넣는다.
    private String bill_cst_nm;         // 정산거래처명
    private String receive_date;        // 접수일자

    private String tax_bill_id;         // 정산담당자
    private String tax_bill_nm;         // 정산담당자명
    private String tax_bill_email;      // 정산담당자이메일

    private String tax_bill_tp_cd;      // 세금계산서 발행구분
    private String tax_bill_tp_nm;      // 세금계산서 발행구분명

    private String fs_mng_id;           // 세스코담당자
    private String fs_mng_nm;           // 세스코담당자명

    private String mdfr_dt;             // 수정일시

    private String fs_bill_yn;          // 세금계산서 발행구분(정산여부)
    private String fs_bill_no;          // 쳥구번호 (서비스견적서 번호)

    private int est_amt;                // 견적금액
    private int est_samt;               // 견적금액합

    private String tax_bill_make_date;  // 정산내역 생성일자
    private String tax_bill_date;       // 계산서 발행일자
    private String tax_bill_no;         // 세금계산서 번호
    private String tax_bill_cnt;        // 세금계산서 개수

    private String p_type;


}
