package com.cesco.hc.consulting_hc.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingResDto implements Serializable {
  private static final long serialVersionUID = -2652142427983006421L;
  
  private String sys_id;
  private String cst_id;
  private String cst_nm;					        // 고객사명
  private String fs_no;                   // 컨설팅번호
  private String fs_seq;                  // 컨설팅번호순번, 수행MD
  private String prod_cd;                 // 상품코드
  private String prod_nm;                 // 상품명
  private String prod_tp_nm;              // 상품유형명
  private String corp_nm;                 // 참여자명  
  private String cst_mng_id;              // 고객담당자
  private String cst_mng_nm;              // 고객담당자명
  private String cst_mng_mobile;          // 고객담당자 모바일
  private String bill_cst_id;             // 정산거래처
  private String bill_cst_nm;             // 정산거래처명   
  private String receive_date;            // 접수일자
  private String tax_bill_id;             // 정산담당자
  private String tax_bill_nm;             // 정산담당자명
  private String tax_bill_email;          // 정산담당자이메일 
  private String fs_mng_id;               // 세스코담당자
  private String cst_cesco_mng_nm;        // 세스코담당자명 
  private String cseco_dept_cd;           // 세스코담당자 부서코드
  private String cseco_dept_nm;           // 세스코담당자 부서명
  private String md_day;                  // 계약MD
  private String mw_day;                  //수행MD
  private String fs_status_cd;            // 진행상태 
  private String fs_status_nm;            // 진행상태명 (완료, 진행중)
  private String mem_cnt;                 // 참여자수
  private String est_amt;                 // 견적금액
  private String bill_ar_amt;
  private String bill_amt;
  private String bill_net_amt;
  private String mdfr_dt;                 // 등록일시(수정일시)
  private String complt_yn;               // 완료여부
  private String cont_yn;                 // 동의여부
  private String cont_no;                 // 계약번호
  private String cont_id;
  private String cont_nm;
  private String complet_tp_cd;           //완료확인서 진행상태 코드
  private String complet_stt;             //완료확인서 진행상태
  private String prod_ser_cd;             //정기 비정정기 서비스구분
  private String prod_ser_nm;             //정기 비정정기 서비스구분명
  private String prod_unit_cd;            //단가구분(의뢰구분)  
  private String prod_unit_nm;            //단가구분(의뢰구분)명  
  private String est_tp_nm;               // 견적금액구분 
  private String prod_qty_nm;             // 내용물
  private String cont_url;
  private String read_yn;

}
