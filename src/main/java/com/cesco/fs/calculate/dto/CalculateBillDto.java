package com.cesco.fs.calculate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateBillDto {

    /**
     * 서비스견적서 생성
     */
    private String sys_id;                  // 시스템id
    private String fs_bill_no;              // 청구번호
    private String fs_bill_seq;             // 청구순번
    private String fs_bill_nm;              // 청구명(2022년01월 + 고객사명 + '서비스내역서')
    private String bill_cst_id;             // 정산고객ID
    private String bill_req_cd;             // 비용확인요청코드 01 확인요청전 02 확인요청  02 확인완료 FS009
    private String bill_req_date;           // 비용확인요청일자 NULL
    private String tax_bill_make_date;      // 견적서 생성일자 GETDATE
    private int tax_bill_cnt;               // 세금계산서건수 CHECK건수
    private String tax_bill_id;             // 세금계산서수신자명 정산담당자
    private String tax_email;               // 세금계산서수신메일 정산담당자이메일
    private String tax_bill_date;           // 세금계산서발행일자 NULL
    private String tax_bill_tp_cd;          // 세금계산서발행구분 FS006  02 후발행
    private String tax_bill_no;             // 세금계산서번호 NULL
    private int supply_amt;                 // 부가세공급금액 견적단가금액 합
    private int vat_amt;                    // 부가세금액 견적단가금액 10% 원 절사
    private String bill_ar_cd;              // 대금수금코드  ''01미수 '' 02''수금  FS007 01
    private String status;                  // 상태
    private String rgstr_id;                // 등록자id
    private String rgstr_dt;                // 등록일
    private String mdfr_id;                 // 수정자id
    private String mdfr_dt;                 // 수정일
    private String ssmm;                    // 비고
    private String bill_rep_date;           // 비용확인일자 NULL

}
