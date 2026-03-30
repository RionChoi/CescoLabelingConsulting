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
public class HcgetChatStatusDto {
  private String sys_id;                  // 시스템구분
  private String fs_no;                   // 컨설팅번호
  private String md_day;                  // 컨설팅MD
  private String md_yn;                   // 수행여부
  private String md_date;                 // 수행일자
  private String con_text11;              // 컨설팅내역
  private String con_text12;              // 컨설팅내역
  private String con_text21;              // 컨설팅내역
  private String con_text22;              // 컨설팅내역
  private String con_text31;              // 컨설팅내역
  private String con_text32;              // 컨설팅내역
  private String con_text41;              // 컨설팅내역
  private String con_text42;              // 컨설팅내역
  private String cst_mng_id;              // 고객사담당자
  private String cst_mng_sig_yn;          // 고객사서명여부
  private String cst_mng_sig_date;        // 고객사서명일자
  private String fs_mng_id;               // 세스코담당자
  private String fs_mng_sig_yn;           // 세스코서명여부
  private String fs_mng_sig_date;         // 세스코서명일자
  private String atch_nm;
  private String atch_size;
  private String atch_ref_id;
  private String url;
  private String org_file_nm;
  private String ser_file_nm;
  private String status;
  private String rgstr_id;
  private String rgstr_dt;
  private String mdfr_id;
  private String mdfr_dt;
  private String ssmm;
}
