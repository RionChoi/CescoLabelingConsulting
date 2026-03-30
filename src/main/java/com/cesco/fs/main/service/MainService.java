package com.cesco.fs.main.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cesco.fs.main.dto.MainConsultingDto;
import com.cesco.fs.main.dto.MainDto;
import com.cesco.fs.main.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainMapper mainMapper;

    /**
     * main 화면 목록
     * @param mainDto
     * @param session
     * @return
     */
    public List<MainConsultingDto> getTobeComplTdConsultList(MainDto mainDto, HttpSession session) {
        mainDto.setSys_id(session.getAttribute("sysCode").toString());
        mainDto.setUser_id(session.getAttribute("userId").toString());
        List<MainConsultingDto> mainConsultingList = mainMapper.getTobeComplTdConsultList(mainDto);
        return mainConsultingList;
    }

}