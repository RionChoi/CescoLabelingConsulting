package com.cesco.hc.consultMgt_hc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultMgtHcAmtDTO {
    private String sys_id;          // 시스템 구분 FS : 표시컨설팅
    private String fs_no;           // 컨설팅 번호
    private Integer fs_seq;         // 컨설팅 순번
    private String cst_id;          // 대금청구 고객사번호
    private String est_tp_cd;       // 청구구분 01선금 02중도금 03 잔금
    private String est_use_yn;      // 청구구분 01선금 02중도금 03 잔금
    private Integer est_amt;        // 견적금액
    private String status;          
    private String rgstr_id;
    private String mdfr_id;
    private String search_seq;

    public ConsultMgtHcAmtDTO(String sys_id, String fs_no, Integer fs_seq, String cst_id, String est_tp_cd
                    ,String est_use_yn , Integer est_amt, String status, String rgstr_id, String mdfr_id, String search_seq){

        this.sys_id         = sys_id;
        this.fs_no          = fs_no;
        this.fs_seq         = fs_seq;
        this.cst_id         = cst_id;
        this.est_tp_cd      = est_tp_cd;
        this.est_use_yn     = est_use_yn;
        this.est_amt        = est_amt;
        this.status         = status;
        this.rgstr_id       = rgstr_id;
        this.mdfr_id        = mdfr_id;
        this.search_seq     = search_seq;

    }    
}
