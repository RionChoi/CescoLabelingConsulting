package com.cesco.fs.consulting.dto;

import java.io.Serializable;
import java.util.List;

import com.cesco.sys.comm.dto.ConsultingAttach;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingNew implements Serializable{
  private static final long serialVersionUID = -2652142427983006421L;
  private String sys_id;             // 시스템 id
  private String fs_no;              // 컨설팅번호
  private String prod_nm;            // 상품명
  private String prod_tp_cd;         // 상품유형							
  private String prod_mafa_nm;       // 제조업체명
  private String user_id;            // 사용자 id

  private List<ConsultingNew> ConsultingNewList;        // 신규의뢰리스트
  private List<ConsultingAttach> ConsultingAttachList;  // 파일 리스트

}
