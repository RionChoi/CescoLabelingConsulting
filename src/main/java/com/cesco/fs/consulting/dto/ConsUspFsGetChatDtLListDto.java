package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsUspFsGetChatDtLListDto {

  private String srt;             // 정렬구분
  private String sys_id;
  private String fs_no;           // 컨설팅번호 
  private String fs_seq;          // 컨설팅순번
  private String fs_chat_seq;     // 채팅순번
  private String fs_chat_id;      // 상태변경자
  private String fs_chat_nm;
  private String fs_chat_ref_seq; // 답글순번
  private String contents;        // 내용
  private String fs_status_cd;    // 상태
  private String fs_status_nm;    // 상태명
  private String mdfr_dt;         // 수정일시
  private String seq;             // 정렬순서
  private String atch_seq;        // 첨부순서
  private String atch_nm;
  private String url;
  private String atch_kn_cd;
  private String ser_file_nm;
  private String org_file_nm;
  private String max_fs_chat_seq;
  private String tel_no;
  private String prod_nm;
  private String email;
  private String min_date;        // 상태 별 최초 등록일
  private String read_yn;
}
