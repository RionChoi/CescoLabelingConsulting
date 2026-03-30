package com.cesco.fs.main.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cesco.co.notice.dto.NoticeDto;
import com.cesco.co.notice.dto.NoticeMgtDto;
import com.cesco.co.notice.service.NoticeMgtService;
import com.cesco.fs.main.dto.MainConsultingDto;
import com.cesco.fs.main.dto.MainDto;
import com.cesco.fs.main.service.MainService;

@Controller
@RequestMapping("/fs/main")
public class MainController {

    @Autowired
    private NoticeMgtService noticeMgtService;

    @Autowired
    private MainService mainService;

    /**
     * main
     * @return
     */
    @GetMapping("")
    public String main() {
        return "/main";
    }

    /**
     * main화면의 공지사항 목록, 금일 완료 예정 컨설팅 목록
     * @param mainDto
     * @return
     * @throws Exception
     */
    @GetMapping(value="/mainList")
    @ResponseBody
    public MainDto postMethodName(MainDto mainDto, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();

        // 공지사항 목록 가져오기
        NoticeMgtDto noticeMgtDto = new NoticeMgtDto();
        List<NoticeDto> noticeList = noticeMgtService.getNoticeList(noticeMgtDto, session);
        
        // 금일 완료 예정 컨설팅 목록 가져오기
        List<MainConsultingDto> mianConsultingList = mainService.getTobeComplTdConsultList(mainDto, session);

        // return
        mainDto.setNoticeList(noticeList);
        mainDto.setMainConsultingList(mianConsultingList);
        
        return mainDto;
    }
    
}
