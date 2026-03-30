package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultManagerDTO {
	private String fs_no;
	private Integer fs_seq;
    private String cst_id;              // 협력사 ID
	private String cst_nm;              // 협력사 이름
	private String user_id;             // 담당자 ID
	private String user_nm;             // 담당자 이름
	private String fs_chat_yn;          // 참여 여부
	private String fs_bill_mng_yn;      // 정산 여부
    private String user_dept_nm;        // 부서명	
	private String status;				// 'Y'
	private String rgstr_id;			// 등록자 ID
	private String mdfr_id;				// 수정자 ID
}
