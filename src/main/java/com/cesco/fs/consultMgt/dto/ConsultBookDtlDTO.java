package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultBookDtlDTO {
    
    private String sys_id;          // FS : 표시컨설팅
    private Integer book_seq;       // Mst IDX
    private Integer book_sub_seq;   // IDX
    private String cst_ref_id;      // 즐겨찾기 협력사 ID
    private String cst_ref_nm;      // 즐겨찾기 협력사명
    private String status;          // 상태 Y : 사용
    private String rgstr_id;
    private String rgstr_dt;
    private String mdfr_id;
    private String mdfr_dt;
    private String ssmm;

}
