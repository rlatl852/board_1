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



	<div id="contentForm">
		<content-form>
		</content-form>
	</div>
	
	
	
	
	<template id="commentForm">
	<div>
		<h1>comment.jsp</h1>
		<form action="commentModify" method="POST">
		<table>
			<tr>
				<td>코멘트 아이디</td>
				<td>
					{{comment.comment_id}}
					<input type="hidden" :value="comment.comment_id" name="comment_id">
				</td>
				
			</tr>
			<tr>
				<td>게시판 아이디</td>
				<td>
					{{comment.bbs_id}}
					<input type="hidden" :value="comment.bbs_id" name="bbs_id">
				</td>
				
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea rows="4" cols="30" name="content" style="resize: none;">{{comment.content}}</textarea>
				</td>
			</tr>
			<tr>
				<td>생성일</td>
				<td>{{comment.crt_dt}}</td>
			</tr>
			<tr>
				<td>수정일</td>
				<td>{{comment.update_dt}}</td>
			</tr>
		</table>
		<input type="hidden" :value="pageNum" name="pageNum">
		<button type="submit">수정</button>
		</form>
	</div>
	</template>
	<script>
		Vue.component('content-form', {
			template : '#commentForm',
			data : function(){
				return{
					comment_id: ${param.comment_id},
					pageNum : ${param.pageNum},
					comment : null
				}
			},
			methods : {
				getComment : function(){
					var vm = this;
					var data = {
						comment_id : this.comment_id,
						pageNum : this.pageNum
					}
					$.ajax({
						url:"/Comments/comment",
						data: JSON.stringify(data),
						type:'POST',
						async: false,
						contentType: "application/json; charset=utf-8",
						success: function(result){
							vm.comment = result;
						},
						error : function(e){
							console.log('err', e)
						}
					})
				}
			},
			created() {
				this.getComment();
			}
		})
		var vm = new Vue({
			el : '#contentForm',
		})
	</script>
</body>
</html>