package com.cesco.hc.consultMgt_hc.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cesco.hc.consultMgt_hc.dto.ConCompletReqDto;
import com.cesco.hc.consultMgt_hc.dto.ConCompleteDto;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcChatStart;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcContInfoDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcDeptDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcManagerDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcProdDTO;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtHcReqInfo;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtReqDto;
import com.cesco.hc.consultMgt_hc.dto.ConsultMgtResDto;
import com.cesco.hc.consultMgt_hc.service.ConsultMgtListService_hc;
import com.cesco.sys.comm.dto.ConsultingAttach;
import com.cesco.sys.comm.dto.FtpUploadReturn;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hcConsultMgt")
public class ConsultMgtApiController_hc {
    private static final Logger LOGGER = LogManager.getLogger(ConsultMgtApiController_hc.class);

    @Autowired
    private final ConsultMgtListService_hc consultMgtService;

    @Autowired
    private final CommService commService;

    // 컨설팅 목록
    @GetMapping(value ="/getList")
    public List<ConsultMgtResDto> getList(ConsultMgtReqDto params, HttpSession session) throws Exception{        
        return consultMgtService.getList(params, session);        
    }

    // 담당자 정보 가져오기
    @GetMapping(value = "/managerList")
    public List<ConsultMgtHcManagerDTO> getManagerList(
        @RequestParam("p_type") String p_type, 
        @RequestParam("sys_id") String sys_id, 
        @RequestParam("cst_id") String cst_id,
        @RequestParam("fs_no") String fs_no,
        HttpSession session ) throws Exception{

        try{
            return consultMgtService.getManagerList(p_type, sys_id, fs_no, cst_id, (String)session.getAttribute("userId"));
        }catch (IndexOutOfBoundsException  e) {
            throw new CommDuplicateException("담당자 조회에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }

    }

    // 지역본부 목록
    @GetMapping(value="/deptList")
    public List<ConsultMgtHcDeptDTO> getDeptList(String p_type, HttpSession session) throws Exception{

        String userId = (String)session.getAttribute("userId");
        String sys_id = (String)session.getAttribute("sysCode");

        return consultMgtService.getDeptList(p_type, sys_id, "", userId);
    }

    // 계약 일련 번호 가져오기
    @GetMapping(value = "/contNumList")
    public List<ConsultMgtHcContInfoDTO> getContNumList(String prod_cd) throws Exception{
        return consultMgtService.getContNumList(prod_cd);
    }

    @GetMapping(value = "/prodList")
    public List<ConsultMgtHcProdDTO> getProdList(String sys_id, String fs_no) throws Exception{
        return consultMgtService.getProdList(sys_id, fs_no);
    }

    // 컨성팅 신규 등록
    @PostMapping(value ="/newConsultReg")
    public ResponseEntity<String> setNewConsultReg(
        @RequestPart(value = "consinfo") ConsultMgtHcReqInfo consultMgtHcReqInfo,
        @RequestPart(value = "attachfiles", required = false) List<MultipartFile> attachfiles,
        HttpSession session) throws Exception{
        
        String userId = (String)session.getAttribute("userId");

        try{
            // FS_NO 컨설팅 번호 생성
            consultMgtHcReqInfo.getConsultMgtHcMst().setFs_no(consultMgtService.getFsMstNo(consultMgtHcReqInfo.getConsultMgtHcMst().getSys_id()));

            // 로그인 ID 설정
            consultMgtHcReqInfo.setConsultInfo(userId);

            // 담당자 목록 설정
            consultMgtHcReqInfo.setManagerInfo();

            // 상품 정보 설정
            consultMgtHcReqInfo.setProdInfo();

            // 견적 금액 설정
            consultMgtHcReqInfo.setAmt();

            // MDay 설정
            consultMgtHcReqInfo.setMDay();

        }catch(Exception e){
            throw new CommDuplicateException("처리중 오류가 발생했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
                
        // 컨설팅 등록
        consultMgtService.setMstInsList(consultMgtHcReqInfo.getListConsultMgtHcMst());
        
        // 담당자 정보 등록
        consultMgtService.setDtlMemInsList(consultMgtHcReqInfo.getListConsultMgtHcManager());
        
        // 견적 금액 저장
        consultMgtService.setEstmateInsList(consultMgtHcReqInfo.getListConsultMgtHcAmt()); 

        // MD 등록
        consultMgtService.setMdayInsList(consultMgtHcReqInfo.getListConsultMgtHcMday());

        // 파일 저장
        if(attachfiles != null){

            int fileIdx = 0;
            
            for(MultipartFile f : attachfiles){

                ConsultingAttach atc = consultMgtHcReqInfo.getListConsultAttach().get(fileIdx++);

                // 파일 업로드
                FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, consultMgtHcReqInfo.getSys_id(), "");

                // 파일 DB 저장
                ConsultingAttach consultingAttach = new ConsultingAttach(consultMgtHcReqInfo.getConsultMgtHcMst().getSys_id()
                ,consultMgtHcReqInfo.getConsultMgtHcMst().getFs_no(), "0", atc.getAtch_kn_cd(), atc.getAtch_nm(),  String.valueOf(f.getSize()),
                atc.getAtch_ref_id(), ftpUploadReturn.getDirectory(), f.getOriginalFilename(), ftpUploadReturn.getFilename(), "Y", 
                userId, "", userId, "", "");

                consultMgtService.setAttachIns(consultingAttach);   // DB 직접 저장
            }
        }

        // 등록 완료 후 채팅 문구 넣기          
        consultMgtService.setChatStartIns(new ConsultMgtHcChatStart(
            consultMgtHcReqInfo.getSys_id(), 
            consultMgtHcReqInfo.getFs_no(), 
            consultMgtHcReqInfo.getConsultMgtHcMst().getFs_mng_id(), 
            "1", null, "안녕하세요, 컨설팅을 진행하세요.", "02", "Y", userId, userId));

        return new ResponseEntity<>("컨설팅 등록 완료", HttpStatus.OK);

    }

    // 컨설팅 수정
    @PostMapping(value ="/uptConsultReg")
    public ResponseEntity<String> setUptConsultReg(
        @RequestPart(value = "consinfo") ConsultMgtHcReqInfo consultMgtHcReqInfo,
        @RequestPart(value = "attachfiles", required = false) List<MultipartFile> attachfiles,
        HttpSession session) throws Exception{

        // 로그인 ID
        String userId = (String)session.getAttribute("userId");

        try{
            
            // 로그인 ID 설정
            consultMgtHcReqInfo.setConsultInfo(userId);

            // 담당자 목록 설정
            consultMgtHcReqInfo.setManagerInfo();

            // 상품 정보 설정
            consultMgtHcReqInfo.setProdInfo();

            // 견적 금액 설정
            consultMgtHcReqInfo.setAmt();

            // MDay 설정
            consultMgtHcReqInfo.setMDay();

        }catch(Exception e){
            throw new CommDuplicateException("처리중 오류가 발생했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }

        // 컨설팅 등록
        consultMgtService.setMstInsList(consultMgtHcReqInfo.getListConsultMgtHcMst());
        
        // 담당자 정보 등록
        consultMgtService.setDtlMemInsList(consultMgtHcReqInfo.getListConsultMgtHcManager());
        
        // 견적 금액 저장
        consultMgtService.setEstmateInsList(consultMgtHcReqInfo.getListConsultMgtHcAmt()); 

        // MD 등록
        consultMgtService.setMdayInsList(consultMgtHcReqInfo.getListConsultMgtHcMday());
        
        // 첨부파일 등록(수정시 파일업로드 및 정보 변경)
        List<ConsultingAttach> insAttachList = new ArrayList<>();

        for(ConsultingAttach addfile : consultMgtHcReqInfo.getListConsultAttach()){

            addfile.setMdfr_id(userId);
            addfile.setFs_no(consultMgtHcReqInfo.getFs_no());

            if(addfile.getAtch_seq().equals("0")){
                // 신규 등록
                insAttachList.add(addfile);
            }else{
                // 정보 적용
                consultMgtService.setAttachFileUpt(addfile);
                if(!addfile.getStatus().equals("Y")){
                    insAttachList.add(addfile);
                }
            }
        }

        if(consultMgtHcReqInfo.getListConsultAttachDel() != null){
            for(ConsultingAttach addfile : consultMgtHcReqInfo.getListConsultAttachDel()){
                addfile.setStatus("N");
                addfile.setMdfr_id(userId);
                addfile.setFs_no(consultMgtHcReqInfo.getFs_no());
                
                // 삭제 처리
                consultMgtService.setAttachFileDel(addfile);
            }
        }

        if(attachfiles != null){
            
            int fileIdx = 0;
            
            for(MultipartFile f : attachfiles){
                ConsultingAttach atc = insAttachList.get(fileIdx++);

                // 파일 업로드
                FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, consultMgtHcReqInfo.getSys_id(), "");

                // 파일 저장[임시]
                ConsultingAttach consultingAttach = new ConsultingAttach(consultMgtHcReqInfo.getSys_id(),consultMgtHcReqInfo.getFs_no(), "0", atc.getAtch_kn_cd(), atc.getAtch_nm(),  String.valueOf(f.getSize()),
                atc.getAtch_ref_id(), ftpUploadReturn.getDirectory(), f.getOriginalFilename(), ftpUploadReturn.getFilename(), "Y", userId, "", userId, "", "");

                consultMgtService.setAttachIns(consultingAttach);   // DB 직접 저장
            }
        
        } 

        // 등록 완료 후 채팅 문구 넣기
        consultMgtService.setChatStartIns(new ConsultMgtHcChatStart(
            consultMgtHcReqInfo.getSys_id(), 
            consultMgtHcReqInfo.getFs_no(), 
            consultMgtHcReqInfo.getConsultMgtHcMst().getFs_mng_id(), 
            "1", null, "안녕하세요, 컨설팅을 진행하세요..", "02", "Y", userId, userId));
        
        return new ResponseEntity<>("컨설팅 수정 완료", HttpStatus.OK);
    }









    /**
     * haccp 완료확인서
     * @return
     */
    @RequestMapping(value = "/getComplete")
    @ResponseBody
    public ConCompleteDto getComplet(ConCompletReqDto param, HttpSession session)throws Exception{
        if((String)session.getAttribute("userId") == param.getFs_mng_id()){
            param.setWrite_auth("1"); //수정가능(컨설턴트)
        }else if((String)session.getAttribute("userId") == param.getCst_mng_id()){
            param.setWrite_auth("2"); //서명가능(고객담당자)
        }else{
            param.setWrite_auth("1"); //읽기가능(그외 사용자)3
        }

        return consultMgtService.getComplete(param);
    }




    /**
     * haccp 완료확인서 저장
     * @return
     */
    @RequestMapping(value = "/setComplete")
    @ResponseBody 
    public int setComplete(ConCompleteDto param) throws Exception{
        return consultMgtService.setComplete(param);
    }

}
