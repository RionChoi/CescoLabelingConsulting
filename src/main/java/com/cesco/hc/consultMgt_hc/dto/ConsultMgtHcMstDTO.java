package com.cesco.hc.consultMgt_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultMgtHcMstDTO implements Cloneable {
    
    private String sys_id;
    private String fs_no;               // 컨설팅번호
    private Integer fs_seq;              // 컨선팅번호순번
    private String reg_channel;         // 의뢰등록구분FS012
    private String cst_id;              // 고객사번호
    private String receive_date;        // 접수일시
    private String issue_date;          // 컨설팅확인등록
    private String close_date;          // 최종종료일시
    private String bill_cst_id;         // 정산고객아이디
    private String fs_mng_id;           // 세스코담당자
    private String prod_cd;             // 상품코드
    private String prod_nm;             // 상품명(제품명)
    private String prod_tp_cd;          // 식품유형 FS001
    private String prod_tp_cust_nm;     // 고객입력식품유형명
    private String prod_item;           // 품목보고번호
    private String prod_mafa_nm;        // 제조업체명
    private String prod_ser_cd;         // 서비스구분FS005
    private String prod_qty_nm;         // 내용물
    private String prod_unit_cd;        // 단가구분FS002
    private String status;              // 상태
    private String rgstr_id;
    private String rgstr_dt;
    private String mdfr_id;
    private String mdfr_dt;
    private String ssmm;
    private String cst_mng_id;          // 담당자명
    private String tax_bill_id;         // 세금계산서수취인
    private String tax_bill_email;      // 세금계산서수취인이메일
    private String tax_bill_tp_cd;      // 세금계산서발행구분 FS006
    private Integer md_day;              // 햅섭사용용 MD DAY수
    private String cont_no;             // 계약번호
    private String cont_id;             // 계약자ID

    private Integer est_amt1;           // 계약금
    private Integer est_amt2;           // 중도금
    private Integer est_amt3;           // 잔금

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
