package com.cesco.fs.serviceStatement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.fs.serviceStatement.dto.ServiceStatementDtlDto;
import com.cesco.fs.serviceStatement.dto.ServiceStatementDto;
import com.cesco.fs.serviceStatement.dto.ServiceStatementMgtDto;

@Mapper
public interface ServiceStatementMapper {

    /**
     * 서비스 견적서 목록
     * @param serviceStatementDto
     * @return
     * @throws Exception
     */
    List<ServiceStatementDto> getServiceStmtList(ServiceStatementMgtDto serviceStatementMgtDto) throws Exception;

    /**
     * 서비스 견적서 상세 목록
     * @param serviceStatementMgtDto
     * @return
     * @throws Exception
     */
    List<ServiceStatementDtlDto> getServiceStmtDtlList(ServiceStatementMgtDto serviceStatementMgtDto) throws Exception;

    /**
     * 서비스 견적서 정보
     * @param serviceStatementDto
     * @return
     * @throws Exception
     */
    ServiceStatementDto getServiceStmtInfo(ServiceStatementDto serviceStatementDto) throws Exception;

    /**
     * 서비스 견적서 비고 수정
     * @param serviceStatementDto
     * @return
     * @throws Exception
     */
    int setServiceStmtRemark(ServiceStatementDto serviceStatementDto) throws Exception;

    /**
     * 서비스 견적서 삭제(status = 'N')
     * @param serviceStatementDto
     * @return
     * @throws Exception
     */
    int setDelServiceStmtList(ServiceStatementDto serviceStatementDto) throws Exception;

    /**
     * 단가내역(견적금액내역)에 청구여부 (fs_bill_yn = 'N')
     * @param serviceStatementDto
     * @return
     * @throws Exception
     */
    int setModiCalculateBillYn(ServiceStatementDto serviceStatementDto) throws Exception;

    /**
     * 서비스견적서 고객확인요청 (02:확인요청, 03:확인완료, 04:수정요청)
     * @param dto
     * @return
     * @throws Exception
     */
    int setModiServiceStmtBillReq(ServiceStatementDto serviceStatementDto) throws Exception;

    /**
     * 세금계산서발행요청
     * @param dto
     * @return
     * @throws Exception
     */
    int setTaxBillReq(ServiceStatementDto dto) throws Exception;

  
}
