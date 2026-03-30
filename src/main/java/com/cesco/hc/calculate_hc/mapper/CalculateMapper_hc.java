package com.cesco.hc.calculate_hc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.hc.calculate_hc.dto.CalculateBillDto;
import com.cesco.hc.calculate_hc.dto.CalculateDetailDto;
import com.cesco.hc.calculate_hc.dto.CalculateDto;
import com.cesco.hc.calculate_hc.dto.CalculateMgtDto;

@Mapper
public interface CalculateMapper_hc {
    
    /**
     * 정산 목록
     * @param calculateMgtDto
     * @return
     * @throws Exception
     */
    List<CalculateDto> getCalculateList(CalculateMgtDto calculateMgtDto) throws Exception;



    /**
     * 정산 상세 목록
     * @param calculateMgtDto
     * @return
     * @throws Exception
     */
    List<CalculateDetailDto> getCalculateDtlList(CalculateMgtDto calculateMgtDto) throws Exception;


    /**
     * 정산 정보
     * @param dto
     * @return
     * @throws Exception
     */
    CalculateDto getCalculateInfo(CalculateDto calculateDto) throws Exception;



    /**
     * 발행요청
     * @param calculateBillDto
     * @return
     * @throws Exception
     */ 
    void requestPublish(CalculateBillDto calculateBillDto) throws Exception;




    // /**
    //  * 발행  // Origin Source
    //  * @param calculateBillDto
    //  * @return
    //  * @throws Exception
    //  */ 
    // List<CalculateBillDto> publish(CalculateBillDto calculateBillDto) throws Exception;

    /**
     * 발행
     * @param calculateBillDto
     * @return
     * @throws Exception
     */ 
    void publish(CalculateBillDto calculateBillDto) throws Exception;

    
}
