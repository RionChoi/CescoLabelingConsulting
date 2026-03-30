package com.cesco.hc.consultMgt_hc.dto;

import java.util.ArrayList;
import java.util.List;
import com.cesco.sys.comm.dto.ConsultingAttach;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultMgtHcReqInfo {
    
    private String userId;          // 로그인 유저 ID
    private String sys_id;          // 시스템ID
    private String fs_no;           // 컨설팅 번호
    private String cst_id;          // 고객사ID    

    private ConsultMgtHcMstDTO consultMgtHcMst;                     // 상품정보
    private List<ConsultMgtHcManagerDTO> listConsultMgtHcManager;   // 담당자 리스트
    private List<ConsultingAttach> listConsultAttach;               // 첨부 파일 리스트
    private List<ConsultingAttach> listConsultAttachDel;            // 첨부 삭제 파일 리스트    
    private List<ConsultMgtHcAmtDTO> listConsultMgtHcAmt;           // 견적 단가 리스트
    private List<ConsultMgtHcProdTpCdInfo> listProdTpCd;            // 식품유형 코드 목록
    private List<ConsultMgtHcMstDTO> listConsultMgtHcMst;           // 상품정보
    private List<ConsultMgtHcMdayDTO> listConsultMgtHcMday;         // Mday 목록

    // 기본값 설정
    public void setConsultInfo(String userId){

        this.sys_id = this.consultMgtHcMst.getSys_id();
        this.fs_no = this.consultMgtHcMst.getFs_no();
        this.cst_id = this.consultMgtHcMst.getCst_id();
        this.userId = userId;
        
    }

    // 담당자 설정
    public void setManagerInfo(){

        for(ConsultMgtHcManagerDTO manager : this.listConsultMgtHcManager){

            if(manager.getFs_bill_mng_yn() == null){
                manager.setFs_bill_mng_yn("N");
            }

            if(manager.getFs_chat_yn() == null){
                manager.setFs_chat_yn("N");
            }

            manager.setSys_id(this.sys_id);
            manager.setFs_no(this.fs_no);
            manager.setFs_seq(1);
            manager.setStatus("Y");
            manager.setRgstr_id(this.userId);
            manager.setMdfr_id(this.userId);

            // 세스코 담당자 제외 후 정산 담당자 가져오기
            if(!manager.getCst_id().equals("10000000") && manager.getFs_bill_mng_yn().equals("Y")){
                consultMgtHcMst.setBill_cst_id(manager.getCst_id());    // bill_cst_id = manager.getCst_id();
                consultMgtHcMst.setTax_bill_id(manager.getUser_id());   // bill_id     = manager.getUser_id();
            }
        }

    }

    // 상품 기본 정보 설정
    public void setProdInfo() throws CloneNotSupportedException {

        consultMgtHcMst.setRgstr_id(this.userId);
        consultMgtHcMst.setMdfr_id(this.userId);
        consultMgtHcMst.setProd_ser_cd("");
        consultMgtHcMst.setProd_unit_cd("");

        int fn_seq = 1;

        listConsultMgtHcMst = new ArrayList<>();

        for(ConsultMgtHcProdTpCdInfo prodTpcd : listProdTpCd){ 
            
            ConsultMgtHcMstDTO prod = (ConsultMgtHcMstDTO)consultMgtHcMst.clone();
            
            prod.setProd_tp_cd(prodTpcd.getCmm_dtl_cd());
            prod.setProd_tp_cust_nm(prodTpcd.getCmm_dtl_nm());
            prod.setFs_seq(fn_seq++);
            
            listConsultMgtHcMst.add(prod);
        }

    }

    // 정산 설정
    public void setAmt(){
        this.listConsultMgtHcAmt = new ArrayList<>();
        int fs_seq = 0;
        
        // 계약금 01        
        listConsultMgtHcAmt.add(new ConsultMgtHcAmtDTO(this.sys_id, this.fs_no, fs_seq, this.cst_id,"01", "Y", 
                                this.consultMgtHcMst.getEst_amt1(), "Y", this.userId, this.userId, this.fs_no + fs_seq));
        // fs_seq++;
        // 중도금 02
        listConsultMgtHcAmt.add(new ConsultMgtHcAmtDTO(this.sys_id, this.fs_no, fs_seq, this.cst_id,"02", "Y", 
                                this.consultMgtHcMst.getEst_amt2(), "Y", this.userId, this.userId, this.fs_no + fs_seq));
        // fs_seq++;
        // 잔금 03
        listConsultMgtHcAmt.add(new ConsultMgtHcAmtDTO(this.sys_id, this.fs_no, fs_seq, this.cst_id,"03", "Y", 
                                this.consultMgtHcMst.getEst_amt3(), "Y", this.userId, this.userId, this.fs_no + fs_seq));
    }    
    
    // 계약 MD 설정
    public void setMDay(){

        listConsultMgtHcMday = new ArrayList<>();

        int mCnt = consultMgtHcMst.getMd_day();
        String cst_mng_id = consultMgtHcMst.getCst_mng_id();
        String fs_mng_id = consultMgtHcMst.getFs_mng_id();

        for(int i = 1; i <= mCnt; i++){            
            listConsultMgtHcMday.add(new ConsultMgtHcMdayDTO(this.sys_id, this.fs_no, String.valueOf(i), cst_mng_id, fs_mng_id
                                   , "", "", "", "", "Y"
                                   , this.userId, this.userId));
        }


    }
}
