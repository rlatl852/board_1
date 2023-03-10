package com.board.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.board.dao.BoardDAO;
import com.board.domain.BoardDTO;
import com.board.domain.Page;

//@Service(value="BoardService")
@Service("BoardService")
public class BoardServiceImpl implements BoardService {
	
	//@Autowired
	@Resource(name="BoardDAO")
	private BoardDAO boardDAO;

	@Override
	public List<BoardDTO> readAll(Page page) {
		return boardDAO.getList(page);
	}

	@Override
	public int totalCount(Page page) {
		return boardDAO.totalCount(page);
	}

	@Override
	public BoardDTO read(int bId) {
		
		return boardDAO.getBoard(bId);
	}

	@Override
	public void register(BoardDTO boardDTO) {
		boardDAO.insert(boardDTO);
	}

	@Override
	public void readCount(int bId) {
		boardDAO.readCount(bId);
	}

	@Override
	public void modify(BoardDTO boardDTO) {
		boardDAO.update(boardDTO);
	}
	
	@Override
	public void remove(int bId) {
		boardDAO.delete(bId);
	}

	@Override
	public String regLike(int bId, HttpServletRequest request) {
		String requestIp = request.getRemoteAddr();
		String checkIp = boardDAO.getLike(bId, requestIp);
		if(requestIp.equals(checkIp)) {
			return "이미 추천된 IP입니다.";
		} else {
			boardDAO.insertLike(bId, requestIp);
			return "추천 완료"; 
		}
	}
	
	@Override
	public String delLike(int bId, HttpServletRequest request) {
		String requestIp = request.getRemoteAddr();
		String checkIp = boardDAO.getLike(bId, requestIp);
		if(requestIp.equals(checkIp)) {
			boardDAO.delLike(bId, requestIp);
			return "추천 취소 완료";
		} else {
			return "추천 후 취소 가능"; 
		}
	}
}