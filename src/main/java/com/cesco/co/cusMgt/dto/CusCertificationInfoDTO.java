package com.cesco.co.cusMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CusCertificationInfoDTO {
    
    private String cust_cd;         // ERP고객코드
    private String cust_nm;         // 고객사명
    private String biz_no;          // 사업자번호
    private String biz_sub_no;      // 종사업자번호
    
}
