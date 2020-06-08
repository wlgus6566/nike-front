<template>
    <div class="file-box">
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
                <img src="http://placehold.it/180x180" alt="" />
                <button type="button" class="btn-del"><span>삭제</span></button>
            </li>
            <li>
                <div class="flag-box">
                    <span class="flag">AI</span>
                </div>
                <img src="http://placehold.it/180x180" alt="" />
                <button type="button" class="btn-del"><span>삭제</span></button>
            </li>
            <li>
                <div class="flag-box">
                    <span class="flag">AI</span>
                </div>
                <img src="http://placehold.it/180x180" alt="" />
                <button type="button" class="btn-del"><span>삭제</span></button>
            </li>
            <li>
                <div class="flag-box">
                    <span class="flag">AI</span>
                </div>
                <img src="http://placehold.it/180x180" alt="" />
                <button type="button" class="btn-del"><span>삭제</span></button>
            </li>
            <li>
                <div class="flag-box">
                    <span class="flag">AI</span>
                </div>
                <img src="http://placehold.it/180x180" alt="" />
                <button type="button" class="btn-del"><span>삭제</span></button>
            </li>
        </ul>
        <button type="button" class="btn-down">
            <span class="gage" style="width: 50%;"></span>
            <span class="txt">DOWNLOAD</span>
        </button>
    </div>
</template>
<script>
export default {
    name: 'file.vue',
}
</script>
<style scoped>
.file-box {
    margin-top: 43px;
}
.file-box .title {
    display: block;
    line-height: 20px;
    font-size: 20px;
    color: #000;
    font-weight: bold;
}
.file-box .list {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    box-sizing: border-box;
    height: 360px;
    margin-top: 10px;
    padding: 15px;
    background: #eee;
}
.file-box .list .no-data {
    height: 100%;
}
.file-box .list li {
    position: relative;
    flex: 0 0 100px;
    height: 100px;
}
.file-box .list li:nth-child(2n) {
    margin-left: 10px;
}
.file-box .list li:nth-child(n + 3) {
    margin-top: 10px;
}
.file-box .list li.no-data {
    flex: 1 0 100px;
}
.file-box .list li .flag-box {
    position: absolute;
    top: 0;
    left: 0;
}
.file-box .list li .btn-del {
    position: absolute;
    top: 0;
    right: 0;
    display: block;
    width: 20px;
    height: 20px;
    border-radius: 0;
    background: red;
}
.file-box .list li .btn-del span {
    display: block;
    text-indent: -9999999px;
    overflow: hidden;
}
.file-box .list li img {
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
    background: #000;
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
    width: 45px;
    height: 45px;
    background: red;
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
    font-size: 10px;
    line-height: 15px;
    color: #999;
    font-weight: 300;
    text-align: center;
}
</style>
