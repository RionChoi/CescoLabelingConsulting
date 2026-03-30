package com.cesco.fs.consultMgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.cesco.fs.consultMgt.mapper.ConsultMgtMapper;
import com.cesco.fs.consulting.mapper.ConsultingMapper;
import com.cesco.sys.comm.dto.ConsultingAttach;

import lombok.RequiredArgsConstructor;

/**
 * @author parkminho
 * @apiNote consultMgt
 */

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ConsultMgtService {

    private final ConsultMgtMapper consultMgtMapper;
    private final ConsultingMapper consultingMapper;
    // 컨성팅 관리 표시컨설팅 목록
    public List<ConsultMgtDTO> getConsultList(ConsultSearchDTO consultSearch) throws Exception{        
        return consultMgtMapper.getConsultList(consultSearch);
    }

    // 컨설팅 관리 협렵사별 담당자 목록
    public List<ConsultManagerDTO> getManagerList(String p_type, String sys_id, String cst_id) throws Exception{

        if(sys_id == null)  sys_id = "";
        if(cst_id == null)  cst_id = "";

        return consultMgtMapper.getManagerList(p_type, sys_id, cst_id);        
    }

    // 컨설팅 신규 번호 생성
    public String getFsMstNo() throws Exception{
        return consultMgtMapper.getFsMstNo();
    }

    // 컨설팅 제품 등록
    public Integer setMstInsList(List<ConsultProdInfoDTO> prodList) throws Exception{
        return consultMgtMapper.setMstInsList(prodList);
    }

    // 컨설팅 채팅 맴버 등록
    public Integer setDtlMemInsList(List<ConsultManagerDTO> managerList) throws Exception{
        return consultMgtMapper.setDtlMemInsList(managerList);
    }

    // 컨설팅 제품 견적 금액 등록
    public Integer setEstmateInsList(List<ConsultAmtDTO> amtlist) throws Exception{
        return consultMgtMapper.setEstmateInsList(amtlist);
    }

    // 파일 저장
    public Integer setAttachIns(ConsultingAttach consultingAttach) throws Exception{
        return consultMgtMapper.setAttachIns(consultingAttach);
    }

    // 컨설팅의뢰 제품정보
    public List<ConsultProdInfoDTO> getConsultProdList(String sys_id, String fs_no) throws Exception{
        return consultMgtMapper.getConsultProdList(sys_id, fs_no);
    }

    // 채팅 시작 정보 넣기
    public Integer setChatStartIns(ConsultChatStartDTO chat) throws Exception{
        return consultMgtMapper.setChatStartIns(chat);
    }

    // 컨설팅 첨부파일 목록
    public List<ConsultingAttach> getAttachList(String sys_id, String fs_no, String atch_kn_cd) throws Exception{
        return consultMgtMapper.getAttachList(sys_id, fs_no, atch_kn_cd);
    }

    // 컨설팅의뢰 담당자 목록
    public List<ConsultManagerDTO> getConsultManagerList(String sys_id, String fs_no) throws Exception{
        return consultMgtMapper.getConsultManagerList(sys_id, fs_no);
    }

    // 컨설팅 수정 관련 정보 삭제
    public Integer delConsultRelatedInfo(String sys_id, String fs_no) throws Exception{
        return consultMgtMapper.delConsultRelatedInfo(sys_id, fs_no);
    };

    // 파일 삭제(상태 변경 'N')
    public Integer setAttachFileDel(ConsultingAttach atc) throws Exception{
        return consultMgtMapper.setAttachFileDel(atc);
    }

    // 파일 정보 변경
    public Integer setAttachFileUpt(ConsultingAttach atc) throws Exception{
        return consultMgtMapper.setAttachFileUpt(atc);
    }

    // 즐겨 찾기 MST 등록
    public Integer setBookMstIns(String userId, ConsultBookMstDTO consultBookMst) throws Exception{

        consultBookMst.setFs_mng_id(userId);
        consultBookMst.setRgstr_id(userId);
        consultBookMst.setMdfr_id(userId);

        return consultMgtMapper.setBookMstIns(consultBookMst);        
    }

    // 즐겨 찾기 MST 삭제
    public Integer setBookMstDel(Integer book_seq, String fs_mng_id){
        return consultMgtMapper.setBookMstDel(book_seq, fs_mng_id);
    }

    // 즐겨 찾기 DTL 등록
    public void setBookDtlIns(String userId, Integer bookSeq, List<ConsultBookDtlDTO> consultBookDtl) throws Exception{

        for(ConsultBookDtlDTO cb : consultBookDtl){
            cb.setBook_seq(bookSeq);
            cb.setRgstr_id(userId);
            cb.setMdfr_id(userId);

            consultMgtMapper.setBookDtlIns(cb);
        }        
    }

    // 즐겨 찾기 목록
    public List<ConsultBookMstDTO> getBookMstList(String userId, String book_nm) throws Exception{
        return consultMgtMapper.getBookMstList(userId, book_nm);
    }

    // 즐겨 찾기 협력사 목록
    public List<ConsultBookDtlDTO> getBookDtlList(Integer book_seq) throws Exception{
        return consultMgtMapper.getBookDtlList(book_seq);
    }

    // 협력사 목록 정보
    public ConsultCusTitleInfoDTO getCusTitleInfo(String cst_id) throws Exception{
        return consultMgtMapper.getCusTitleInfo(cst_id);
    }

    // 파일 다운로드 정보 가져오기
    public ConsultingAttach getAttachDownloadInfo(String ser_file_nm,String viewid) throws Exception{

        if ("chat".equals(viewid)) {
            return consultingMapper.getAttachDownloadInfo(ser_file_nm);
        } else {
            return consultMgtMapper.getAttachDownloadInfo(ser_file_nm);
        }
    }

    // 계약자 목록
    public List<ConsultStaffDTO> getContList(String user_nm) throws Exception{
        return consultMgtMapper.getContList(user_nm);
    }
    
}