package com.board.service;

import java.util.List;

import com.board.domain.CommentDTO;
import com.board.domain.Page;

public interface CommentsService {

	List<CommentDTO> getCommentsList(int bbs_id, Page page);
	
	int totalCount(int bbs_id, Page page);
	
	void createComment(CommentDTO commentDTO);
	
	void remove(int comment_id);
	
	CommentDTO getComment(int comment_id);
	
	void modify(CommentDTO commentDTO);
}
