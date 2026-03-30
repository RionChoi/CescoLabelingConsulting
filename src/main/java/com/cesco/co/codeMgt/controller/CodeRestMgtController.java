package com.cesco.co.codeMgt.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import com.cesco.co.codeMgt.dto.CodeCommCodeDTO;
import com.cesco.co.codeMgt.dto.CodeDtlCodeDTO;
import com.cesco.co.codeMgt.service.CodeMgtService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

/**
 * @author parkminho
 * @apiNote codeMgt
 */

@RestController
@RequestMapping("/code/api")
public class CodeRestMgtController {    

    @Autowired
    CodeMgtService codeMgtService;

    /**
     * 세부 코드 리스트
     * @param cmm_cd (유형코드)
     * @return
     * @throws Exception
     */
    @GetMapping(value="/codeDtlList")
    public List<CodeDtlCodeDTO> getCodeDtlList(@RequestParam("cmm_cd") String cmm_cd) throws Exception {
        
        if(cmm_cd == null){
            cmm_cd = "D";
        }

        try{
            return codeMgtService.getCodeDtlList(cmm_cd);
        }catch(Exception e){
            throw new CommDuplicateException("코드 불러오기에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);
        } 
        
    }
    
    // 코드 유형 목록
    @GetMapping(value="/codeCommList")
    public List<CodeCommCodeDTO> getCodeCommList(CodeCommCodeDTO codeComm) throws Exception {

        try{
            return codeMgtService.getCodeCommList(codeComm);
        }catch(Exception e){
            throw new CommDuplicateException("코드 유형 불러오기에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }   

    }
    
    // 상세 코드 등록 및 수정
    @PostMapping(value="/codeCommDtlIns")
    public ResponseEntity<String> setCodeCommDtlIns(@RequestBody List<CodeDtlCodeDTO> codeDtlList, HttpSession session) throws Exception {

        // 로그인 ID
        String userId = (String)session.getAttribute("userId");

        // 등록 리스트
        List<CodeDtlCodeDTO> codeInsDtlList = new ArrayList<>();
        // 수정 리스트
        List<CodeDtlCodeDTO> codeUptDtlList = new ArrayList<>();
        // 코드중복 체크 위한 코드 리스트
        List<String> listDtlCode = new ArrayList<>();
        // 코드명중복 체크 위한 코드 리스트
        List<String> listDtlNm = new ArrayList<>();

        try{

            for(CodeDtlCodeDTO codeDtl : codeDtlList){            
                
                listDtlCode.add(codeDtl.getCmm_dtl_cd());
                listDtlNm.add(codeDtl.getCmm_dtl_nm());

                codeDtl.setMdfr_id(userId);

                // 추가 목록
                if(codeDtl.getGrid_stats().equals("N")){

                    codeDtl.setRgstr_id(userId);                
                    codeInsDtlList.add(codeDtl);

                // 수정 목록
                }else if(codeDtl.getGrid_stats().equals("U")){
                    codeUptDtlList.add(codeDtl);
                }

            }

        }catch(Exception e){
            throw new CommDuplicateException("처리중 오류가 발생했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }

        if(codeInsDtlList.size() == 0 && codeUptDtlList.size() == 0){
            throw new CommDuplicateException("수정할 내용이 없습니다.",ErrorCode.REQUIRED_VALUE_ERROR);
        }

        // 코드 입력 값 중 중복 값 찾기
        for(String dtlCode : listDtlCode){            
            if(Collections.frequency(listDtlCode, dtlCode) > 1){
                throw new CommDuplicateException("입력된 내용중 중복된 코드 값이 있습니다.",ErrorCode.REQUIRED_VALUE_ERROR);                
            }
        }

        // 명칭 입력 값 중 중복 값 찾기
        for(String dtlNm : listDtlNm){            
            if(Collections.frequency(listDtlNm, dtlNm) > 1){
                throw new CommDuplicateException("입력된 내용중 중복된 명칭이 있습니다.",ErrorCode.REQUIRED_VALUE_ERROR);                
            }
        }        

        // 코드 추가
        for(CodeDtlCodeDTO insCode : codeInsDtlList){
            codeMgtService.setCodeDtlIns(insCode);
        }

        // 수정
        for(CodeDtlCodeDTO uptCode : codeUptDtlList){
            codeMgtService.setCodeDtlUpt(uptCode);
        }

        return new ResponseEntity<>("코드 정보가 등록 되었습니다.", HttpStatus.OK);
    }

    // 코드 유형 등록[POST]
    @PostMapping(value="/codeCommIns")
    public ResponseEntity<String> setCodeCommIns(@RequestBody List<CodeCommCodeDTO> codeCommList, HttpSession session) throws Exception {
        
        // 로그인 ID
        String userId = (String)session.getAttribute("userId");

        // 등록할 코드 유형 + 등록자 아이디 설정
        for(CodeCommCodeDTO codeComm : codeCommList){

            // 등록자, 수정자 등록
            codeComm.setRgstr_id(userId);
            codeComm.setMdfr_id(userId);

            // 등록 가능한 아이디 인지 체크
            if(codeMgtService.getCodeCommCnt(codeComm.getCmm_cd()) > 0){                
                throw new CommDuplicateException("이미 등록된 코드가 있습니다.",ErrorCode.REQUIRED_VALUE_ERROR);
            }

        }

        // 등록
        for(CodeCommCodeDTO codeComm : codeCommList){
            // 리턴 값 설정(등록 중 에러)
            codeMgtService.setCodeCommIns(codeComm);
        }

        return new ResponseEntity<>("코드유형이 등록 되었습니다.", HttpStatus.OK);
    }

    // 코드 유형 수정[PUT]
    @PutMapping(value="/codeCommUpt")
    public ResponseEntity<String> setCodeCommUpt(@RequestBody List<CodeCommCodeDTO> codeCommList, HttpSession session) throws Exception {
        
        // 로그인 ID
        String userId = (String)session.getAttribute("userId");

        try{
            // 등록할 코드 유형 + 등록자 아이디 설정
            for(CodeCommCodeDTO codeComm : codeCommList){                
                codeComm.setMdfr_id(userId);    // 수정자 등록
            }            
        }catch(Exception e){
            throw new CommDuplicateException("수정 작업중 장애가 발생했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }

        // 수정
        for(CodeCommCodeDTO codeComm : codeCommList){
            codeMgtService.setCodeCommUpt(codeComm);
        }

        return new ResponseEntity<>("코드 정보가 수정 되었습니다.", HttpStatus.OK);
    }

}
