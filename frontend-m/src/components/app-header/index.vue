<template>
    <header :class="{ 'page-header': tabMenuData !== null }">
        <h1 class="logo" v-if="this.$route.path === '/'">
            <a href="/"><span>나이키</span></a>
        </h1>
        <template v-else>
            <button
                type="button"
                class="btn-back"
                v-if="$route.meta.historyBack !== null"
                @click="$router.go(-1)"
            >
                뒤로가기
            </button>
            <div class="inner" v-if="tabMenuData !== null">
                <h1 class="page-title" v-if="!$route.meta.detail">
                    {{ tabMenuData.menuName }}
                </h1>
                <div class="btn-box" v-if="$route.meta.btn">
                    <button type="button" class="btn-txt" @click="delFn">
                        삭제
                    </button>
                    <button type="button" class="btn-txt" @click="modiFn">
                        수정
                    </button>
                </div>
                <NavItem
                    v-if="!$route.meta.detail"
                    :tabMenuData="tabMenuData.menus"
                />
            </div>
        </template>
    </header>
</template>
<script>
import NavItem from '@/components/app-header/nav-item';
import { deleteReport } from '@/api/report';

export default {
    name: 'headerIndex',
    data() {
        return {
            tabMenuData: null,
        };
    },
    created() {
        this.tabMenuFn();
    },
    computed: {
        pathUrl() {
            return this.$route.path;
        },
    },
    watch: {
        pathUrl() {
            this.tabMenuFn();
        },
    },
    components: {
        NavItem,
    },
    methods: {
        async delFn() {
            console.log(this.$route.meta.topCode);
            console.log(this.$route.params.id);
            if (this.$route.meta.topCode === 'report') {
                if (confirm('REPORT를 삭제 하시겠습니까?')) {
                    try {
                        const {
                            data: { data: response },
                        } = await deleteReport(this.$route.params.id);
                    } catch (error) {
                        console.log(error);
                    }
                }
            }
        },
        modiFn() {},
        async tabMenuFn() {
            const titleValue = this.$route.path.split('/')[1];
            this.tabMenuData = this.$store.state.menuData.filter(el => {
                if (titleValue.toUpperCase() === el.menuName) {
                    return el.menuPathUrl === '/' + titleValue;
                } else {
                    return null;
                }
            });
            this.tabMenuData =
                this.tabMenuData.length === 0 ? null : this.tabMenuData[0];
            if (this.$route.meta.depth) {
                const menu = this.tabMenuData.menus.filter(el => {
                    return el.menuPathUrl === this.$route.meta.depth;
                });
                this.tabMenuData = menu.length === 0 ? null : menu[0];
            }
        },
    },
};
</script>
<style scoped></style>
