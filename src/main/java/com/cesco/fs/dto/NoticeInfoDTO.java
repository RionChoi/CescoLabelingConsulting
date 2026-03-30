package com.cesco.fs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfoDTO {
    
    private Integer idx;
    private Integer state;
    private String title;
    private String opener;
    private Integer count;
    private String regdate;
    private String regenddate;
    private String cmm_dtl_cd;

}
