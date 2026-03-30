package com.cesco.sys.comm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.sys.comm.dto.CommPopReqDTO;
import com.cesco.sys.comm.dto.UserDTO;
import com.cesco.sys.comm.mapper.CommPopupMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommPopupService {
    
    private final CommPopupMapper commPopupMapper;

    /**
     * 공통팝업 : 고객사 검색
     * @param cusInfoDTO
     * @return
     */
    public List<CusInfoDTO> getCustomerListForPop(CommPopReqDTO commPopReqDTO) {
        List<CusInfoDTO> cutomerList = commPopupMapper.getCustomerListForPop(commPopReqDTO);
        return cutomerList;
    }
    
    /**
     * 공통팝업 : 담당자 검색
     * @param userDTO
     * @return
     */
    public List<UserDTO> getUserListForPop(CommPopReqDTO commPopReqDTO) {
        List<UserDTO> userList = commPopupMapper.getUserListForPop(commPopReqDTO);
        return userList;
    }
}
