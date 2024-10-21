package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.BoardMapper;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.service.CrudService;
import com.example.demo.service.FileService;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FileMasterVO;
import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BoardServiceImpl implements CrudService<BoardVO> {
	
	private BoardMapper boardMapper;
	private FileService fileService;

	@Override
	public List<BoardVO> selectList(BoardVO e) {
		return boardMapper.selectList(e);
	}

	@Override
	public BoardVO selectOne(BoardVO e) {
		return null;
	}
	
	public BoardVO selectBoard(Long idx) {
		BoardVO boardVO = boardMapper.selectOne(idx);
		FileMasterVO fileMasterVO = new FileMasterVO();
		fileMasterVO.setFileMstId(boardVO.getFileMstId());
		fileMasterVO.setRegID(boardVO.getRegID());
		fileMasterVO.setRegDate(boardVO.getRegDate());
		fileMasterVO.setFileList(fileService.selectFileByMstId(boardVO.getFileMstId()));
		boardVO.setFileMasterVO(fileMasterVO);
		
		if (boardVO == null) throw new NotFoundException("게시물을 찾을 수 없습니다.");
		
		log.info(boardVO.toString());
		return boardVO;
	}

	@Override
	public void insert(BoardVO e) {
		boardMapper.insertBoard(e);
	}
	
	public ApiResponse insertBoard(BoardVO boardVO, HttpServletRequest request) {
		log.info(boardVO.toString());
		boardMapper.insertBoard(boardVO);
		return new ApiResponse(true, String.valueOf(boardVO.getIdx()));
	}

	@Override
	public void update(BoardVO e) {
		boardMapper.update(e);
	}
	
	public ApiResponse updateBoard(BoardVO e) {
		BoardVO boardVO = boardMapper.selectOne(e.getIdx());
		if (boardVO == null) throw new NotFoundException("게시물을 찾을 수 없습니다.");
		boardMapper.update(e);
		return new ApiResponse(true, "저장되었습니다.");
	}

	@Override
	public void delete(BoardVO e) {
		boardMapper.delete(e.getIdx());
	}
	
	public void delete(Long idx) {
		boardMapper.delete(idx);
	}

}
