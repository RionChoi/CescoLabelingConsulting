package com.cesco.hc.consulting_hc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingReqDto {

  private String p_type;                // 조회 구분
  private String sys_id;                // 시스템 id
  private String cst_id;                // 고객사유형
  private String rgstr_id;              // 등록자
  private String food_type;             // 식품유형 코드 
  private String product_name;          // 제품명
  private String cst_mng_id;            // 담당자
  private String fs_mng_id;             // 세스코 담당자
  private String start_date;            // 시작일
  private String end_date;              // 종료일
  private String fs_status_cd;          // 진행상태
  private String md_day;                // 계약MD
  private String fs_seq;                // 수행MD
  private String dept_cd;               // 지역본부
}