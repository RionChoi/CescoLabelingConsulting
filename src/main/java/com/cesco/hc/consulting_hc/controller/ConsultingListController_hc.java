package com.cesco.hc.consulting_hc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/consulting")
public class ConsultingListController_hc {
    private static final Logger logger = LogManager.getLogger(ConsultingListController_hc.class);

    // @Value("${ECMLoc}")
    // private String ECM_Loc;
    
	@Value("${eformLoc}")
	private String EFORM_LOC;
	
	@Value("${eform.crf.dir}")
	private String EFORM_DIR;


    /**
     * haccp컨설팅_목록 (정다원 11-14new)
     * @return
     */
    @RequestMapping("/consult/hcConsultList")
    public String haccpConsultList(Model model) {
        model.addAttribute("eformDir", EFORM_DIR);
    	model.addAttribute("eformLoc", EFORM_LOC);
        return "/hc/consult/hcConsultList";
    }

    public String consultMngList_hc() {
        return "/hc/consultManage/consultMngList_hc";

    }

    /**
     * haccp 컨설팅 상세보기
     * @return
     */
    @RequestMapping("/consult/hcConsultChatDetail")
        public ModelAndView hcConsultChatDetail(ModelAndView mv) {
        mv.setViewName("/hc/consult/hcConsultChatDetail");
        return mv;
    }

    /**
     * haccp 완료확인서
     * @return
     */
    @RequestMapping(value = "/consult/hcComplete")
    public String haccpComplete(Model model, String sys_id, String fs_no, String fs_seq) {
    	model.addAttribute("eformDir", EFORM_DIR);
    	model.addAttribute("eformLoc", EFORM_LOC);
        model.addAttribute("sys_id", sys_id);
        model.addAttribute("fs_no", fs_no);
        model.addAttribute("fs_seq", fs_seq);
        return "/hc/consult/hcComplete";
    }

}