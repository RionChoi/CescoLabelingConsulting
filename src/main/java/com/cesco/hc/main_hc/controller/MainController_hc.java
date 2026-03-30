package com.cesco.hc.main_hc.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Value;
import com.cesco.co.notice.dto.NoticeDto;
import com.cesco.co.notice.dto.NoticeMgtDto;
import com.cesco.co.notice.service.NoticeMgtService;
import com.cesco.fs.main.dto.MainDto;
import com.cesco.hc.main_hc.dto.ActivityRecordDto;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController_hc {

    private static final Logger logger = LogManager.getLogger(MainController_hc.class);
    @Autowired
    private NoticeMgtService noticeMgtService;

    @Value("${eformLoc}")
	private String EFORM_LOC;
	
	@Value("${eform.crf.dir}")
	private String EFORM_DIR;

    @GetMapping("")
    public String main() {
        return "/hc/main/main";
    }

    /**
     * main화면의 공지사항 목록
     * @param mainDto
     
     * @return
     * @throws Exception
     */
    @GetMapping(value="/main_hc/hcmain")
    @ResponseBody
    public MainDto postMethodName(MainDto mainDto, HttpSession session) throws Exception {

        // 공지사항 목록 가져오기
        NoticeMgtDto noticeMgtDto = new NoticeMgtDto();
        noticeMgtDto.setSys_id(mainDto.getSys_id());
        List<NoticeDto> noticeList = noticeMgtService.getNoticeList(noticeMgtDto,session);
        mainDto.setNoticeList(noticeList);
        logger.info("메인테스트");
        return mainDto;
        
    }
   
   
    /**
     * haccp 수행일지
     * @return
     */
     @RequestMapping(value = "/main_hc/getActivity")
    public String getActivityRecord(Model model, String sys_id, String fs_no, String fs_seq,String md_day,String user_id){
        model.addAttribute("eformDir", EFORM_DIR);
    	model.addAttribute("eformLoc", EFORM_LOC);
        model.addAttribute("sys_id", sys_id);
        model.addAttribute("fs_no", fs_no);
        model.addAttribute("fs_seq", fs_seq);
        model.addAttribute("md_day", md_day);
        model.addAttribute("user_id", user_id);
        logger.info("main컨트롤러테스트");
        return "/hc/main/SalesActivityPop";
    }
    
}

