package com.cesco.fs.serviceStatement.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.fs.serviceStatement.dto.ServiceStatementDtlDto;
import com.cesco.fs.serviceStatement.dto.ServiceStatementDto;
import com.cesco.fs.serviceStatement.dto.ServiceStatementMgtDto;
import com.cesco.fs.serviceStatement.mapper.ServiceStatementMapper;
import com.cesco.sys.comm.dto.MailDto;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.common.Mail;
import com.cesco.sys.common.Utils;
import com.cesco.sys.communityhandlers.CommDuplicateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ServiceStatementService {

    private final ServiceStatementMapper serviceStatementMapper;

    private final CommService commService;

    /**
     * 서비스 견적서 목록
     * @param serviceStatementDto
     * @param session
     * @return
     * @throws Exception
     */
    public List<ServiceStatementDto> getServiceStmtList(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        // null check
        serviceStatementMgtDto.setSys_id(session.getAttribute("sysCode").toString());
        serviceStatementMgtDto.setUser_id(session.getAttribute("userId").toString());

        List<ServiceStatementDto> serviceStmtList = serviceStatementMapper.getServiceStmtList(serviceStatementMgtDto);

        return serviceStmtList;
    }

    /**
     * 서비스 견적서 상세 목록
     * @param serviceStatementMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    public List<ServiceStatementDtlDto> getServiceStmtDtlList(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        // null check
        serviceStatementMgtDto.setSys_id(session.getAttribute("sysCode").toString());

        List<ServiceStatementDtlDto> serviceStmtDtlList = serviceStatementMapper.getServiceStmtDtlList(serviceStatementMgtDto);
        return serviceStmtDtlList;
    }

    /**
     * 서비스 견적서 정보
     * @param serviceStatementDto
     * @return
     * @throws Exception
     */
    private ServiceStatementDto getServiceStmtInfo(ServiceStatementDto serviceStatementDto, HttpSession session) throws Exception {
        // null check
        isBlankCheck(serviceStatementDto.getFs_bill_no(), "청구번호가");
        isBlankCheck(serviceStatementDto.getFs_bill_seq(), "청구번호순번이");

        serviceStatementDto.setSys_id(session.getAttribute("sysCode").toString());
        serviceStatementDto = serviceStatementMapper.getServiceStmtInfo(serviceStatementDto);
        return serviceStatementDto;
    }

    /**
     * 서비스 견적서 비고 수정
     * @param serviceStatementDto
     * @param session
     * @return
     * @throws Exception
     */
    public int setServiceStmtRemark(ServiceStatementDto serviceStatementDto, HttpSession session) throws Exception {
        // null check
        isBlankCheck(serviceStatementDto.getFs_bill_no(), "청구번호가");
        isBlankCheck(serviceStatementDto.getFs_bill_seq(), "청구번호순번이");
        isBlankCheck(serviceStatementDto.getSsmm(), "비고가");
        
        serviceStatementDto.setSys_id(session.getAttribute("sysCode").toString());
        serviceStatementDto.setMdfr_id(session.getAttribute("userId").toString());

        int result = serviceStatementMapper.setServiceStmtRemark(serviceStatementDto);

        return result;
    }

    /**
     * 서비스 견적서 삭제(status = 'N')
     * @param serviceStatementMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    public int setDelServiceStmtList(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        int result = 0;

        List<ServiceStatementDto> serviceStatementList = serviceStatementMgtDto.getServiceStatementList();
        if(serviceStatementList == null || serviceStatementList.size() == 0) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }

        for(ServiceStatementDto dto : serviceStatementList) {
            // null check
            isBlankCheck(dto.getFs_bill_no(), "청구번호가");

            // 서비스 견적서 삭제(status = 'N')
            dto.setSys_id(session.getAttribute("sysCode").toString());
            dto.setMdfr_id(session.getAttribute("userId").toString());
            result += serviceStatementMapper.setDelServiceStmtList(dto);

            // 단가내역(견적금액내역)에 청구여부 (fs_bill_yn = 'N')
            serviceStatementMapper.setModiCalculateBillYn(dto);
        }

        return result;
    }

    /**
     * 서비스견적서 고객확인요청
     * @param serviceStatementMgtDto
     * @param session
     * @return
     * @throws Exception
     */
    public int sendEmailForCstReq(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        int result = 0;

        // list check
        List<ServiceStatementDto> serviceStatementList = serviceStatementMgtDto.getServiceStatementList();
        if(serviceStatementList == null || serviceStatementList.size() == 0) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }

        for(ServiceStatementDto dto : serviceStatementList) {
            isBlankCheck(dto.getFs_bill_no(), "청구번호가");
            isBlankCheck(dto.getFs_bill_seq(), "청구번호순번이");

            dto.setSys_id(session.getAttribute("sysCode").toString());
            dto.setMdfr_id(session.getAttribute("userId").toString());
            dto.setBill_req_cd("02"); // 02:확인요청

            // 서비스견적서 고객확인요청 (02:확인요청, 03:확인완료, 04:수정요청)
            result += serviceStatementMapper.setModiServiceStmtBillReq(dto);

            // 서비스 견적서 정보: 메일발송에 필요한 정보
            dto = serviceStatementMapper.getServiceStmtInfo(dto);

            // 메일발송
            MailDto mailDto = new MailDto();
            mailDto.setSend_tp_cd("06"); // 06:서비스견적서 확인요청 시
            mailDto.setReceive_nm(dto.getTax_bill_nm()); // 받는사람명
            mailDto.setReceive_email(dto.getTax_email()); // 받는 email
            mailDto.setReceive_tel_no(dto.getTax_bill_mobile()); // 받는 전화번호
            mailDto.setSend_url("https://fsccsdev.cesco.co.kr/user/login"); // 전달 url
            mailDto.setSend_text("서비스견적서 확인요청"); // text
            mailDto.setSsmm("서비스견적서 확인요청");

            this.sendEmail(mailDto, session);
        }

        return result;
    }

    /**
     * 서비스견적서 확정
     * @param serviceStatementDto
     * @param session
     * @return
     * @throws Exception
     */
    public int setServiceStmtConfirm(ServiceStatementDto serviceStatementDto, HttpSession session) throws Exception {
        // null check
        isBlankCheck(serviceStatementDto.getFs_bill_no(), "청구번호가");
        isBlankCheck(serviceStatementDto.getFs_bill_seq(), "청구번호순번이");
        
        serviceStatementDto.setSys_id(session.getAttribute("sysCode").toString());
        serviceStatementDto.setMdfr_id(session.getAttribute("userId").toString());
        serviceStatementDto.setBill_req_cd("03"); // 03:확인완료

        // 서비스견적서 고객확인요청 (02:확인요청, 03:확인완료, 04:수정요청)
        int result = serviceStatementMapper.setModiServiceStmtBillReq(serviceStatementDto);

        return result;
    }

    /**
     * 서비스견적서 수정요청
     * @param serviceStatementDto
     * @param session
     * @return
     * @throws Exception
     */
    public int setServiceStmtReq(ServiceStatementDto serviceStatementDto, HttpSession session) throws Exception {
        // null check
        isBlankCheck(serviceStatementDto.getFs_bill_no(), "청구번호가");
        isBlankCheck(serviceStatementDto.getFs_bill_seq(), "청구번호순번이");
        isBlankCheck(serviceStatementDto.getBill_mdify_note(), "수정요청내용이");
        
        serviceStatementDto.setSys_id(session.getAttribute("sysCode").toString());
        serviceStatementDto.setMdfr_id(session.getAttribute("userId").toString());
        serviceStatementDto.setBill_req_cd("04"); // 04:수정요청

        // 서비스견적서 고객확인요청 (02:확인요청, 03:확인완료, 04:수정요청)
        int result = serviceStatementMapper.setModiServiceStmtBillReq(serviceStatementDto);

        return result;
    }

    /**
     * 세금계산서발행요청
     * @param serviceStatementMgtDto
     * @param session
     * @return
     */
    public int setTaxBillReq(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) {
        int result = 0;

        List<ServiceStatementDto> serviceStatementList = serviceStatementMgtDto.getServiceStatementList();
        if(serviceStatementList == null || serviceStatementList.size() == 0) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }

        for(ServiceStatementDto dto : serviceStatementList) {
            try {
                // null check
                isBlankCheck(dto.getFs_bill_no(), "청구번호가");
                isBlankCheck(dto.getCont_id(), "계약담당자가");
    
                // 세금계산서발행요청
                dto.setSys_id(session.getAttribute("sysCode").toString());
                result += serviceStatementMapper.setTaxBillReq(dto);
                
            } catch (Exception e) {
                continue;
            }
        }

        return result;
    }


    /**
     * direct email 전송
     * @param serviceStatementMgtDto
     * @param session
     * @throws Exception
     */
    public void sendDirectEmail(ServiceStatementMgtDto serviceStatementMgtDto, HttpSession session) throws Exception {
        // null check
        isBlankCheck(serviceStatementMgtDto.getFs_bill_no(), "청구번호가");
        isBlankCheck(serviceStatementMgtDto.getFs_bill_seq(), "청구번호순번이");
        
        ServiceStatementDto serviceStatementDto = new ServiceStatementDto();
        serviceStatementDto.setSys_id(session.getAttribute("sysCode").toString());
        serviceStatementDto.setFs_bill_no(serviceStatementMgtDto.getFs_bill_no().toString());
        serviceStatementDto.setFs_bill_seq(serviceStatementMgtDto.getFs_bill_seq().toString());

        // 서비스 견적서 정보: 메일발송에 필요한 정보
        serviceStatementDto = serviceStatementMapper.getServiceStmtInfo(serviceStatementDto);

        String content = serviceStatementMgtDto.getEmailContent();


        // 메일발송
        MailDto mailDto = new MailDto();
        mailDto.setSend_tp_cd("07"); // 06:서비스견적서 확인요청 시
        mailDto.setReceive_nm(serviceStatementDto.getTax_bill_nm());
        mailDto.setReceive_email(serviceStatementMgtDto.getDirectEmail());
        mailDto.setReceive_tel_no(serviceStatementDto.getTax_bill_mobile());
        mailDto.setSend_url("https://fsccsdev.cesco.co.kr/user/login");
        mailDto.setSend_text(content);
        mailDto.setSsmm("서비스견적서 확인요청");

        this.sendEmail(mailDto, session);
    }

    /**
     * 메일발송
     * @param serviceStatementDto
     * @param session
     */
    private void sendEmail(MailDto mailDto, HttpSession session) {
        
        // session
        String sysId = session.getAttribute("sysCode").toString();
        String userId = session.getAttribute("userId").toString();
        String userNm = session.getAttribute("userNm").toString();

        // mail dto
        mailDto.setUser_id(userId);
        mailDto.setSys_id(sysId);
        mailDto.setSend_nm(userNm); // 보내는사람
        mailDto.setReceive_email("rddw0804@gmail.com");
        mailDto.setStatus("Y");
        // mailDto.setRgstr_id(userId);
        // mailDto.setRgstr_dt("");
        // mailDto.setMdfr_id(userId);
        // mailDto.setMdfr_dt("");
        // mailDto.setCst_nm("");
        // mailDto.setProd_nm("");
        // mailDto.setSend_chat_st_nm("");

        // 인증 번호 생성
        Integer intval = Utils.generateAuthNo();
        mailDto.setInit_code(intval.toString());

        // subject
        String subject = mailDto.getReceive_nm() + "확인요청.";

        Mail mail = new Mail(commService);
        mail.sendMail(mailDto, subject);
    }

    // null check
    private void isBlankCheck(String value, String str) {
        if(StringUtils.isBlank(value)) {
            throw new CommDuplicateException(str + " 존재하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
        }
    }

}
