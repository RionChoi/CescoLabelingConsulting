package com.cesco.hc.main_hc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.hc.main_hc.dto.ActivityRecordDto;
import com.cesco.hc.main_hc.dto.ActivityRecordSetDto;
import com.cesco.sys.comm.dto.MailDto;



@Mapper
public interface MainMapper_hc {
    
    public ActivityRecordDto getActivityRecord(ActivityRecordDto param) throws Exception;

    public int setActivityRecord(ActivityRecordSetDto param) throws Exception ;
    //세스톡 보내기
    int setMsgAlarm(ActivityRecordSetDto param) throws Exception;
}
