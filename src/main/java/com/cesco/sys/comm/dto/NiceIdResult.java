package com.cesco.sys.comm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NiceIdResult implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = -3834423154563810229L;
	private String cipherTime;		/** 복호화한 시간 */
	private String requestNumber;	/** 요청 번호 */
    private String responseNumber;  /** 인증 고유번호 */
    private String authType;        /** 인증 수단 */
    private String name;            /** 성명 */
    private String birthDate;       /** 생년월일(YYYYMMDD) */
    private String gender;          /** 성별 */
    private String nationalInfo;    /** 내/외국인정보 ("0":내국인, "1":외국인) */
    private String dupInfo;         /** 중복가입 확인값 (DI_64 byte) */
    private String connInfo;        /** 연계정보 확인값 (CI_88 byte) */
    private String mobileNo;        /** 휴대전화번호 */
    private String mobileCo;        /** 통신사 */
}
