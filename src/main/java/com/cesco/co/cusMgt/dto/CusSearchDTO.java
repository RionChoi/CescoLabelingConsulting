package com.cesco.co.cusMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CusSearchDTO {

    private String cst_id;          // 고객사 ID
    private String cst_nm;          // 고객사 명
    private String cst_biz_no;      // 사업자 등록 번호
    private String status;          // 거래상태
    private String userId;          // 담당자ID(미사용)
        
}
