package com.cesco.hc.consultMgt_hc.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultMgtResDto implements Serializable {
    private static final long serialVersionUID = -2652142427983006421L;

    private String sys_id;
    private String cst_nm;			    // 고객사명
    private String fs_no;			    // 컨설팅번호
    private String fs_seq;			    // 컨설팅순번합계
    private String prod_cd;             // 상품코드
    private String prod_nm;             // 상품명  
    private String prod_tp_nm;		    // 상품유형명 
    private String corp_nm;             // 참여자명
    private String cst_mng_id;		    // 고객담당자
    private String cst_mng_nm;          // 고객담당자명
    private String cst_mng_mobile;      // 담당자연락처
    private String bill_cst_id;	        // 정산거래처 :  신청시 등록시 넣는다.
    private String bill_cst_nm;         // 정산거래처명
    private String receive_date;	    // 접수일자
    private String tax_bill_id;         // 정산담당자
    private String tax_bill_nm;         // 정산담당자명
    private String tax_bill_email;      // 정산담당자이메일
    private String fs_bill_item;        // 세금계산서 발행여부/청구번호
    private String fs_bill_no;          // 청구번호
    private String fs_mng_id;		    // 세스코담당자
    private String cst_cesco_mng_nm;    // 세스코담당자명
    private String cseco_dept_cd;       // 지역본부 코드
    private String cseco_dept_nm;       // 지역본부 명
    private String md_day;              // 계약일수
    private String mw_day;              // 진행일수
    private String fs_status_cd;        // 진행상태
    private String fs_status_nm;        // 진행상태명
    private String mem_cnt;             // 참여자수
    private String est_amt;             // 견적금액
    private String bill_ar_amt;         // 납입금액(수금금액)  
    private String bill_amt;            // 청구금액  
    private String bill_net_amt;        // 미청구금액  
    private String mdfr_dt;             // 수정일시 
    private String complt_yn;           // 완료여부
    private String cont_yn;             // 동의여부		    
    private String cont_no;             // 계약번호
    private String cont_id;             // 계약사원번호   
    private String cont_nm;             // 계약자명
    private String complet_tp_cd;       // 완료확인서 상태
    private String complet_stt;         // 완료확인서 상태 1:작성중, 2:확인요청, 3:완료

}
