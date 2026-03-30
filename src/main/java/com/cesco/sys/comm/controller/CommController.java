package com.cesco.sys.comm.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cesco.sys.comm.dto.UserDTO;
import com.cesco.sys.comm.dto.UserReqDTO;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

@Controller
@RequiredArgsConstructor
public class CommController {
    // private final CommService commService;
    private static final Logger logger = LogManager.getLogger(CommController.class);

    @Autowired
    private final CommService commService;

    /**
     * session 체크
     * @return
     */
    @RequestMapping(value = {"/main", "/"})
    public String mainPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        if (userId==null || userId.trim().equals("")) {
            return "redirect:/user/login";
        }else {
            return "redirect:/fs/main";
        }
    }  

    /**
     * 로그인 화면
     * @return
     */
    @GetMapping("/user/login")
    public String login() {
        return "common/user/login";
    }

    /**
     * 아이디 찾기 화면
     * @return
     */
    @GetMapping("/user/loginId/{system}")
    public ModelAndView loginId(ModelAndView model,@PathVariable String system) {
        model.setViewName("common/user/loginId");
        model.addObject("sysid", system);
        return model;
    }

    /**
     * 아이디 찾기 결과 화면
     * @return
     */
    @GetMapping("/user/loginFindIdResult")
    public ModelAndView loginFindIdResult(ModelAndView model,@PathParam("id") String id) {
        model.setViewName("common/user/loginFindIdResult");
        model.addObject("userId", id);
        return model;
    }

    /**
     * 비밀번호 찾기 화면
     * @return
     */
    @GetMapping("/user/loginForgotPw/{system}")
    public ModelAndView loginForgotPw(ModelAndView model,@PathVariable String system) {
        model.setViewName("common/user/loginForgotPw");
        model.addObject("sysid", system);
        return model;
    }

    /**
     * 비밀번호 찾기 입력 화면
     * @return
     * @throws Exception
     */
    @GetMapping("/user/loginForgotPwEnter/{email}")
    public ModelAndView loginForgotPwEnter(ModelAndView model,@PathVariable String email) throws Exception {

        model.addObject("userEmail", email);
        model.setViewName("common/user/loginForgotPwEnter");
        return model;
    }

    /**
     * 비밀번호변경 팝업 화면
     * @return
     */
    @GetMapping("/user/loginChangePwPopup")
    public String loginChangePwPopup() {
        return "common/user/loginChangePwPopup";
    }

    /**
     * 고객사검색_팝업 화면
     * @return
     */
    @GetMapping("/user/customerSearchPopup")
    public String customerSearchPopup() {
        return "common/popup/customerSearchPopup";
    }

    /**
     * 담당자검색_팝업 화면
     * @return
     */
    @GetMapping("/user/personSearchPopup")
    public String personSearchPopup() {
        return "common/popup/personSearchPopup";
    }

    /**
     * 담당자추가_팝업 화면
     * @return
     */
    @GetMapping("/user/addContactPopup")
    public ModelAndView addContactPopup(String sysId, String fsNo, ModelAndView model) {
        model.setViewName("common/popup/addContactPopup");
            if(StringUtils.hasLength(fsNo)) model.addObject("fsNo", fsNo);
            if(StringUtils.hasLength(sysId)) model.addObject("sysId", sysId);
        return model;
    }

    // 로그아웃
    @RequestMapping(value = "/user/logout")
    public ModelAndView logout(ModelAndView mav, HttpServletRequest request, HttpSession session) throws Exception {

        //1) 세션 가져오기
        if (session != null){
            session.invalidate();
        }
        mav.setViewName("redirect:/user/login");
        return mav;
    }


    /**
     * 성공 화면 호출
     * @return
     */
    @GetMapping("/user/success")
    public ModelAndView niceSuccess(ModelAndView model) {
        model.setViewName("common/user/niceSuccess");
        // todo....
        // model.addObject("sysId", "");
        return model;
    }

    /**
     * 실페 화면 호출
     * @return
     */
    @GetMapping("/user/nicefail")
    public String niceFail() {
        return "common/user/niceFail";
    }
}
