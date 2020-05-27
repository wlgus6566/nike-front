

<template>
	<div>
		<form action="#">
			<input type="text" v-model="title" v-on:input="updateInput">
			<button type="button" v-on:click="addTodo(title)">ADD</button>
		</form>
		<ul>
			<li v-for="(item, index) in todos" v-bind:key="index">
				<input type="checkbox" v-bind:checked="item.checked">
				{{item.title}}
				{{item.date}}
				<button type="button" v-on:click="deleteTodo(index)">삭제</button>
			</li>
		</ul>
	</div>
</template>
<script>
	//import { Post } from '../api/index'
	export default {
		data() {
			return {
				title : '',
				todos: [
					{
						date:"2020.05.26",
						checked : true,
						title : "나의 할일"
					},
					{
						date:"2020.05.26",
						checked : false,
						title : "나의 할일"
					},
					{
						date:"2020.05.26",
						checked : false,
						title : "나의 할일"
					}
				]
			}
		},
		methods: {
			addTodo : function(value){
				this.todos.push({
					data : new Date(),
					checked: false,
					title : value
				});
				this.title = '';
			},
			deleteTodo : function(index){
				this.todos.splice(index, 1);
			},
			updateInput : function(event){
				this.$emit('input', event.target.value);
			}
		}
	}
</script>
