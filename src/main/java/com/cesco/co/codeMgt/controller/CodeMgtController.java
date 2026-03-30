package com.cesco.co.codeMgt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comm")
public class CodeMgtController {
  
     /*
     * FSCCS10060/코드관리 목록	codeMgtList
    */
    @GetMapping("/code/codeMgtList")
    public String codeMgtList() {
        return "/system/code/codeMgtList";        
    }

    /*
     * FSCCS10061/코드관리 목록 - 코드등록 팝업	codeMgtCodeRegPop
    */
    @GetMapping("/code/codeMgtCodeRegPop")
    public String codeMgtCodeRegPop() {
        return "/system/code/codeMgtCodeRegPop";        
    }

    /*
     * FSCCS10062/코드관리 목록 - 코드수정 팝업	codeMgtCodeModificationPop
    */
    @GetMapping("/code/codeMgtCodeModificationPop")
    public String codeMgtCodeModificationPop() {
        return "/system/code/codeMgtCodeModificationPop";        
    }

    /*
     * FSCCS10063/코드관리 목록 - 코드정렬 수정 팝업	codeMgtCodeAlignmentEditPop
    */
    @GetMapping("/code/codeMgtCodeAlignmentEditPop")
    public String codeMgtCodeAlignmentEditPop() {
        return "/system/code/codeMgtCodeAlignmentEditPop";        
    }
}
