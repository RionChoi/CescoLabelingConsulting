package com.cesco.co.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cesco.co.notice.dto.NoticeMgtDto;
import com.cesco.co.notice.service.NoticeMgtService;

@Controller
@RequestMapping("/notice")
public class NoticeMgtController {

    @Autowired
    NoticeMgtService noticeMgtService;
    
    /**
     * 공지사항_목록
     * @return
     */
    @GetMapping(value = "/noticeList")
    public String noticeList(Model model, NoticeMgtDto noticeMgtDto) throws Exception {
        return "/customer/notice/noticeList";
    }

    /**
     * 공지사항_상세
     * @return
     */
    @GetMapping(value = "/noticeDetails")
    public String noticeDetails(Model model, NoticeMgtDto noticeMgtDto) throws Exception {
        model.addAttribute("noticeMgtDto", noticeMgtDto);
        return "/customer/notice/noticeDetails";
    }

    /**
     * 공지사항등록 팝업
     * @return
     */
    @GetMapping("/noticeNoticeRegPop")
    public String noticeNoticeRegPop() throws Exception {
        return "/customer/notice/noticeNoticeRegPop";
    }

    /**
     * 공지사항수정 팝업
     * @return
     */
    @GetMapping("/noticeNoticeEditPop")
    public String noticeNoticeEditPop() throws Exception {
        return "/customer/notice/noticeNoticeEditPop";
    }

}
