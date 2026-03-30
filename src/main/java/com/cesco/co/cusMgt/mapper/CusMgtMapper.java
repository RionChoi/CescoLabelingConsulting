package com.cesco.co.cusMgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cesco.co.cusMgt.dto.CusBookDTO;
import com.cesco.co.cusMgt.dto.CusCertificationInfoDTO;
import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.co.cusMgt.dto.CusSearchDTO;

@Mapper
public interface CusMgtMapper {
    
    // 고객사 관리 목록
    List<CusInfoDTO> getCusList(CusSearchDTO cusSearchDTO);

    // 고객사 즐겨 찾기 등록
    Integer setCusBookIns(CusBookDTO cusBookDTO);

    // 고객사 즐겨 찾기 해제
    Integer setCusBookDel(CusBookDTO cusBookDTO);

    // 고객사 즐겨 찾기 해제(BOOK_SEQ)
    Integer setCusBookDelSeq(@Param("book_seq") Integer book_seq, @Param("userId") String userId);

    // 고객사 코드 중복 확인
    Integer getCusInfoCnt(CusInfoDTO cusInfoDTO);
    
    // 고객사 등록
    Integer setCusInfoIns(CusInfoDTO cusInfoDTO);

    // 고객사 수정
    Integer setCusInfoUpt(CusInfoDTO cusInfoDTO);

    // 고객사 인증 정보 가져오기
    CusCertificationInfoDTO getCusCertificationInfo(@Param("cust_cd") String cust_cd);
}
