package com.cesco.hc.calculate_hc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// import com.cesco.hc.calculate_hc.service.CalculateService_hc;

@Controller
@RequestMapping("/calculate")
public class CalculateController_hc {
    

    @Autowired
    // private CalculateService_hc calculateService_hc;
    


     /* 정산 목록
     * @return
     */
    @GetMapping("/settlementList")
    public String calculateSettlementList(){
        return "hc/calculate/calculateListAdmin_hc";
        // return "/hc/calculate/hc_2_정산_목록_고객";

    }

    /**
     * 정산 상세
     * @return
     */
    @RequestMapping("/settlementList/calculateDetail_hc")
    public String calculateDetail() {
        return "/hc/calculate/calculateListDetail_hc";
        // return "/hc/calculate/hc_2_정산_목록_고객";
    }
    // /**
    //  * 정산 단가 수정 팝업
    //  * @return
    //  */
    // @GetMapping("/settlementUnitPriceCorrPop")
    // public String calculateSettlementUnitPriceCorrPop(){
    //     return "/customer/calculate/settlementUnitPriceCorrPop";
    // }
    
    // /**
    //  * 정산 서비스 내역서 비고 팝업
    //  * @return
    //  */
    // @GetMapping("/settlementSvcDetailsPop")
    // public String calculateSettlementSvcDetailsPop(){
    //     return "/customer/calculate/settlementSvcDetailsPop";
    // }


}
