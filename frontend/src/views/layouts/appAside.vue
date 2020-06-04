<template>
	<aside class="sticky-container" sticky-container>
		<div class="inner" on-stick="onStick" sticky-offset="{top: 10, bottom: 30}" sticky-side="both" sticky-z-index="20" v-sticky>
			<div class="aside-wrap">
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
				<div class="aside-menu" style="display:none">
					<strong class="title">MANAGEMENT</strong>
					<ul class="menu">
						<li>
							<a href="#">파트너 계정관리</a>
						</li>
						<li>
							<a href="#">에이전시 계정관리</a>
						</li>
					</ul>
					<strong class="title">UPLOAD</strong>
					<ul class="menu">
						<li>
							<a href="#">내가 업로드한 파일</a>
						</li>
						<li>
							<a href="#">최근 업로드 파일</a>
						</li>
					</ul>
				</div>
				<div class="file-box" style="display:none">
					<strong class="title">FILE</strong>
					<ul class="list">
						<!--						<li class="no-data">-->
						<!--							<i class="icon-file"></i>-->
						<!--							<p class="txt">더욱 빠르게 파일 받기</p>-->
						<!--							<p class="desc">-->
						<!--								이곳에 끌어다 놓으면 파일을 바로<br>-->
						<!--								다운받을 수 있어요.-->
						<!--							</p>-->
						<!--						</li>-->
						<li>
							<div class="flag-box">
								<span class="flag">AI</span>
							</div>
							<img src="http://placehold.it/180x180" alt="">
							<button type="button" class="btn-del"><span>삭제</span></button>
						</li>
						<li>
							<div class="flag-box">
								<span class="flag">AI</span>
							</div>
							<img src="http://placehold.it/180x180" alt="">
							<button type="button" class="btn-del"><span>삭제</span></button>
						</li>
						<li>
							<div class="flag-box">
								<span class="flag">AI</span>
							</div>
							<img src="http://placehold.it/180x180" alt="">
							<button type="button" class="btn-del"><span>삭제</span></button>
						</li>
						<li>
							<div class="flag-box">
								<span class="flag">AI</span>
							</div>
							<img src="http://placehold.it/180x180" alt="">
							<button type="button" class="btn-del"><span>삭제</span></button>
						</li>
						<li>
							<div class="flag-box">
								<span class="flag">AI</span>
							</div>
							<img src="http://placehold.it/180x180" alt="">
							<button type="button" class="btn-del"><span>삭제</span></button>
						</li>
					</ul>
					<button type="button" class="btn-down">
						<span class="gage" style="width:50%"></span>
						<span class="txt">DOWNLOAD</span>
					</button>
				</div>
				<div class="tab-box">
					<ul class="tab-list">
						<li class="active">
							<a href="#"><span>HISTORY</span></a>
						</li>
						<li>
							<a href="#"><span>ORDER</span></a>
						</li>
					</ul>
					<div class="tab-contents">
						<ul>
							<li>

							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</aside>
</template>
<script>
	import Sticky from 'vue-sticky-directive'

	export default {
		name: 'appAside',
		directives: {
			Sticky
		},
		mounted() {

		},
		methods: {}
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
	.alram-box:active{display:block;}
	.user-info.photo-type .photo{display:block;}
	.user-info.photo-type .side{top:25px;}
	.user-info.photo-type .alram-box{top:60px;}
	.space-info {margin-top:13px; font-weight:300; font-size:12px; color:#000;}
	.space-info .store{display:flex; height:18px; align-content:center; }
	.space-info address{display:flex; height:18px; margin-top:1px; align-content:center; opacity:0.6;}
	.file-box{margin-top:43px;}
	.file-box .title{display:block; line-height:20px; font-size:20px; color:#000; font-weight:bold;}
	.file-box .list{display:flex; flex-wrap:wrap; align-content:flex-start; box-sizing:border-box; height:360px; margin-top:10px; padding:15px; background:#eee;}
	.file-box .list .no-data{height:100%;}
	.file-box .list li{position:relative; flex:0 0 100px;   height:100px;}
	.file-box .list li:nth-child(2n){margin-left:10px;}
	.file-box .list li:nth-child(n+3){margin-top:10px;}
	.file-box .list li.no-data{flex:1 0 100px; }
	.file-box .list li .flag-box{position:absolute; top:0; left:0;}
	.file-box .list li .btn-del{position:absolute; top:0; right:0; display:block; width:20px; height:20px; border-radius:0; background:red;}
	.file-box .list li .btn-del span{display:block; text-indent:-9999999px; overflow:hidden;}
	.file-box .list li img{vertical-align:top;}
	.btn-down{position:relative; display:flex; width:100%; height:40px; border-radius:0; justify-content:center; align-items:center;font-size:12px; font-weight:bold; color:#fff; background:#ccc;}
	.btn-down .gage {position:absolute; top:0; left:0; display:block; width:100%; height:100%; background:#000;}
	.btn-down .txt{position:relative;}
	.btn-down:disabled{}
	.btn-down:disabled .gage{width:0;}
	.flag-box .flag{display:inline-flex; align-items:center; justify-content:center; width:24px; height:24px; background:#000; font-size:10px; color:#fff; font-weight:bold;}
	.no-data{display:flex; align-items:center; justify-content:center; flex-direction:column;}
	.no-data .icon-file{display:block; width:45px; height:45px; background:red;}
	.no-data .txt{margin-top:15px; font-size:11px; font-weight:500; color:#000; line-height:17px; text-align:center;}
	.no-data .txt + .desc{margin-top:2px;}
	.no-data .desc{font-size:10px; line-height:15px; color:#999; font-weight:300; text-align:center;}

	.aside-menu{margin-top:43px;}
	.aside-menu .title{display:block; font-size:14px; font-weight:bold; line-height:40px;}
	.aside-menu .menu + .title{margin-top:10px;}
	.aside-menu .menu li a{display:block; line-height:30px; font-size:12px; color:#000;}

	.tab-box{margin-top:63px;}
	.tab-list{display:flex; margin-left:-15px;}
	.tab-list a{display:block; padding:0 15px; line-height:40px; font-size:14px; color:#ccc; font-weight:bold;}
	.tab-list .active a{color:#000;}
	.tab-list .active span{text-decoration:underline}
</style>
