package com.cesco.co.notice.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cesco.co.notice.dto.NoticeAttachDto;
import com.cesco.co.notice.dto.NoticeDtlDto;
import com.cesco.co.notice.dto.NoticeDto;
import com.cesco.co.notice.dto.NoticeMgtDto;
import com.cesco.co.notice.dto.NoticeReadDto;
import com.cesco.co.notice.mapper.NoticeMgtMapper;
import com.cesco.sys.comm.dto.FtpUploadReturn;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class NoticeMgtService {
 
    private final NoticeMgtMapper noticeMgtMapper;

    private final CommService commService;

    /**
     * 공지사항 목록
     * @param noticeMgtDto
     * @param session
     * @return
     */
    public List<NoticeDto> getNoticeList(NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {
        noticeMgtDto.setSys_id(session.getAttribute("sysCode").toString());
        List<NoticeDto> noticeList = noticeMgtMapper.getNoticeList(noticeMgtDto);
        return noticeList;
    }

    /**
     * 공지사항 단건
     * @param noticeMgtDto
     * @return
     */
    public NoticeDto getNoticeInfo(NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {

        // 입력값 체크
        this.validationGetCheck(noticeMgtDto);

        NoticeDto noticeInfo = noticeMgtMapper.getNoticeInfo(noticeMgtDto);
        return noticeInfo;
    }

    /**
     * 공지사항 첨부파일 리스트
     * @param noticeMgtDto
     * @return
     */
    public List<NoticeAttachDto> getNoticeAttachList(NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {

        // 입력값 체크
        this.validationGetCheck(noticeMgtDto);

        List<NoticeAttachDto> noticeAttachList = noticeMgtMapper.getNoticeAttachList(noticeMgtDto);
        return noticeAttachList;
    }

    /**
     * 공지사항 댓글 리스트
     * @param noticeMgtDto
     * @return
     * @throws Exception
     */
    public List<NoticeDtlDto> getNoticeDtlList(NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {

        // 입력값 체크
        this.validationGetCheck(noticeMgtDto);

        List<NoticeDtlDto> noticeDtlList = noticeMgtMapper.getNoticeDtlList(noticeMgtDto);
        return noticeDtlList;
    }

    /**
     * 공지사항 댓글 저장
     * @param noticeDtlDto
     * @return
     * @throws Exception
     */
    public int setNoticeDtlInfo(NoticeDtlDto noticeDtlDto, HttpSession session) throws Exception {

        // 입력값 체크
        this.isBlankCheck(noticeDtlDto.getSys_id(), "시스템 id가");
        this.isBlankCheck(noticeDtlDto.getNoti_seq(), "공지사항 key");
        this.isBlankCheck(noticeDtlDto.getContents(), "댓글 내용이");

        String userId = session.getAttribute("userId").toString();

        noticeDtlDto.setNoti_rep_id(userId);
        noticeDtlDto.setNoti_rep_ref_seq("1");
        noticeDtlDto.setRgstr_id(userId);
        noticeDtlDto.setMdfr_id(userId);

        int result = noticeMgtMapper.setNoticeDtlInfo(noticeDtlDto);

        if(result == 0) {
            throw new CommDuplicateException("저장에 실패하였습니다.", ErrorCode.INTER_SERVER_ERROR);
        }

        return result;
    }

    /**
     * 공지사항 저장(등록, 수정)
     * @param noticeInfo
     * @param fileList
     * @param delAttachList
     * @param session
     * @throws Exception
     */
    public void setNoticeInfo(NoticeDto noticeInfo, List<MultipartFile> fileList, List<NoticeAttachDto> delAttachList, HttpSession session) throws Exception {
        // 공지사항 등록
        if(StringUtils.isBlank(noticeInfo.getNoti_seq())) {
            this.setNewNoticeInfo(noticeInfo, session);

        // 공지사항 수정
        } else {
            this.setModiNoticeInfo(noticeInfo, session);

            // 삭제된 파일 존재시 => status = 'N' update
            if(delAttachList != null && delAttachList.size() > 0) {
                this.setDelNoticeAttach(delAttachList, session);
            }
        }

        // 공지사항 파일 저장
        this.ftpFileUpload(noticeInfo, fileList);
    }

    /**
     * 공지사항 파일 저장
     * @param noticeInfo
     * @param fileList
     */
    private void ftpFileUpload(NoticeDto noticeInfo, List<MultipartFile> fileList) {
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

                    // 공지사항 파일정보
                    NoticeAttachDto noticeAttachDto = new NoticeAttachDto();
                    noticeAttachDto.setSys_id(noticeInfo.getSys_id());
                    noticeAttachDto.setNoti_seq(noticeInfo.getNoti_seq());
                    noticeAttachDto.setAtch_nm(f.getOriginalFilename());
                    noticeAttachDto.setAtch_size(fileSize+ "");
                    noticeAttachDto.setOrg_file_nm(f.getOriginalFilename());
                    noticeAttachDto.setUrl(ftpUploadReturn.getDirectory() + "/");
                    noticeAttachDto.setStatus("Y");
                    noticeAttachDto.setRgstr_id(noticeInfo.getMdfr_id());
                    noticeAttachDto.setSer_file_nm(ftpUploadReturn.getFilename());
                    noticeAttachDto.setMdfr_id(noticeInfo.getMdfr_id());
                    noticeAttachDto.setAtch_ref_id("1");

                    // 공지사항 파일 정보 저장
                    this.setNoticeAttachInfo(noticeAttachDto);
                }
            }
        } catch (Exception e) {
            throw new CommDuplicateException("",ErrorCode.UPLOAD_FAIL);
        }
    }

    /**
     * 공지사항 등록 저장
     * @param noticeInfo
     * @return
     * @throws Exception
     */
    public int setNewNoticeInfo(NoticeDto noticeInfo, HttpSession session) throws Exception {

        // 입력값 체크
        this.validationSetCheck(noticeInfo);

        String sysId = session.getAttribute("sysCode").toString();
        String userId = session.getAttribute("userId").toString();

        // 공지사항 저장 데이터 세팅
        noticeInfo.setSys_id(sysId);
        noticeInfo.setAssign_id(userId);
        noticeInfo.setRgstr_id(userId);
        noticeInfo.setMdfr_id(userId);
        noticeInfo.setType_cd(StringUtils.isBlank(noticeInfo.getType_cd()) ? "02" : noticeInfo.getType_cd());

        int result = noticeMgtMapper.setNewNoticeInfo(noticeInfo);
        return result;
    }

    /**
     * 공지사항 수정
     * @param noticeInfo
     * @return
     * @throws Exception
     */
    public int setModiNoticeInfo(NoticeDto noticeInfo, HttpSession session) throws Exception {

        // 입력값 체크
        this.validationSetCheck(noticeInfo);
        this.isBlankCheck(noticeInfo.getSys_id(), "시스템 id가");

        String userId = session.getAttribute("userId").toString();

        // 공지사항 저장 데이터 세팅
        noticeInfo.setMdfr_id(userId);
        noticeInfo.setType_cd(StringUtils.isBlank(noticeInfo.getType_cd()) ? "02" : noticeInfo.getType_cd());

        int result = noticeMgtMapper.setModiNoticeInfo(noticeInfo);
        return result;
    }

    /**
     * 공지사항 파일 정보 저장
     * @param noticeAttachDto
     * @throws Exception
     */
    private int setNoticeAttachInfo(NoticeAttachDto noticeAttachDto) throws Exception {
        return noticeMgtMapper.setNoticeAttachInfo(noticeAttachDto);
    }

    /**
     * 공지사항 파일 삭제(status = 'N')
     * @param delAttachList
     * @return
     * @throws Exception
     */
    public int setDelNoticeAttach(List<NoticeAttachDto> delAttachList, HttpSession session) throws Exception {
        int result = 0;

        String userId = session.getAttribute("userId").toString();

        if(delAttachList != null && delAttachList.size() > 0) {
            for(NoticeAttachDto dto : delAttachList) {
                // 입력값 체크
                this.isBlankCheck(dto.getSys_id(), "시스템 id가");
                this.isBlankCheck(dto.getNoti_seq(), "공지사항 key가");
                this.isBlankCheck(dto.getAtch_seq(), "첨부파일 key가");
    
                // 첨부파일 상태변경(status = 'N')
                dto.setMdfr_id(userId);
                result += noticeMgtMapper.setDelNoticeAttach(dto);
            }
        }
        
        return result;
    }

    /**
     * 공지사항 삭제(status = 'N')
     * @param delNoticeList
     * @param userId
     * @return
     * @throws Exception
     */
    public int setDelNoticeList(List<NoticeDto> noticeList, HttpSession session) throws Exception {
        int result = 0;

        String userId = session.getAttribute("userId").toString();

        if(noticeList != null && noticeList.size() > 0) {
            for(NoticeDto dto : noticeList) {
                // 입력값 체크
                this.isBlankCheck(dto.getSys_id(), "시스템 id가");
                this.isBlankCheck(dto.getNoti_seq(), "공지사항 key가");
    
                // 공지사항 상태변경(status = 'N')
                dto.setMdfr_id(userId);
                result += noticeMgtMapper.setDelNoticeList(dto);
    
                // 첨부파일 상태변경(status = 'N')
                NoticeAttachDto noticeAttachDto = new NoticeAttachDto();
                noticeAttachDto.setNoti_seq(dto.getNoti_seq());
                noticeAttachDto.setMdfr_id(userId);
                noticeMgtMapper.setDelNoticeAttach(noticeAttachDto);
            }
        }
        
        return result;
    }

    /**
     * 공지사항 게시종료
     * @param noticeList
     * @param userId
     * @return
     * @throws Exception
     */
    public int setCmpltNoticeList(List<NoticeDto> noticeList, HttpSession session) throws Exception {
        int result = 0;

        String userId = session.getAttribute("userId").toString();

        if(noticeList != null && noticeList.size() > 0) {
            for(NoticeDto dto : noticeList) {
                // 입력값 체크
                this.isBlankCheck(dto.getSys_id(), "시스템 id가");
                this.isBlankCheck(dto.getNoti_seq(), "공지사항 key가");
    
                // 공지사항 게시종료
                dto.setMdfr_id(userId);
                result += noticeMgtMapper.setCmpltNoticeList(dto);
            }
        }
        
        return result;
    }


    /**
     * 공지사항 history set
     * @param noticeReadDto
     * @param userId
     * @return
     * @throws Exception
     */
    public int setNoticeRead(NoticeReadDto noticeReadDto, HttpSession session) throws Exception {
        
        // 입력값 체크
        this.isBlankCheck(noticeReadDto.getSys_id(), "시스템 아이디가");
        this.isBlankCheck(noticeReadDto.getNoti_seq(), "공지사항 key");

        String userId = session.getAttribute("userId").toString();

        // 공지사항 저장 데이터 세팅
        noticeReadDto.setRead_id(userId);
        noticeReadDto.setRgstr_id(userId);
        noticeReadDto.setMdfr_id(userId);

        int result = noticeMgtMapper.setNoticeRead(noticeReadDto);
        return result;
    }


    /**
     * 공지사항 게시글 조회 현황
     * @param noticeReadDto
     * @return
     * @throws Exception
     */
    public List<NoticeReadDto> getNoticeReadList(NoticeReadDto noticeReadDto, HttpSession session) throws Exception {
        // 입력값 체크
        this.isBlankCheck(noticeReadDto.getSys_id(), "시스템 아이디가");
        this.isBlankCheck(noticeReadDto.getNoti_seq(), "공지사항 key가");

        List<NoticeReadDto> noticeReadList = noticeMgtMapper.getNoticeReadList(noticeReadDto);
        return noticeReadList;
    }

    /**
     * 공지사항 파일정보 가져오기.
     * @param filename
     * @param viewid
     * @return
     * @throws Exception
     */
    public NoticeAttachDto getAttachDownloadInfo(String filename, String viewid) throws Exception {

        // 입력값 체크
        this.isBlankCheck(filename, "서버파일명이");

        // 공지사항 파일정보 가져오기.
        NoticeAttachDto noticeAttachDto = new NoticeAttachDto();
        noticeAttachDto.setSer_file_nm(filename);
        noticeAttachDto = noticeMgtMapper.getAttachDownloadInfo(noticeAttachDto);

        return noticeAttachDto;
    }


    // get 필수값 체크
    private void validationGetCheck(NoticeMgtDto noticeMgtDto) {
        this.isBlankCheck(noticeMgtDto.getSys_id(), "시스템 id가");
        this.isBlankCheck(noticeMgtDto.getNoti_seq(), "공지사항 key가");
    }

    // set 필수값 체크
    private void validationSetCheck(NoticeDto noticeDto) {
        this.isBlankCheck(noticeDto.getNoti_title(), "제목이");
        this.isBlankCheck(noticeDto.getNoti_contents(), "내용이");
        this.isBlankCheck(noticeDto.getComplete_date(), "공지 종료일자가");
    }

    // null check
    private void isBlankCheck(String value, String str) {
        if(StringUtils.isBlank(value)) {
            throw new CommDuplicateException(str + " 존재하지 않습니다.", ErrorCode.REQUIRED_VALUE_ERROR);
        }
    }

}
