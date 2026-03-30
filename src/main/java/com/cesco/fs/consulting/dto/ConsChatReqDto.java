package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsChatReqDto {
  private String p_type;
  private String d_type;
  private String sys_id;
  private String fs_no;
  private String fs_seq;
  private String fs_chat_id;
  private String fs_chat_seq;
  private String fs_interest_yn;
  private String fs_file_type;
  private String status;
  private String contents;
  private Integer fs_chat_ref_seq;
  private String fs_status_cd;
  private String atch_kn_cd;
  private String atch_nm;
  private String fs_chat_nm;
  private String fs_status_nm;
  private String mdfr_dt;
  private String ser_file_nm;
  private String srt;
  private String url;
  private String fs_chat_yn;          // 참여 여부
  private String fs_chat_yn_pre;          // 참여 여부 이전 상태
  private Integer cnt;
  private String tel_no;
  private String prod_nm;
  private String email;
  private String md_day;
  private String read_yn;
}



