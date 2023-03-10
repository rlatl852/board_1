package com.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

	private int comment_id;
	private int bbs_id;
	private String content;
	private String crt_dt;
	private String update_dt;
}
