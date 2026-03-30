package com.cesco.fs.consulting.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingMngDto {
  
  private String sys_id;
  private String user_id;         // 사용자 ID
  private String user_nm;         // 사용자 명
  private String cst_id;          // 고객사 ID
  private String cst_nm;					// 고객사명
  private String email;           // 이메일
  private String mphone_no;       // 휴대전화번호명
  private String user_dept_nm;    // 부서명
  private String rgstr_dt;          // 등록일
  private String consult_cnt;     // 참여 컨설팅 수
  private String last_dt    ;     // 최근로그인일시

}
