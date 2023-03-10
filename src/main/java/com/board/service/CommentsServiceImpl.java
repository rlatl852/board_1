package com.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.board.dao.CommentsDAO;
import com.board.domain.CommentDTO;
import com.board.domain.Page;

//@Service("CommentService")
@Service(value="CommentService")
public class CommentsServiceImpl implements CommentsService {
	
	//@Autowired
	@Resource(name = "CommentDAO")
	private CommentsDAO commentsDAO;

	@Override
	public List<CommentDTO> getCommentsList(int bbs_id, Page page) {
		return commentsDAO.commentsList(bbs_id, page);
	}
	
	@Override
	public int totalCount(int bbs_id, Page page) {
		return commentsDAO.totalCount(bbs_id, page);
	}

	@Override
	public void createComment(CommentDTO commentDTO) {
		commentsDAO.insert(commentDTO);	
	}

	@Override
	public void remove(int comment_id) {
		commentsDAO.delete(comment_id);
	}

	@Override
	public CommentDTO getComment(int comment_id) {
		return commentsDAO.getComment(comment_id);
	}

	@Override
	public void modify(CommentDTO commentDTO) {
		commentsDAO.update(commentDTO);
	}

}
