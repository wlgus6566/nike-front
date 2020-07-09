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
                        <el-select v-model="value1" placeholder="Select" style="width: 100px;">
                            <el-option
                                v-for="item in options1"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            >
                            </el-option>
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
                            <col style="width: 7.317%;" />
                            <col style="width: 20.731%;" />
                            <col style="width: auto;" />
                            <col style="width: 8.536%;" />
                            <col style="width: 9.756%;" />
                            <col style="width: 12.195%;" />
                            <col style="width: 9.756%;" />
                            <col style="width: 6.707%;" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>
                                    <span class="checkbox">
                                        <input type="checkbox" />
                                        <span></span>
                                    </span>
                                </th>
                                <th>구분</th>
                                <th>상품명</th>
                                <th>최소<br />주문수량</th>
                                <th>AGENCY</th>
                                <th>최종수정자</th>
                                <th>최종수정일</th>
                                <th>노출</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <span class="checkbox">
                                        <input type="checkbox" />
                                        <span></span>
                                    </span>
                                </td>
                                <td>부자재 > 운영 비품</td>
                                <td>
                                    <a href="#" class="under-link"><span>포스터 600X800</span></a>
                                </td>
                                <td>10,000</td>
                                <td>HISWILL</td>
                                <td>NIKE0001</td>
                                <td>2020.05.30</td>
                                <td>Y</td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="checkbox">
                                        <input type="checkbox" />
                                        <span></span>
                                    </span>
                                </td>
                                <td>부자재 > 운영 비품</td>
                                <td>
                                    <a href="#" class="under-link"><span>포스터 600X800</span></a>
                                </td>
                                <td>10,000</td>
                                <td>HISWILL</td>
                                <td>NIKE0001</td>
                                <td>2020.05.30</td>
                                <td>Y</td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="checkbox">
                                        <input type="checkbox" />
                                        <span></span>
                                    </span>
                                </td>
                                <td>부자재 > 운영 비품</td>
                                <td>
                                    <a href="#" class="under-link"><span>포스터 600X800</span></a>
                                </td>
                                <td>10,000</td>
                                <td>HISWILL</td>
                                <td>NIKE0001</td>
                                <td>2020.05.30</td>
                                <td>Y</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="btn-tbl-box">
                    <button type="button" class="btn-form">
                        <span data-v-1756ba49="">삭제</span>
                    </button>
                    <div class="right">
                        <button type="button" class="btn-form-gray">
                            <span data-v-1756ba49="">등록</span>
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
    name: 'NIKE_P_ORDER_06',
    data() {
        return {
            options1: [
                {
                    value: '부자재',
                    label: '부자재',
                },
                {
                    value: 'NIKE BY YOU',
                    label: 'NIKE BY YOU',
                },
                {
                    value: 'CUSTOM23(JORDAN ONLY)',
                    label: 'CUSTOM23(JORDAN ONLY)',
                },
                {
                    value: 'MNQ',
                    label: 'MNQ',
                },
            ],
            value1: '대분류',
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
