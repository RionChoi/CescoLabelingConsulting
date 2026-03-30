package com.cesco.sys.comm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommCodeDTO {
    private String sys_cd;
    private String cmm_cd;
    private String cmm_nm;
    private String cmm_type;

    // 고객사, 관리자 procedure param
    private String p_type;
    private String p_parm1;
    private String p_parm2;
    private String p_parm3;
    private String p_parm4;
    private String p_parm5;

    // 공통코드 list
    private List<CommDtlCodeDTO> dtlCodeList;

    // 고객사 combo list
    private List<CommDtlCodeDTO> cstList;

    // 관리자 combo list
    private List<CommDtlCodeDTO> mngList;
    
    // 부서 combo list
    private List<CommDtlCodeDTO> deptList;
}
