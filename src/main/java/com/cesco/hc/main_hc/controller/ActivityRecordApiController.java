package com.cesco.hc.main_hc.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import com.cesco.hc.main_hc.dto.ActivityRecordDto;
import com.cesco.hc.main_hc.dto.ActivityRecordSetDto;
import com.cesco.sys.comm.dto.MailDto;
import com.cesco.hc.main_hc.service.MainService_hc;
import com.cesco.sys.common.Mail;
import com.cesco.hc.consulting_hc.service.ConsultingListService_hc;
import com.cesco.sys.comm.service.CommService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.cesco.sys.common.Mail;
import com.cesco.sys.common.Utils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main_hc")
public class ActivityRecordApiController {
    private static final Logger logger = LogManager.getLogger(ActivityRecordApiController.class);
    @Autowired
    private final MainService_hc mainservice_hc;
    
    @Autowired
    private final CommService commService;
    /**
     * 수행일지
     * @return
     */
    @RequestMapping(value = "/getActivityRecord")
    @ResponseBody
    public ActivityRecordDto getActivityRecord(ActivityRecordDto param, HttpSession session) throws Exception{
        logger.info("오픈팝업테스트");
        return mainservice_hc.getActivityRecord(param,session);
    }
    
    /**
     * 
     * 수행일지 저장
     * @return
     */
    @RequestMapping(value = "/setActivityRecord")
    @ResponseBody
    public int setActivityRecord(ActivityRecordSetDto param) throws Exception{
        logger.info("컨트롤러테스트");
        return mainservice_hc.setActivityRecord(param);
    }

    /**
     * 멘션 안내 메일발송
     * @return
     */
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestBody MailDto param, HttpSession session) {
        // if((param.getStatus()).equals("1")){
        //     param.setProd_nm("완료확인서 저장");
        // }else if((param.getStatus()).equals("2")){
        //     param.setProd_nm("완료확인서 수정");
        // }
        Mail mail = new Mail(commService);
        // 인증 번호 생성
        Integer intval = Utils.generateAuthNo();
        // 이메일 내용 저장
        MailDto eMailDto = MailDto.builder()
            .user_id(param.getUser_id()) 
            .sys_id(param.getSys_id())   
            .send_tp_cd("06")   
            //.send_nm(param.getSend_nm())    
            .send_nm("테스트")    
            //.receive_nm(param.getReceive_nm())   
                .receive_nm("받는사람")   
            //.receive_email(param.getReceive_email())
            .receive_email("oh5451@cesco.co.kr")
            .init_code(intval.toString())
            .send_url("")
            .send_text("수행일지")   
            .status("")   
            .cst_nm("")   
            .prod_nm(param.getProd_nm())    
            .send_chat_st_nm("").build();

        String subject = "수행일지 작성 알림";
        // 이메일 전송
        mail.sendMail(eMailDto,subject);
        return new ResponseEntity<>(intval.toString(),HttpStatus.OK);
    }
    @RequestMapping(value ="/setMsgAlarm")
    @ResponseBody
    public ResponseEntity<String> setMsgAlarm(@RequestBody ActivityRecordSetDto param) throws Exception{
        logger.info("알림톡컨트롤러");
        int result = mainservice_hc.setMsgAlarm(param);

        if(result == -1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
