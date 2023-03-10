<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<template id="pagination">
<div>
	<div>
		<span v-if="page.prev === true">
			<a href="#" v-on:click.prevent="callPage(1)"> [처음] </a>
			<a href="#" v-on:click.prevent="callPage(page.startPage - 1)"> << </a>
		</span>
		<span v-for="(pageNum, index) in pageNum">
			<a href="#" v-on:click.prevent="callPage(pageNum)"> {{pageNum}} </a>
		</span>
		<span v-if="page.next === true">
			<a href="#" v-on:click.prevent="callPage(page.endPage + 1)"> >> </a>
			<a href="#" v-on:click.prevent="callPage(page.realEndPage)"> [마지막] </a>
		</span>
	</div>
</div>
</template>
<script>
	Vue.component('pagination-com',{
		template : '#pagination',
		props : {
			page : Object,
			title : String
		},
		computed : {
			pageNum : function() {
				var range = []
				for(var i = this.page.startPage; i <= this.page.endPage; i++) {
					range.push(i);
				};
				return range;
			}
		},
		methods : {
			
			callPage : function(data){
				var vm = this;
				this.$emit('pagingfn', data);
// 				부모 컴포넌트에서 @pagingfn=""로 통일하면 아래의 코드는 불필요

// 				if(vm.title == 'content'){
// 					this.$parent.getComment(data);
// 					this.$emit('searchcomment', data);
					
// 				} else if(vm.title == 'boardList') {
// 					this.$emit('searchboard', data);
// 				}
			}
		}
	})
</script>