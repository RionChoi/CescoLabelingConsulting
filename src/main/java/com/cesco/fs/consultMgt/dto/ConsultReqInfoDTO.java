package com.cesco.fs.consultMgt.dto;

import java.util.ArrayList;
import java.util.List;

import com.cesco.sys.comm.dto.ConsultingAttach;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultReqInfoDTO {

    private String reg_type;        // 의뢰 입력자 S: 관리자, C: 고객사
    private String sys_id;
    private String bill_cst_id;     // 정산 담당자 CST_ID
    private String bill_id;         // 정산 담당자 ID
    private String userId;          // 로그인 유저 ID

    private ConsultCusInfoDTO consultCusInfo;               // 컨설팅 정보
    private List<ConsultManagerDTO> listConsultManager;     // 담당자 리스트
    private List<ConsultProdInfoDTO> listConsultProdInfo;   // 제품 리스트
    private List<ConsultingAttach> listConsultEstAttach;    // 견적서 파일 리스트
    private List<ConsultingAttach> listConsultAttach;       // 첨부 파일 리스트
    private List<ConsultingAttach> listConsultAttachDel;       // 첨부 파일 리스트
    private List<ConsultAmtDTO> amtList;                    // 견젹단가 리스트

    // 담당자 기본값 설정
    public void setManagerInfo(){

        for(ConsultManagerDTO manager : this.listConsultManager){

            if(manager.getFs_bill_mng_yn() == null){
                manager.setFs_bill_mng_yn("N");
            }

            if(manager.getFs_chat_yn() == null){
                manager.setFs_chat_yn("N");
            }

            manager.setFs_no(this.consultCusInfo.getFs_no());
            manager.setFs_seq(1);
            manager.setStatus("Y");
            manager.setRgstr_id(this.userId);
            manager.setMdfr_id(this.userId);

            // 세스코 담당자 제외 후 정산 담당자 가져오기
            if(!manager.getCst_id().equals("10000000") && manager.getFs_bill_mng_yn().equals("Y")){
                bill_cst_id = manager.getCst_id();
                bill_id     = manager.getUser_id();
            }
        }

    }

    // 제품 등록 정보 설정
    public void setProdInfo(){

        int fs_seq = 1;                                     // fs_seq 순차 증가
        this.amtList = new ArrayList<>();

        for(ConsultProdInfoDTO prod : this.listConsultProdInfo){            

            prod.setFs_no(this.consultCusInfo.getFs_no());
            prod.setCst_id(this.consultCusInfo.getCst_id());
            prod.setFs_seq(fs_seq++);
            prod.setFs_sub_seq(1);
            prod.setCont_id(this.consultCusInfo.getCont_id());

            if(prod.getReg_channel() == null){
                if(reg_type.equals("S")){
                    prod.setReg_channel("02");
                }else{
                    prod.setReg_channel("01"); 
                }
            }

            prod.setFs_mng_id(this.consultCusInfo.getCescoMainManager());
            prod.setCst_mng_id(this.consultCusInfo.getCusMainManager());
            prod.setRgstr_id(this.userId);
            prod.setMdfr_id(this.userId);

            prod.setBill_cst_id(bill_cst_id);
            prod.setTax_bill_id(bill_id);
            prod.setTax_bill_tp_cd(this.consultCusInfo.getTax_bill_tp_cd());

            ConsultAmtDTO amt = new ConsultAmtDTO(sys_id, this.consultCusInfo.getFs_no(), prod.getFs_seq(), 
                                            this.consultCusInfo.getCst_id(),"01", "Y", prod.getEst_amt(), "Y", 
                                            userId, userId, this.consultCusInfo.getFs_no() + prod.getFs_seq());

            amtList.add(amt);
        }

    }
    
}
