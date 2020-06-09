<template>
    <div class="aside-file">
        <div>
            <ul class="file-list">
                <!-- <li class="no-data">
					<i class="icon-file"></i>
					<p class="txt">더욱 빠르게 파일 받기</p>
					<p class="desc">
						이곳에 끌어다 놓으면 파일을 바로<br>
						다운받을 수 있어요.
					</p>
				</li>-->
                <li>
                    <img
                        src="../../assets/images/img-asset-none@2x.png"
                        alt=""
                    />
                    <button type="button" class="btn-del">
                        <span>삭제</span>
                    </button>
                </li>

                <li>
                    <img
                        src="../../assets/images/img-asset-none@2x.png"
                        alt=""
                    />
                    <button type="button" class="btn-del">
                        <span>삭제</span>
                    </button>
                </li>
            </ul>
            <button type="button" class="btn-down">
                <span class="gage" style="width: 50%;"></span>
                <span class="txt" style="display: none;">DOWNLOAD</span>
                <span class="txt">DOWNLOAD...</span>
            </button>
        </div>
        <div>
            <div class="aside-menu">
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
        </div>
    </div>
</template>
<script>
export default {
    name: 'AsideFile',
};
</script>
<style scoped>
.file-list {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    box-sizing: border-box;
    height: 360px;
    margin-top: 15px;
    padding: 15px;
    background: #eee;
    overflow: auto;
}
.file-list .no-data {
    height: 100%;
}
.file-list li {
    position: relative;
    flex: 0 0 100px;
    height: 100px;
}
.file-list li:nth-child(2n) {
    margin-left: 4px;
}
.file-list li:nth-child(n + 3) {
    margin-top: 4px;
}
.file-list li.no-data {
    flex: 1 0 100px;
}
.file-list li .flag-box {
    position: absolute;
    top: 0;
    left: 0;
}
.file-list li .btn-del {
    position: absolute;
    top: 0;
    right: 0;
    display: block;
    width: 20px;
    height: 20px;
    border-radius: 0;
    background: url(../../assets/images/svg/icon-close-white-small.svg)
        no-repeat center;
}
.file-list li .btn-del span {
    display: block;
    text-indent: -9999999px;
    overflow: hidden;
}
.file-list li img {
    vertical-align: top;
}
.btn-down {
    position: relative;
    display: flex;
    width: 100%;
    height: 40px;
    border-radius: 0;
    justify-content: center;
    align-items: center;
    font-size: 12px;
    font-weight: bold;
    color: #fff;
    background: #ccc;
}
.btn-down .gage {
    position: absolute;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    background: #fa5400;
}
.btn-down .txt {
    position: relative;
}
.btn-down:disabled {
}
.btn-down:disabled .gage {
    width: 0;
}
.flag-box .flag {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 24px;
    height: 24px;
    background: #000;
    font-size: 10px;
    color: #fff;
    font-weight: bold;
}
.no-data {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}
.no-data .icon-file {
    display: block;
    width: 50px;
    height: 50px;
    background: url(../../assets/images/svg/illust-file-empty.svg);
}
.no-data .txt {
    margin-top: 15px;
    font-size: 11px;
    font-weight: 500;
    color: #000;
    line-height: 17px;
    text-align: center;
}
.no-data .txt + .desc {
    margin-top: 2px;
}
.no-data .desc {
    margin-top: 7px;
    font-size: 10px;
    line-height: 15px;
    color: #999;
    font-weight: 300;
    text-align: center;
}

.aside-menu {
    margin-top: 43px;
}
.aside-menu .title {
    display: block;
    font-size: 14px;
    font-weight: bold;
    line-height: 40px;
}
.aside-menu .menu + .title {
    margin-top: 10px;
}
.aside-menu .menu li a {
    display: block;
    line-height: 30px;
    font-size: 12px;
    color: #000;
}
</style>
