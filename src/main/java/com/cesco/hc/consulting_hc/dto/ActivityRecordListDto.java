package com.cesco.hc.consulting_hc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRecordListDto {
  private String sys_id;
  private String fs_no;
  private String fs_seq;
  private String cst_id;
  private String cst_mng_nm;
  private String con_prod;
  private String cst_nm;						  // 컨설팅 업체명
  private String md_day;					    // 진행일수
  private String md_date;				      // 수행일자
  private String con_text11;				  // 수행업무내용 -> 컨설팅내용
  private String con_text12;				  // 비고 -> 컨설팅내용 
  private String con_text21;			    // 수행업무내용 -> 다음일정
  private String con_text22;			    // 비고 -> 컨설팅내용 
  private String con_text31;			    // 이슈 및 수정사항
  private String cst_mng_sig_date;		// 서명일자
  private String cst_mng_sig_yn;
}
