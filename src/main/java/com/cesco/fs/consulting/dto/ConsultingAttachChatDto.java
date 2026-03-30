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
public class ConsultingAttachChatDto {

  private String sys_id;
  private String fs_no;
  private String fs_chat_id;
  private String fs_chat_seq;
  private String atch_seq;
  private String atch_kn_cd;
  private String atch_nm;
  private String atch_size;
  private String atch_ref_id;
  private String url;
  private String org_file_nm;
  private String ser_file_nm;
  private String status;
  private String rgstr_id;
  private String rgstr_dt;
  private String mdfr_id;
  private String mdfr_dt;
  private String ssmm;
  private String interest_yn;

}
