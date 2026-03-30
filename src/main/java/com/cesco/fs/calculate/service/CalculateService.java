package com.cesco.fs.calculate.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.fs.calculate.dto.CalculateBillDto;
import com.cesco.fs.calculate.dto.CalculateDto;
import com.cesco.fs.calculate.dto.CalculateMgtDto;
import com.cesco.fs.calculate.dto.CalculateReqDto;
import com.cesco.fs.calculate.mapper.CalculateMapper;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CalculateService {

    private final CalculateMapper calculateMapper;

    /**
     * 정산 목록
     * @param calculateMgtDto
     * @return
     * @throws Exception
     */
    public List<CalculateDto> getCalculateList(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception {
        
        isBlankCheck(calculateMgtDto.getFs_bill_yn(), "정산여부가");

        calculateMgtDto.setSys_id(session.getAttribute("sysCode").toString());
        calculateMgtDto.setUser_id(session.getAttribute("userId").toString());
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
    public List<CalculateDto> getCalculateDtlList(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception {

        isBlankCheck(calculateMgtDto.getFs_no(), "정산여부가");

        calculateMgtDto.setSys_id(session.getAttribute("sysCode").toString());
        List<CalculateDto> calculateDtlList = calculateMapper.getCalculateDtlList(calculateMgtDto);
        return calculateDtlList;
    }


    /**
     * 단가 수정
     * @param calculateReqDto
     * @param session
     * @return
     * @throws Exception
     */
    public int setCalculateEst(CalculateReqDto calculateReqDto, HttpSession session) throws Exception {
        int result = 0;

        // 입력값 체크
        this.isBlankCheck(calculateReqDto.getFs_no(), "컨설팅번호가");
        this.isBlankCheck(calculateReqDto.getFs_seq(), "컨설팅번호순번이");
        this.isBlankCheck(calculateReqDto.getEst_seq(), "견적단가순번이");
        this.isBlankCheck(calculateReqDto.getEst_amt(), "견적단가가");
        this.isBlankCheck(calculateReqDto.getProd_unit_cd(), "의뢰구분이");

        String userId = session.getAttribute("userId").toString();
        String sysId = session.getAttribute("sysCode").toString();

        calculateReqDto.setSys_id(sysId);
        calculateReqDto.setStatus("N");
        calculateReqDto.setEst_use_yn("N");
        calculateReqDto.setRgstr_id(userId);
        calculateReqDto.setMdfr_id(userId);
        calculateReqDto.setEst_chg_id(userId);

        // 견적 단가 수정(상태값 변경 => N)
        result += calculateMapper.setModiCalculateEst(calculateReqDto);
        
        // 견적 단가 수정(insert => Y)
        result += calculateMapper.setRegCalculateEst(calculateReqDto);

        // 견적 단가 수정(의뢰구분 수정)
        result += calculateMapper.setModiCalculateMst(calculateReqDto);

        if(result != 3) {
            throw new CommDuplicateException("정산 견적단가 수정에 실패했습니다.", ErrorCode.INTER_SERVER_ERROR);
        }
        
        return Math.round(result/3);
    }

    /**
     * 서비스 견적서 생성
     * @param calculateMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    public int setCalculateSvc(CalculateMgtDto calculateMgtDto, HttpSession session) throws Exception {
        int result = 0;
        
        // 입력값 체크
        this.isBlankCheck(calculateMgtDto.getSsmm(), "비고가");

        // session
        String userId = session.getAttribute("userId").toString();
        String sysId = session.getAttribute("sysCode").toString();

        // 서비스 견적서 생성에 필요한 fs_no list
        List<CalculateDto> calculateList = calculateMgtDto.getCalculateList();
        if(calculateList == null || calculateList.size() == 0) {
            throw new CommDuplicateException("서비스 견적서 생성에 필요한 목록이 없습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
        }

        // 서비스 견적서 생성에 필요한 데이터 info
        String cstId = "";                       // 고객사
        String taxBillId = "";                   // 정산담당자
        String taxBillEmail = "";                // 정산담당자 email
        int estSamt = 0;                         // 견적단가 sum
        int supplyAmt = 0;                       // 부가세금액
        String ssmm = calculateMgtDto.getSsmm(); // 비고
        
        for(CalculateDto dto : calculateList) {
            // 입력값 체크
            this.isBlankCheck(dto.getFs_no(), "컨설팅번호가");

            dto.setSys_id(sysId);
            CalculateDto calculateDto = calculateMapper.getCalculateInfo(dto);

            if(calculateDto == null || StringUtils.isBlank(calculateDto.getFs_no())) {
                throw new CommDuplicateException("서비스 견적서 생성에 필요한 데이터가 없습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
            }
            
            // 고객사 동일 여부 확인
            if(!StringUtils.isBlank(cstId) && !cstId.equals(calculateDto.getCst_id())) {
                throw new CommDuplicateException("고객사가 동일하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
            }

            // 정산 담당자 동일 여부 확인
            if(!StringUtils.isBlank(taxBillId) && !taxBillId.equals(calculateDto.getTax_bill_id())) {
                throw new CommDuplicateException("정산 담당자가 동일하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
            }

            // 정산 담당자 이메일 동일 여부 확인
            if(!StringUtils.isBlank(taxBillEmail) && !taxBillEmail.equals(calculateDto.getTax_bill_email())) {
                throw new CommDuplicateException("정산 담당자 email이 동일하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
            }

            cstId = calculateDto.getCst_id();
            taxBillId = calculateDto.getTax_bill_id();
            taxBillEmail = calculateDto.getTax_bill_email();
            estSamt += calculateDto.getEst_samt();
        }

        // 부가세금액 : 합계 10% (원 절사)
        supplyAmt = Math.round((estSamt * 10 / 1000)) * 10;

        // 서비스 견적서 생성
        CalculateBillDto calculateBillDto = new CalculateBillDto();
        calculateBillDto.setSys_id(sysId);
        calculateBillDto.setBill_cst_id(cstId);                   // 고객사
        calculateBillDto.setBill_req_cd("01");       // 비용확인요청코드 01 확인요청전
        calculateBillDto.setTax_bill_cnt(calculateList.size());   // 세금계산서건수
        calculateBillDto.setTax_bill_id(taxBillId);               // 정산 담당자 id
        calculateBillDto.setTax_email(taxBillEmail);              // 정산 담당자 email
        calculateBillDto.setTax_bill_tp_cd("02"); // 세금계산서발행구분 02 후발행
        calculateBillDto.setSupply_amt(estSamt);                  // 합계
        calculateBillDto.setVat_amt(supplyAmt);                   // 합계 10% (원 절사)
        calculateBillDto.setBill_ar_cd("01");         // 대금수금코드 01 미수
        calculateBillDto.setRgstr_id(userId);
        calculateBillDto.setMdfr_id(userId);
        calculateBillDto.setSsmm(ssmm);

        // 서비스 견적서 생성
        result = calculateMapper.setRegCalculateSvc(calculateBillDto);
        
        // 생성된 견적서 번호 견적 단가에 update
        for(CalculateDto dto : calculateList) {
            dto.setFs_bill_no(calculateBillDto.getFs_bill_no());
            dto.setMdfr_id(userId);
            calculateMapper.setModiCalculateSvc(dto);
        }

        return result;
    }

    // null check
    private void isBlankCheck(String value, String str) {
        if(StringUtils.isBlank(value)) {
            throw new CommDuplicateException(str + " 존재하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
        }
    }
}
