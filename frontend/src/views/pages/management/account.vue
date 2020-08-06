<template>
    <div>
        <h2 class="page-title">
            <span class="ko">{{ this.$route.meta.title }}</span>
        </h2>
        <div class="sorting-area">
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
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
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
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                        </td>
                        <td>
                            <a href="#" class="under-link"
                                ><span>포스터 600X800</span></a
                            >
                        </td>
                        <td>Da****y6jae@naver.com</td>
                        <td>NIKE DIRECT</td>
                        <td><span>정상</span></td>
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
    </div>
</template>
<script>
export default {
    name: 'account',
};
</script>
<style scoped></style>
