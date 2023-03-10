package com.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {

	private String type;
	private String keyword;

	private int startPage; // 하나의 페이지 에서 시작 페이지
	private int endPage; // 하나의 페이지 에서 마지막 페이지
	private int realEndPage; // 총 페이지 에서 마지막 페이지
	private int totalCount; // 전체 목록의 숫자
	private int displayPageNum = 10; // (페이지 버튼 수)
	private int viewPage; // 현재 보여지는 페이지 숫자

	private int pageNum; // 페이지 숫자
	private int amount; // 페이지 에서 보여지는 목록의 개수

	private boolean prev; // 이전페이지 버튼
	private boolean next; // 다음페이지 버튼

	// 게시물 리스트 정보 설정
	public Page() {
		this.pageNum = 1;
		this.amount = 10;
	}

	// 페이징 정보가 바뀔때 마다 정보 초기화
	public Page(int totalCount, Page page) {

		this.totalCount = totalCount;

		this.viewPage = page.getPageNum();
		
		endPage = (int) Math.ceil(page.getPageNum() / (double) displayPageNum) * displayPageNum; // 10

		startPage = endPage - displayPageNum + 1;

		// 게시물 412개 실제 마지막 페이지는 (올림) 412/10.0 => 41.2 => 42.0 => 42
		realEndPage = (int) Math.ceil(totalCount / (double) page.getAmount());

		// 이전 페이지는 1~10 페이지를 제외하고 모두 출력 : true
		// 1~10페이지는 startPage가 항상1
		prev = startPage != 1; // ? true : false; 삼항연산자
		next = endPage < realEndPage;

		// endPage realEndPage
		// 1~10 : 10 42
		// 11~20 : 20 42
		// ...
		// 41~50 : 50 42
		if (endPage > realEndPage)
			endPage = realEndPage;
	}

	// 페이지 넘버가 바뀔때마다 게시물 리스트 정보가 초기화
	public Page(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	// 게시물 리스트의 시작 위치 설정 
	public int getStart() { // sql문에서 start
		return (this.pageNum - 1) * this.amount;
	}

	public String[] getTypeCollection() { // #{typeCollection }
		// type => T, C, W
		return this.type != null ? type.split("") : new String[] {};
	}
}
