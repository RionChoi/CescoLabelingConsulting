package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultCusInfoDTO {

    private String fs_no;               // 컨설팅 번호
    private String cst_id;              // 고객사 ID
    private String cst_nm;              // 고객사 명
    private String cusMainManager;      // 주 담당자 ID
    private String cescoMainManager;    // 세스코 주 담당자 ID
    private String tax_bill_tp_cd;      // 정산 담당자
    private String cont_id;             // 계약자 ID
        
}
