<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://cdn.jsdelivr.net/npm/vue@2.5.2/dist/vue.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<style>
table {
	border: 1px solid #444444;
}

th, td {
	border: 1px solid #444444;
}
</style>
</head>
<body>
	<div id="content">
		<content-form></content-form>
		<comment-form></comment-form>
	</div>
	<template id="contentForm">
	<div>
		<h1>content.jsp</h1>

		<form method="post" action="remove">

			<table>
				<tr>
					<td>번호 : {{board.bId}} <input type="hidden" :value="board.bId"
						name="bId">
					</td>
					<td colspan="3">제목 : {{board.bTitle}}</td>
				</tr>
				<tr>
					<td>작성일 : {{board.bCreatedAt}}</td>
					<td>수정일 : {{board.bModifiedAt}}</td>
					<td>조회수 : {{board.bCount}}</td>
					<td>추천 : {{board.like}}</td>
				</tr>
				<tr>
					<td colspan="3">{{board.bContent}}</td>
				</tr>
			</table>

			<button type="button" v-on:click="list()">목록</button>
			<button type="button" v-on:click="modify()">수정</button>
			<button type="submit">삭제</button>
			<button type="button" v-on:click="likeCount('like')">추천</button>
			<button type="button" v-on:click="likeCount('hate')">추천 취소</button>
		</form>
	</div>
	</template>
	<template id="commentForm">
	<div>
		<form v-on:submit="creComment">
			<textarea type="text" v-model="content" rows="4" cols="52" style="resize: none;"></textarea>
			<br>
			<button>글쓰기</button>
		</form>
		<div v-if="comments.length">
			<table>
				<tr>
					<td>코멘트 아이디</td>
					<td>게시판 아이디</td>
					<td>내용</td>
					<td>생성일</td>
					<td>수정일</td>
					<td colspan="2">수정/삭제</td>
				</tr>
				<tr v-for="(comment, index) in comments">
					<td>{{comment.comment_id}}</td>
					<td>{{comment.bbs_id}}</td>
					<td>{{comment.content}}</td>
					<td>{{comment.crt_dt}}</td>
					<td>{{comment.update_dt}}</td>
					<td><button v-on:click.prevent="modify(comment)">수정</button></td>
					<td><button v-on:click="remove(comment)">삭제</button></td>
				</tr>
			</table>
		</div>
		<pagination-com :page="page" title="content" @pagingfn="getComment"></pagination-com>
	</div>
	</template>
	<jsp:include page="./pagination.jsp" />
	<script>
		Vue.component('content-form', {
			template : '#contentForm',
			data : function(){
				return{
				board : null,
				bId: ${param.bId},
				pageNum: ${param.pageNum},
				like : null
				}
			},
			methods : {
				getBoard : function(){
					var vm = this;
					var data = {bId : vm.bId};
					$.ajax({
						url:"/Board/content",
						data: JSON.stringify(data),
						type:'POST',
						async: false,
						contentType: "application/json; charset=utf-8",
						success: function(result){
							vm.board = result;
						},
						error : function(e){
							console.log('err', e)
						}
					})
				},
				likeCount : function(likeBoo) {
					var vm = this;
					var data = {
							bId : vm.bId,
							likeCheck : likeBoo
							};
					$.ajax({
						url : '/Board/regLike',
						data : JSON.stringify(data),
						type : 'POST',
						contentType: "application/json; charset=utf-8",
						success : function(result){
							vm.board = result.board;
							alert(result.likeResult);
						},
						error : function(e){
							console.log('error', e);
						}
					})
				},
				list : function(){
					location.href = '/Board/list?pageNum=' + this.pageNum
				},
				modify: function() {
					location.href = '/Board/modify?bId=' + this.bId + '&pageNum=' + this.pageNum
				}
			},
			created() {
				this.getBoard();
			}
// 			,
// 			mounted : function(){
// 				var vm = this;
// 			}
		})
		
		Vue.component('comment-form', {
			template : '#commentForm',
			data : function(){
				return{
				comments : null,
				bId: ${param.bId},
				content : null,
				pageNum: ${param.pageNum},
				page : null
				}
			},
			methods : {
				getComment : function(data) {
					var vm = this;
					if(data ==null){
						data = 1;
					}
					var data = {
						bbs_id : vm.bId,
						pageNum : data
					};
					$.ajax({
						url:"/Comments/get",
						data: JSON.stringify(data),
						type:'POST',
						async: false,
						contentType: "application/json; charset=utf-8",
						success: function(result){
							vm.comments = result.comments;
							vm.page = result.page;
						},
						error : function(e){
							console.log('err', e)
						}
					})
				},
				creComment : function(e){
					e.preventDefault();
					var vm = this;
					var data = {
							bbs_id : this.bId,
							content : this.content
					}
					
					$.ajax({
						url:"/Comments/new",
						data: JSON.stringify(data),
						type:'POST',
						contentType: "application/json; charset=utf-8",
						success: function(result){
							vm.comments = result;
						},
						error : function(e){
							console.log('err', e)
						}
					})
				},
				remove : function(e) {
					var vm = this;
					var data = {comment_id : e.comment_id}
					$.ajax({
						url:"/Comments/remove",
						data: JSON.stringify(data),
						type:'POST',
						contentType: "application/json; charset=utf-8",
						success: function(result){
							location.reload();
						},
						error : function(e){
							console.log('err', e)
						}
					})
				},
				modify : function(e){
					location.href = '/Comments/comment?comment_id=' + e.comment_id + '&pageNum=' + this.boardPageNum;
				}
			},
			created() {
				this.getComment();
			}
		})
		
		var content = new Vue({
			el:"#content"			
		})
	</script>
</body>
</html>