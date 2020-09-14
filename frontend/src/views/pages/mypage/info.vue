<template>
    <div>
        <h2 class="page-title">
            <span class="ko">회원정보 조회</span>
        </h2>
        <div class="member-inquiry">
            <div class="inner">
                <span class="desc">HELLO!</span>
                <strong class="name">
                    <em>
                        {{ userData.nickname }}
                    </em>
                    님
                </strong>
                <dl class="info-list">
                    <dt>권한 그룹명</dt>
                    <dd>{{ userData.authName }}</dd>
                    <dt>ID(E-MAIL)</dt>
                    <dd>{{ userData.userId }}</dd>
                    <dt>최종접속일시</dt>
                    <dd>
                        <ul class="data-list">
                            <li
                                v-for="(loginLog, index) in loginLogs"
                                :key="index"
                            >
                                {{ loginLog.loginDt }}
                            </li>
                        </ul>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
</template>

<script>
import { getMyInfo } from '@/api/mypage';

export default {
    data() {
        return {
            userData: {
                authName: '',
                loginLogs: '',
                nickname: '',
                userId: '',
            },
            lognLength: 5,
            num: 0,
        };
    },
    created() {
        this.fetchData();
    },
    computed: {
        pageCount() {
            let logLeng = this.userData.loginLogs.length,
                logSize = this.lognLength,
                log = Math.floor(logLeng / logSize);
            if (logLeng % logSize > 0) log += 1;

            return log;
        },
        loginLogs() {
            const start = this.num * this.lognLength,
                end = start + this.lognLength;
            return this.userData.loginLogs.slice(start, end);
        },
    },
    methods: {
        async fetchData() {
            try {
                const {
                    data: { data: response },
                } = await getMyInfo({});
                //console.log(response);
                this.userData = response;
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>

<style scoped></style>
