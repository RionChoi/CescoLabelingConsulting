package com.cesco.fs.consultMgt.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cesco.fs.consultMgt.dto.ConsultBookDtlDTO;
import com.cesco.fs.consultMgt.dto.ConsultBookMstDTO;
import com.cesco.fs.consultMgt.dto.ConsultChatStartDTO;
import com.cesco.fs.consultMgt.dto.ConsultCusTitleInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultManagerDTO;
import com.cesco.fs.consultMgt.dto.ConsultMgtDTO;
import com.cesco.fs.consultMgt.dto.ConsultProdInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultReqInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultSearchDTO;
import com.cesco.fs.consultMgt.dto.ConsultStaffDTO;
import com.cesco.fs.consultMgt.service.ConsultMgtService;
import com.cesco.sys.comm.dto.ConsultingAttach;
import com.cesco.sys.comm.dto.FtpUploadReturn;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

/**
 * @author parkminho
 * @apiNote consultMgt
 */

@RestController
@RequestMapping("/consult/api")
public class ConsultRestMgtController {

    @Autowired
    ConsultMgtService consultMgtService;

    @Autowired
    CommService commService;

    // private static final Logger LOGGER = LogManager.getLogger(ApiController.class);
    // LOGGER.info("===============================================");
    // LOGGER.info("user_nm : " + user_nm);
    // LOGGER.info("===============================================");

    // 컨설팅 목록
    @GetMapping(value = "/consultList")
    public List<ConsultMgtDTO> getConsultList(ConsultSearchDTO consultSearch) throws Exception{

        try {

            return consultMgtService.getConsultList(consultSearch);

        }catch (IndexOutOfBoundsException  e) {

            throw new CommDuplicateException("목록 조회에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }
        
    }

    // 담당자 정보 가져오기
    @GetMapping(value = "/managerList")
    public List<ConsultManagerDTO> getManagerList(
        @RequestParam("p_type") String p_type, 
        @RequestParam("sys_id") String sys_id, 
        @RequestParam("cst_id") String cst_id ) throws Exception{

        try{

            return consultMgtService.getManagerList(p_type, sys_id, cst_id);

        }catch (IndexOutOfBoundsException  e) {

            throw new CommDuplicateException("담당자 조회에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }

    }

    // 컨설팅의뢰 제품정보
    @GetMapping("/consultProdList")
    public List<ConsultProdInfoDTO> getConsultProdList(
        @RequestParam("sys_id") String sys_id, 
        @RequestParam("fs_no") String fs_no) throws Exception {

        try{

            return consultMgtService.getConsultProdList(sys_id, fs_no);

        }catch(Exception e){

            throw new CommDuplicateException("제품 조회에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }
    }

    // 컨설팅의뢰 첨부파일 목록
    @GetMapping("/consultAttachList")
    public List<ConsultingAttach> getConsultAttachList(
        @RequestParam("sys_id") String sys_id, 
        @RequestParam("fs_no") String fs_no,
        @RequestParam("atch_kn_cd") String atch_kn_cd) throws Exception {

        try{

            return consultMgtService.getAttachList(sys_id, fs_no, atch_kn_cd);

        }catch(Exception e){

            throw new CommDuplicateException("파일 조회에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }
    }

    // 컨설팅의뢰 담당자 목록
    @GetMapping("/consultManagerList")
    public List<ConsultManagerDTO> getConsultManagerList(
        @RequestParam("sys_id") String sys_id, 
        @RequestParam("fs_no") String fs_no) throws Exception {
        try{

            return consultMgtService.getConsultManagerList(sys_id, fs_no);

        }catch(Exception e){

            throw new CommDuplicateException("담당자 조회에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }
    }

    // 컨성팅 신규 등록
    @PostMapping(value ="/newConsultReg")
    public ResponseEntity<String> setNewConsultReg(
        @RequestPart(value = "consinfo") ConsultReqInfoDTO consultReqInfo, 
        @RequestPart(value = "estfiles", required = false) List<MultipartFile> estfiles,
        @RequestPart(value = "attachfiles", required = false) List<MultipartFile> attachfiles,
        HttpSession session) throws Exception{
        
        // 로그인 ID
        String userId = (String)session.getAttribute("userId");

        if(consultReqInfo.getReg_type().equals("S")){
            // FS_NO 컨설팅 번호 생성
            consultReqInfo.getConsultCusInfo().setFs_no(consultMgtService.getFsMstNo());
        }

        try{
            // 로그인ID 설정
            consultReqInfo.setUserId(userId);

            // 담당자 기본값 설정
            consultReqInfo.setManagerInfo();

            // 제품 정보 설정
            consultReqInfo.setProdInfo();

        }catch(Exception e){
            throw new CommDuplicateException("처리중 오류가 발생했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }        

        // 제품 정보 설정(업체 등록)
        consultMgtService.setMstInsList(consultReqInfo.getListConsultProdInfo());       
        
        // 담당자 정보 등록
        consultMgtService.setDtlMemInsList(consultReqInfo.getListConsultManager());       
        
        // 견적 금액 저장
        consultMgtService.setEstmateInsList(consultReqInfo.getAmtList());

        if(estfiles != null){            
            int fileIdx = 0;

            for(MultipartFile f : estfiles){
                ConsultingAttach atc = consultReqInfo.getListConsultEstAttach().get(fileIdx++);

                // 파일 업로드
                FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, "FS", "");

                // 파일 DB 저장
                ConsultingAttach consultingAttach = new ConsultingAttach(consultReqInfo.getSys_id(),consultReqInfo.getConsultCusInfo().getFs_no(), "0", atc.getAtch_kn_cd(), atc.getAtch_nm(), String.valueOf(f.getSize()),
                atc.getAtch_ref_id(), ftpUploadReturn.getDirectory(), f.getOriginalFilename(), ftpUploadReturn.getFilename(), "Y", userId, "", userId, "", "");

                consultMgtService.setAttachIns(consultingAttach);   // DB 직접 저장
                
            }

        }

        if(attachfiles != null){        
            int fileIdx = 0;
            
            for(MultipartFile f : attachfiles){

                ConsultingAttach atc = consultReqInfo.getListConsultAttach().get(fileIdx++);

                // 파일 업로드
                FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, "FS", "");

                // 파일 DB 저장
                ConsultingAttach consultingAttach = new ConsultingAttach(consultReqInfo.getSys_id(),consultReqInfo.getConsultCusInfo().getFs_no(), "0", atc.getAtch_kn_cd(), atc.getAtch_nm(),  String.valueOf(f.getSize()),
                atc.getAtch_ref_id(), ftpUploadReturn.getDirectory(), f.getOriginalFilename(), ftpUploadReturn.getFilename(), "Y", userId, "", userId, "", "");

                consultMgtService.setAttachIns(consultingAttach);   // DB 직접 저장
            }
        
        }        

        // 등록 완료 후 채팅 문구 넣기          
        consultMgtService.setChatStartIns(new ConsultChatStartDTO(consultReqInfo.getSys_id(), consultReqInfo.getConsultCusInfo().getFs_no(), consultReqInfo.getConsultCusInfo().getCescoMainManager(), "1", null
            , "안녕하세요, 컨설팅을 진행하세요.", "02", "Y", userId, userId));

        return new ResponseEntity<>("컨설팅 등록 완료", HttpStatus.OK);

    }
    
    // 컨설팅 수정
    @PostMapping(value ="/uptConsultReg")
    public ResponseEntity<String> setUptConsultReg(
        @RequestPart(value = "consinfo") ConsultReqInfoDTO consultReqInfo, 
        @RequestPart(value = "estfiles", required = false) List<MultipartFile> estfiles,
        @RequestPart(value = "attachfiles", required = false) List<MultipartFile> attachfiles,
        HttpSession session) throws Exception{

        // 로그인 ID
        String userId = (String)session.getAttribute("userId");

        try{
            // 로그인ID 설정
            consultReqInfo.setUserId(userId);

            // 담당자 기본값 설정
            consultReqInfo.setManagerInfo();

            // 제품 정보 설정
            consultReqInfo.setProdInfo();

        }catch(Exception e){
            throw new CommDuplicateException("처리중 오류가 발생했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }        
        
        // 제품 등록
        consultMgtService.setMstInsList(consultReqInfo.getListConsultProdInfo());

        // 담당자 등록
        consultMgtService.setDtlMemInsList(consultReqInfo.getListConsultManager());

        // 견적단가 등록        
        consultMgtService.setEstmateInsList(consultReqInfo.getAmtList());
        
        // 견적서 수정시
        if(estfiles != null){
            int fileIdx = 0;

            for(MultipartFile f : estfiles){
                ConsultingAttach atc = consultReqInfo.getListConsultEstAttach().get(fileIdx++);

                atc.setMdfr_id(userId);
                atc.setFs_no(consultReqInfo.getConsultCusInfo().getFs_no());

                // 파일 삭제 처리(상태 변경)
                consultMgtService.setAttachFileDel(atc);

                // 파일 업로드
                FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, "FS", "");
             
                // 파일 저장[임시]
                ConsultingAttach consultingAttach = new ConsultingAttach(consultReqInfo.getSys_id(),consultReqInfo.getConsultCusInfo().getFs_no(), "0", atc.getAtch_kn_cd(), atc.getAtch_nm(), String.valueOf(f.getSize()),
                atc.getAtch_ref_id(), ftpUploadReturn.getDirectory(), f.getOriginalFilename(), ftpUploadReturn.getFilename(), "Y", userId, "", userId, "", "");

                consultMgtService.setAttachIns(consultingAttach);   // DB 직접 저장
            }

        }

        // 첨부파일 등록(수정시 파일업로드 및 정보 변경)
        List<ConsultingAttach> insAttachList = new ArrayList<>();

        for(ConsultingAttach addfile : consultReqInfo.getListConsultAttach()){

            addfile.setMdfr_id(userId);
            addfile.setFs_no(consultReqInfo.getConsultCusInfo().getFs_no());

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

        if(consultReqInfo.getListConsultAttachDel() != null){
            for(ConsultingAttach addfile : consultReqInfo.getListConsultAttachDel()){
                addfile.setStatus("N");
                addfile.setMdfr_id(userId);
                addfile.setFs_no(consultReqInfo.getConsultCusInfo().getFs_no());
                
                // 삭제 처리
                consultMgtService.setAttachFileDel(addfile);
            }
        }

        if(attachfiles != null){
            
            int fileIdx = 0;
            
            for(MultipartFile f : attachfiles){
                ConsultingAttach atc = insAttachList.get(fileIdx++);

                // 파일 업로드
                FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, "FS", "");

                // 파일 저장[임시]
                ConsultingAttach consultingAttach = new ConsultingAttach(consultReqInfo.getSys_id(),consultReqInfo.getConsultCusInfo().getFs_no(), "0", atc.getAtch_kn_cd(), atc.getAtch_nm(),  String.valueOf(f.getSize()),
                atc.getAtch_ref_id(), ftpUploadReturn.getDirectory(), f.getOriginalFilename(), ftpUploadReturn.getFilename(), "Y", userId, "", userId, "", "");

                consultMgtService.setAttachIns(consultingAttach);   // DB 직접 저장
            }
        
        }

        // 채팅 등록
        consultMgtService.setChatStartIns(new ConsultChatStartDTO(consultReqInfo.getSys_id(), consultReqInfo.getConsultCusInfo().getFs_no(), 
            consultReqInfo.getConsultCusInfo().getCescoMainManager(), "1", null
            , "안녕하세요, 컨설팅을 진행하세요.", "02", "Y", userId, userId ));
        
        return new ResponseEntity<>("컨설팅 수정 완료", HttpStatus.OK);
    }

    // 협력사 구성 즐겨찾기 등록
    @PostMapping(value="/favoritesIns")
    public ResponseEntity<String> setFavoritesIns(
        @RequestPart(value = "consultBookMst") ConsultBookMstDTO consultBookMst, 
        @RequestPart(value = "consultBookDtlList") List<ConsultBookDtlDTO> consultBookDtlList,        
        HttpSession session) throws Exception {

        String userId = (String)session.getAttribute("userId");

        if(userId.isBlank())
            throw new CommDuplicateException("",ErrorCode.USER_ID_DUPLICATION);
        
        // 즐겨찾기 MST 등록
        Integer bookSeq = consultMgtService.setBookMstIns(userId, consultBookMst);

        // 즐겨찾기 DTL 등록
        consultMgtService.setBookDtlIns(userId, bookSeq, consultBookDtlList);
        
        return new ResponseEntity<>("즐겨찿기가 등록 되었습니다.", HttpStatus.OK);
    }

    // 협력사 구성 즐겨찾기 삭제
    @GetMapping(value="/favoritesListDel")
    public ResponseEntity<String> setFavoritesListDel(@RequestParam("dellList") List<Integer> delList, HttpSession session) throws Exception{
        
        String userId = (String)session.getAttribute("userId");

        if(userId.isBlank())
            throw new CommDuplicateException("",ErrorCode.USER_ID_DUPLICATION);

        if(delList != null){
            
            for(Integer book_seq : delList){                
                consultMgtService.setBookMstDel(book_seq, userId);
            }
        }

        return new ResponseEntity<>("즐겨찿기가 삭제 되었습니다.", HttpStatus.OK);
    }

    // 즐겨찾기 목록
    @GetMapping(value = "/favoritesList")
    public List<ConsultBookMstDTO> getFavoritesList(@RequestParam("book_nm") String book_nm, HttpSession session) throws Exception{
        
        String userId = (String)session.getAttribute("userId");

        if(userId.isBlank())
            throw new CommDuplicateException("",ErrorCode.USER_ID_DUPLICATION);        

        return consultMgtService.getBookMstList(userId, book_nm);
    }

    // 즐겨찾기 협력사 목록
    @GetMapping(value="/favoritesDtlList") 
    public List<ConsultBookDtlDTO> getFavoritesDtlList(Integer book_seq, HttpSession session) throws Exception{

        String userId = (String)session.getAttribute("userId");

        if(userId.isBlank())
            throw new CommDuplicateException("",ErrorCode.USER_ID_DUPLICATION);

        return consultMgtService.getBookDtlList(book_seq);
    }

    // 즐겨찾기 협력사 적용
    @GetMapping(value="/getAddCusList")
    public List<ConsultCusTitleInfoDTO> getAddCusList(
        @RequestParam("bookMstList") List<Integer> bookMstList, 
        @RequestParam("bookDtlList") Set<String> bookDtlList
        ) throws Exception{  
            
        List<ConsultCusTitleInfoDTO> titleList = new ArrayList<>();
        Set<String> cusList = new LinkedHashSet<String>();
        
        for(Integer bookSeq : bookMstList){

            List<ConsultBookDtlDTO> consultBookList = consultMgtService.getBookDtlList(bookSeq);

            for(ConsultBookDtlDTO bookDtl : consultBookList){
                cusList.add(bookDtl.getCst_ref_id());
            }

        }        

        cusList.removeAll(bookDtlList);

        for(String cst_id : cusList){
            titleList.add(consultMgtService.getCusTitleInfo(cst_id));
        }

        return titleList;
    }    

    // 계약자 리스트
    @GetMapping(value = "/contList")
    public List<ConsultStaffDTO> getContList(@RequestParam("user_nm") String user_nm) throws Exception{

        if(user_nm == null)
            user_nm = "";
            
        return consultMgtService.getContList(user_nm);

    }

}