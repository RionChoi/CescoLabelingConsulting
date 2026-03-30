package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsUspFsGetChatMemAddListDto {

  private String sys_id;
  private String fs_chat_id;  // 챗팅자아이디
  private String fs_chat_nm;         // 챗팅자명
  private String fs_chat_cst_nm;         // 고객사
  private String email;       // email

}
