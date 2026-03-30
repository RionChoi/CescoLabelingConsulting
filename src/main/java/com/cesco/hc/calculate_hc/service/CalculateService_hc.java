package com.cesco.hc.calculate_hc.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.hc.calculate_hc.dto.CalculateBillDto;
import com.cesco.hc.calculate_hc.dto.CalculateDetailDto;
import com.cesco.hc.calculate_hc.dto.CalculateDto;
import com.cesco.hc.calculate_hc.dto.CalculateMgtDto;
import com.cesco.hc.calculate_hc.mapper.CalculateMapper_hc;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CalculateService_hc {

    private final CalculateMapper_hc calculateMapper;
    
    
    /**
     * 정산 목록
     * @param calculateMgtDto
     * @return
     * @throws Exception
     */
    public List<CalculateDto> getCalculateList(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception{

        //isBlankCheck(calculateMgtDto.getFs_bill_yn(), "정산여부가");

        calculateMgtDto.setSys_id(session.getAttribute("sysCode").toString());
        List<CalculateDto> calculateList = calculateMapper.getCalculateList(calculateMgtDto);
        return calculateList;
    }


    /**
     * 정산 상세 목록
     * @param calculateMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    public List<CalculateDetailDto> getCalculateDtList(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception{

        // isBlankCheck(calculateMgtDto.getFs_bill_yn(), "정산여부가");

        calculateMgtDto.setSys_id(session.getAttribute("sysCode").toString());
        List<CalculateDetailDto> calculateDtList = calculateMapper.getCalculateDtlList(calculateMgtDto);
        return calculateDtList;
    }




    // /**
    //  * 발행요청
    //  * @param CalculateBillDto
    //  * @param 
    //  * @return CalculateBillDto
    //  * @throws Exception
    //  */
    // public List<CalculateBillDto> requestPublish(CalculateBillDto calculateBillDto, HttpSession session) throws Exception{
    //     calculateBillDto.setSys_id(session.getAttribute("sysCode").toString());
    //     List<CalculateBillDto> requestPublish = calculateMapper.requestPublish(calculateBillDto);
    //     // return requestPublish;
    // }

    /**
     * 발행요청
     * @param CalculateBillDto
     * @param 
     * @return CalculateBillDto
     * @throws Exception
     */
    public void requestPublish(CalculateBillDto calculateBillDto, HttpSession session) throws Exception{
        calculateBillDto.setSys_id(session.getAttribute("sysCode").toString());
        calculateMapper.requestPublish(calculateBillDto);
        // return requestPublish;
    }



    // /**
    //  * 발행 / Origin Source
    //  * @param CalculateBillDto
    //  * @param 
    //  * @return CalculateBillDto
    //  * @throws Exception
    //  */
    // public List<CalculateBillDto> publish(CalculateBillDto calculateBillDto, HttpSession session) throws Exception{
    //     calculateBillDto.setSys_id(session.getAttribute("sysCode").toString());
    //     List<CalculateBillDto> publish = calculateMapper.publish(calculateBillDto);
    //     return publish;
    // }


    /**
     * 발행 / Origin Source
     * @param CalculateBillDto
     * @param 
     * @return CalculateBillDto
     * @throws Exception
     */
    public void publish(CalculateBillDto calculateBillDto, HttpSession session) throws Exception{

        calculateBillDto.setSys_id(session.getAttribute("sysCode").toString());
        calculateMapper.publish(calculateBillDto);
    }





    // null check
    // private void isBlankCheck(String value, String str){
    //     if(StringUtils.isBlank(value)){
    //         throw new CommDuplicateException(str + " 존재하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
    //     }
    // }

}
