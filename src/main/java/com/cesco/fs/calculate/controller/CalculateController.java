package com.cesco.fs.calculate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculate")
public class CalculateController {

    /**
     * 정산 목록
     * @return
     */
    @GetMapping("/calculateList")
    public String calculateSettlementList(){
        return "/customer/calculate/calculateList";
    }

    /**
     * 정산 단가 수정 팝업
     * @return
     */
    @GetMapping("/settlementUnitPriceCorrPop")
    public String calculateSettlementUnitPriceCorrPop(){
        return "/customer/calculate/settlementUnitPriceCorrPop";
    }
    
    /**
     * 정산 서비스 내역서 비고 팝업
     * @return
     */
    @GetMapping("/settlementSvcDetailsPop")
    public String calculateSettlementSvcDetailsPop(){
        return "/customer/calculate/settlementSvcDetailsPop";
    }
    
}
