package com.cesco.co.acctMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccSearchInfo {

    private String start_date;          // 등록일 시작 '20221101'
    private String end_date;            // 등록일 끝 '20221101'
    private String cst_id;              // 소속사 코드
    private String user_nm;             // 사용자명
    private String user_id;             // 사용자ID
    private String consult_cnt;         // 참여중
    private String cst_tp_cd;           // 고객사 유형
    private String status;              // 상태
    
}
