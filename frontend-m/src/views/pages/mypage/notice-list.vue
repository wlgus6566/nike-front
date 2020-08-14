<template>
    <div>
        <div class="sorting-area">
            <p class="total">전체 <strong>(20)</strong></p>
            <div class="search-input"> <!-- active 추가하면 검색 화면 보임 -->
                <div class="input-box">
                    <input type="text" placeholder="검색어를 입력해주세요." />
                    <button type="button" class="search"><span>검색</span></button>
                </div>
                <button type="button" class="btn-txt"><span>취소</span></button>
            </div>
        </div>
        <ul class="notice-list">
            <li>
                <a href="">
                    <span class="label-noti">중요</span>
                    <span class="title">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</span>
                    <span class="data">2020.06.17</span>
                </a>
            </li>
            <li>
                <a href="">
                    <span class="title">NIKE 2020 PSKO 일정이 업데이트 되었습니다.</span>
                    <span class="data">2020.06.17</span>
                </a>
            </li>
        </ul>
    </div>
</template>
<script>
import getCustomerList from '@/api/customer';

export default {
    name: 'notice-list',
    data() {
        return {
            noticeList: {}
        }
    },
    mounted() {
        this.getNoticeList();
    },
    methods: {
        async getNoticeList() {
            console.log("this is getNoticeList");
            try {
                const {
                    data: { data: response },
                } = await getCustomerList("NOTICE", {});
                this.noticeList = response;
                console.log(this.noticeList);
            } catch (error) {
                console.log(error);
            }
        }
    }
};
</script>
<style scoped></style>
