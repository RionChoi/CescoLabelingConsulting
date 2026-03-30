package com.cesco.co.notice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {
    /**
     * 공지사항
     */
    private String sys_id;              // 시스템id
    private String noti_seq;            // 공지seq
    private String type_cd;             // 타입코드 01 공지 ''02 일반
    private String priority_cd;         // 우선순위 순차적부여 01 중요 02 높음 03중간 04낮음 => 사용안함.
    private String noti_title;          // 제목
    private String noti_contents;       // 내용
    private String assign_date;         // 등록일자 => 중복
    private String start_date;          // 시작일자 => 중복
    private String complete_date;       // 종료일자
    private String assign_id;           // 게시자
    private String assign_nm;           // 게시자명
    private String rep_yn;              // 답글여부 => 사용안함
    private String status;              // 상태
    private String rgstr_id;            // 등록자id => 중복
    private String rgstr_dt;            // 등록일자 => 중복
    private String mdfr_id;             // 수정자id
    private String mdfr_dt;             // 수정일자
    private String ssmm;                // 
    private String read_cnt;            // 조회수
    private String noti_cmm_yn;         // 공통공지 : Y N
}
