package com.cesco.fs.serviceStatement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cesco.fs.serviceStatement.dto.ServiceStatementDto;

@Controller
@RequestMapping("/service")
public class ServiceStatementController {

    @Value("${eformLoc}")
	private String EFORM_LOC;
	
	@Value("${eform.crf.dir}")
	private String EFORM_DIR;

    /**
     * 서비스견적서_목록화면
     * @return
     */
    @GetMapping("/serviceStatementList")
    public String customServiceStatementList() {
        return "/customer/service/serviceStatementList";
    }

    /**
     * 서비스견적서_팝업화면(report)
     * @return
     */
    @GetMapping("/serviceStatementPop")
    public String customServiceStatementPop(Model model, ServiceStatementDto serviceStatementDto){

        model.addAttribute("eformDir", EFORM_DIR);
    	model.addAttribute("eformLoc", EFORM_LOC);
        model.addAttribute("serviceStmtData", serviceStatementDto);

        return "/customer/service/serviceStatementPop";
    }

}
