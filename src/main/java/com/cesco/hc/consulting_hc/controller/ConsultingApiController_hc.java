package com.cesco.hc.consulting_hc.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cesco.fs.consultMgt.service.ConsultMgtService;
import com.cesco.fs.consulting.dto.ConsChatReqDto;
import com.cesco.hc.consulting_hc.dto.CompletAttachDto;
import com.cesco.hc.consulting_hc.dto.CompletAttachListDto;
import com.cesco.hc.consulting_hc.dto.ConCompletReqDto;
import com.cesco.hc.consulting_hc.dto.ConCompleteDto;
import com.cesco.hc.consulting_hc.dto.ConsultingReqDto;
import com.cesco.hc.consulting_hc.dto.ConsultingResDto;
import com.cesco.hc.consulting_hc.dto.HcgetChatStatusDto;
import com.cesco.hc.consulting_hc.dto.NonServiceVisitDateRegDto;
import com.cesco.hc.consulting_hc.dto.TbFsHcEtcdayDto;
import com.cesco.hc.consulting_hc.dto.TbFsHcMdayDto;
import com.cesco.hc.consulting_hc.service.ConsultingListService_hc;
import com.cesco.sys.comm.dto.MailDto;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.Mail;
import com.cesco.sys.common.Utils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hcConsulting")
public class ConsultingApiController_hc {
    private static final Logger logger = LogManager.getLogger(ConsultingApiController_hc.class);

    @Autowired
    private final ConsultingListService_hc consultingService;
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
    public List<ConsultingResDto> getList(ConsultingReqDto params, HttpSession session) throws Exception{
        if(((String)session.getAttribute("userTpCd")).equals("03")){
            params.setP_type("2");
        }else{
            params.setP_type("1");
        }
        params.setRgstr_id((String)session.getAttribute("userId"));
        return consultingService.getList(params);
    }

    /**
     * haccp 완료확인서
     * @return
     */
    @RequestMapping(value = "/getComplete")
    @ResponseBody
    public ConCompleteDto getComplete(ConCompletReqDto param, HttpSession session) throws Exception{
        return consultingService.getComplete(param, session);
    }

    /**
     * 완료확인서 첨부파일
     * @param param
     * @return
     * @throws Exception
     */
    @GetMapping(value="/getCompletAttach")
    @ResponseBody
    public List<CompletAttachDto> getCompletAttach(CompletAttachListDto completListDto) throws Exception {
        return consultingService.getCompletAttachList(completListDto);
    }
    /**
     * haccp 완료확인서 저장
     * @return
     */
    @RequestMapping(value = "/setComplete")
    @ResponseBody
    public int setComplete(ConCompleteDto param) throws Exception{
        return consultingService.setComplete(param);
    }

    @RequestMapping(value = "/setReqUpdate")
    @ResponseBody
    public ResponseEntity<String> setReqUpdate(@RequestBody ConCompleteDto param) throws Exception{
        int result = consultingService.setReqUpdate(param);

        if(result == 1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        //return consultingService.setReqUpdate(param);
    }
    
    @PostMapping("/setAttachInfo")
	public ResponseEntity<String> setAttachInfo (
        @RequestPart(value = "completInfo", required = false) ConCompleteDto completInfo,
        @RequestPart(value = "files", required = false) List<MultipartFile> fileList,
        @RequestPart(value = "delAttachList", required = false) List<CompletAttachDto> delAttachList,
        HttpSession session) throws Exception{
            
        consultingService.setAttachInfo(completInfo, fileList, delAttachList, session);
        return new ResponseEntity<>("저장 성공", HttpStatus.OK);
	}

    /**
     * 멘션 안내 메일발송
     * @return
     */
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestBody MailDto param, HttpSession session) {
        if((param.getStatus()).equals("1")){
            param.setProd_nm("완료확인서 작성 완료");
        }else if((param.getStatus()).equals("2")){
            param.setProd_nm("완료확인서 수정 요청");
        }
        Mail mail = new Mail(commService);
        // 인증 번호 생성
        Integer intval = Utils.generateAuthNo();
        // 이메일 내용 저장
        MailDto eMailDto = MailDto.builder()
            .user_id(param.getUser_id()) 
            .sys_id(param.getSys_id())   
            .send_tp_cd("06")   
            .send_nm(param.getSend_nm())    
            .receive_nm(param.getReceive_nm())   
            //.receive_email(param.getReceive_email())
            .receive_email("udding@cesco.co.kr")
            .init_code(intval.toString())
            .send_url("")
            .send_text("완료확인서")   
            .status("")   
            .cst_nm("")   
            .prod_nm(param.getProd_nm())    
            .send_chat_st_nm("").build();

        String subject = "완료확인서 작성 알림";
        // 이메일 전송
        mail.sendMail(eMailDto,subject);
        return new ResponseEntity<>(intval.toString(),HttpStatus.OK);
    }

    @RequestMapping(value ="/setMsgAlarm")
    @ResponseBody
    public ResponseEntity<String> setMsgAlarm(@RequestBody ConCompleteDto param) throws Exception{
        int result = consultingService.setMsgAlarm(param);

        if(result == -1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @apiNote 수행일지 조회
     * @param   ConsultingReqDto
     * @return ActivityRecordDto
     */
    @GetMapping(value = "/getCoslMDayList")
    public List<TbFsHcMdayDto> getCoslMDayList(ConsChatReqDto params) throws Exception{
        return consultingService.uspHcgetCoslMDayList(params);
    }
    /**
     * @apiNote 시간외방문내역조회
     * @param   ConsultingReqDto
     * @return TbFsHcEtcdayDto
     */
    @GetMapping(value = "/getCoslEtcDayList")
    public List<TbFsHcEtcdayDto> getCoslEtcDayList(ConsChatReqDto params) throws Exception{
        return consultingService.uspHcgetCoslEtcDayList(params);
    }
    /**
     * @apiNote 협업툴안에서 진행상태 표시
     * @param   ConsultingReqDto
     * @return HcgetChatStatusDto
     */
    @GetMapping(value = "/getChatStatusList")
    public List<HcgetChatStatusDto> getChatStatusList(ConsChatReqDto params) throws Exception{
        return consultingService.uspHcgetChatStatusList(params);
    }

    /**
     * @apiNote 협업툴안에서 진행상태 표시
     * @param   ConsultingReqDto
     * @return HcgetChatStatusDto
     */
    @PostMapping(value = "/visitConfirmation")
    public void visitConfirmation(@RequestBody NonServiceVisitDateRegDto date) throws Exception{
        
        consultingService.saveVisitConfirmation(date);
    }

    /**
     * @apiNote 서비스외 방문일 등록
     * @param   ConsultingReqDto
     * @return HcgetChatStatusDto
     */
    @PostMapping(value = "/nonServiceVisitDateReg")
    public void nonServiceVisitDateReg(@RequestBody NonServiceVisitDateRegDto date) throws Exception{
        
        consultingService.nonServiceVisitDateReg(date);
    }

    /**
     * @apiNote 서비스외 방문일 삭제
     * @param   ConsultingReqDto
     * @return HcgetChatStatusDto
     */
    @DeleteMapping(value = "/nonServiceVisitDateDel")
    public void nonServiceVisitDateDel(NonServiceVisitDateRegDto date) throws Exception{
        
        consultingService.nonServiceVisitDateDel(date);
    }


}
