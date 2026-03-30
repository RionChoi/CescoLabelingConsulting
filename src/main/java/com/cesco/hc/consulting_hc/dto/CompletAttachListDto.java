package com.cesco.hc.consulting_hc.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletAttachListDto {
    
    String fs_no;
    String sys_id;
    String atch_kn_cd;

    List<CompletAttachDto> completAttachDto;
}
