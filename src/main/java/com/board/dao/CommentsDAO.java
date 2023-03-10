package com.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.board.domain.CommentDTO;
import com.board.domain.Page;

public interface CommentsDAO {

	List<CommentDTO> commentsList(@Param("bbs_id") int bbs_id, Page page);
	
	int totalCount(@Param("bbs_id") int bbs_id, Page page);
	
	void insert(CommentDTO commentDTO);
	
	void delete(int comment_id);
	
	CommentDTO getComment(int comment_id);
	
	void update(CommentDTO commentDTO);
}
