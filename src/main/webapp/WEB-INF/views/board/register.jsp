<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
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
	<div id="register">
		<register-form></register-form>
	</div>

	<template id="registerForm">
	<div>
		<h1>register.jsp</h1>
		<form action="register" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>제목</td>
					<td><input type="text" name="bTitle" id="bTitle"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="bContent" rows="8" cols="22" style="resize: none; right: none;"></textarea></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><input type="file" name="multipartFiles" multiple/></td>
				</tr>
			</table>
			<button type="button" v-on:click="cancel()">취소</button>
			<button type="submit">확인</button>
		</form>
	</div>
	</template>
	<script>
		Vue.component('register-form', {
			template : '#registerForm',
			data : function(){
				return {
					pageNum : ${param.pageNum}
				}
			},
			methods : {
				cancel : function() {
					location.href = '/Board/list?&pageNum=' + this.pageNum;
				}
			}
		})
		var vm = new Vue({
			el : '#register'
		})
	</script>
</body>
</html>