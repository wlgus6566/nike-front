<template>
	<div class="user">
		<div class="user-info">
			<div class="photo">
				<img src="http://placehold.it/75x75" alt="">
			</div>
			<span class="label">PARTNER</span>
			<span class="name">김나이</span>
			<div class="side">
				<button type="button" class="btn-alram"><span>알람</span></button>
				<div class="alram-box">
					<strong class="title">NEW</strong>
					<p class="txt">에어맥스 2090 신제품 런칭 그래픽 자료가 업데이트되었습니다.</p>
					<button type="button" class="btn-close"><span>닫기</span></button>
				</div>
				<button type="button" class="btn-menu"><span>멀까요?</span></button>
			</div>
		</div>
		<div class="space-info">
			<p class="store">홍대 SKNRS</p>
			<address>서울 마포구 와우산로 78</address>
		</div>
	</div>
</template>
<script>
	export default {
		name: "user.vue"
	}
</script>
<style scoped>
	.user-info{position:relative;}
	.user-info:after{content:""; clear:both; display:block; overflow:hidden; }
	.user-info .photo{clear:left; display:none; width:75px; height:75px; margin-bottom:17px; border-radius:100%; overflow:hidden;}
	.user-info .photo img{vertical-align:top;}
	.user-info .label{float:left; display:inline-flex; height:16px; padding:0 5px; border-radius:2px; align-content:center; justify-content:center; background:#000; font-weight:bold; font-size:10px; color:#fff; letter-spacing:0;}
	.user-info .name{float:left; display:inline-flex; margin-left:6px; line-height:16px; font-size:14px; font-weight:bold; color:#000;}
	.user-info .side{position:absolute; top:-2px; right:0; display:flex; width:100%; justify-content:end}
	.user-info .side [class^=btn-]{display:block; width:22px; height:22px; background:red;}
	.user-info .side [class^=btn-] span{display:block; text-indent:-9999999px; overflow:hidden;}
	.user-info .side [class^=btn-]:first-child{margin-left:auto;}
	.user-info .side [class^=btn-] +  [class^=btn-]{margin-right:-2px;}
	.alram-box{z-index:2; position:absolute; top:34px; left:0; display:none; box-sizing:border-box; width:100%; padding:12px 52px 14px 19px; border-radius:2px; box-shadow:0 5px 10px 0 rgba(0, 0, 0, 0.1); background:#fff;}
	.alram-box .title{display:block; font-size:12px; line-height:12px; font-weight:bold; color:#000;}
	.alram-box .txt{margin-top:2px; font-size:11px; line-height:17px; color:#333;}
	.alram-box .btn-close{position:absolute; top:4px; right:4px; width:30px; height:30px; border-radius:0; background:red;}
	.alram-box.active{display:block;}
	.user-info.photo-type .photo{display:block;}
	.user-info.photo-type .side{top:25px;}
	.user-info.photo-type .alram-box{top:60px;}

	.space-info {margin-top:13px; font-weight:300; font-size:12px; color:#000;}
	.space-info .store{display:flex; height:18px; align-content:center; }
	.space-info address{display:flex; height:18px; margin-top:1px; align-content:center; opacity:0.6;}

</style>
