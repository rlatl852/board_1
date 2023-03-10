package com.board.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.board.domain.BoardDTO;
import com.board.domain.Page;

public interface BoardService {

	List<BoardDTO> readAll(Page page);

	int totalCount(Page page);
	
	BoardDTO read(int bId);
	
	void register(BoardDTO boardDTO);
	
	void readCount(int bId);
	
	void modify(BoardDTO boardDTO);
	
	void remove(int bId);
	
	String regLike(int bId, HttpServletRequest request);
	
	String delLike(int bId, HttpServletRequest request);
}