package com.cesco.hc.calculate_hc.controller;

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

import com.cesco.hc.calculate_hc.dto.CalculateBillDto;
import com.cesco.hc.calculate_hc.dto.CalculateDetailDto;
import com.cesco.hc.calculate_hc.dto.CalculateDto;
import com.cesco.hc.calculate_hc.dto.CalculateMgtDto;
import com.cesco.hc.calculate_hc.service.CalculateService_hc;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

@Controller
@RequestMapping("api/hc_calculate")
public class CalculateApiController_hc {
    
    
    @Autowired
    CalculateService_hc calculateService;


    /**
     * 정산 목록
     * @param CalculateMgtDto
     * @return
     */
    @GetMapping(value="/getCalculateList_hc")
    @ResponseBody
    public List<CalculateDto> getCalculateList(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception{
        
        try{
            // 정산 목록 가져오기
            List<CalculateDto> calculateList = calculateService.getCalculateList(calculateMgtDto, session);
            System.out.println(calculateMgtDto.toString());
            return calculateList;
        }catch(CommDuplicateException e){
            throw new CommDuplicateException(e);
        }catch(Exception ex){
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }
    }


    /**
     * 정산 목록
     * @param CalculateMgtDto
     * @return
     */
    @GetMapping("/getCalculateDtList_hc")
    @ResponseBody
    public List<CalculateDetailDto> getCalculateDtlist(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception{
        
        try{
            // 정산 상세 목록 가져오기
            List<CalculateDetailDto> calculateDtList = calculateService.getCalculateDtList(calculateMgtDto, session);
            System.out.println(calculateMgtDto.toString());
            return calculateDtList;

        }catch(CommDuplicateException e){
            throw new CommDuplicateException(e);
        }catch(Exception ex){
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }
    }
    





    /**
     * 발행요청 : set
     * @param CalculateBillDto
     * @return
     */
    @PutMapping("/getCalculateDtList_hc/rqPublish")
    @ResponseBody
    public ResponseEntity<String> requestPublish(@RequestBody CalculateBillDto calculateBillDto, HttpSession session) throws Exception{

        try{
            // Bill테이블에 insert + 상태코드 update 
            calculateService.requestPublish(calculateBillDto, session);
            System.out.println("API!@!@!@!@!@!@@!"+calculateBillDto.toString());
        }catch(Exception ex){
            throw new CommDuplicateException("발행요청에 실패 했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
        return new ResponseEntity<>("발행요청 완료", HttpStatus.OK);
    }


    // /**
    //  * 발행 : set + get / Origin Source 
    //  * @param CalculateBillDto
    //  * @return
    //  */
    // @GetMapping("/getCalculateDtList_hc/Publish")
    // @ResponseBody
    // public List<CalculateBillDto> publish(CalculateBillDto calculateBillDto, HttpSession session) throws Exception{
    //     try{
    //         List<CalculateBillDto> calculateBillList = calculateService.publish(calculateBillDto, session);
    //         System.out.println(calculateBillDto.toString());
    //         return calculateBillList;

    //     }catch(CommDuplicateException e){
    //         throw new CommDuplicateException(e);
    //     }catch(Exception ex){
    //         throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
    //     }
    // }


    /**
     * 발행 : set
     * @param CalculateBillDto
     * @return
     */
    @PutMapping("/getCalculateDtList_hc/Publish")
    @ResponseBody
    public ResponseEntity<String> publish(@RequestBody CalculateBillDto calculateBillDto, HttpSession session) throws Exception{

        try{
            calculateService.publish(calculateBillDto, session);
            System.out.println("API!@!@!@!@!@!@@!"+calculateBillDto.toString());
        }catch(Exception ex){
            throw new CommDuplicateException("발행요청에 실패 했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
        return new ResponseEntity<>("발행요청 완료", HttpStatus.OK);
    }




}
