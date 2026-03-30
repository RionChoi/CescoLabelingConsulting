package com.cesco.sys.comm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.sys.comm.dto.ChangePasswdParam;
import com.cesco.sys.comm.dto.CommCodeDTO;
import com.cesco.sys.comm.dto.CommDtlCodeDTO;
import com.cesco.sys.comm.dto.ConsultingAttach;
import com.cesco.sys.comm.dto.MailDto;
import com.cesco.sys.comm.dto.SendPhoneDto;
import com.cesco.sys.comm.dto.UserDTO;
import com.cesco.sys.comm.dto.UserReqDTO;

@Mapper
public interface CommMapper {

    /**
     * 사용자 조회
     * @return
     */
    UserDTO getUser(UserReqDTO param) throws Exception;
    
    /**
     * 사용자 조회 이메일
     * @return
     */
    UserDTO getUsersMail(UserReqDTO param) throws Exception;
    /**
     * 사용자 조회 phone
     * @return
     */
    UserDTO getUsersPhone(UserReqDTO param) throws Exception;
    
    /**
     * 사용자 조회 이름/폰
     * @return
     */
    UserDTO findUserId(UserReqDTO param) throws Exception;

    /**
     * 로그인 이력 조회
     * @return
     */
    List<UserDTO> getUserHis(UserReqDTO param) throws Exception;

    /**
     * 로그인 이력 저장
     * @return
     */
    int saveLoginHis(UserDTO  param) throws Exception;

    /**
     * 이메일 저장
     * @return
     */
    int saveEmail(MailDto  param) throws Exception;
    
    /**
     * 비밀번호 초기화
     * @return
     */
    int loginForgotPwEnter(ChangePasswdParam  param) throws Exception;

    /**
     * 비밀번호 변경
     * @return
     */
    int changePass(ChangePasswdParam param) throws Exception;
    
    /**
     * 로그인 이력 저장
     * @return
     */
    String saveUspFsSetAttach(ConsultingAttach param) throws Exception;

    /**
     * 공통 코드 목록 조회
     * @return
     */
    List<CommDtlCodeDTO> getCommDtlCode(CommCodeDTO commCode) throws Exception;

    /**
     * 고객사 combo box 조회
     * @param commCode
     * @return
     * @throws Exception
     */
    List<CommDtlCodeDTO> getComboCstList(CommCodeDTO commCode) throws Exception;

    /**
     * 관리자 combo box 조회
     * @param commCode
     * @return
     * @throws Exception
     */
    List<CommDtlCodeDTO> getComboMngList(CommCodeDTO commCode) throws Exception;

    /**
     * 부서 combo box 조회
     * @param commcode
     * @return
     * @throws Exception
     */
    List<CommDtlCodeDTO> getComboDeptList(CommCodeDTO commcode) throws Exception;

    /**
     * 휴대폰 인증 발송
     * @return
     */
    int uspFsSetCommCellPhone(SendPhoneDto  param) throws Exception;

    /**
     * 세션 알림정보 변경
     * @param userDto
     * @return
     * @throws Exception
     */
    int setUserChangeNoti(UserDTO userDto) throws Exception;
    
}
