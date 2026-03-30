package com.cesco.fs.calculate.dto;

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
    private String p_type;       // 검색 type
    private String sys_id;       // 시스템id
    private String fs_no;        // 컨설팅번호
    private String fs_seq;       // 컨설팅번호순번
    private String start_date;   // 검색 시작일자
    private String end_date;     // 검색 종료일자
    private String cst_id;       // 고객사
    private String tax_bill_id;  // 정산담당자
    private String fs_bill_yn;   // 정산여부
    private String ssmm;         // 비고
    private String cont_id;      // 계약담당자
    private String user_id;

    List<CalculateDto> calculateList;
    CalculateBillDto calculateBillDto;
}
