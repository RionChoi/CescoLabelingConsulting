package com.cesco.sys.comm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {

  private String user_id;
  private String sys_id;
  private String send_tp_cd;
  private String send_nm;
  private String receive_nm;
  private String receive_email;
  private String receive_tel_no;
  private String send_url;
  private String send_text;
  private String status;
  private String rgstr_id;
  private String rgstr_dt;
  private String mdfr_id;
  private String mdfr_dt;
  private String ssmm;
  private String cst_nm;
  private String prod_nm;
  private String send_chat_st_nm;
  private String init_code;

}
