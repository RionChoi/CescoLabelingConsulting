package com.cesco.co.cusMgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.co.cusMgt.dto.CusBookDTO;
import com.cesco.co.cusMgt.dto.CusCertificationInfoDTO;
import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.co.cusMgt.dto.CusSearchDTO;
import com.cesco.co.cusMgt.mapper.CusMgtMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author parkminho
 * @apiNote cusMgt
 */

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CusMgtService {

    private final CusMgtMapper cusMgtMapper;

    // 고객사 관리 목록
    public List<CusInfoDTO> getCusList(CusSearchDTO cusSearchDTO, String userId) throws Exception{

        cusSearchDTO.setUserId(userId);

        return cusMgtMapper.getCusList(cusSearchDTO);
        
    }

    // 고객사 즐겨 찾기 등록
    public Integer setCusBookIns(CusBookDTO cusBookDTO) throws Exception{

        return cusMgtMapper.setCusBookIns(cusBookDTO);

    }

    // 고객사 즐겨 찾기 해제
    public Integer setCusBookDel(CusBookDTO cusBookDTO) throws Exception{

        return cusMgtMapper.setCusBookDel(cusBookDTO);

    }

    // 고객사 즐겨 찾기 해제(BOOK_SEQ)
    public Integer setCusBookDelSeq(Integer book_seq, String userId) throws Exception{
        return cusMgtMapper.setCusBookDelSeq(book_seq, userId);
    }

    // 고객사 코드 중복 확인
    public Integer getCusInfoCnt(CusInfoDTO cusInfoDTO) throws Exception{
        return cusMgtMapper.getCusInfoCnt(cusInfoDTO);
    }

    // 고객사 등록
    public Integer setCusInfoIns(CusInfoDTO cusInfoDTO, String userId) throws Exception{

        cusInfoDTO.setRgstr_id(userId);
        cusInfoDTO.setMdfr_id(userId);
        cusInfoDTO.setCst_mng_biz(cusInfoDTO.getCst_mng_biz().replaceAll("-", ""));
        cusInfoDTO.setCst_biz_no(cusInfoDTO.getCst_biz_no().replaceAll("-", ""));

        return cusMgtMapper.setCusInfoIns(cusInfoDTO);
    }

    // 고객사 수정
    public Integer setCusInfoUpt(CusInfoDTO cusInfoDTO, String userId) throws Exception{

        cusInfoDTO.setMdfr_id(userId);
        cusInfoDTO.setCst_mng_biz(cusInfoDTO.getCst_mng_biz().replaceAll("-", ""));
        cusInfoDTO.setCst_biz_no(cusInfoDTO.getCst_biz_no().replaceAll("-", ""));

        return cusMgtMapper.setCusInfoUpt(cusInfoDTO);
    }

    // 고객사 인증 정보 가져오기
    public CusCertificationInfoDTO getCusCertificationInfo(String cust_cd) throws Exception{

        return  cusMgtMapper.getCusCertificationInfo(cust_cd);

    }
  
}
