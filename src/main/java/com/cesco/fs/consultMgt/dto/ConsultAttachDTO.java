package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultAttachDTO {

    private String file_cd;       // 파일 유형선택
    private String file_org_nm;   // 파일 원본 이름
    private String file_nm;       // 파일 이름
    
}
