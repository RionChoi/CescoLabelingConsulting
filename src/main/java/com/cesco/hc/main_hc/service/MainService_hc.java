package com.cesco.hc.main_hc.service;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import com.cesco.hc.main_hc.mapper.MainMapper_hc;

import com.cesco.hc.main_hc.dto.ActivityRecordDto;
import com.cesco.hc.main_hc.dto.ActivityRecordSetDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.cesco.sys.communityhandlers.CommDuplicateException;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.comm.dto.MailDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService_hc {
    private static final Logger logger = LogManager.getLogger(MainService_hc.class);
    private final MainMapper_hc activityRecordMapper;

    public ActivityRecordDto getActivityRecord(ActivityRecordDto param,HttpSession session) throws Exception{
        logger.info("서비스테스트");
        return activityRecordMapper.getActivityRecord(param);
      }

      public int setActivityRecord(ActivityRecordSetDto param) throws Exception{
        return activityRecordMapper.setActivityRecord(param);
        }
    
/**
     * 세스톡 보내기
     * @param ActivityRecordSetDto
     * @throws Exception
     */
    public int setMsgAlarm(ActivityRecordSetDto param) throws Exception {
      logger.info("메일서비스테스트");
      int result = activityRecordMapper.setMsgAlarm(param);
      logger.info("메일서비스테스트2");
      if(result == 0) {
        throw new CommDuplicateException("발송에 실패하였습니다.", ErrorCode.INTER_SERVER_ERROR);
      }
      logger.info("메일서비스테스트" + result);
      return result;
    }
    
}
