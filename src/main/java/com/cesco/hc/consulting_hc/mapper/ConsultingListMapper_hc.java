package com.cesco.hc.consulting_hc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.fs.consulting.dto.ConsChatReqDto;
import com.cesco.hc.consulting_hc.dto.ActivityRecordListDto;
import com.cesco.hc.consulting_hc.dto.CompletAttachDto;
import com.cesco.hc.consulting_hc.dto.CompletAttachListDto;
import com.cesco.hc.consulting_hc.dto.ConCompletReqDto;
import com.cesco.hc.consulting_hc.dto.ConCompleteDto;
import com.cesco.hc.consulting_hc.dto.ConsultingReqDto;
import com.cesco.hc.consulting_hc.dto.ConsultingResDto;
import com.cesco.hc.consulting_hc.dto.HcgetChatStatusDto;
import com.cesco.hc.consulting_hc.dto.NonServiceVisitDateRegDto;
import com.cesco.hc.consulting_hc.dto.TbFsHcEtcdayDto;
import com.cesco.hc.consulting_hc.dto.TbFsHcMdayDto;


@Mapper
public interface ConsultingListMapper_hc {
  
  public List<ConsultingResDto> getList(ConsultingReqDto consultingDto) throws Exception;

  public ConCompleteDto getComplete(ConCompletReqDto param) throws Exception;

  public List<CompletAttachDto> getCompletAttach(CompletAttachListDto attachListDto) throws Exception;

  public int setComplete(ConCompleteDto param) throws Exception;

  int setReqUpdate(ConCompleteDto param) throws Exception;
  
  public int setCompletAttachInfo(CompletAttachDto param) throws Exception;

  CompletAttachDto getAttachDownloadInfo(CompletAttachDto attachDto) throws Exception;

  int setDelAttach(CompletAttachDto param) throws Exception;

  int setMsgAlarm(ConCompleteDto param) throws Exception;
  
  public List<HcgetChatStatusDto> uspHcgetChatStatusList(ConsChatReqDto consultingDto) throws Exception;
  public List<TbFsHcEtcdayDto> uspHcgetCoslEtcDayList(ConsChatReqDto consultingDto) throws Exception;
  public List<TbFsHcMdayDto> uspHcgetCoslMDayList(ConsChatReqDto consultingDto) throws Exception;
  int saveVisitConfirmation(NonServiceVisitDateRegDto date) throws Exception;
  int nonServiceVisitDateReg(NonServiceVisitDateRegDto date) throws Exception;
  int nonServiceVisitDateDel(NonServiceVisitDateRegDto date) throws Exception;
  
}
