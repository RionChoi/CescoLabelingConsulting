package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsUspFsGetChatAtchListDto {
  
  private String sys_id;
  private String atch_nm;       // 첨부파일 명 aaa.xls
  private String url;           // 경로
  private String atch_kn_cd;    // 첨부종류
  private String interest_yn;   // 관심여부
  private String ser_file_nm;  
  private String org_file_nm;
  private String file_type;     //파일타입  
  private String mdfr_dt;       //생성날짜
  private String fs_chat_id;
  private String fs_chat_nm;    //첨부자
  private String fs_cst_nm;     //첨부업체  
  private Integer cnt;
}
