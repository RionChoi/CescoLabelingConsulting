package com.cesco.fs.main.dto;

import java.util.List;

import com.cesco.co.notice.dto.NoticeDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainDto {
    
    private String sys_id;
    private String cst_id;
    private String user_id;
    private String fs_mng_id;
    private List<NoticeDto> noticeList;
    private List<MainConsultingDto> mainConsultingList;

}