package com.cesco.fs.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cesco.fs.main.dto.MainConsultingDto;
import com.cesco.fs.main.dto.MainDto;

@Mapper
public interface MainMapper {

    public List<MainConsultingDto> getTobeComplTdConsultList(MainDto mainDto);
}

