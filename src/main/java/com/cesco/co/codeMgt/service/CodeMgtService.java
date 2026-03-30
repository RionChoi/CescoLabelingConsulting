package com.cesco.co.codeMgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.co.codeMgt.dto.CodeCommCodeDTO;
import com.cesco.co.codeMgt.dto.CodeDtlCodeDTO;
import com.cesco.co.codeMgt.mapper.CodeMgtMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author parkminho
 * @apiNote cusMgt
 */

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CodeMgtService {

    private final CodeMgtMapper codeMgtMapper;

    // 그룹 코드 목록
    public List<CodeCommCodeDTO>  getCodeCommList(CodeCommCodeDTO codeComm) throws Exception{
        return codeMgtMapper.getCodeCommList(codeComm);
    }
    
    // 세부 코드 목록
    public List<CodeDtlCodeDTO> getCodeDtlList(String cmm_cd) throws Exception{
        return codeMgtMapper.getCodeDtlList(cmm_cd);
    }

    // 코드 유형 중복 카운터
    public Integer getCodeCommCnt(String cmm_cd) throws Exception{
        return codeMgtMapper.getCodeCommCnt(cmm_cd);
    }

    // 코드 유형 등록
    public Integer setCodeCommIns(CodeCommCodeDTO codeComm) throws Exception{
        return codeMgtMapper.setCodeCommIns(codeComm);
    }

    // 코드 유형 수정
    public Integer setCodeCommUpt(CodeCommCodeDTO codeComm) throws Exception{
        return codeMgtMapper.setCodeCommUpt(codeComm);
    }

    // 세부 코드 등록
    public Integer setCodeDtlIns(CodeDtlCodeDTO codeDtl) throws Exception{
        return codeMgtMapper.setCodeDtlIns(codeDtl);
    }

    // 세부 코드 수정
    public Integer setCodeDtlUpt(CodeDtlCodeDTO codeDtl) throws Exception{
        return codeMgtMapper.setCodeDtlUpt(codeDtl);
    }

}
