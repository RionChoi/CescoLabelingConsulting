package com.cesco.fs.serviceStatement.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceStatementMgtDto {

    /**
     * 서비스견적서관리
     */

    // param
    private String p_type;                    // 
	private String sys_id;                    // 시스템구분
    private String fs_bill_no;                // 서비스견적서번호
    private String fs_bill_seq;                // 서비스견적서번호
	private String start_date;                // 일자
	private String end_date;                  // 일자
	private String cst_id;                    // 고객사
	private String bill_req_cd;               // 비용확인상태
	private String fs_mng_id;                 // 담당자
	private String bill_ar_cd;                // 입금확인
	private String directEmail;               // 다이렉트 이메일
	private String emailContent;              // email 내용
    private String cont_id;                   // 계약담당자id
    private String user_id;                   // 사용자 id

    List<ServiceStatementDto> serviceStatementList;
    List<ServiceStatementDtlDto> serviceStatementDtlList;
}
