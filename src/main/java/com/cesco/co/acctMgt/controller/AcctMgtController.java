package com.cesco.co.acctMgt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comm")
public class AcctMgtController {

     /*
     * FSCCS10051/고객사계정 목록	AcctMgtCusAcctList [계정관리]
    */
    @GetMapping("/account/AcctMgtCusAcctList")
    public String AcctMgtCusAcctList() {
        return "system/account/AcctMgtCusAcctList";        
    }

    /*
     * FSCCS10054/고객사계정 목록 - 고객 계정 등록 팝업	AcctMgtCusAcctRegPop
    */
    @GetMapping("/account/AcctMgtCusAcctRegPop")
    public String AcctMgtCusAcctRegPop() {
        return "system/account/AcctMgtCusAcctRegPop";
    }

    /*
     * FSCCS10055/고객사계정 목록 - 고객 계정 수정 팝업	AcctMgtCusAcctEditPop
    */
    @GetMapping("/account/AcctMgtCusAcctEditPop")
    public String AcctMgtCusAcctEditPop() {
        return "system/account/AcctMgtCusAcctEditPop";        
    }
    
    /*
     * FSCCS10058/Admin계정 목록 - 관리가 계정 등록  팝업	AcctMgtAdmAcctRegPop
    */
    @GetMapping("/account/AcctMgtAdmAcctRegPop")
    public String AcctMgtAdmAcctRegPop() {
        return "system/account/AcctMgtAdmAcctRegPop";        
    }

    /*
     * FSCCS10059/Admin계정 목록 - 관리가 계정 수정  팝업	AcctMgtAdmAcctEditPop
    */
    @GetMapping("/account/AcctMgtAdmAcctEditPop")
    public String AcctMgtAdmAcctEditPop() {
        return "system/account/AcctMgtAdmAcctEditPop";        
    }

    /*
     * FSCCS10053/Admin계정 목록	AccMgtAdmAcctList
    */
    @GetMapping("/account/AccMgtAdmAcctList")
    public String AccMgtAdmAcctList() {
        return "system/account/AccMgtAdmAcctList";        
    }

    // 그리드 샘플
    @GetMapping("/account/AccGridExample")
    public String AccGridExample() {
        return "system/account/AccGridExample";        
    }

    // html 페이지 보기
    @GetMapping("/account/view_org")
    public String view_org() {
        return "system/account/view_org";
    }
    
}
