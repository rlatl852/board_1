package com.board.domain;

import lombok.Data;

public class BoardDTO {

	private int bId;
	private String bTitle;
	private String bContent;
	private String bCreatedAt;
	private String bModifiedAt;
	private int bCount;
	private int like;
	
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getbTitle() {
		return bTitle;
	}
	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}
	public String getbContent() {
		return bContent;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public String getbCreatedAt() {
		return bCreatedAt;
	}
	public void setbCreatedAt(String bCreatedAt) {
		this.bCreatedAt = bCreatedAt;
	}
	public String getbModifiedAt() {
		return bModifiedAt;
	}
	public void setbModifiedAt(String bModifiedAt) {
		this.bModifiedAt = bModifiedAt;
	}
	public int getbCount() {
		return bCount;
	}
	public void setbCount(int bCount) {
		this.bCount = bCount;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
}
