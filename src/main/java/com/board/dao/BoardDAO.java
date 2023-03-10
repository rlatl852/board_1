package com.board.dao;

import java.util.List;

import com.board.domain.BoardDTO;
import com.board.domain.LikeVO;
import com.board.domain.Page;

public interface BoardDAO {

	List<BoardDTO> getList(Page page);

	int totalCount(Page page);

	BoardDTO getBoard(int bId);

	void insert(BoardDTO boardDTO);

	void readCount(int bId);

	void update(BoardDTO boardDTO);

	void delete(int bId);
	
	void insertLike(int bId, String ip);
	
	void delLike(int bId, String ip);
	
	String getLike(int bId, String ip);
}
