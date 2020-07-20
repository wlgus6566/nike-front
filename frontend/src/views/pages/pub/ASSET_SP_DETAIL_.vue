<template>
    <div id="wrap">
        <header>
            <div class="inner">
                <h1>
                    <a href="#"><img alt="" src="@/assets/images/logo-nike.svg" /></a>
                </h1>
                <nav>
                    <ul>
                        <li>
                            <a href="#">TEST</a>
                        </li>
                    </ul>
                    <div class="bg"></div>
                </nav>
            </div>
        </header>
        <section id="container">
            <div class="contents">
                <div class="btn-detail">
                    <a href="#" class="btn-list"><span>목록으로 가기</span></a>
                    <div class="btn-box">
                        <button type="button" class="btn-o-white"><span>삭제</span></button>
                        <button type="button" class="btn-o-white"><span>수정</span></button>
                        <button type="button" class="btn-o-gray">
                            <i class="icon-mail"></i>
                            <span>알림메일전송</span>
                        </button>
                    </div>
                </div>

                <div class="folder-wrap">
                    <h2 class="folder-title">
                        NIKE DIRECT AM90 NIKE DIRECT AM90 나이키 다이렉트 NSW
                    </h2>
                    <div class="inner">
                        <p class="folder-desc">
                            나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가
                            업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료가
                            업데이트 되었습니다.
                        </p>
                        <span class="folder-date">2020.01.01 - 2020.01.03</span>
                        <p class="folder-name">나이키 담당자 정보 : <em>홍길동</em></p>
                    </div>

                    <div class="noti-box">
                        <ul class="noti-item-list">
                            <li class="noti-item">
                                KEEP IT TIGHT! 본 자료는 NIKE.INC.와 NIKE KOREA LLC.의 자산입니다.
                                보안 규정을 준수하시기 바랍니다.
                            </li>
                            <li class="noti-item">
                                자료의 조회와 다운로드 이력이 추적 / 관리됩니다. 자료의 불법적인
                                유출은 관련 법에 의거 처벌될 수 있습니다.
                            </li>
                        </ul>
                    </div>
                </div>

                <ul class="sorting-list">
                    <li class="active">
                        <button type="button"><span>ALL</span></button>
                    </li>
                    <li>
                        <button type="button"><span>ASSET</span></button>
                    </li>
                    <li>
                        <button type="button"><span>GUIDE</span></button>
                    </li>
                </ul>
                <div class="all-box">
                    <el-checkbox
                        :indeterminate="isIndeterminate"
                        v-model="checkAll"
                        @change="handleCheckAllChange"
                        >전체선택</el-checkbox
                    >
                    <p class="desc">
                        <em>{{ checkLength }}</em
                        >개의 파일이 선택됨
                    </p>
                    <!-- todo select 스크립트 작업 필요  -->
                    <div class="filter-select">
                        <select>
                            <option value="필터">필터</option>
                        </select>
                    </div>
                </div>
                <!-- todo 추가 스크립트 작업 필요  -->
                <el-checkbox-group v-model="checkItem" @change="handleCheckedCitiesChange">
                    <ul class="file-item-list">
                        <li class="file-item" v-for="(item, index) in items" :key="index">
                            <div class="list">
                                <el-checkbox :label="item.title">
                                    <span class="thumbnail">
                                        <img :src="item.img" alt="" />
                                    </span>
                                    <span class="info-box">
                                        <strong class="title">{{ item.title }}</strong>
                                    </span>
                                </el-checkbox>
                                <div class="btn-box">
                                    <button type="button" class="btn-s-sm-white">
                                        <i class="icon-check"></i><span>ADDED</span>
                                    </button>
                                    <button type="button" class="btn-more active">
                                        <span>더보기</span>
                                    </button>
                                </div>
                            </div>
                            <div class="detail active">
                                <div class="thumbnail">
                                    <img :src="item.img" alt="" />
                                </div>
                                <div class="down-info">
                                    <span class="key">다운로드 횟수</span>
                                    <span class="val"
                                        ><strong>{{ item.down }}</strong> 회</span
                                    >
                                </div>
                            </div>
                        </li>
                    </ul>
                </el-checkbox-group>
            </div>
            <aside>
                <div class="aside-wrap">
                    aside
                </div>
            </aside>
        </section>
        <footer></footer>
    </div>
</template>
<script>
export default {
    data() {
        return {
            items: [
                {
                    title: '타이틀',
                    state: true,
                    img: require('@/assets/images/img-asset-none@2x.png'),
                    url: null,
                    down: 0,
                },
                {
                    title: '타이틀2',
                    state: true,
                    img: require('@/assets/images/img-asset-none@2x.png'),
                    url: 'https://question0.tistory.com/20',
                    down: 100,
                },
                {
                    title: '타이틀3',
                    state: true,
                    img: require('@/assets/images/img-asset-none@2x.png'),
                    url: 'https://question0.tistory.com/20',
                    down: 100,
                },
            ],
            checkAll: false,
            checkLength: 0,
            checkItem: [],
            isIndeterminate: true,
        };
    },
    created() {
        const _this = this;
        this.items.forEach((element) => check(element, _this));
        function check(val, _this) {
            if (val.state) {
                _this.checkItem.push(val.title);
                if (_this.checkItem.length == _this.cities) {
                    _this.checkAllFn;
                }
            }
        }
        this.lenthCheck;
    },
    computed: {
        allCheck() {
            const allCheck = [];
            this.items.forEach((element) => allCheck.push(element.title));
            return allCheck;
        },
        lenthCheck() {
            return (this.checkLength = this.checkItem.length);
        },
        checkAllFn() {
            return (this.checkAll = true);
        },
        cities() {
            return this.items.length;
        },
    },
    methods: {
        handleCheckAllChange(val) {
            this.checkItem = val ? this.allCheck : [];
            this.isIndeterminate = false;
            this.lenthCheck;
        },
        handleCheckedCitiesChange(value) {
            console.log(value);
            let checkedCount = value.length;
            this.checkAll = checkedCount === this.cities;
            this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities;
            //체크된 갯수가 0보다 크고 체크된 갯수가 아이템의 갯수보다 작으면 false
            this.lenthCheck;
        },
    },
};
</script>
<style scoped></style>
