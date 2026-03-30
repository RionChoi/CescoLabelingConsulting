package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsUspFsDelChatMemDto {

  private String sys_id;// 시스템구분
  private String fs_no ; // 컨설팅번호 
  private String fs_seq ; // 컨설팅순번
  private String fs_chat_yn;  // 참여하지않은 사람만
  
}
