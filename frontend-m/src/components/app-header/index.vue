<template>
    <header
        :class="{
            'page-header': tabMenuData !== null,
            'header-detail': $route.meta.detail,
        }"
    >
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
                <h1
                    class="page-title"
                    v-if="!$route.meta.detail"
                    v-html="title"
                />
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
    import {deleteReport} from '@/api/report';

    export default {
    name: 'headerIndex',
    data() {
        return {
            title:'',
            tabMenuData: null,
        };
    },
    computed: {
        pathUrl() {
            return this.$route.path;
        },
    },
    watch: {
        tabMenuData(){
          const titleValue = this.$route.path.split('/')[1]
          if(titleValue === 'information'){
            this.title =  'INFO.'
          }else{
            this.title = this.tabMenuData.menuName
          }
        },
        pathUrl() {
            this.tabMenuFn();
        },
        '$store.state.menuData'(val) {
            console.log(val);
            this.tabMenuFn();
        },
    },
    components: {
        NavItem,
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    created() {
        this.tabMenuFn();
        window.addEventListener('scroll', this.handleScroll);
    },
    activated() {
        window.addEventListener('scroll', this.handleScroll);
    },
    deactivated() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    mounted() {},
    methods: {
        handleScroll() {
            const body = document.querySelector('body');
            const windowE = document.documentElement;
            if (windowE.scrollTop === 0) {
                body.classList.remove('sticky-header');
            } else {
                body.classList.add('sticky-header');
            }
        },
        async delFn() {
            if (this.$route.meta.topCode === 'report') {
                if (confirm('삭제 시 등록한 내용이 전부 삭제 됩니다. 삭제하시겠습니까?')) {
                    try {
                        const response = await deleteReport(this.$route.params.id);
                        if(response.data.success) {
                            await this.$router.push(`/report/management`);
                        }
                    } catch (error) {
                        console.log(error);
                    }
                }
            }
        },
        modiFn() {
            this.$router.push(`/report/modify/${this.$route.params.id}`);
        },
        tabMenuFn() {
            if (!this.$store.state.menuData) return;
            const titleValue = this.$route.path.split('/')[1];
            this.tabMenuData = this.$store.state.menuData.filter(el => {
                if (titleValue.toUpperCase() === el.menuCode) {
                    return el.menuPathUrl === '/' + titleValue;
                } else {
                    return null;
                }
            });
            console.log(titleValue);
            console.log(this.$store.state.menuData);
            console.log('tabMenuData', this.tabMenuData);
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
<style scoped>
.page-title .ko {
    display: none;
}
</style>
