package com.cesco.hc.consultMgt_hc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
import com.cesco.sys.comm.dto.ConsultingAttach;


@Mapper
public interface ConsultMgtListMapper_hc {

    //컨설팅 목록 조회
    public List<ConsultMgtResDto> getList(ConsultMgtReqDto consultMgtReqDto) throws Exception;

    public ConCompleteDto getComplete(ConCompletReqDto param) throws Exception;

    public int setComplete(ConCompleteDto param) throws Exception;

    // 컨설팅 관리 협렵사별 담당자 목록
    List<ConsultMgtHcManagerDTO> getManagerList(@Param("p_type") String p_type, 
                                                    @Param("sys_id") String sys_id, 
                                                    @Param("fs_no") String fs_no,
                                                    @Param("cst_id") String cst_id,
                                                    @Param("user_id") String user_id);
    // 컨설팅 신규 번호 생성
    String getFsMstNo(@Param("sys_id") String sys_id);

    // 파일 저장
    Integer setAttachIns(ConsultingAttach consultingAttach);
    
    // 파일 삭제(상태 변경 'N')
    Integer setAttachFileDel(ConsultingAttach atc);

    // 파일 정보 변경
    Integer setAttachFileUpt(ConsultingAttach atc);

    // 계약 일련 번호
    List<ConsultMgtHcContInfoDTO> getContNumList(@Param("prod_cd") String prod_cd);

    // 상품 정보 가져오기
    List<ConsultMgtHcProdDTO> getProdList(@Param("sys_id") String sys_id, @Param("fs_no") String fs_no);

    // 컨설팅 제품 등록
    Integer setMstInsList(List<ConsultMgtHcMstDTO> insProdList);

    // 컨설팅 채팅 맴버 등록
    Integer setDtlMemInsList(List<ConsultMgtHcManagerDTO> managerList);

    // 컨설팅 제품 견적 금액 등록
    Integer setEstmateInsList(List<ConsultMgtHcAmtDTO> amtlist);

    // 계약 MD 등록
    Integer setMdayInsList(List<ConsultMgtHcMdayDTO> mdayList);

    // 채팅 시작 정보 넣기
    Integer setChatStartIns(ConsultMgtHcChatStart chat);

    // 지역본부 목록
    List<ConsultMgtHcDeptDTO> getDeptList(@Param("p_type") String p_type, @Param("sys_id") String sys_id, @Param("cst_id") String cst_id, @Param("userId") String userId);
}
