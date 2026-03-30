package com.cesco.hc.consultMgt_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsultMgtNewReq {

    private String sys_id;             // 시스템 id
    private String fs_no;              // 컨설팅번호
    private String cst_id;             // 고객사 코드 
    private String prod_nm;            // 상품명
    private String prod_tp_cd;         // 상품유형
    private String prod_tp_cust_nm;         // 상품유형
    private String prod_mafa_nm;       // 제조업체명
    private String prod_item;          // 품목보고번호
    private String user_id;            // 사용자 id    


}
