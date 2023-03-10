<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
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
	<div id="boardList">
		<board-list>
			
		</board-list>
		
	</div>
	<template id="list">
	<div>
		<h1>boardList.jsp</h1>

		<button id="register" v-on:click="register(page.viewPage)">글쓰기</button>

<!-- 		<form v-on:submit.prevent="getBoardList(page.viewPage)"> -->
			<select name="type" v-model="search.type" id="type">
				<option value="T">제목</option>
				<option value="I">번호</option>
				<option value="C">내용</option>
			</select>
			<input name="keyword" type="text" placeholder="Search" v-model="search.keyword" @keyup.enter="getBoardList(1)">
			<button type="button" @click="getBoardList(1)">검색</button>
<!-- 		</form> -->

		<div>
			<table>
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>등록일</td>
					<td>수정일</td>
					<td>조회수</td>
					<td>추천수</td>
				</tr>
				<tr v-for="(list, index) in boardList">
					<td>{{list.bId}}</td>
					<td><a href="#" v-on:click="content(list.bId, page.viewPage)">{{list.bTitle}}</a></td>
					<td>{{list.bCreatedAt}}</td>
					<td>{{list.bModifiedAt}}</td>
					<td>{{list.bCount}}</td>
					<td>{{list.like}}</td>
				</tr>
			</table>
		</div>
		<pagination-com :page="page" title="boardList" @pagingfn="getBoardList"></pagination-com>
		<!-- @searchBoard="getBoardList 자식 컴포넌트가 searchBoard을 요청하면 getBoardList 메소드 실행  -->
		<!-- :page="page" 는 자식 컴포넌트에  page라는 이름으로 page data를 넘겨주겠다 -->
	</div>
	</template>
	<jsp:include page="./pagination.jsp"/>
	<script>
// 		vue 객체 
// 	    el : 선택 html요소 내부에 있는 html요소들을 vue가 컨트롤 해줌.
// 	    data : vue 객체 내부에서 컨트롤 할 수 있는 변수
// 	    methods : vue 객체 내부에서 컨트롤 할 수 있는 메소드
// 	    filters : 데이터 값을 로직을 통해 걸러 데이터를 화면에 반환해줌, 실 데이터는 바뀌지 않음
// 	    computed : vue data 객체 내부에 있는 변수들의 값이 바뀔때 감지를 해주고 function을 통해 로직 처리가 가능
// 	    watch : vue data 객체 내부에 있는 변수들의 값이 바뀔때 감지를 해주고 function을 통해 로직 처리가 가능
// 			- computed와 watch가 하는 기능은 비슷하지만 다름.
// 		mounted : vue 객체가 메모리에 올라가고, 화면에 로드될 때 function을 통해 로직이 실행됨.
		
		Vue.component('board-list', {
			template : '#list',
			data : function(){
				return{
					boardList : ${readAll},
					page : ${page},
					search : {
						type : '',
						keyword : ''
					},
				}
			},
			methods : {
				getBoardList : function(data) {
					var vm = this;
					var pageNumber = {
							pageNum : data,
							type : this.search.type,
							keyword : this.search.keyword
						};
					$.ajax({
						url:"/Board/list",
						data: JSON.stringify(pageNumber),
						type:'POST',
						contentType: "application/json; charset=utf-8",
						success: function(result){
							vm.boardList = result.readAll;
							vm.page = result.page;
						},
						error : function(e){
							console.log('err', e)
						}
					})
				},
				register : function(pageNum){
					location.href = '${contextPath }/Board/register?&pageNum='+ pageNum
				},
				content : function(bId, pageNum){
					location.href = '${contextPath }/Board/content?bId=' + bId + '&pageNum='+ pageNum
				}
			}
		})
		
		var vm = new Vue({
			el : '#boardList',
		})
	</script>
</body>

</html>