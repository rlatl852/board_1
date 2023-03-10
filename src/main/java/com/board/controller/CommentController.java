package com.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.board.domain.CommentDTO;
import com.board.domain.Page;
import com.board.service.CommentsService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/Comments")
@Log4j
public class CommentController {

	@Autowired
	private CommentsService commentService;

	// 댓글 목록
	@PostMapping("/get")
	public ResponseEntity<Map<String, Object>> getCommentList(@RequestBody Map<String, String> map) {
		Page page = new Page();
		page.setPageNum(Integer.parseInt(map.get("pageNum")));
		List<CommentDTO> comments = commentService.getCommentsList(Integer.parseInt(map.get("bbs_id")), page);
		Page pages = new Page(commentService.totalCount(Integer.parseInt(map.get("bbs_id")), page), page);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("comments", comments);
		map1.put("page", pages);
		return new ResponseEntity<Map<String, Object>>(map1, HttpStatus.OK);
	}
	
	// 댓글 작성
	@PostMapping("/new")
	public ResponseEntity<List<CommentDTO>> create(@RequestBody CommentDTO com, Page page) {
		commentService.createComment(com);
		List<CommentDTO> comments = commentService.getCommentsList(com.getBbs_id(), page);
		return new ResponseEntity<List<CommentDTO>>(comments, HttpStatus.OK);
	}

	// 댓글 삭제
	@PostMapping("/remove")
	public ResponseEntity<List<CommentDTO>> remove(@RequestBody CommentDTO com, Page page) {
		commentService.remove(com.getComment_id());
		List<CommentDTO> comments = commentService.getCommentsList(com.getBbs_id(), page);
		return new ResponseEntity<List<CommentDTO>>(comments, HttpStatus.OK);
	}

	// 댓글 페이지 이동
	@GetMapping("/comment")
	public String getComment() {
		return "board/comment";
	}

	// 댓글 수정 폼
	@PostMapping("/comment")
	public ResponseEntity<CommentDTO> getComment(@RequestBody CommentDTO com) {
		CommentDTO comment = commentService.getComment(com.getComment_id());
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
	}

	// 수정 처리
	@PostMapping("/commentModify")
	public String modify(CommentDTO com, int pageNum) {
		commentService.modify(com);
		return "redirect:/Board/content?bId=" + com.getBbs_id() + "&pageNum=" + pageNum;
	}

}
