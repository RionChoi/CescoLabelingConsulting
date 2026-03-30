package com.cesco.fs.consulting.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/consulting")
public class ConsultingController {
    private static final Logger logger = LogManager.getLogger(ConsultingController.class);
    /**
     * 표시컨설팅_목록
     * @return
     */
    @RequestMapping("/consult/indicationCoslList")
    public String customIndicationCoslList() {
        return "/customer/consult/indicationCoslList";
    }

    /**
     * 표시컨설팅_신규컨설팅의뢰_팝업
     * @return
     */
    @RequestMapping("/consult/indicationCoslNewCoslResPop")
    public String customIndicationCoslNewCoslResPop() {
        return "/customer/consult/indicationCoslNewCoslResPop";
    }

    /**
     * 표시컨설팅_채팅창_파일더보기
     * @return
     */
    @RequestMapping("/consult/indicationCoslChatMore")
    public String customIndicationCoslChatMore() {
        return "/customer/consult/indicationCoslChatMore";
    }

    /**
     * 표시컨설팅_채팅창
     * @return
     */
    @RequestMapping(value = "/consult/indicationCoslChat")
    public String customIndicationCoslChat() {
        return "/customer/consult/indicationCoslChat";
    }

   
}
