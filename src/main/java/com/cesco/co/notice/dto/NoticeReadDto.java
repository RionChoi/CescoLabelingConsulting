package com.cesco.co.notice.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeReadDto {
    /**
     * 공지사항 조회 history
     */
    private String sys_id;              // 시스템id
    private String noti_seq;            // 공지seq
    private String read_dt;             // 조회일자
    private String read_id;             // 조회자id
    private String read_nm;             // 조회자명
    private String cst_id;              // 고객사id
    private String cst_nm;              // 고객사명
    private String status;              // 상태
    private String rgstr_id;            // 등록자id
    private String rgstr_dt;            // 등록일자
    private String mdfr_id;             // 수정자id
    private String mdfr_dt;             // 수정일자
    private String ssmm;                // 

    // test
    private int cnt;
}
