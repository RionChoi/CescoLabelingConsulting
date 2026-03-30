package com.cesco.fs.consulting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingReqDto {
  private String p_type;                // 조회 구분
  private String sys_id;                // 시시템 id
  private String customer;              // 고객사유형
  private String rgstr_id;              // 등록자 
  private String food_type;             // 식품유형 코드 
  private String product_name;          // 제품명
  private String manager;               // 담당자
  private String cesco_person_in_charge;// 세스코 담당자
  private String start_date;            // 시작일
  private String end_date;              // 종료일
  private String fs_status_cd;          // 진행상태

}