package com.cesco.sys.comm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.sys.comm.dto.CommPopReqDTO;
import com.cesco.sys.comm.dto.UserDTO;
import com.cesco.sys.comm.service.CommPopupService;

@Controller
@RequestMapping("/commPopup")
public class CommPopupController {

    @Autowired
    private CommPopupService commPopupService;

     /**
     * 공통팝업 테스트 화면
     * @return
     */
    @GetMapping("/popupCallTest")
    public String popupCallTest() {
        return "/popupCallTest";
    }
    
    /**
     * 고객사검색_팝업 화면
     * @return
     */
    @GetMapping("/customerSearchPopup")
    public String customerSearchPopup(String cstNm, String checkboxYn, Model model) {
        if(StringUtils.hasLength(cstNm)) model.addAttribute("cstNm", cstNm);
        if(StringUtils.hasLength(checkboxYn)) model.addAttribute("checkboxYn", checkboxYn);
        return "/common/popup/customerSearchPopup";
    }

    /**
     * 담당자검색_팝업 화면
     * @return
     */
    @GetMapping("/personSearchPopup")
    public String personSearchPopup(String cstNm, String userNm, String checkboxYn, Model model) {
        if(StringUtils.hasLength(userNm)) model.addAttribute("userNm", userNm);
        if(StringUtils.hasLength(cstNm)) model.addAttribute("cstNm", cstNm);
        if(StringUtils.hasLength(checkboxYn)) model.addAttribute("checkboxYn", checkboxYn);

        return "/common/popup/personSearchPopup";
    }


    /**
     * 담당자 목록 (담당자 검색 팝업)
     * @return
     */
    @GetMapping("/getUserListForPop")
    @ResponseBody
    public List<UserDTO> getUserListForPop(@RequestParam String srchUserNm, String srchCstNm) {
        
        // 프로시저 type
        String pType = "00";
        // if(StringUtils.hasLength(srchCstNm) && !StringUtils.hasLength(srchPartnerNm)) {
        //     pType = "01";
        // } else if(StringUtils.hasLength(srchPartnerNm) && !StringUtils.hasLength(srchCstNm)) {
        //     pType = "02";
        // } else if(StringUtils.hasLength(srchCstNm) && StringUtils.hasLength(srchPartnerNm)) {
        //     pType = "03";
        // }

        CommPopReqDTO commPopReqDTO = new CommPopReqDTO();
        commPopReqDTO.setSrch_user_nm(srchUserNm);
        commPopReqDTO.setSrch_cst_nm(srchCstNm);
        commPopReqDTO.setP_type(pType);

        List<UserDTO> userList = commPopupService.getUserListForPop(commPopReqDTO);
        return userList;
    }

    /**
     * 고객사 목록 (고객사 검색 팝업)
     * @return
     */
    @GetMapping("/getCustomerListForPop")
    @ResponseBody
    public List<CusInfoDTO> getCustomerListForPop(@RequestParam String srchCstNm) {
        CommPopReqDTO commPopReqDTO = new CommPopReqDTO();
        commPopReqDTO.setSrch_cst_nm(srchCstNm);
        List<CusInfoDTO> customerList = commPopupService.getCustomerListForPop(commPopReqDTO);
        return customerList;
    }
}
