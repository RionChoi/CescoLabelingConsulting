package com.cesco.fs.calculate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.fs.calculate.dto.CalculateBillDto;
import com.cesco.fs.calculate.dto.CalculateDto;
import com.cesco.fs.calculate.dto.CalculateMgtDto;
import com.cesco.fs.calculate.dto.CalculateReqDto;

@Mapper
public interface CalculateMapper {

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
    List<CalculateDto> getCalculateDtlList(CalculateMgtDto calculateMgtDto) throws Exception;

    /**
     * 정산 정보
     * @param dto
     * @return
     * @throws Exception
     */
    CalculateDto getCalculateInfo(CalculateDto calculateDto) throws Exception;

    /**
     * 견적 단가 수정(상태값 변경 => N)
     * @param cltEstmateDto
     * @return
     * @throws Exception
     */
    int setModiCalculateEst(CalculateReqDto cltEstmateDto) throws Exception;

    /**
     * 견적 단가 수정(insert => Y)
     * @param calculateReqDto
     * @return
     * @throws Exception
     */
    int setRegCalculateEst(CalculateReqDto calculateReqDto) throws Exception;

    /**
     * 견적 단가 수정(의뢰구분 수정)
     * @param calculateReqDto
     * @return
     * @throws Exception
     */
    int setModiCalculateMst(CalculateReqDto calculateReqDto) throws Exception;

    /**
     * 서비스 견적서 생성
     * @param calculateBillDto
     * @return
     * @throws Exception
     */
    int setRegCalculateSvc(CalculateBillDto calculateBillDto) throws Exception;

    /**
     * 생성된 견적서 번호 견적 단가에 update
     * @param calculateDto
     * @return
     * @throws Exception
     */
    int setModiCalculateSvc(CalculateDto calculateDto) throws Exception;


  
}
