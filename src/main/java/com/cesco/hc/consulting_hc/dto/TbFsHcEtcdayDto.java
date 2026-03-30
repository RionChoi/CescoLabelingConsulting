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
public class TbFsHcEtcdayDto {

  private String sys_id;
  private String fs_no;
  private String etc_seq;
  private String etc_date;
  private String etc_status_cd;
  private String fs_mng_id;
  private String cst_mng_id;
  private String cst_mng_date;
  private String status;
  private String rgstr_id;
  private String rgstr_dt;
  private String mdfr_id;
  private String mdfr_dt;
  private String ssmm;

}
