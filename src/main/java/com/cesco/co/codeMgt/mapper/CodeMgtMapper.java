package com.cesco.co.codeMgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.co.codeMgt.dto.CodeCommCodeDTO;
import com.cesco.co.codeMgt.dto.CodeDtlCodeDTO;

@Mapper
public interface CodeMgtMapper {
  
    // 그룹 코드 목록
    List<CodeCommCodeDTO>  getCodeCommList(CodeCommCodeDTO codeComm);

    // 세부 코드 목록
    List<CodeDtlCodeDTO> getCodeDtlList(String cmm_cd);

    // 코드 유형 중복 카운터
    Integer getCodeCommCnt(String cmm_cd);

    // 코드 유형 등록
    Integer setCodeCommIns(CodeCommCodeDTO codeComm);

    // 코드 유형 수정
    Integer setCodeCommUpt(CodeCommCodeDTO codeComm);

    // 세부 코드 등록
    Integer setCodeDtlIns(CodeDtlCodeDTO codeDtl);

    // 세부 코드 수정
    Integer setCodeDtlUpt(CodeDtlCodeDTO codeDtl);

}
