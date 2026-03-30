package com.cesco.fs.consulting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
import com.cesco.sys.comm.dto.ConsultingAttach;


@Mapper
public interface ConsultingMapper {
  // 컨설팅 목록 조회
  List<ConsultingResDto> getList(ConsultingReqDto consultingDto) throws Exception;
  // 컨설팅 담당자정보조회
  List<ConsultingMngDto> getMngInfo(ConsultingReqDto consultingDto) throws Exception;  
  // 신규의뢰
  Integer saveConsultingNew(ConsultingNewReq data) throws Exception;  
  //신규의뢰내역취소(삭제)
  Integer deleteMst(ConsultingNewReq data) throws Exception;  
  // 체팅 파일 저장
  Integer saveUspFsSetAttachChat(ConsultingAttachChatDto data);
  // 컨설팅 체팅 상세 조회
  List<ConsUspFsGetChatDtLListDto> uspFsGetChatDtLList(ConsChatReqDto data) throws Exception;
  // 컨설팅 체팅 상세  파일 목록 조회
  List<ConsUspFsGetChatAtchListDto> uspFsGetChatAtchList(ConsChatReqDto data) throws Exception;
  // 컨설팅 체팅 상세 멤버 조회
  List<ConsUspFsGetChatMemAddListDto> uspFsGetChatMemAddList(ConsChatReqDto data) throws Exception;
  // 컨설팅 체팅 상세 진행 상태 조회
  List<ConsUspFsGetChatStatusListDto> uspFsGetChatStatusList(ConsChatReqDto data) throws Exception;
  // 컨설팅 체팅 상세 멤버 추가
  Integer uspFsSetChatMem(ConsChatReqDto data) throws Exception;
  // 컨설팅 체팅 상세 체팅 글추가
  Integer uspFsSetChatDtLList(ConsChatReqDto data) throws Exception;
  // 컨설팅 체팅 상세 멤버 삭제
  Integer uspFsDelChatMem(ConsChatReqDto memId) throws Exception;
  // 파일 다운로드 정보 가져오기
  ConsultingAttach getAttachDownloadInfo(@Param("ser_file_nm") String ser_file_nm);
  // 컨설팅 체팅 진행 상태 수정
  Integer modifyFsStatusCd(ConsChatReqDto data);
}
