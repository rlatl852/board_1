package com.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.board.domain.CommentDTO;
import com.board.domain.Page;

//@Repository("CommentDAO")
@Repository(value="CommentDAO")
public class CommentsDAOImpl implements CommentsDAO {

	@Autowired
	private SqlSession sqlsession;
	
	private String namespace = "CommentDAO.";

	@Override
	public List<CommentDTO> commentsList(int bbs_id, Page page) {
		Map<String, Object> map = new HashMap<>();
		map.put("bbs_id", bbs_id);
		map.put("page", page);
		return sqlsession.selectList(namespace + "commentsList", map);
	}
	
	@Override
	public int totalCount(int bbs_id, Page page) {
		Map<String, Object> map = new HashMap<>();
		map.put("bbs_id", bbs_id);
		map.put("page", page);
		return sqlsession.selectOne(namespace + "totalCount", map);
	}

	@Override
	public void insert(CommentDTO commentDTO) {
		sqlsession.selectOne(namespace + "insert", commentDTO);
	}

	@Override
	public void delete(int comment_id) {
		sqlsession.selectOne(namespace + "delete", comment_id);
	}

	@Override
	public CommentDTO getComment(int comment_id) {
		return sqlsession.selectOne(namespace + "getComment", comment_id);
	}

	@Override
	public void update(CommentDTO commentDTO) {
		sqlsession.selectOne(namespace + "update", commentDTO);
	}

}
