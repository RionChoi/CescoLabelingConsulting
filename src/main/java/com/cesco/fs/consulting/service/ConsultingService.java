package com.cesco.fs.consulting.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesco.fs.consulting.dto.ConsChatReqDto;
import com.cesco.fs.consulting.dto.ConsUspFsGetChatAtchListDto;
import com.cesco.fs.consulting.dto.ConsUspFsGetChatDtLListDto;
import com.cesco.fs.consulting.dto.ConsUspFsGetChatMemAddListDto;
import com.cesco.fs.consulting.dto.ConsUspFsGetChatStatusListDto;
import com.cesco.fs.consulting.dto.ConsultingAttachChatDto;
import com.cesco.fs.consulting.dto.ConsultingNewReq;
import com.cesco.fs.consulting.dto.ConsultingReqDto;
import com.cesco.fs.consulting.dto.ConsultingResDto;
import com.cesco.fs.consulting.dto.ConsultingMngDto;
import com.cesco.fs.consulting.mapper.ConsultingMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultingService {
  

  private final ConsultingMapper consultingMapper;

  /**
   * @apiNote 표시컨설팅_목록 조회
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public List<ConsultingResDto> getList(ConsultingReqDto param) throws Exception{
      return consultingMapper.getList(param);
  }
  /**
   * @apiNote 표시컨설팅_담당자조회
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public List<ConsultingMngDto> getMngInfo(ConsultingReqDto param) throws Exception{
    return consultingMapper.getMngInfo(param);
  }

  /** 
   * @apiNote 신규컨설팅의뢰
   * @param   ConsultingNewReq
   * @return void
  */
  public Integer saveConsultingNew(ConsultingNewReq data) throws Exception{
    return consultingMapper.saveConsultingNew(data);
  }

  /** 
   * @apiNote 신규컨설팅취소(삭제)
   * @param   ConsultingNewReq
   * @return void
  */
  public Integer deleteMst(ConsultingNewReq data) throws Exception{
    return consultingMapper.deleteMst(data);
  };

  /** 
   * @apiNote 컨설팅 체팅 글 목록 
   * @param   ConsChatReqDto
   * @return void
  */
  public List<ConsUspFsGetChatDtLListDto> uspFsGetChatDtLList(ConsChatReqDto data) throws Exception{
     return consultingMapper.uspFsGetChatDtLList(data);
  };

  /** 
   * @apiNote 컨설팅 체팅 글 추가
   * @param   ConsChatReqDto
   * @return void
  */
  public Integer uspFsSetChatDtLList(ConsChatReqDto data) throws Exception{
    return consultingMapper.uspFsSetChatDtLList(data);
  };

    /** 
   * @apiNote 컨설팅 체팅 파일 목록
   * @param   ConsChatReqDto
   * @return void
  */
  public List<ConsUspFsGetChatAtchListDto> uspFsGetChatAtchList(ConsChatReqDto data) throws Exception{
    return consultingMapper.uspFsGetChatAtchList(data);
  };
    /** 
   * @apiNote 컨설팅 체팅 방 멤버 조회
   * @param   ConsChatReqDto
   * @return void
  */
  public List<ConsUspFsGetChatMemAddListDto> uspFsGetChatMemAddList(ConsChatReqDto data) throws Exception{
    return consultingMapper.uspFsGetChatMemAddList(data);
  };
    /** 
   * @apiNote 컨설팅 진행 상태 조회
   * @param   ConsChatReqDto
   * @return void
  */
  public List<ConsUspFsGetChatStatusListDto> uspFsGetChatStatusList(ConsChatReqDto data) throws Exception{
    return consultingMapper.uspFsGetChatStatusList(data);
  };

    /** 
   * @apiNote 컨설팅 체팅 멤버 추가
   * @param   ConsChatReqDto
   * @return void
  */
  public Integer uspFsSetChatMem(ConsChatReqDto data) throws Exception{
     return consultingMapper.uspFsSetChatMem(data);
  };
  
    /** 
   * @apiNote 체팅 파일 정보 저장
   * @param   ConsChatReqDto
   * @return void
  */
  public Integer saveUspFsSetAttachChat(ConsultingAttachChatDto data) throws Exception{
    return consultingMapper.saveUspFsSetAttachChat(data);
  };

  /** 
   * @apiNote 컨설팅 체팅 진행 상태 수정
   * @param   ConsChatReqDto
   * @return void
  */
  public Integer modifyFsStatusCd(ConsChatReqDto data) throws Exception{
    return consultingMapper.modifyFsStatusCd(data);
  }  

}
