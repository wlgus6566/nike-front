<template>
    <div>
        <div class="tbl-list">
            <table>
                <colgroup>
                    <col style="width: 90px;" />
                    <col style="width: auto;" />
                    <col style="width: 110px;" />
                </colgroup>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>등록일</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="noti">
                        <td>
                            <span class="label-noti">중요</span>
                        </td>
                        <td class="agl">
                            <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                        </td>
                        <td><span class="date">2020.06.17</span></td>
                    </tr>
                    <tr class="noti">
                        <td>
                            <span class="label-noti">중요</span>
                        </td>
                        <td class="agl">
                            <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                        </td>
                        <td><span class="date">2020.06.17</span></td>
                    </tr>
                    <tr class="noti">
                        <td>
                            <span class="label-noti">중요</span>
                        </td>
                        <td class="agl">
                            <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                        </td>
                        <td><span class="date">2020.06.17</span></td>
                    </tr>
                    <tr>
                        <td>
                            <span class="num">18</span>
                        </td>
                        <td class="agl">
                            <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                        </td>
                        <td><span class="date">2020.06.17</span></td>
                    </tr>
                    <tr>
                        <td>
                            <span class="num">18</span>
                        </td>
                        <td class="agl">
                            <a href="#">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</a>
                        </td>
                        <td><span class="date">2020.06.17</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
<script>
export default {
    name: 'noticeList',
    data() {
        return {
            items: [
                {
                    title:
                        '111글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던서울’ 오픈 포토리캡',
                    img: require('@/assets/images/svg/img-asset-none.svg'),
                    url: 'https://www.nike.co.kr',
                    esc: '2020.06.17',
                },
                {
                    title:
                        '222글로벌 스포츠 브랜드 나이키, 브랜드 문화 공간 마이클 조던을 담은 ‘조던서울’ 오픈 포토리캡',
                    img: require('@/assets/images/svg/img-asset-none.svg'),
                    url: 'https://www.nike.co.kr',
                    esc: '2020.06.18',
                },
            ],
        };
    },
    methods: {},
};
</script>
<style scoped></style>
