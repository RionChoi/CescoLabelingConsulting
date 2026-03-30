package com.cesco.co.acctMgt.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cesco.co.acctMgt.dto.AccSearchInfo;
import com.cesco.co.acctMgt.dto.AccUserInfoDTO;
import com.cesco.co.acctMgt.service.AcctMgtService;
import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultStaffDTO;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

/**
 * @author parkminho
 * @apiNote acctMgt
 */

@RestController
@RequestMapping("/acc/api")
public class AcctRestMgtController {

    @Autowired
    AcctMgtService acctMgtService;

    // private static final Logger LOGGER = LogManager.getLogger(ApiController.class); 
    // LOGGER.info("===================================");
    // LOGGER.info(accSearchInfo);
    // LOGGER.info("===================================");   

    // 계정 목록
    @GetMapping(value="/accUserList")
    public List<AccUserInfoDTO> getAccUserList(AccSearchInfo accSearchInfo, HttpSession session) throws Exception{

        try{
            
            return acctMgtService.getAccUserList(accSearchInfo);

        }catch(Exception e){

            throw new CommDuplicateException("목록 불러오기에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }
    }

    // 사용자 아이디 중복 체크
    @GetMapping(value="/chkUserEmail")
    public ConsultStaffDTO getChkUserId(
        @RequestParam("email") String email,
        @RequestParam("user_id") String user_id) throws Exception {
                  
        if(acctMgtService.getUserEmailCnt(email) > 0){
            
            if(acctMgtService.getSelfUserEmailCnt(user_id, email) > 0){
                return acctMgtService.getCescoUserInfo(email);
            }
            
            throw new CommDuplicateException("이미 사용중인 메일 입니다.",ErrorCode.REQUIRED_VALUE_ERROR);
            
        }else{

        }

        return acctMgtService.getCescoUserInfo(email);
    }
    
    // 소속회사 목록
    @PostMapping(value="/comList")
    public List<CusInfoDTO> getComList() throws Exception{

        try{

            return acctMgtService.getComList();

        }catch(Exception e){

            throw new CommDuplicateException("목록 불러오기에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }

    }

    // 계정 정보 수정
    @PutMapping(value="/uptUserInfo")
    public ResponseEntity<String> setUptUserInfo(@RequestBody AccUserInfoDTO accUserInfo, HttpSession session) throws Exception{

        String userId = (String)session.getAttribute("userId");

        try{ 

            acctMgtService.setUserInfoUpt(accUserInfo, userId);

        }catch(Exception e){

            throw new CommDuplicateException("계정 수정에 실패 했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }

        return new ResponseEntity<>("정보 수정 완료", HttpStatus.OK);
    }

    // 계정 정보 등록
    @PostMapping(value="/insUserInfo")
    public ResponseEntity<String> setInsUserInfo(@RequestBody AccUserInfoDTO accUserInfo, HttpSession session) throws Exception{

        String userId = (String)session.getAttribute("userId");

        try{

            // 유저 ID 생성
            accUserInfo.setUser_id(acctMgtService.getUserId(accUserInfo.getUser_tp_cd()));            

            // 유저 등록
            acctMgtService.setUserInfoIns(accUserInfo, userId);

        }catch(Exception e){

            throw new CommDuplicateException("계정 등록에 실패 했습니다.",ErrorCode.INTER_SERVER_ERROR);
            
        }

        return new ResponseEntity<>("계정 등록 완료", HttpStatus.OK);
    }

    // 삭제 순간 포함컨설팅 수 가져오기
    @GetMapping(value = "/getConsultCnt")
    public Integer getConsultCnt(@RequestParam("user_id") String user_id) throws Exception{

        try{

            // 참여 컨설팅 수 리턴
            return acctMgtService.getConsultCnt(user_id);

        }catch(Exception e){

            throw new CommDuplicateException("참여 컨설팅 조회에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }
        
    }    

    // 비밀번호 초기화
    @PutMapping(value="/resetPassword")
    public ResponseEntity<String> selResetPassword(@RequestBody Map<String,String> reqInfo, HttpSession session) throws Exception{

        String userId = (String)session.getAttribute("userId");

        try{

            acctMgtService.setPassReSet(reqInfo.get("user_id"), userId );

        }catch(Exception e){

            throw new CommDuplicateException("비밀번호 초기화에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }

        return new ResponseEntity<>("비밀번호 초기화 완료", HttpStatus.OK);
    }    
}
