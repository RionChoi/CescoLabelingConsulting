package com.cesco.hc.consulting_hc.service;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.cesco.hc.consulting_hc.mapper.ConsultingListMapper_hc;
import com.cesco.hc.main_hc.dto.ActivityRecordDto;
import com.cesco.sys.comm.dto.FtpUploadReturn;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultingListService_hc {
  

  private final ConsultingListMapper_hc consultingMapper;

  @Autowired
  private final CommService commService;
  
  /**
   * @apiNote 표시컨설팅_목록 조회
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public List<ConsultingResDto> getList(ConsultingReqDto param) throws Exception{
      param.setSys_id("HC");
      return consultingMapper.getList(param);
  }

  public ConCompleteDto getComplete(ConCompletReqDto param, HttpSession session) throws Exception{

    if(((String)session.getAttribute("userId")).equals(param.getFs_mng_id())){
      if(param.getComplet_tp_cd().equals("01") || param.getComplet_tp_cd().equals("04") ){
        param.setWrite_auth("1"); //수정가능(컨설턴트)
      }else{
        param.setWrite_auth("3");
      }
    }else if(((String)session.getAttribute("userId")).equals(param.getCst_mng_id())){
        if(param.getComplet_tp_cd().equals("02")){
          param.setWrite_auth("2"); //서명가능(고객담당자)
        }else{
          param.setWrite_auth("3");
        }
    }else{
      param.setWrite_auth("3"); //읽기가능(그외 사용자)3
    }

    return consultingMapper.getComplete(param);
  }

  public List<CompletAttachDto> getCompletAttachList(CompletAttachListDto attachListDto) throws Exception{
    List<CompletAttachDto> attachList =  consultingMapper.getCompletAttach(attachListDto);
    return attachList;
  }

  /**
     * 파일 정보 저장
     * @param CompletAttachDto
     * @throws Exception
     */
    private int setCompletAttachInfo(CompletAttachDto attachDto) throws Exception {
      return consultingMapper.setCompletAttachInfo(attachDto);
  }

  /**
   * 완료확인서 저장
   */
  public int setComplete(ConCompleteDto param) throws Exception{
    //관리자 서명 이미지 저장
    // if(param.getFs_mng_sig_yn().equals("Y")){
    //   String image = param.getCon_text102();
    //   byte[] decodeBytes = Base64.getDecoder().decode(image);
    //   String fileName = param.getFs_mng_id()+"_sign.png"; 
    //   //FileOutputStream fos = new FileOutputStream("D:\\"+ param.getFs_mng_id()+"sign.png");
    //   //fos.write(decodeBytes);
    //   //ftp 파일 저장
    //   FtpUploadReturn ftpUploadReturn = commService.uploadImgFTP(param.getSys_id(),fileName,decodeBytes);
      
    //   // 서명 이미지 파일정보
    //   CompletAttachDto attachDto = new CompletAttachDto();
    //   attachDto.setSys_id(param.getSys_id());
    //   attachDto.setFs_no(param.getFs_no());
    //   attachDto.setAtch_kn_cd("F00005");
    //   attachDto.setAtch_nm(fileName);
    //   attachDto.setAtch_size("");
    //   attachDto.setUrl(ftpUploadReturn.getDirectory() + "/");
    //   attachDto.setOrg_file_nm(fileName);
    //   attachDto.setSer_file_nm(ftpUploadReturn.getFilename());
    //   attachDto.setStatus("Y");
    //   attachDto.setRgstr_id(param.getFs_mng_id());
    //   attachDto.setMdfr_id(param.getFs_mng_id());
    //   attachDto.setAtch_ref_id("1");
      
    //   param.setCon_text102(ftpUploadReturn.getFilename());
    //   // 서명 파일 정보 저장
    //   this.setCompletAttachInfo(attachDto);
    // }
    param.setStatus("Y");
    return consultingMapper.setComplete(param);
  }

  public int setReqUpdate(ConCompleteDto param) throws Exception{
    int result = consultingMapper.setReqUpdate(param);

    if(result == 0) {
        throw new CommDuplicateException("수정에 실패하였습니다.", ErrorCode.INTER_SERVER_ERROR);
    }

    return result;
    //consultingMapper.setReqUpdate(param);
  }

  //파일 정보 가져오기
  public CompletAttachDto getAttachDownloadInfo(String filename, String viewid) throws Exception {

    // 입력값 체크
    this.isBlankCheck(filename, "서버파일명이");

    CompletAttachDto attachDto = new CompletAttachDto();
    attachDto.setSer_file_nm(filename);
    attachDto = consultingMapper.getAttachDownloadInfo(attachDto);

    return attachDto;
  }

  //파일 저장
  public void setAttachInfo(ConCompleteDto completInfo, List<MultipartFile> fileList, List<CompletAttachDto> attachDto, HttpSession session) throws Exception {

    // 삭제된 파일 존재시 => status = 'N' update
    if(attachDto != null && attachDto.size() > 0) {
        this.setDelAttach(completInfo, attachDto, session);
    }
    
    // 공지사항 파일 저장
    this.ftpFileUpload(completInfo, fileList);
}

  /**
   * 첨부파일 저장
   * @param completInfo
   * @param fileList
   */
  private void ftpFileUpload(ConCompleteDto completInfo, List<MultipartFile> fileList) {
      try {
          if(fileList != null){
              for(MultipartFile f : fileList){
                  long fileSize = 0;
                  int maxFileSize = 1000 * (1024 * 1024);
                  fileSize = f.getSize();
                  
                  if (maxFileSize > 0 && fileSize > maxFileSize) {
                      throw new CommDuplicateException("저장가능한 파일크기(" + maxFileSize + ")를 초과하였습니다." ,ErrorCode.UPLOAD_FAIL);
                  }

                  // 파일 업로드
                  FtpUploadReturn ftpUploadReturn = commService.uploadFileFTP(f, "FS",f.getOriginalFilename());

                  //파일정보
                  CompletAttachDto attachDto = new CompletAttachDto();
                  attachDto.setSys_id(completInfo.getSys_id());
                  attachDto.setFs_no(completInfo.getFs_no());
                  attachDto.setAtch_kn_cd("F00006"); //첨부파일
                  attachDto.setAtch_nm(f.getOriginalFilename());
                  attachDto.setAtch_size(fileSize+ "");
                  attachDto.setUrl(ftpUploadReturn.getDirectory() + "/");
                  attachDto.setOrg_file_nm(f.getOriginalFilename());
                  attachDto.setSer_file_nm(ftpUploadReturn.getFilename());
                  attachDto.setStatus("Y");
                  attachDto.setRgstr_id(completInfo.getFs_mng_id());
                  attachDto.setMdfr_id(completInfo.getFs_mng_id());
                  attachDto.setAtch_ref_id("1");

                  //파일 정보 저장
                  this.setCompletAttachInfo(attachDto);
              }
          }
      } catch (Exception e) {
          throw new CommDuplicateException("",ErrorCode.UPLOAD_FAIL);
      }
  }

  //첨부파일 삭제
  public int setDelAttach(ConCompleteDto completInfo, List<CompletAttachDto> delAttachList, HttpSession session) throws Exception {
    int result = 0;

    String userId = session.getAttribute("userId").toString();
    String fs_no = completInfo.getFs_no();

    if(delAttachList != null && delAttachList.size() > 0) {
        for(CompletAttachDto dto : delAttachList) {
            // 입력값 체크
            this.isBlankCheck(dto.getSys_id(), "시스템 아이디가");
            this.isBlankCheck(dto.getAtch_seq(), "첨부파일 key가");

            // 첨부파일 상태변경(status = 'N')
            dto.setMdfr_id(userId);
            dto.setFs_no(fs_no);
            result += consultingMapper.setDelAttach(dto);
        }
    }
    
    return result;
  }

  /**
     * 세스톡 보내기
     * @param ConCompleteDto
     * @throws Exception
     */
  public int setMsgAlarm(ConCompleteDto param) throws Exception {
    int result = consultingMapper.setMsgAlarm(param);

    if(result == 0) {
        throw new CommDuplicateException("발송에 실패하였습니다.", ErrorCode.INTER_SERVER_ERROR);
    }

    return result;
  }


  private void isBlankCheck(String value, String str) {
    if(StringUtils.isBlank(value)) {
        throw new CommDuplicateException(str + " 존재하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
    }
  }

  /**
   * @apiNote 협업툴안에서 진행상태 표시
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public List<HcgetChatStatusDto> uspHcgetChatStatusList(ConsChatReqDto param) throws Exception{
    param.setP_type("1");

    param.setSys_id("HC");
    return consultingMapper.uspHcgetChatStatusList(param);
}

  /**
   * @apiNote 시간외방문내역조회
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public List<TbFsHcEtcdayDto> uspHcgetCoslEtcDayList(ConsChatReqDto param) throws Exception{
    param.setP_type("1");

    param.setSys_id("HC");
    return consultingMapper.uspHcgetCoslEtcDayList(param);
}

  /**
   * @apiNote 수행일지내역
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public List<TbFsHcMdayDto> uspHcgetCoslMDayList(ConsChatReqDto param) throws Exception{

    param.setSys_id("HC");
    return consultingMapper.uspHcgetCoslMDayList(param);
}

  /**
   * @apiNote 서비스외 방문일 확인
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public void saveVisitConfirmation(NonServiceVisitDateRegDto date) throws Exception{
    consultingMapper.saveVisitConfirmation(date);
}

  /**
   * @apiNote 서비스외 방문일 등록
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public void nonServiceVisitDateReg(NonServiceVisitDateRegDto date) throws Exception{
    consultingMapper.nonServiceVisitDateReg(date);
}
  /**
   * @apiNote 서비스외 방문일 삭제
   * @param   ConsultingReqDto
   * @return UserDTO
  */
  public void nonServiceVisitDateDel(NonServiceVisitDateRegDto date) throws Exception{
    consultingMapper.nonServiceVisitDateDel(date);
}
}