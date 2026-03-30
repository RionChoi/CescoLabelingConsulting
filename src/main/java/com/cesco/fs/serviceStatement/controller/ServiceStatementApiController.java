package com.cesco.fs.serviceStatement.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cesco.fs.serviceStatement.dto.ServiceStatementDtlDto;
import com.cesco.fs.serviceStatement.dto.ServiceStatementDto;
import com.cesco.fs.serviceStatement.dto.ServiceStatementMgtDto;
import com.cesco.fs.serviceStatement.service.ServiceStatementService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;


@Controller
@RequestMapping("/api/service")
public class ServiceStatementApiController {

    @Autowired
    ServiceStatementService serviceStatementService;

    /**
     * 서비스 견적서 목록
     * @param noticeMgtDto
     * @return
     */
    @GetMapping(value="/getServiceStmtList")
    @ResponseBody
    public List<ServiceStatementDto> getServiceStmtList(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        // try {
            // 서비스 견적서 목록 가져오기
            List<ServiceStatementDto> serviceStmtList = serviceStatementService.getServiceStmtList(serviceStatementMgtDto, session);
            return serviceStmtList;

        // } catch(CommDuplicateException e) {
        //     throw new CommDuplicateException(e);

        // } catch(Exception ex) {
        //     throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        // }
    }

    /**
     * 서비스 견적서 목록
     * @param calculateMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value="/getServiceStmtDtlList")
    @ResponseBody
    public List<ServiceStatementDtlDto> getServiceStmtDtlList(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        try {
            // 정산 상세 목록 가져오기
            List<ServiceStatementDtlDto> serviceStmtDtlList = serviceStatementService.getServiceStmtDtlList(serviceStatementMgtDto, session);
            return serviceStmtDtlList;

        } catch(CommDuplicateException e) {
            throw new CommDuplicateException(e);
            
        } catch(Exception ex) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }
    }

    
    /**
     * 서비스견적서 고객확인요청
     * @param calculateReqDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/sendEmailForCstReq")
    public ResponseEntity<String> sendEmailForCstReq(@RequestBody ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        int result = serviceStatementService.sendEmailForCstReq(serviceStatementMgtDto, session);

        if(result == serviceStatementMgtDto.getServiceStatementList().size()) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * 서비스 견적서 비고 수정
     * @param calculateReqDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/setServiceStmtRemark")
    public ResponseEntity<String> setServiceStmtRemark(@RequestBody ServiceStatementDto serviceStatementDto, HttpSession session) throws Exception {
        int result = serviceStatementService.setServiceStmtRemark(serviceStatementDto, session);

        if(result == 1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * 서비스 견적서 삭제(status = 'N')
     * @param serviceStatementMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/setDelServiceStmtList")
    public ResponseEntity<String> setDelServiceStmtList(@RequestBody ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        int result = serviceStatementService.setDelServiceStmtList(serviceStatementMgtDto, session);

        if(result == serviceStatementMgtDto.getServiceStatementList().size()) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * 서비스 견적서 삭제(status = 'N')
     * @param calculateReqDto
     * @param session
     * @return
     * @throws Exception
     */
    @PutMapping(value="/setDelServiceStmtList2")
    public ResponseEntity<String> setDelServiceStmtList2(@RequestBody ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        int result = 0;

        if(result == 1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * 서비스견적서 확정
     * @param ServiceStatementDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/setServiceStmtConfirm")
    public ResponseEntity<String> setServiceStmtConfirm(@RequestBody ServiceStatementDto ServiceStatementDto, HttpSession session) throws Exception {
        int result = serviceStatementService.setServiceStmtConfirm(ServiceStatementDto, session);

        if(result == 1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * 서비스견적서 수정요청
     * @param ServiceStatementDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/setServiceStmtReq")
    public ResponseEntity<String> setServiceStmtReq(@RequestBody ServiceStatementDto ServiceStatementDto, HttpSession session) throws Exception {
        int result = serviceStatementService.setServiceStmtReq(ServiceStatementDto, session);

        if(result == 1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * 세금계산서발행요청
     * @param calculateReqDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/setTaxBillReq")
    public ResponseEntity<String> setTaxBillReq(@RequestBody ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        int result = serviceStatementService.setTaxBillReq(serviceStatementMgtDto, session);

        if(result == serviceStatementMgtDto.getServiceStatementList().size()) {
            return new ResponseEntity<>("요청 성공", HttpStatus.OK); 
        } else if(result > 0) {
            return new ResponseEntity<>("요청 부분 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        } else {
            return new ResponseEntity<>("요청 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * direct email 전송
     * @param ServiceStatementMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value="/sendDirectEmail")
    public ResponseEntity<String> sendDirectEmail(@RequestBody ServiceStatementMgtDto ServiceStatementMgtDto, HttpSession session) throws Exception {
        serviceStatementService.sendDirectEmail(ServiceStatementMgtDto, session);
        return new ResponseEntity<>("변경 성공", HttpStatus.OK); 
    }
}
