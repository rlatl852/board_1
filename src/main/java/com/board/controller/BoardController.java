package com.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.BoardDTO;
import com.board.domain.Page;
import com.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/Board")
@Log4j
public class BoardController {

	@Resource(name = "BoardService")
	private BoardService boardService;

	// 게시물 목록
	@GetMapping("/list")
//	public String list(@ModelAttribute VueBoard vueBoard, Model model) throws Exception {
	public String boardList(Model model, Page page) throws Exception {
		ObjectMapper om = new ObjectMapper();
		model.addAttribute("readAll", om.writeValueAsString(boardService.readAll(page)));
		model.addAttribute("page", om.writeValueAsString(new Page(boardService.totalCount(page), page)));

		return "board/boardList";
	}

	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> boardList(@RequestBody Page page) throws Exception {
		List<BoardDTO> readAll = boardService.readAll(page);
		Page pages = new Page(boardService.totalCount(page), page);

		Map<String, Object> map = new HashMap<>();

		map.put("readAll", readAll);
		map.put("page", pages);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	// 게시물 조회 페이지로 이동
	@GetMapping("/content")
	public String getBoard(int bId, Model model, HttpServletRequest request, HttpServletResponse response) {
		Cookie oldCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("postView")) {
					oldCookie = cookie;
				}
			}
		}

		if (oldCookie != null) {
			if (!oldCookie.getValue().contains("[" + bId + "]")) {
				this.boardService.readCount(bId);
				oldCookie.setValue(oldCookie.getValue() + "_[" + bId + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60 * 60 * 24); // 쿠키 시간
				response.addCookie(oldCookie);
			}
		} else {
			this.boardService.readCount(bId);
			Cookie newCookie = new Cookie("postView", "[" + bId + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24); // 쿠키 시간
			response.addCookie(newCookie);
		}

		return "board/content";
	}
	
	// 게시물 조회
	@PostMapping("/content")
	public ResponseEntity<BoardDTO> getContent(@RequestBody BoardDTO boardDTO) {
		BoardDTO board = boardService.read(boardDTO.getbId());
		return new ResponseEntity<BoardDTO>(board, HttpStatus.OK);
	}

	// 좋아요 카운트
	@PostMapping("regLike")
	public ResponseEntity<Map<String, Object>> like(@RequestBody Map<String,String> reqMap, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<>();
		if(reqMap.get("likeCheck").equals("like")) {
			String likeResult = boardService.regLike(Integer.parseInt(reqMap.get("bId")), request);
			resMap.put("likeResult", likeResult);
		} else if(reqMap.get("likeCheck").equals("hate")) {
			String likeResult = boardService.delLike(Integer.parseInt(reqMap.get("bId")), request);
			resMap.put("likeResult", likeResult);
		}
		BoardDTO board = boardService.read(Integer.parseInt(reqMap.get("bId")));
		resMap.put("board", board);
		return new ResponseEntity<Map<String, Object>>(resMap, HttpStatus.OK);
	}

	// 글쓰기 폼
	@GetMapping("/register")
	public String getRegisterView() {
		return "board/register";
	}

	// 글쓰기 등록 처리
	@PostMapping("/register")
	public String register(MultipartFile[] multipartFiles, BoardDTO boardDTO) {
		// MultipartFile[]로 첨부파일들을 받아옴
		
		String uploadFolder = "C:\\Users\\User\\Desktop\\uploadFile";
		// 첨부파일이 저장될 경로
		
		File uploadPath = new File(uploadFolder, getFolder());
//		부모 파일의 경로(uploadFolder)와, 그 하위의 파일명(getFolder())을 각각 매개변수로 지정하여 해당 경로를 조합하여 그 위치에 대한 File 객체를 생성합니다.
//		C:\\Users\\User\\Desktop\\uploadFile\\2023\\03\\10 - 날짜별 폴더 추가(오늘날짜)
		
		if(uploadPath.exists() == false) {
//			uploadFolder 경로에 uploadPath 폴더가 존재하는지 체크
			uploadPath.mkdirs();
//			uploadPath 폴더가 존재하지 않는다면 디렉토리 생성
		}
		
//		
		for(MultipartFile multipartFile : multipartFiles) {	// 배열 하나하나의 첨부파일(향상된 for문)
			
			String uploadFileName = multipartFile.getOriginalFilename();
//			파일에서 확장자를 포함한 파일의 이름을 반환
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
//			파일명만 추출
			
			UUID uuid = UUID.randomUUID();
//			유일한 식별자를 생성
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
//			유일한 식별자를 문자열로 바꾸고 '_'와 추출된 파일명을 더하기
			
			
//			File saveFile = new File(uploadFolder, uploadFileName);
			File saveFile = new File(uploadPath, uploadFileName);
//			부모 파일의 경로(uploadPath)와, 그 하위의 파일명(uploadFileName)을 각각 매개변수로 지정하여 해당 경로를 조합하여 그 위치에 대한 File 객체를 생성
//			saveFile = 'C:\\Users\\User\\Desktop\\uploadFile\\2023\\03\\10\\uuid + '_' + data.txt'
			try {
				multipartFile.transferTo(saveFile);
//				받은 파일(multipart)을 지정된 대상 파일(savefile)로 전송
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		boardService.register(boardDTO);
		return "redirect:list";
	}

	// 수정 폼
	@GetMapping("/modify")
	public String getModifyForm(int bId, Model model) {
		BoardDTO board = boardService.read(bId);
		model.addAttribute("board", board);
		return "board/modify";
	}

	// 수정 처리
	@PostMapping("/modify")
	public String modify(BoardDTO boardDTO, int pageNum) {
		boardService.modify(boardDTO);
		return "redirect:content?bId=" + boardDTO.getbId() + "&pageNum=" + pageNum;
	}

	// 삭제 처리
	@PostMapping("/remove")
	public String remove(int bId) {
		boardService.remove(bId);
		return "redirect:list";
	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
}