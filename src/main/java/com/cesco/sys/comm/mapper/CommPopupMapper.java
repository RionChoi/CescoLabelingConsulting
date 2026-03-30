package com.cesco.sys.comm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.sys.comm.dto.CommPopReqDTO;
import com.cesco.sys.comm.dto.UserDTO;

@Mapper
public interface CommPopupMapper {
    
    public List<CusInfoDTO> getCustomerListForPop(CommPopReqDTO commPopReqDTO);
    
    public List<UserDTO> getUserListForPop(CommPopReqDTO commPopReqDTO);
}
