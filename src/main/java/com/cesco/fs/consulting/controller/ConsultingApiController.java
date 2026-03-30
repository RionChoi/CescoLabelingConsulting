package com.cesco.fs.consulting.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cesco.fs.consultMgt.service.ConsultMgtService;
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
import com.cesco.fs.consulting.service.ConsultingService;
import com.cesco.sys.comm.dto.ConsultingAttach;
import com.cesco.sys.comm.dto.FtpUploadReturn;
import com.cesco.sys.comm.dto.MailDto;
import com.cesco.sys.comm.dto.UserDTO;
import com.cesco.sys.comm.dto.UserReqDTO;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.common.Mail;
import com.cesco.sys.common.Utils;
import com.cesco.sys.communityhandlers.CommDuplicateException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consulting")
public class ConsultingApiController {
    private static final Logger logger = LogManager.getLogger(ConsultingApiController.class);

    @Autowired
    private final ConsultingService constltingService;
    @Autowired
    private final CommService commService;
    @Autowired
    ConsultMgtService consultMgtService;

    /**
     * @apiNote 표시컨설팅_목록 조회
     * @param   ConsultingReqDto
     * @return UserDTO
     */
    @GetMapping(value = "/getList")
    public List<ConsultingResDto> getList(ConsultingReqDto params) throws Exception{
        return constltingService.getList(params);
    }

    /**
     * 표시컨설팅_목록삭제
     * @return
     * @throws Exception
     */        
    @PutMapping("/deleteMst")
    public ResponseEntity<String> deleteMst(@RequestBody ConsultingNewReq param) throws Exception {        
       
        Integer res =  constltingService.deleteMst(param);
        if (res > 0) {
            return new ResponseEntity<>("컨설팅의뢰내역이 취소되였습니다..",HttpStatus.OK);
        } else {
            throw new CommDuplicateException("컨설팅의뢰내역이 실패 하였습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
    }
    /**
     * @apiNote 표시컨설팅_담당자정보조회
     * @param   ConsultingReqDto
     * @return UserDTO
     */
    @GetMapping(value = "/getMngInfo")
    public List<ConsultingMngDto> getMngInfo(ConsultingReqDto params) throws Exception{
        return constltingService.getMngInfo(params);
    }    
	/**
	 * 신규컨설팅의뢰
	 * @param userId
     * @param itemList      //의뢰리스트
     * @param fileInfoList  //파일정보
     * @param files         //파일
     * @param session
	 * @throws Exception
	 */
	@PostMapping("/saveConsultingNew")
	public ResponseEntity<String> saveConsultingNew (
        @RequestPart(value ="itemList") List<ConsultingNewReq> itemList,
        @RequestPart (value = "fileInfoList") List<ConsultingAttach> fileInfoList,
        @RequestPart(value = "files", required = false) List<MultipartFile> files,HttpSession session) throws Exception{
        // 로그인 ID
        String userId = (String)session.getAttribute("userId");
        String sysCode = (String)session.getAttribute("sysCode");
        String cstcd = (String)session.getAttribute("cstcd");
        String fsNoMst = "";
        String fileSizeNm = "";
        if(itemList != null && itemList.size() > 0){
             // FS_NO 컨설팅 번호 생성
             try {
                fsNoMst = consultMgtService.getFsMstNo();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            for(ConsultingNewReq data : itemList) {
                try {
                    data.setUser_id(userId);
                    data.setSys_id(sysCode);
                    data.setFs_no(fsNoMst);
                    data.setCst_id(cstcd);
                    // 신규컨설팅의뢰 등록
                    Integer res = constltingService.saveConsultingNew(data);
                    if (res<1) {
                        throw new CommDuplicateException("신규의뢰 실페하었습니다.",ErrorCode.SAVE_NEW_CONSULTING_DUPLICATION);
                    }
                } catch (Exception e) {
                    throw new CommDuplicateException("",ErrorCode.SAVE_NEW_CONSULTING_DUPLICATION);
                }
            }

            try {
                // 파일 저장
                // commService.multipartFileUpload(sysCode,userId,files);
                if(files != null){            
                    int fileIdx = 0;
        
                    for(MultipartFile f : files){
                        ConsultingAttach atc = fileInfoList.get(fileIdx++);
                        long fileSize = 0;
                        fileSize = f.getSize();
                        fileSizeNm = Utils.getFileSize(fileSize);
                        // 파일 업로드
                        FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, "FS",atc.getAtch_nm());
                        ConsultingAttach consultingAttach = ConsultingAttach.builder()
                            .sys_id(itemList.get(0).getSys_id())
                            .fs_no(itemList.get(0).getFs_no())
                            .atch_kn_cd(atc.getAtch_kn_cd())
                            .atch_nm(atc.getAtch_nm())
                            .atch_ref_id(atc.getAtch_ref_id())
                            .atch_size(fileSizeNm)
                            .url(ftpUploadReturn.getDirectory())
                            .ser_file_nm(ftpUploadReturn.getFilename())
                            .org_file_nm(atc.getAtch_nm())
                            .status("Y")
                            .rgstr_id(userId)
                            .mdfr_id(userId).build();
                        consultMgtService.setAttachIns(consultingAttach);   // DB 직접 저장[임시
                    }
                }
            } catch (IOException e) {
                throw new CommDuplicateException("",ErrorCode.UPLOAD_FAIL);
            }
        }
        return new ResponseEntity<>("신규의뢰 등록되었습니다.",HttpStatus.OK);

	}

    /**
     * 표시컨설팅 체팅 글 조회
     * @return
     */
    @GetMapping("/consult/uspFsGetChatDtLList")
    public List<ConsUspFsGetChatDtLListDto> uspFsGetChatDtLList(ConsChatReqDto consChatReqDto) throws Exception {
        return constltingService.uspFsGetChatDtLList(consChatReqDto);
    }

    /**
     * 컨설팅 체팅 파일 목록
     * @return
     */
    @GetMapping("/consult/uspFsGetChatAtchList")
    public List<ConsUspFsGetChatAtchListDto> uspFsGetChatAtchList(ConsChatReqDto consChatReqDto) throws Exception {
        return constltingService.uspFsGetChatAtchList(consChatReqDto);
    }

    /**
     * 컨설팅 체팅 방 멤버 조회
     * @return
     */
    @GetMapping("/consult/uspFsGetChatMemAddList")
    public List<ConsUspFsGetChatMemAddListDto> uspFsGetChatMemAddList(ConsChatReqDto consChatReqDto) throws Exception {
        return constltingService.uspFsGetChatMemAddList(consChatReqDto);
    }

    /**
     * 컨설팅 진행 상태 조회
     * @return
     */
    @GetMapping("/consult/uspFsGetChatStatusList")
    public List<ConsUspFsGetChatStatusListDto> uspFsGetChatStatusList(ConsChatReqDto consChatReqDto) throws Exception {
        return constltingService.uspFsGetChatStatusList(consChatReqDto);
    }

    /**
     * 표시컨설팅_채팅창 글 등록
     * @return
     */
    @PostMapping("/consult/uspFsSetChatDtLList")
    public ResponseEntity<String> uspFsSetChatDtLList(@RequestBody ConsChatReqDto consChatReqDto,HttpSession session) throws Exception {
        consChatReqDto.setStatus("Y");
        try {
            constltingService.uspFsSetChatDtLList(consChatReqDto);
        } catch (Exception e) {
            return new ResponseEntity<String>("글 등록 실페했습니다.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("등록 되었습니다.",HttpStatus.OK);
    }

    /**
     * 표시컨설팅_채팅창 파일 등록
     * @return
     */ 
    @PostMapping("/consult/uspFsSetChatFileList")
    public ResponseEntity<String> uspFsSetChatFileList(
        @RequestPart(value ="chatInfo") ConsChatReqDto chatInfo,
        @RequestPart (value = "chatFileInfo") List<ConsultingAttach> chatFileInfo,
        @RequestPart(value = "files", required = false) List<MultipartFile> files,HttpSession session
    ) throws Exception {
        String userId = (String)session.getAttribute("userId");
        String sysCode = (String)session.getAttribute("sysCode");
        String fileSizeNm ="";
        try {
           // 파일 저장
            // commService.multipartFileUpload(sysCode,userId,files);
            if(files != null){            
                int fileIdx = 0;
    
                for(MultipartFile f : files){
                    if (chatFileInfo.size() > 0) {
                        ConsultingAttach atc = chatFileInfo.get(fileIdx++);
                        long fileSize = 0;
                        fileSize = f.getSize();
                        fileSizeNm = Utils.getFileSize(fileSize);
                        // 파일 업로드
                        FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, "FS",atc.getAtch_nm());

                        ConsultingAttachChatDto consultingAttach = ConsultingAttachChatDto.builder()
                            .sys_id(sysCode)
                            .fs_no(chatInfo.getFs_no())
                            .fs_chat_id(chatInfo.getFs_chat_id())
                            .fs_chat_seq(chatInfo.getFs_chat_seq())
                            .atch_kn_cd("00001")
                            .atch_nm(atc.getAtch_nm())
                            .atch_size(fileSizeNm)
                            .atch_ref_id("")
                            .url(ftpUploadReturn.getDirectory())
                            .org_file_nm(atc.getAtch_nm())
                            .ser_file_nm(ftpUploadReturn.getFilename())
                            .status("Y")
                            .rgstr_id(userId)
                            .mdfr_id(userId)
                            .ssmm("")
                            .interest_yn("N")
                            .build();
                        // 채팅파일 정보 저장
                        constltingService.saveUspFsSetAttachChat(consultingAttach);   // DB 직접 저장
                    }
                }
            }
        } catch (Exception e) {
            throw new CommDuplicateException("",ErrorCode.UPLOAD_FAIL);
        }
        return new ResponseEntity<String>("등록 되었습니다.",HttpStatus.OK);
    }

    /**
     * 표시컨설팅_채팅창 멤버 추가
     * @return
     */
    @PutMapping("/consult/uspFsSetChatMem")
    public ResponseEntity<String> uspFsSetChatMem(@RequestBody ConsChatReqDto consChatReqDto) throws Exception{
       
        Integer res = constltingService.uspFsSetChatMem(consChatReqDto);
        if(res > 0){
            if (res == 1) {
                return new ResponseEntity<>("멤버 추가 되었습니다.",HttpStatus.OK);
                
            } else {
                return new ResponseEntity<>("멤버 삭제 되었습니다.",HttpStatus.OK);
            }
        } else {
            throw new CommDuplicateException("멤버 변경 실페했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
    }
    
    /**
     * 멘션 안내 메일발송
     * @return
     * @throws Exception
     */
    @PostMapping("/consult/sendMail")
    public ResponseEntity<String> sendMail(@RequestBody ConsChatReqDto param) throws Exception {
        // 인증 번호 생성
        Integer intval = Utils.generateAuthNo();
        Mail mail = new Mail(commService);
        UserReqDTO userReqDto = new UserReqDTO();
        userReqDto.setEmail(param.getEmail());
        // 이력조회
        List<UserDTO> res = commService.getUserHis(userReqDto);
        
        if (res.size() > 0) {
            
            // 이메일 내용 저장
            MailDto eMailDto = MailDto.builder()
                .user_id(res.get(0).getUser_id()) 
                .sys_id(param.getSys_id())   
                .send_tp_cd(param.getD_type())   
                .send_nm(res.get(0).getUser_nm())    
                .receive_nm(param.getFs_chat_nm())   
                .receive_email(param.getEmail())
                .init_code(intval.toString())
                .send_url("")
                .send_text(param.getContents())    
                .status("")   
                .cst_nm("")   
                .prod_nm(param.getProd_nm())    
                .send_chat_st_nm(param.getFs_status_nm()).build();

            String subject = param.getContents();
            // 이메일 전송
            mail.sendMail(eMailDto,subject);
        } else {
            throw new CommDuplicateException("사용자 정보 없습니다. 확인 후 다시 시도 해주세요",ErrorCode.INTER_SERVER_ERROR);
        }
        return new ResponseEntity<>(intval.toString(),HttpStatus.OK);
    }

    /**
     * 컨설팅 체팅 진행 상태 수정
     * @return
     * @throws Exception
     */
    @PutMapping("/consult/modifyFsStatusCd")
    public ResponseEntity<String> modifyFsStatusCd(@RequestBody ConsChatReqDto param) throws Exception {

        Integer res =  constltingService.modifyFsStatusCd(param);
        if (res > 0) {
            return new ResponseEntity<>("진행 상태 변경 되었습니다.",HttpStatus.OK);
        } else {
            throw new CommDuplicateException("진행 상태 변경 실페하였습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
    }

    /**
     * 표시컨설팅_채팅창_파일더보기
     * @return
     */
    // @GetMapping("/consult/indicationCoslChatMore")
    // public String customIndicationCoslChatMore() {
    //     return "/customer/consult/indicationCoslChatMore";
    // }

    /**
     * 표시컨설팅_채팅창
     * @return
     */
    // @GetMapping("/consult/indicationCoslChat")
    // public String customIndicationCoslChat() {
    //     return "/customer/consult/indicationCoslChat";
    // }

    /**
     * 표시컨설팅_채팅창
     * @return
     */
}
