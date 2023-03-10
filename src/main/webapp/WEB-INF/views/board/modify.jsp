<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
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
	<div id="modify">
		<modify-form></modify-form>
	</div>
	<template id="modifyForm">
	<div>
		<h1>modify.jsp</h1>

		<form method="post" action="modify">
			<table>
				<tr>
					<td>번호 : {{board.bId }} <input type="hidden" :value="board.bId" name="bId">
					</td>
					<td colspan="2">제목 : <input type="text" name="bTitle" :value="board.bTitle">
					</td>
				</tr>
				<tr>
					<td>작성일 : {{board.bCreatedAt}}</td>
					<td>수정일 : {{board.modifiedAt}}</td>
					<td>조회수 : {{board.bCount}}</td>
				</tr>
				<tr>
					<td colspan='3'><textarea name="bContent" rows="8" cols="55"
							style="resize: none; right: none;">{{board.bContent }}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan='3'>
						<div>첨부파일</div>
					</td>
				</tr>
			</table>
			<input type="hidden" name="pageNum" :value="pageNum">
			<button type="button" v-on:click="cancel()">취소</button>
			<button type="submit">수정</button>

		</form>
	</div>
	</template>

	<script>
	Vue.component('modify-form', {
		template : '#modifyForm',
		data : function(){
			return{
				board : null,
				bId : ${param.bId},
				pageNum : ${param.pageNum}
			}
		},
		methods : {
			bbbbb : function(){
				var vm = this;
				var data = {bId : this.bId};
				console.log(data);
				$.ajax({
					url:"/Board/content",
					data: JSON.stringify(data),
					type:'POST',
					async: false,
					contentType: "application/json; charset=utf-8",
					success: function(result){
						vm.board = result;
						console.log('board : ', vm.board);
					},
					error : function(e){
						console.log('err', e)
					}
				})
			},
			cancel : function(){
				location.href = '/Board/content?bId=' + this.bId + '&pageNum=' + this.pageNum
			}
		},
		created() {
			this.bbbbb();
		}
	})
	var vm = new Vue({
		el:"#modify"
	})
	</script>
</body>
</html>