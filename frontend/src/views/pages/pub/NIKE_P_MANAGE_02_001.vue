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
                <h2 class="page-title"><span class="ko">상품관리</span></h2>
                <div class="sorting-area">
                    <!-- todo select 스크립트 작업 필요  -->
                    <div class="filter-select">
                        <el-select v-model="value" placeholder="Select">
                            <el-option-group
                                v-for="group in options"
                                :key="group.label"
                                :label="group.label"
                            >
                                <el-option
                                    v-for="item in group.options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                </el-option>
                            </el-option-group>
                        </el-select>
                    </div>
                    <div class="filter-select">
                        <el-select v-model="value2" placeholder="Select">
                            <el-option
                                v-for="item in options2"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div class="filter-select">
                        <el-select v-model="value3" placeholder="Select">
                            <el-option
                                v-for="item in options3"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div class="search-input">
                        <input type="text" placeholder="검색어를 입력해주세요." />
                        <button type="button"><span>검색</span></button>
                    </div>
                </div>
                <div class="tbl-list">
                    <table>
                        <colgroup>
                            <col style="width: 60px;" />
                            <col style="width: auto;" />
                            <col style="width: auto;" />
                            <col style="width: 150px;" />
                            <col style="width: 100px;" />
                            <col style="width: 160px;" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>
                                    <el-checkbox v-model="checked" />
                                </th>
                                <th>계정명</th>
                                <th>ID (E-MAIL)</th>
                                <th>권한그룹</th>
                                <th>상태</th>
                                <th>최종로그인</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <el-checkbox v-model="checked" />
                                </td>
                                <td>
                                    <a href="#" class="under-link"><span>포스터 600X800</span></a>
                                </td>
                                <td>Da****y6jae@naver.com</td>
                                <td>NIKE DIRECT</td>
                                <td><span>정상</span></td>
                                <td>2020.05.30</td>
                            </tr>
                            <tr>
                                <td>
                                    <el-checkbox v-model="checked" />
                                </td>
                                <td>
                                    <a href="#" class="under-link"><span>포스터 600X800</span></a>
                                </td>
                                <td>Da****y6jae@naver.com</td>
                                <td>NIKE DIRECT</td>
                                <td><span class="fc-lightgray">휴먼</span></td>
                                <td>2020.05.30</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="btn-tbl-box">
                    <button type="button" class="btn-form">
                        <span>삭제</span>
                    </button>
                    <div class="right">
                        <button type="button" class="btn-form-gray">
                            <span>등록</span>
                        </button>
                    </div>
                </div>

                <div class="pagination">
                    <button type="button" class="prev"><span>이전</span></button>
                    <ul>
                        <li><em>1</em></li>
                        <li>
                            <button type="button"><span>2</span></button>
                        </li>
                        <li>
                            <button type="button"><span>3</span></button>
                        </li>
                        <li>
                            <button type="button"><span>4</span></button>
                        </li>
                        <li>
                            <button type="button"><span>5</span></button>
                        </li>
                        <li>
                            <button type="button"><span>6</span></button>
                        </li>
                        <li>
                            <button type="button"><span>7</span></button>
                        </li>
                        <li>
                            <button type="button"><span>8</span></button>
                        </li>
                        <li>
                            <button type="button"><span>9</span></button>
                        </li>
                        <li>
                            <button type="button"><span>10</span></button>
                        </li>
                    </ul>
                    <button type="button" class="next"><span>다음</span></button>
                </div>
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
    name: 'NIKE_P_MANAGE_02_001',
    data() {
        return {
            options: [
                {
                    label: '그룹1',
                    options: [
                        {
                            value: '그룹1-1',
                            label: 'Shanghai',
                        },
                        {
                            value: 'Beijing',
                            label: 'Beijing',
                        },
                    ],
                },
                {
                    label: 'City name',
                    options: [
                        {
                            value: 'Chengdu',
                            label: 'Chengdu',
                        },
                        {
                            value: 'Shenzhen',
                            label: 'Shenzhen',
                        },
                        {
                            value: 'Guangzhou',
                            label: 'Guangzhou',
                        },
                        {
                            value: 'Dalian',
                            label: 'Dalian',
                        },
                    ],
                },
            ],
            value: '전체 권한그룹',
            options2: [
                {
                    value: '최신순',
                    label: '최신순',
                },
                {
                    value: '시작일 순',
                    label: '시작일 순',
                },
            ],
            value2: '소구분',
            options3: [
                {
                    value: '최신순',
                    label: '최신순',
                },
                {
                    value: '시작일 순',
                    label: '시작일 순',
                },
            ],
            value3: '전체 AGENCY',
        };
    },
};
</script>
<style scoped></style>
