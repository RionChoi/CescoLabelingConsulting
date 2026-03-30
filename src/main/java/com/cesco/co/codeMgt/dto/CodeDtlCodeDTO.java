package com.cesco.co.codeMgt.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeDtlCodeDTO {    
    private String sys_id;      //
    private String cmm_cd;      // 그룹코드
    private String cmm_dtl_cd;  // 상세코드
    private Integer cmm_dtl_seq; // 상세코드순번
    private String cmm_dtl_nm;  // 명칭    
    private Integer srt;        // 정렬
    private String etc_cd1;     // 기타1
    private String etc_cd2;     // 기타2
    private String etc_cd3;     // 기타3
    private String etc_cd4;     // 기타4
    private String etc_cd5;     // 기타5
    private BigDecimal etc_cd1_amt;         // 기타1 가격
    private String rgstr_id;    // 등록자 ID
    private String rgstr_name;  // 등록자 이름
    private Date rgstr_dt;      // 등록일
    private String mdfr_id;     // 수정자 ID
    private String mdfr_name;   // 수정자 이름
    private Date mdfr_dt;       // 수정일
    private String ssmm;        //
    private String status;      // 상태 Y: 사용 N : 미사용
    private String grid_stats;     // 그리드 상태 N : 신규등록, U : UPDATE(수정), D : DELETE(삭제), S : SELECT(불러온 정보:기본)
}
