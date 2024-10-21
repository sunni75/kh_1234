package com.example.demo.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.BoardVO;

@Mapper
public interface BoardMapper {
	// CRUD
	void insertBoard(BoardVO boardVO);
	
	List<BoardVO> selectList(BoardVO boardVO);
	
	BoardVO selectOne(Long idx);
	
	void update(BoardVO boardVO);
	
	void delete(Long idx);
	
}
