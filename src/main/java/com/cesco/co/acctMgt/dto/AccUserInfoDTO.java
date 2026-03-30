package com.cesco.co.acctMgt.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccUserInfoDTO {    

    private String user_id;         // 사용자 ID
    private String user_nm;         // 사용자 명
    private String pwd;              // 비밀번호(등록용)
    private String cst_id;          // 소속사코드    
    private String cst_nm;          // 소속사명
    private String user_tp_cd;      // 사용자구분 01 고객 02 운영자 03 관리자
    private String user_ref_id;     // 사용자 연결코드
    private String email;           // 이메일
    private String tel_no;          // 전화번호
    private String mphone_no;       // 휴대전화번호
    private String fax_no;          // 팩스
    private String cst_tp_cd;       // 고객구분01원청02협력사
    private String user_posit_nm;   // 직위명
    private String user_dept_nm;    // 부서명
    private String user_dept_nm_view; // 부서명(세스코)
    private String status;          // 상태
    private Date rgstr_dt;          // 등록일
    private String rgstr_nm;        // 등록자명
    private String rgstr_id;        // 등록자id
    private Date mdfr_dt;           // 수정일
    private String mdfr_nm;         // 수정자명
    private String mdfr_id;         // 수정자id
    private String consult_cnt;     // 참여 컨설팅 수
    private String notice1_yn;      // 신규의뢰시 알림
    private String notice2_yn;      // 멘션 알림
    private String notice3_yn;      // 참여컨설팅 알림
    private String notice4_yn;      // 견젹서 및 입금확인 알림
    
}
