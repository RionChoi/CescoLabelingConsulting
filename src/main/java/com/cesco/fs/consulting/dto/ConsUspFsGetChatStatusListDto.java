package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsUspFsGetChatStatusListDto {
  
private String sys_id;
private String fs_no;    // 컨설팅번호 
private String fs_seq;   // 컨설팅순번
private String fs_chat_seq;   // 채팅순번
private String fs_chat_id;  // 상태변경자
private String fs_status_nm;         // 상태명
private String fs_status_cd;         // 상태코드
private String mdfr_dt; // 수정일시

}
