package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultProdInfoDTO {
    
    private String fs_no;               // 생성 번호
    private Integer fs_seq;             // 제품 순번
    private Integer fs_sub_seq;         // 1 고정
    private String reg_channel;         // 01: 고객 02: 관리자
    private String cst_id;              // 고객사 ID
    private String bill_cst_id;         // 
    private String fs_mng_id;           // 세스코 담당자 ID
    private String cst_mng_id;          // 담당자 ID
    private String prod_nm;             // 요청시 등록한 유형이름 상품명
    private String prod_tp_cd;          // 관리자가 정의한 유형 
    private String prod_tp_cust_nm;     // 요청시 등록한 유형이름
    private String prod_item;           // 품목보고번호
    private String prod_ser_cd;         // 정기 비정정기 서비스구분
    private String prod_unit_cd;        // 단가구분(의뢰구분)
    private String prod_mafa_nm;        // 제조업체명
    private Integer est_amt;            // 견적단가
    private String rgstr_id;            // 등록자ID
    private String mdfr_id;             // 수정자ID
    private Integer est_amt1;            // 견적단가 amt1
    private String tax_bill_id;         // 정산 담당자 ID
    // private String tax_bill_email;      // 정산 담당자 Email
    private String tax_bill_tp_cd;      // 세금계산거 발행 구분
    private String cont_id;             // 계약자 ID
}
