package com.cesco.fs.consultMgt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comm")
public class ConsultMgtController {
   /*
     * FSCCS10045/컨설팅관리 목록 	coslMgtList
    */
    @GetMapping("/consult/coslMgtList")
    public String coslMgtList() {
        return "/system/consult/coslMgtList";        
    }

    /*
     * FSCCS10047/컨설팅관리 목록 - 신규 컨설팅 등록 팝업	coslMgtNewCoslRegPop
    */
    @GetMapping("/consult/coslMgtNewCoslRegPop")
    public String coslMgtNewCoslRegPop() {
        return "/system/consult/coslMgtNewCoslRegPop";        
    }

    /*
      * FSCCS10049/컨설팅관리 목록 - 의뢰 컨설팅 등록 팝업	coslMgtRequestCoslRegPop
     */
     @GetMapping("/consult/coslMgtRequestCoslRegPop")
     public String coslMgtRequestCoslRegPop() {
         return "/system/consult/coslMgtRequestCoslRegPop";        
     }

    /*
     * FSCCS10076/컨설팅관리 목록 - 신규 컨설팅 등록 팝업 - 협력사 구성 즐겨찾기 등록 팝업
    */
    @GetMapping("/consult/coslMgtPartnerConfRegPop")
    public String coslMgtPartnerConfRegPop() {
        return "/system/consult/coslMgtPartnerConfRegPop";        
    }

    /*
     * FSCCS10077/컨설팅관리 목록 - 신규 컨설팅 등록 팝업 - 협력사 구성 즐겨찾기 조회 팝업	coslMgtPartnerConfFavorPop
    */
    @GetMapping("/consult/coslMgtPartnerConfFavorPop")
    public String coslMgtPartnerConfFavorPop() {
        return "/system/consult/coslMgtPartnerConfFavorPop";        
    }
}
