package com.cesco.sys.comm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {
    
    @GetMapping("/chat")
    public String chatGET(){

        log.info("@ChatController, chat GET() 채팅호출.");
        
        return "chat/chat";
    }
}
