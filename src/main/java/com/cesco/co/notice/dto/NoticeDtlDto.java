package com.cesco.co.notice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDtlDto {
    /**
     * 공지사항 댓글
     */
    private String sys_id;              // sys id
    private String noti_seq;            // 공지순번 
    private String noti_rep_seq;        // 댓글순번
    private String noti_rep_id;         // 댓글자
    private String noti_rep_nm;         // 댁글자명
    private String noti_rep_ref_seq;    // 연결댓글순번
    private String contents;            // 내용
    private String rgstr_id;            // 등록자 id
    private String rgstr_dt;            // 등록일자
    private String mdfr_id;             // 수정자 id
    private String mdfr_dt;             // 수정일시

}
