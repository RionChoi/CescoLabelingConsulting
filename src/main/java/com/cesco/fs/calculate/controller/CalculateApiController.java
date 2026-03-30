package com.cesco.fs.calculate.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cesco.fs.calculate.dto.CalculateDto;
import com.cesco.fs.calculate.dto.CalculateMgtDto;
import com.cesco.fs.calculate.dto.CalculateReqDto;
import com.cesco.fs.calculate.service.CalculateService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;


@Controller
@RequestMapping("/api/calculate")
public class CalculateApiController {

    @Autowired
    CalculateService calculateService;

    /**
     * 정산 목록
     * @param noticeMgtDto
     * @return
     */
    @GetMapping(value="/getCalculateList")
    @ResponseBody
    public List<CalculateDto> getCalculateList(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception {
        try {
            // 정산 목록 가져오기
            List<CalculateDto> calculateList = calculateService.getCalculateList(calculateMgtDto, session);
            return calculateList;

        } catch(CommDuplicateException e) {
            throw new CommDuplicateException(e);

        } catch(Exception ex) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }
    }

    /**
     * 정산 상세 목록
     * @param calculateMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value="/getCalculateDtlList")
    @ResponseBody
    public List<CalculateDto> getCalculateDtlList(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception {
        try {
            // 정산 상세 목록 가져오기
            List<CalculateDto> calculateDtlList = calculateService.getCalculateDtlList(calculateMgtDto, session);
            return calculateDtlList;

        } catch(CommDuplicateException e) {
            throw new CommDuplicateException(e);
            
        } catch(Exception ex) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }
    }

    /**
     * 단가 수정
     * @param calculateReqDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/setCalculateEst")
    public ResponseEntity<String> setCalculateEst(@RequestBody CalculateReqDto calculateReqDto, HttpSession session) throws Exception {
        int result = calculateService.setCalculateEst(calculateReqDto, session);

        if(result == 1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }

    }

    /**
     * 서비스 견적서 생성
     * @param calculateMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/setCalculateSvc")
    public ResponseEntity<String> setCalculateSvc(@RequestBody CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception {
        int result = calculateService.setCalculateSvc(calculateMgtDto, session);

        if(result == 1) {
            return new ResponseEntity<>("등록 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("등록 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }

    }
    
}
