package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.FileMasterVO;

@Mapper
public interface FileMasterMapper {

	void insertFileMaster(FileMasterVO fileMasterVO);
	
}
