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
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    <button type="button" class="btn-sel">
                        <span>삭제</span>
                    </button>
                </li>

                <li>
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    <button type="button" class="btn-sel">
                        <span>삭제</span>
                    </button>
                </li>
                <li>
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    <button type="button" class="btn-sel">
                        <span>삭제</span>
                    </button>
                </li>
                <li>
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    <button type="button" class="btn-sel">
                        <span>삭제</span>
                    </button>
                </li>
                <li>
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    <button type="button" class="btn-sel">
                        <span>삭제</span>
                    </button>
                </li>
                <li>
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    <button type="button" class="btn-sel">
                        <span>삭제</span>
                    </button>
                </li>
                <li>
                    <img src="../../assets/images/img-asset-none@2x.png" alt="" />
                    <button type="button" class="btn-sel">
                        <span>삭제</span>
                    </button>
                </li>
            </ul>
            <button type="button" class="btn-sown">
                <span class="gage" style="width: 50%;"></span>
                <span class="txt" style="display: none;">DOWNLOAD</span>
                <span class="txt">DOWNLOAD...</span>
            </button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'FileItem',
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
    padding: 15px 0 15px 15px;
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
.file-list li .btn-sel {
    position: absolute;
    top: 0;
    right: 0;
    display: block;
    width: 20px;
    height: 20px;
    border-radius: 0;
    background: url(../../assets/images/svg/icon-close-white-small.svg) no-repeat center;
}
.file-list li .btn-sel span {
    display: block;
    text-indent: -9999999px;
    overflow: hidden;
}
.file-list li img {
    vertical-align: top;
}
.btn-sown {
    position: relative;
    display: flex;
    width: 100%;
    height: 40px;
    border-radius: 0;
    justify-content: center;
    align-items: center;
    font-size: 14px;
    color: #fff;
    background: #ccc;
}
.btn-sown .gage {
    position: absolute;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    background: #fa5400;
}
.btn-sown .txt {
    position: relative;
    font-family: 'Bebas Neue', sans-serif;
    letter-spacing: 0.58px;
}
.btn-sown:disabled .gage {
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
</style>
