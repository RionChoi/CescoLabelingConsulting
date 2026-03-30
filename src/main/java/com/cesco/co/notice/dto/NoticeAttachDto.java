package com.cesco.co.notice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeAttachDto {
    /**
     * 공지사항 첨부파일
     */
    private String sys_id;            // 첨부파일순번
    private String noti_seq;            // 첨부파일순번
    private String atch_seq;            // 첨부파일순번
    private String atch_nm;             // 파일명
    private String atch_size;           // 파일사이즈
    private String atch_ref_id;         // 파일연계번호
    private String url;                 // 파일경로
    private String org_file_nm;         // 원본파일명
    private String ser_file_nm;         // 서버파일명
    private String status;
    private String rgstr_id;
    private String rgstr_dt;
    private String mdfr_id;
    private String mdfr_dt;
    private String ssmm;
}
