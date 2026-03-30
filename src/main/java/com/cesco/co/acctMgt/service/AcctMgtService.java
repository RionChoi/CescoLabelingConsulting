package com.cesco.co.acctMgt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.co.acctMgt.dto.AccSearchInfo;
import com.cesco.co.acctMgt.dto.AccUserInfoDTO;
import com.cesco.co.acctMgt.mapper.AcctMgtMapper;
import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultStaffDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AcctMgtService {
    
    
    private final AcctMgtMapper acctMgtMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 계정 목록
    public List<AccUserInfoDTO> getAccUserList(AccSearchInfo accSearchInfo) throws Exception{
        return acctMgtMapper.getAccUserList(accSearchInfo);
    }

    // 계정 생성
    public String getUserId(String user_tp_cd) throws Exception{
        return acctMgtMapper.getUserId(user_tp_cd);
    }

    // 메일 사용 가능 체크
    public Integer getUserEmailCnt(String email) throws Exception{
        return acctMgtMapper.getUserEmailCnt(email);
    }

    // 본인 메일 체크
    public Integer getSelfUserEmailCnt(String user_id, String email) throws Exception{
        return acctMgtMapper.getSelfUserEmailCnt(user_id, email);
    }

    // 소속회사 목록
    public List<CusInfoDTO> getComList() throws Exception{
        return acctMgtMapper.getComList();
    }

    // 참여 컨설팅 수
    public Integer getConsultCnt(String user_id) throws Exception{
        return acctMgtMapper.getConsultCnt(user_id);
    }

    // 비밀번호 초기화
    public Integer setPassReSet(String user_id, String userId) throws Exception{

        AccUserInfoDTO accUserInfo = new AccUserInfoDTO();

        accUserInfo.setUser_id(user_id);
        accUserInfo.setMdfr_id(userId);
        accUserInfo.setPwd(passwordEncoder.encode("0000"));

        return acctMgtMapper.setPassReSet(accUserInfo);
    }    

    /**
     * 계정 정보 수정
     * @param accUserInfo   계정 정보
     * @param loginId       수정자 ID
     * @return
     * @throws Exception
     */
    public Integer setUserInfoUpt(AccUserInfoDTO accUserInfo, String loginId) throws Exception{

        accUserInfo.setMdfr_id(loginId);

        return acctMgtMapper.setUserInfoUpt(accUserInfo);
    }
    
    /**
     * 계정 정보 등록
     * @param accUserInfo   계정 정보
     * @param loginId       수정자 ID
     * @return
     * @throws Exception
     */
    public Integer setUserInfoIns(AccUserInfoDTO accUserInfo, String loginId) throws Exception{

        accUserInfo.setRgstr_id(loginId);
        accUserInfo.setMdfr_id(loginId);
        accUserInfo.setPwd(passwordEncoder.encode("0000"));

        return acctMgtMapper.setUserInfoIns(accUserInfo);
    }

    // 직원 정보 조회
    public ConsultStaffDTO getCescoUserInfo(String email) throws Exception{
        return acctMgtMapper.getCescoUserInfo(email);
    }
    
}
