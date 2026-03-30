package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultChatStartDTO {

    private String sys_id;
    private String fs_no;               // 컨설팅 ID
    private String fs_chat_id;          // userID
    private String fs_chat_seq;         // chat SEQ
    private String fs_chat_ref_seq;
    private String contents;            // 내용
    private String fs_status_cd;        // 02
    private String status;              // Y
    private String rgstr_id;            // 등록자 ID    
    private String mdfr_id;             // 수정자 ID

    public ConsultChatStartDTO(String sys_id, String fs_no, String fs_chat_id, String fs_chat_seq, String fs_chat_ref_seq
                        ,String contents, String fs_status_cd, String status, String rgstr_id, String mdfr_id ){

        this.sys_id = sys_id;
        this.fs_no = fs_no;
        this.fs_chat_id = fs_chat_id;
        this.fs_chat_seq = fs_chat_seq;
        this.fs_chat_ref_seq = fs_chat_ref_seq;
        this.contents = contents;
        this.fs_status_cd = fs_status_cd;
        this.status = status;
        this.rgstr_id = rgstr_id;
        this.mdfr_id = mdfr_id;

    }
    
}
