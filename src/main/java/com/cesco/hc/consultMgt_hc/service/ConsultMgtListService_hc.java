package com.cesco.hc.consultMgt_hc.service;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.hc.consultMgt_hc.dto.ConCompletReqDto;
import com.cesco.hc.consultMgt_hc.dto.ConCompleteDto;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcAmtDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcChatStart;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcContInfoDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcDeptDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcManagerDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcMdayDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcMstDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcProdDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtReqDto;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtResDto;
import com.cesco.hc.consultMgt_hc.mapper.ConsultMgtListMapper_hc;
import com.cesco.sys.comm.dto.ConsultingAttach;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ConsultMgtListService_hc {

  private final ConsultMgtListMapper_hc consultMgtMapper;


  // 표시컨설팅_목록 조회
  public List<ConsultMgtResDto> getList(ConsultMgtReqDto param, HttpSession session) throws Exception{
    String userId = (String)session.getAttribute("userId");
    String sysCode = (String)session.getAttribute("sysCode");
    param.setRgstr_id(userId);
    param.setSys_id(sysCode);
    
    if (param.getFs_status_cd() == null) {
      param.setFs_status_cd("");
    }

    return consultMgtMapper.getList(param);
  }

  public ConCompleteDto getComplete(ConCompletReqDto param) throws Exception{
    return consultMgtMapper.getComplete(param);
  }

  public int setComplete(ConCompleteDto param) throws Exception{
    return consultMgtMapper.setComplete(param);
  }

  // 컨설팅 관리 협렵사별 담당자 목록
  public List<ConsultMgtHcManagerDTO> getManagerList(String p_type, String sys_id, String fs_no, String cst_id, String user_id) throws Exception{
    return consultMgtMapper.getManagerList(p_type, sys_id, fs_no, cst_id, user_id);
  }
    
  // 컨설팅 신규 번호 생성
  public String getFsMstNo(String sys_id) throws Exception{
    if(sys_id == null || sys_id.equals("")){
      sys_id = "HC";
    }    
    return consultMgtMapper.getFsMstNo(sys_id);
  }

  // 파일 저장
  public Integer setAttachIns(ConsultingAttach consultingAttach) throws Exception{
    return consultMgtMapper.setAttachIns(consultingAttach);
  }

  // 파일 삭제(상태 변경 'N')
  public Integer setAttachFileDel(ConsultingAttach atc) throws Exception{
    return consultMgtMapper.setAttachFileDel(atc);
  }

  // 파일 정보 변경
  public Integer setAttachFileUpt(ConsultingAttach atc) throws Exception{
    return consultMgtMapper.setAttachFileUpt(atc);
  }

  // 계약 일련 번호
  public List<ConsultMgtHcContInfoDTO> getContNumList(String prod_cd) throws Exception{
    return consultMgtMapper.getContNumList(prod_cd);
  }

  // 상품 정보 가져오기
  public List<ConsultMgtHcProdDTO> getProdList(String sys_id, String fs_no) throws Exception{
    return consultMgtMapper.getProdList(sys_id, fs_no);
  }

  // 컨설팅 제품 등록
  public Integer setMstInsList(List<ConsultMgtHcMstDTO> insProdList) throws Exception{
    return consultMgtMapper.setMstInsList(insProdList);
  }

  // 컨설팅 채팅 맴버 등록
  public Integer setDtlMemInsList(List<ConsultMgtHcManagerDTO> managerList) throws Exception{
    return consultMgtMapper.setDtlMemInsList(managerList);
  }

  // 컨설팅 제품 견적 금액 등록
  public Integer setEstmateInsList(List<ConsultMgtHcAmtDTO> amtlist) throws Exception{
    return consultMgtMapper.setEstmateInsList(amtlist);
  }

  // 계약 MD 등록
  public Integer setMdayInsList(List<ConsultMgtHcMdayDTO> mdayList) throws Exception{
    return consultMgtMapper.setMdayInsList(mdayList);
  }

  // 채팅 시작 정보 넣기
  public Integer setChatStartIns(ConsultMgtHcChatStart chat) throws Exception{
    return consultMgtMapper.setChatStartIns(chat);
  }

  // 지역본부 목록
  public List<ConsultMgtHcDeptDTO> getDeptList(String p_type, String sys_id, String cst_id,  String userId) throws Exception{
    return consultMgtMapper.getDeptList(p_type, sys_id, cst_id, userId);
  }
}
