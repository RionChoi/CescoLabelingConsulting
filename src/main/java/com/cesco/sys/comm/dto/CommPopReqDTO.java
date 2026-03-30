package com.cesco.sys.comm.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommPopReqDTO implements Serializable{
    
    String srch_user_nm;        // 담당자(사용자)명 검색 param
    String srch_cst_nm;         // 고객사명 검색 param
    // String srch_partner_nm;     // 협력사명 검색 param
    String p_type;              // 프로시저 type : 01 고객사명으로 검색, 02 협력사명으로 검색, 03 고객사, 협력사 둘다 동시 검색
    
}
