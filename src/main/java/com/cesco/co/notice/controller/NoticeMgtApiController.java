package com.cesco.co.notice.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cesco.co.notice.dto.NoticeAttachDto;
import com.cesco.co.notice.dto.NoticeDtlDto;
import com.cesco.co.notice.dto.NoticeDto;
import com.cesco.co.notice.dto.NoticeMgtDto;
import com.cesco.co.notice.dto.NoticeReadDto;
import com.cesco.co.notice.service.NoticeMgtService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/api/notice")
public class NoticeMgtApiController {

    @Autowired
    NoticeMgtService noticeMgtService;

    /**
     * 공지사항 목록
     * @param noticeMgtDto
     * @return
     */
    @GetMapping(value="/getNoticeList")
    @ResponseBody
    public List<NoticeDto> getNoticeList(NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {
        try {
            // 공지사항 목록 가져오기
            List<NoticeDto> noticeList = noticeMgtService.getNoticeList(noticeMgtDto, session);
            return noticeList;

        } catch(CommDuplicateException e) {
            throw new CommDuplicateException(e);

        } catch(Exception ex) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }
    }

    /**
     * 공지사항 상세정보
     * @param noticeMgtDto
     * @return
     */
    @GetMapping(value="/getNoticeInfo")
    @ResponseBody
    public NoticeMgtDto getNoticeInfo(NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {

        try {
            // 공지사항 상세정보 가져오기
            NoticeDto noticeDto = noticeMgtService.getNoticeInfo(noticeMgtDto, session);
            noticeMgtDto.setNoticeDto(noticeDto);
    
            // 공지사항 첨부파일 리스트 가져오기
            List<NoticeAttachDto> noticeAttachList = noticeMgtService.getNoticeAttachList(noticeMgtDto, session);
            noticeMgtDto.setNoticeAttachList(noticeAttachList);

            // 공지사항 댓글 리스트 가져오기
            List<NoticeDtlDto> noticeDtlList = noticeMgtService.getNoticeDtlList(noticeMgtDto, session);
            noticeMgtDto.setNoticeDtlList(noticeDtlList);

        } catch(CommDuplicateException e) {
            throw new CommDuplicateException(e);

        } catch(Exception ex) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }

        return noticeMgtDto;
    }

    /**
     * 공지사항 댓글 리스트 
     * @param noticeMgtDto
     * @return
     */
    @GetMapping(value="/getNoticeDtlList")
    @ResponseBody
    public List<NoticeDtlDto> getNoticeDtlList(NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {

        try {
            // 공지사항 댓글 리스트 가져오기
            List<NoticeDtlDto> noticeDtlList = noticeMgtService.getNoticeDtlList(noticeMgtDto, session);
            return noticeDtlList;

        } catch(CommDuplicateException e) {
            throw new CommDuplicateException(e);

        } catch(Exception ex) {
            throw new CommDuplicateException("운영자에게 문의하세요.", ErrorCode.INTER_SERVER_ERROR);
        }

    }

    /**
     * 공지사항 댓글저장
     * @param noticeMgtDto
     * @returnPostMapping
     */
    @PostMapping(value="/setNoticeDtlInfo")
    @ResponseBody
    public ResponseEntity<String> setNoticeDtlInfo(@RequestBody NoticeDtlDto noticeDtlDto, HttpSession session) throws Exception {

        // 공지사항 댓글 저장
        int result = noticeMgtService.setNoticeDtlInfo(noticeDtlDto, session);

        if(result == 1) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 공지사항 저장
     * @param noticeInfo
     * @param noticeAttachList
     * @param fileList
     * @param session
     * @return
     * @throws Exception
     */
	@PostMapping("/setNoticeInfo")
	public ResponseEntity<String> setNoticeInfo (
        @RequestPart(value = "noticeInfo", required = false) NoticeDto noticeInfo,
        @RequestPart(value = "files", required = false) List<MultipartFile> fileList,
        @RequestPart(value = "delAttachList", required = false) List<NoticeAttachDto> delAttachList,
        HttpSession session) throws Exception{
            
        noticeMgtService.setNoticeInfo(noticeInfo, fileList, delAttachList, session);
        return new ResponseEntity<>("저장 성공", HttpStatus.OK);
	}

    /**
     * 공지사항 삭제(status = 'N')
     * @param noticeMgtDto
     * @returnPostMapping
     */
    @PostMapping(value="/setDelNoticeList")
    @ResponseBody
    public ResponseEntity<String> setDelNoticeList(@RequestBody NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {

        // 공지사항 삭제(status = 'N')
        List<NoticeDto> noticeList = noticeMgtDto.getNoticeList();
        int result = noticeMgtService.setDelNoticeList(noticeList, session);

        if(result == noticeList.size()) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 공지사항 게시종료
     * @param noticeMgtDto
     * @returnPostMapping
     */
    @PostMapping(value="/setCmpltNoticeList")
    @ResponseBody
    public ResponseEntity<String> setCmpltNoticeList(@RequestBody NoticeMgtDto noticeMgtDto, HttpSession session) throws Exception {

        // 공지사항 게시종료
        List<NoticeDto> noticeList = noticeMgtDto.getNoticeList();
        int result = noticeMgtService.setCmpltNoticeList(noticeList, session);

        if(result == noticeList.size()) {
            return new ResponseEntity<>("변경 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * 공지사항 history set
     * @param noticeMgtDto
     * @returnPostMapping
     */
    @PostMapping(value="/setNoticeRead")
    @ResponseBody
    public ResponseEntity<String> setNoticeRead(@RequestBody NoticeReadDto noticeReadDto, HttpSession session) throws Exception {

        // 공지사항 댓글 저장
        int result = noticeMgtService.setNoticeRead(noticeReadDto, session);

        if(result == 1) {
            return new ResponseEntity<>("저장 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("저장 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 공지사항 게시글 조회 현황
     * @param noticeMgtDto
     * @return
     */
    @GetMapping(value="/getNoticeReadList")
    @ResponseBody
    public List<NoticeReadDto> getNoticeReadList(NoticeReadDto noticeReadDto, HttpSession session) throws Exception {
        try {
            // 공지사항 게시글 조회 현황
            List<NoticeReadDto> noticeReadList = noticeMgtService.getNoticeReadList(noticeReadDto, session);
            return noticeReadList;

        } catch(CommDuplicateException e) {
            throw new CommDuplicateException(e);

        } catch(Exception ex) {
            throw new CommDuplicateException("", ErrorCode.INTER_SERVER_ERROR);
        }
    }

}
