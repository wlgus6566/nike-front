<template>
    <div id="wrap">
        <header>
            <div class="inner">
                <h1>
                    <a href="#"
                        ><img alt="" src="@/assets/images/logo-nike.svg"
                    /></a>
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
                <h2 class="page-title">VMS</h2>
                <div class="upload-summury">
                    <strong class="title">파일올리기</strong>
                    <p class="desc">
                        자료는 NIKE.INC.와 NIKE KOREA LLC.의 자산입니다.<br />
                        보안 규정을 준수하시기 바랍니다.
                    </p>
                    <button type="button" class="btn-s-black">
                        <span class="bebas">UPLOAD</span>
                    </button>
                </div>

                <div class="sorting-area">
                    <div class="filter-list">
                        <button
                            type="button"
                            class="type-list"
                            v-bind:class="{ active: listType.active === true }"
                            v-for="(listType, index) in listTypes"
                            :key="index"
                            v-on:click="update(index)"
                        >
                            <span>{{ listType.title }}</span>
                        </button>
                    </div>
                    <!-- todo select 스크립트 작업 필요  -->
                    <div class="filter-select">
                        <el-select v-model="value" placeholder="Select">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div class="search-input">
                        <input
                            type="text"
                            placeholder="검색어를 입력해주세요."
                        />
                        <button type="button"><span>검색</span></button>
                    </div>
                </div>

                <div class="no-data">
                    <i class="icon-folder"></i>
                    <p class="desc">업로드한 폴더가 없습니다.</p>
                </div>

                <div class="no-data">
                    <i class="icon-search"></i>
                    <p class="desc">최근 본 폴더가 없습니다.</p>
                </div>

                <div class="no-data">
                    <i class="icon-data"></i>
                    <p class="desc">주문 내역이 없습니다.</p>
                </div>

                <div class="no-data">
                    <i class="icon-order"></i>
                    <p class="desc">위시리스트에 추가 된 항목이 없습니다.</p>
                </div>
                <ul
                    v-bind:class="[
                        { 'folder-list': listTypes[0].active },
                        { 'folder-list-row': listTypes[1].active },
                    ]"
                >
                    <li
                        class="folder-item"
                        v-for="(item, index) in items"
                        :key="index"
                    >
                        <a href="#">
                            <div class="thumbnail">
                                <img src="item.img" alt="" />
                            </div>
                            <div class="info-box">
                                <strong class="title">{{ item.title }}</strong>
                                <p class="txt">{{ item.desc }}</p>
                                <p class="date">{{ item.date }}</p>
                            </div>
                            <div class="view-area">
                                <span class="view">10,000</span>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
            <aside>
                <div class="aside-wrap">aside</div>
            </aside>
        </section>
        <footer></footer>
    </div>
</template>
<script>
export default {
    name: 'ASSET_SP_LIST',
    data() {
        return {
            listTypes: [
                {
                    title: '컬럼타입',
                    active: true,
                },
                {
                    title: '로우타입',
                    active: false,
                },
            ],
            options: [
                {
                    value: '최신순',
                    label: '최신순',
                },
                {
                    value: '시작일 순',
                    label: '시작일 순',
                },
            ],
            value: '최신순',
            items: [
                {
                    title: '타이틀',
                    img: '@/assets/images/img-asset-none@2x.png"',
                    desc: '설명',
                    date: '2020.00.00 ~ 2020.00.00',
                },
                {
                    title: '타이틀2',
                    img: '@/assets/images/img-asset-none@2x.png"',
                    desc: '설명2',
                    date: '2020.00.00 ~ 2020.00.00',
                },
            ],
        };
    },
    methods: {
        update(index) {
            if (this.listTypes[index].active == false) {
                this.listTypes.forEach((element) => (element.active = false));
                this.listTypes[index].active = true;
            }
            // 자식 컴포넌트에서 부모 컴포넌트로 보내는 것이 $emit()
            //this.$emit("child",this.user)
        },
    },
};
</script>
<style scoped></style>
