package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsChatReqAtchDto {

  private String sys_id;              //  시스템구분
  private String fs_no;               // 	컨설팅번호
  private String fs_chat_id;          //  채팅자
  private String fs_chat_seq;         //  채팅순번
  private String atch_seq;            //  첨부순번
  private String atch_kn_cd;          //  첨부문서종류
  private String atch_nm;             // 	파일명(AA.xls)
  private String atch_size;           // 	파일크기(10.1Byte)
  private String atch_ref_id;         // 	파일연계번호 NAS나 ECM연결번호
  private String url;                 // 	서버주소
  private String org_file_nm;         // 	파일원본파일c:\temp\aa.xls 
  private String ser_file_nm;         // 	서버파일명
  private String status;              // 
  private String rgstr_id;            // 
  private String rgstr_dt;            // 
  private String mdfr_id;             // 
  private String mdfr_dt;             // 
  private String ssmm;                // 

}
