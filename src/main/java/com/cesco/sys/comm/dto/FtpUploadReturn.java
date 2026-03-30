package com.cesco.sys.comm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FtpUploadReturn {

    private String directory;   // 파일 저장 경로
    private String filename;    // 파일 저장 이름
    
}
