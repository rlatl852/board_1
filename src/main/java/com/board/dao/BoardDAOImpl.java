package com.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.board.domain.BoardDTO;
import com.board.domain.LikeVO;
import com.board.domain.Page;

//@Repository("BoardDAO")
@Repository(value="BoardDAO")
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession sqlsession;
	
	private String namespace = "com.board.mapper.BoardMapper.";
	
	@Override
	public List<BoardDTO> getList(Page page) {
		return sqlsession.selectList(namespace + "getList", page);
	}

	@Override
	public int totalCount(Page page) {
		return sqlsession.selectOne(namespace + "totalCount", page);
	}

	@Override
	public BoardDTO getBoard(int bId) {
		return sqlsession.selectOne(namespace + "getBoard", bId);
	}

	@Override
	public void insert(BoardDTO boardDTO) {
		sqlsession.selectOne(namespace + "insert", boardDTO);
	}

	@Override
	public void readCount(int bId) {
		sqlsession.selectOne(namespace + "readCount", bId);
	}

	@Override
	public void update(BoardDTO boardDTO) {
		sqlsession.selectOne(namespace + "update", boardDTO);
	}

	@Override
	public void delete(int bId) {
		sqlsession.selectOne(namespace + "delete", bId);
	}

	@Override
	public void insertLike(int bId, String ip) {
		Map<String, Object> map = new HashMap<>();
		map.put("bbs_id", bId);
		map.put("ip", ip);
		sqlsession.insert(namespace + "regLike", map);
	}
	
	@Override
	public void delLike(int bId, String ip) {
		Map<String, Object> map = new HashMap<>();
		map.put("bbs_id", bId);
		map.put("ip", ip);
		sqlsession.delete(namespace + "delLike", map);
	}

	@Override
	public String getLike(int bId, String ip) {
		Map<String, Object> map = new HashMap<>();
		map.put("bbs_id", bId);
		map.put("ip", ip);
		return sqlsession.selectOne(namespace + "getLike", map);
	}
}
