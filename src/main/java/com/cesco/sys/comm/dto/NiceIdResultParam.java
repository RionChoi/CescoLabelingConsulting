package com.cesco.sys.comm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NiceIdResultParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6604029340971900113L;
	private String encodeData;	/** 휴대폰 본인인증 결과 암호문 */
	private String userNm;			// 사용자 명
	private String telNo;				// 사용자 휴대폰 번호
	private String sessionId;	/** 회원 인증 세션 모음 세션ID */
}
