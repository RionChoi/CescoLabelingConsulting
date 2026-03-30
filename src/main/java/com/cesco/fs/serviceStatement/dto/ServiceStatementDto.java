package com.cesco.fs.serviceStatement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceStatementDto {

    /**
     * 서비스견적서
     */
    private String p_type ;                      // 
    private String sys_id ;                      // 시스템구분
    private String fs_bill_no;                   // 서비스견적서번호
    private String fs_bill_seq;                  // 순번 
    private String fs_bill_nm;                   // 서비스견적서명
    private String est_cnt;                      // 총건수
    private String tax_bill_id;                  // 정산담당자
    private String tax_bill_nm;                  // 정산담당자명
    private String tax_email;                    // 정산담당자이메일
    private String tax_bill_mobile;              // 정산담당자연락처
    private String tax_bill_make_date;           // 작성일
    private String rgstr_id;                     // 작성자
    private String rgstr_nm;                     // 작성자명
    private String fs_mng_id;                    // 담당자
    private String fs_mng_nm;                    // 담당자명
    private String bill_cst_id;                  // 정산주체(회사)
    private String bill_cst_nm;                  // 정산주체(회사)명
    private String tax_bill_no;                  // 세금계산서번호
    private String tax_bill_date;                // 세금계산서발행일자
    private String supply_amt;                   // 총공급가액
    private String vat_amt;                      // 총부가세금액
    private String bill_req_cd;                  // 비용확인요청코드
    private String bill_req_nm;                  // 비용확인요청명(비용확인요청코드)FS009
    private String bill_req_date;                // 비용확인요청일자
    private String bill_rep_date;                // 비용확인일자
	private String bill_mdify_note;		         // 비용수정요청내용
	private String bill_mdify_date;		         // 비용수정요청일자
    private String bill_ar_cd;                   // 입금확인
    private String bill_ar_nm;                   // 입금확인명(대금수금코드)FS007
    private String ssmm;                         // 비고
    private String rgstr_dt;                     // 
    private String mdfr_id;                      // 
    private String mdfr_dt;                      // 
    private String mdfr_nm;                      // 
    private String cont_id;                      // 계약담당자
    private String cont_nm;                      // 계약담당자명

}
