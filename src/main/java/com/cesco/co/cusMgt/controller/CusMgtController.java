package com.cesco.co.cusMgt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comm")
public class CusMgtController {
  
   /*
     * FSCCS10036/고객사관리 목록 cusMgtList
    */
    @GetMapping("/customer/cusMgtList")
    public String cusMgtList() {
        return "system/customer/cusMgtList";
    }
    /*
     * FSCCS10037/고객사관리 목록 - 고객사 등록 팝업  cusMgtCusRegPop
    */
    @GetMapping("/customer/cusMgtCusRegPop")
    public String cusMgtCusRegPop() {
        return "system/customer/cusMgtCusRegPop";        
    }

    /*
     * FSCCS10038/고객사관리 목록 - 고객사 수정 팝업	cusMgtCusAffairsPop
    */
    @GetMapping("/customer/cusMgtCusAffairsPop")
    public String cusMgtCusAffairsPop() {
        return "system/customer/cusMgtCusAffairsPop";        
    }


}
