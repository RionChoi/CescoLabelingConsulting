package com.cesco.hc.consultMgt_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultMgtReqDto {
    
    private String p_type;                      //조회구분
	private String sys_id;                      //시스템구분
	private String cst_id;                      //고객사코드
	private String rgstr_id;                    //입력자
	private String food_type;                   //식품유형
	private String product_name;                //제품명
	private String cst_mng_id;                  //담당자
	private String fs_mng_id;                   //세스코담당자
	private String start_date;                  //일자 시작
	private String end_date;                    //일자 끝
	private String fs_status_cd;                //상태
    private String cseco_dept_cd;               //지역본부

}
