package com.cesco.fs.consultMgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cesco.fs.consultMgt.dto.ConsultAmtDTO;
import com.cesco.fs.consultMgt.dto.ConsultBookDtlDTO;
import com.cesco.fs.consultMgt.dto.ConsultBookMstDTO;
import com.cesco.fs.consultMgt.dto.ConsultChatStartDTO;
import com.cesco.fs.consultMgt.dto.ConsultCusTitleInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultManagerDTO;
import com.cesco.fs.consultMgt.dto.ConsultMgtDTO;
import com.cesco.fs.consultMgt.dto.ConsultProdInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultSearchDTO;
import com.cesco.fs.consultMgt.dto.ConsultStaffDTO;
import com.cesco.sys.comm.dto.ConsultingAttach;

@Mapper
public interface ConsultMgtMapper {

    // 컨성팅 관리 표시컨설팅 목록
    List<ConsultMgtDTO> getConsultList(ConsultSearchDTO consultSearch);

    // 컨설팅 관리 협렵사별 담당자 목록
    List<ConsultManagerDTO> getManagerList(@Param("p_type") String p_type, @Param("sys_id") String sys_id, @Param("cst_id") String cst_id);

    // 컨설팅 신규 번호 생성
    String getFsMstNo();

    // 컨설팅 제품 등록
    Integer setMstInsList(List<ConsultProdInfoDTO> prod);    

    // 컨설팅 채팅 맴버 등록
    Integer setDtlMemInsList(List<ConsultManagerDTO> managerList);

    // 컨설팅 제품 견적 금액 등록
    Integer setEstmateInsList(List<ConsultAmtDTO> amtlist);

    // 파일 저장
    Integer setAttachIns(ConsultingAttach consultingAttach);

    // 컨설팅의뢰 제품정보
    List<ConsultProdInfoDTO> getConsultProdList(@Param("sys_id") String sys_id, @Param("fs_no") String fs_no);

    // 채팅 시작 정보 넣기
    Integer setChatStartIns(ConsultChatStartDTO chat);
  
    // 컨설팅 첨부파일 목록
    List<ConsultingAttach> getAttachList(@Param("sys_id") String sys_id, @Param("fs_no") String fs_no, @Param("atch_kn_cd") String atch_kn_cd);

    // 컨설팅의뢰 담당자 목록
    List<ConsultManagerDTO> getConsultManagerList(@Param("sys_id") String sys_id, @Param("fs_no") String fs_no);

    // 컨설팅 수정 관련 정보 삭제
    Integer delConsultRelatedInfo(@Param("sys_id") String sys_id, @Param("fs_no") String fs_no);

    // 파일 삭제(상태 변경 'N')
    Integer setAttachFileDel(ConsultingAttach atc);

    // 파일 정보 변경
    Integer setAttachFileUpt(ConsultingAttach atc);

    // 즐겨 찾기 MST 등록
    Integer setBookMstIns(ConsultBookMstDTO consultBookMst);

    // 즐겨 찾기 MST 삭제
    Integer setBookMstDel(@Param("book_seq") Integer book_seq, @Param("fs_mng_id") String fs_mng_id);

    // 즐겨 찾기 DTL 등록
    Integer setBookDtlIns(ConsultBookDtlDTO consultBookDtl);

    // 즐겨 찾기 목록
    List<ConsultBookMstDTO> getBookMstList(@Param("userId") String userId, @Param("book_nm") String book_nm);

    // 즐겨 찾기 협력사 목록
    List<ConsultBookDtlDTO> getBookDtlList(@Param("book_seq") Integer book_seq);

    // 협력사 목록 정보
    ConsultCusTitleInfoDTO getCusTitleInfo(@Param("cst_id") String cst_id); 

    // 파일 다운로드 정보 가져오기
    ConsultingAttach getAttachDownloadInfo(@Param("ser_file_nm") String ser_file_nm);

    // 계약자 목록
    List<ConsultStaffDTO> getContList(@Param("user_nm") String user_nm);
    
}
