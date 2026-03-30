package com.cesco.co.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.co.notice.dto.NoticeAttachDto;
import com.cesco.co.notice.dto.NoticeDtlDto;
import com.cesco.co.notice.dto.NoticeDto;
import com.cesco.co.notice.dto.NoticeMgtDto;
import com.cesco.co.notice.dto.NoticeReadDto;

@Mapper
public interface NoticeMgtMapper {

    /**
     * 공지사항 목록
     * @param noticeMgtDto
     * @return
     * @throws Exception
     */
    List<NoticeDto> getNoticeList(NoticeMgtDto noticeMgtDto) throws Exception;

    /**
     * 공지사항 정보
     * @param noticeMgtDto
     * @return
     * @throws Exception
     */
    NoticeDto getNoticeInfo(NoticeMgtDto noticeMgtDto) throws Exception;

    /**
     * 공지사항 첨부파일 리스트
     * @param noticeMgtDto
     * @return
     * @throws Exception
     */
    List<NoticeAttachDto> getNoticeAttachList(NoticeMgtDto noticeMgtDto) throws Exception;

    /**
     * 공지사항 댓글 리스트
     * @param noticeMgtDto
     * @return
     * @throws Exception
     */
    List<NoticeDtlDto> getNoticeDtlList(NoticeMgtDto noticeMgtDto) throws Exception;

    /**
     * 공지사항 댓글 저장
     * @param noticeDtlDto
     * @return
     * @throws Exception
     */
    int setNoticeDtlInfo(NoticeDtlDto noticeDtlDto) throws Exception;

    /**
     * 공지사항 등록 저장
     * @param noticeInfo
     * @return
     * @throws Exception
     */
    int setNewNoticeInfo(NoticeDto noticeInfo) throws Exception;

    /**
     * 공지사항 수정 저장
     * @param noticeInfo
     * @return
     * @throws Exception
     */
    int setModiNoticeInfo(NoticeDto noticeInfo) throws Exception;

    /**
     * 공지사항 파일 저장
     * @param noticeAttachDto
     * @return
     * @throws Exception
     */
    int setNoticeAttachInfo(NoticeAttachDto noticeAttachDto) throws Exception;

    /**
     * 공지사항 파일 삭제(status = 'N')
     * @param dto
     * @return
     * @throws Exception
     */
    int setDelNoticeAttach(NoticeAttachDto noticeAttachDto) throws Exception;

    /**
     * 공지사항 삭제(status = 'N')
     * @param dto
     * @return
     * @throws Exception
     */
    int setDelNoticeList(NoticeDto noticeDto) throws Exception;

    /**
     * 공지사항 게시종료
     * @param noticeDto
     * @return
     * @throws Exception
     */
    int setCmpltNoticeList(NoticeDto noticeDto) throws Exception;

    /**
     * 공지사항 history set
     * @param noticeReadDto
     * @return
     * @throws Exception
     */
    int setNoticeRead(NoticeReadDto noticeReadDto) throws Exception;

    /**
     * 공지사항 게시글 조회 현황
     * @param noticeReadDto
     * @return
     * @throws Exception
     */
    List<NoticeReadDto> getNoticeReadList(NoticeReadDto noticeReadDto) throws Exception;

    /**
     * 공지사항 파일정보 가져오기.
     * @param noticeAttachDto
     * @return
     * @throws Exception
     */
    NoticeAttachDto getAttachDownloadInfo(NoticeAttachDto noticeAttachDto) throws Exception;
  
}
