package com.example.demo.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.FileDetailVO;

@Mapper
public interface FileDetailMapper {
	
	Optional<FileDetailVO> selectFileByFileDetailId(FileDetailVO fileDetailVO);

	void insertDetailFile(FileDetailVO vo);
	
	Optional<FileDetailVO> detailFileExist(Long fileDetailId);
	
	void deleteFileByFileDetailId(Long fileDetailId);
	
	List<FileDetailVO> selectFileByMstId(Long fileMstId); 
	
}
