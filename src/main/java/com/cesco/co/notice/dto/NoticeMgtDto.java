package com.cesco.co.notice.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeMgtDto {
    /**
     * 공지사항 관리
     */

    // param
    private String sys_id;              // 시스템id
    private String noti_seq;            // 공지seq
    private String noti_no;             // 공지번호
    
    // 조회
    private String srch_start_date;     // 조회 시작일
    private String srch_end_date;       // 조회 종료일
    private String srch_noti_title;     // 조회 제목

    // 공지사항 첨부파일
    List<NoticeAttachDto> noticeAttachList;

    // 공지사항 댓글
    List<NoticeDtlDto> noticeDtlList;

    // 공지사항 
    NoticeDto noticeDto;
    List<NoticeDto> noticeList;
}
