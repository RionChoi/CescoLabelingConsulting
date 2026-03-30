package com.cesco.hc.calculate_hc.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalculateMgtDto {

    /**
     * 정산관리
     */
    // param
    private String p_type;       // 검색 type     ok 
    private String sys_id;       // 시스템id      ok 
    private String fs_no;        // 컨설팅번호
    private String fs_seq;       // 컨설팅번호순번
    private String start_date;   // 검색 시작일자 ok
    private String end_date;     // 검색 종료일자 ok 
    private String cst_id;       // 고객사        ok
    private String tax_bill_id;  // 정산담당자    ok
    private String fs_bill_yn;   // 정산여부      ok
    private String ssmm;         // 비고

    // 정다원 추가
    private String fs_status_cd;  // 진행단계
    private String fs_mng_id;     // 세스코 담당자 
    private String fs_prod_cd;    // 상품명

    List<CalculateDto> calculateList;
    CalculateBillDto calculateBillDto;

}
