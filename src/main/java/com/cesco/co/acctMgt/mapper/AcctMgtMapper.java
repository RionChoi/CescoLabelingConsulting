package com.cesco.co.acctMgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cesco.co.acctMgt.dto.AccSearchInfo;
import com.cesco.co.acctMgt.dto.AccUserInfoDTO;
import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.fs.consultMgt.dto.ConsultStaffDTO;

@Mapper
public interface AcctMgtMapper {    

    // 계정 목록
    List<AccUserInfoDTO> getAccUserList(AccSearchInfo accSearchInfo);

    // 계정 생성
    String getUserId(@Param("user_tp_cd") String user_tp_cd);
  
    // 메일 사용 가능 체크
    Integer getUserEmailCnt(@Param("email") String email);

    // 본인 메일 체크
    Integer getSelfUserEmailCnt(@Param("user_id") String user_id, @Param("email") String email);

    // 소속회사 목록
    List<CusInfoDTO> getComList();

    // 참여 컨설팅 수
    Integer getConsultCnt(@Param("user_id") String user_id);

    // 비밀번호 초기화
    Integer setPassReSet(AccUserInfoDTO accUserInfo);

    // 계정 정보 수정
    Integer setUserInfoUpt(AccUserInfoDTO accUserInfo);

    // 계정 정보 등록
    Integer setUserInfoIns(AccUserInfoDTO accUserInfo);

    // 직원 정보 조회
    ConsultStaffDTO getCescoUserInfo(@Param("email") String email);
    
}
