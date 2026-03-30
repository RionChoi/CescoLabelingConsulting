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
public class ConsultingResDto implements Serializable {
  private static final long serialVersionUID = -2652142427983006421L;
  
  private String sys_id;
  private String cst_id;
  private String cst_nm;					        // 고객사명
  private String fs_no;                   // 컨설팅번호
  private String fs_seq;                  // 컨설팅번호순번
  private String prod_item;               // 품목번호
  private String prodnm;                  // 제품명/식품유형
  private String prodnm1;                 // 제품명    
  private String prodnm2;                 // 식품유형    
  private String corpnm;                  // 협력사명  
  private String cst_mng_id;              // 고객담당자
  private String cst_mng_nm;              // 고객담당자명
  private String cst_mng_mobile;          // 고객담당자 모바일
  private String bill_cst_id;             // 정산거래처
  private String bill_cst_nm;             // 정산거래처명   
  private String receive_date;            // 접수일자
  private String prod_ser_cd;             //정기 비정정기 서비스구분
  private String prod_ser_nm;             //정기 비정정기 서비스구분명
  private String prod_unit_cd;            //단가구분(의뢰구분)  
  private String prod_unit_nm;            //단가구분(의뢰구분)명  
  private String tax_bill_id;             // 정산담당자
  private String tax_bill_nm;             // 정산담당자명
  private String tax_bill_email;          // 정산담당자이메일 
  private String est_tp_cd;               // 견적금액구분
  private String est_tp_nm;               // 견적금액구분명 
  private String fs_bill_item;            // 세금계산서 발행여부
  private String fs_bill_no;              // 세금계산서번호
  private String fs_mng_id;               // 세스코담당자
  private String cst_cesco_mng_nm;        // 세스코담당자명 
  private String prod_qty_nm;             // 내용물
  private String fs_status_cd;            // 진행상태 
  private String fs_status_nm;            // 진행상태명
  private String mem_cnt;                 // 참여자수
  private String est_amt;                 // 견적금액
  private String mdfr_dt;                 // 등록일시(수정일시)
  private String cont_no;                 // 계약번호
  private String cont_id;                 // 계약자
  private String cont_nm;                 // 계약자명


}
