package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultBookMstDTO {

    private String sys_id;          // FS : 표시컨설팅
    private Integer book_seq;       // IDX
    private String book_tp_cd;      // 
    private String fs_mng_id;       // 등록 유저 ID
    private String cst_id;          // 등록 고객사 ID
    private String book_nm;         // 줄겨찾기 명
    private String status;          // 상태 Y : 사용
    private String rgstr_id;        
    private String rgstr_dt;
    private String mdfr_id;
    private String mdfr_dt;
    private String ssmm;
    
}
