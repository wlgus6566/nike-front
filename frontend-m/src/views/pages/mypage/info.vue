<template>
    <div>
        <div class="member-inquiry">
            <div class="inner" v-if="userData">
                <span class="desc">HELLO!</span>
                <strong class="name"
                    ><em>{{ userData.nickname }}</em
                    >님</strong
                >
                <dl class="info-list">
                    <dt>권한 그룹명</dt>
                    <dd>{{ userData.authName }}</dd>
                    <dt>ID(E-MAIL)</dt>
                    <dd>{{ userData.userId }}</dd>
                    <dt>최종접속일시</dt>
                    <dd>
                        {{ loginLogs[0].loginDt }}
                        <ul class="data-list" v-if="loginLogs">
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
import { getMyInfo } from '@/api/my-page';

export default {
    data() {
        return {
            userData: null,
            logLength: 5,
            num: 0,
        };
    },
    created() {
        this.fetchData();
    },
    mounted() {
        this.pageAddClass();
    },
    beforeRouteLeave(to, from, next) {
        this.pageRemoveClass();
        next();
    },
    computed: {
        pageCount() {
            let logLength = this.userData.loginLogs.length,
                logSize = this.logLength,
                log = Math.floor(logLength / logSize);
            if (logLength % logSize > 0) log += 1;

            return log;
        },
        loginLogs() {
            if (this.userData) {
                const start = this.num * this.logLength,
                    end = start + this.logLength;
                return this.userData.loginLogs.slice(start, end);
            }
        },
    },
    methods: {
        async fetchData() {
            try {
                const {
                    data: { data: response },
                } = await getMyInfo({});
                this.userData = response;
            } catch (error) {
                console.log(error);
            }
        },
        pageAddClass() {
            const contents = document.querySelector('.contents');
            contents.classList.add('my-info');
        },
        pageRemoveClass() {
            const contents = document.querySelector('.contents');
            contents.classList.remove('my-info');
        },
    },
};
</script>
<style scoped></style>
