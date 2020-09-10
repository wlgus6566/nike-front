<template>
    <header
        :class="{
            'page-header': tabMenuData !== null,
            'header-detail': $route.meta.detail,
        }"
    >
        <h1 class="logo" v-if="this.$route.path === '/'">
            <a href="/">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="120"
                    height="40"
                    viewBox="0 0 120 40"
                >
                    <defs>
                        <filter
                            id="rjfxyf7h0a"
                            width="100%"
                            height="100%"
                            x="0%"
                            y="0%"
                            filterUnits="objectBoundingBox"
                        >
                            <feGaussianBlur in="SourceGraphic" />
                        </filter>
                    </defs>
                    <g fill-rule="evenodd">
                        <path
                            fill-rule="nonzero"
                            d="M43.949 26.306v-9.937h.04l2.625 9.937h2.247V12.694h-1.949v8.148h-.04l-2.128-8.148H42v13.612h1.949zm9.526 0V12.694h-2.188v13.612h2.188zm4.614 0v-4.337l.875-1.73 1.85 6.067H63.1l-2.645-8.614 2.605-4.998h-2.187l-2.784 5.737v-5.737H55.9v13.612h2.188zm12.807 0V24.36h-3.779v-4.18h3.003v-1.945h-3.003V14.64h3.779v-1.945H64.93v13.612h5.966zm8.87.194c1.074 0 1.892-.298 2.456-.894.563-.597.845-1.452.845-2.567 0-.856-.172-1.601-.517-2.236-.345-.635-.975-1.342-1.89-2.12-.715-.609-1.21-1.127-1.481-1.555-.272-.428-.408-.888-.408-1.38 0-.87.365-1.304 1.094-1.304.358 0 .63.107.815.321.186.214.279.554.279 1.021v.7h2.068v-.564c0-1.102-.268-1.947-.805-2.537-.537-.59-1.33-.885-2.377-.885-1.06 0-1.87.295-2.426.885-.557.59-.835 1.422-.835 2.498 0 .791.179 1.5.537 2.13.358.628.987 1.331 1.889 2.11.716.608 1.206 1.13 1.472 1.564.265.435.397.937.397 1.507 0 .48-.096.827-.288 1.04-.192.215-.474.322-.845.322-.756 0-1.134-.441-1.134-1.323V22.3h-2.068v.778c0 1.102.272 1.947.815 2.537.544.59 1.346.885 2.407.885zm7.537-.194v-5.542h1.034c1.087 0 1.903-.285 2.446-.856.544-.57.815-1.406.815-2.508v-1.342c0-1.102-.271-1.938-.815-2.508-.543-.57-1.359-.856-2.446-.856h-3.222v13.612h2.188zm1.034-7.487h-1.034v-4.18h1.034c.358 0 .626.097.805.292.18.194.269.525.269.991v1.614c0 .467-.09.797-.269.992-.179.194-.447.291-.805.291zm6.006 7.487l.398-2.664h2.486v-.04l.397 2.704h2.188l-2.267-13.612h-2.963l-2.268 13.612h2.029zm2.625-4.512h-1.969l.955-6.727h.04l.974 6.727zm7.736 4.706c1.047 0 1.846-.292 2.397-.875.55-.583.825-1.406.825-2.47v-1.924h-2.068v2.08c0 .83-.365 1.245-1.094 1.245-.73 0-1.094-.415-1.094-1.245v-7.603c0-.842.365-1.264 1.094-1.264.729 0 1.094.422 1.094 1.264v1.575h2.068v-1.439c0-1.063-.275-1.886-.825-2.469-.55-.583-1.35-.875-2.397-.875-1.047 0-1.846.292-2.396.875-.55.583-.826 1.406-.826 2.47v7.31c0 1.064.276 1.887.826 2.47.55.583 1.349.875 2.396.875zM116 26.306V24.36h-3.779v-4.18h3.003v-1.945h-3.003V14.64H116v-1.945h-5.966v13.612H116zM16.5.5L33 10v19l-16.5 9.5L0 29V10L16.5.5zm0 2.309L2.005 11.155v16.689L16.5 36.19l14.494-8.346V11.155L16.5 2.809z"
                            transform="translate(0 .5)"
                        />
                        <path
                            d="M9.206 23.997c-.596-.023-1.083-.183-1.465-.48-.072-.057-.246-.228-.304-.3-.154-.19-.26-.376-.33-.582-.215-.635-.104-1.468.318-2.381.361-.783.92-1.559 1.893-2.633.143-.159.57-.621.573-.621l-.052.088c-.253.416-.47.906-.587 1.33-.19.682-.167 1.266.066 1.72.162.312.438.582.748.732.544.262 1.34.283 2.313.063.067-.015 3.383-.88 7.37-1.921C23.738 17.97 27 17.118 27 17.119c.001 0-9.263 3.894-14.073 5.914-.761.32-.965.4-1.323.524-.915.315-1.735.466-2.398.44"
                            filter="url(#rjfxyf7h0a)"
                            transform="translate(0 .5)"
                        />
                    </g>
                </svg>
            </a>
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
                    <button
                        type="button"
                        class="btn-txt"
                        @click="delFn"
                        v-if="folderAuthCheck('DELETE')"
                    >
                        삭제
                    </button>
                    <button
                        type="button"
                        class="btn-txt"
                        @click="modiFn"
                        v-if="folderAuthCheck('CREATE')"
                    >
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
import { authCheck } from '@/utils/authCheck';
import NavItem from '@/components/app-header/nav-item';
import { deleteReport } from '@/api/report';

export default {
    name: 'headerIndex',
    mixins: [authCheck],
    data() {
        return {
            title: '',
            tabMenuData: null,
        };
    },
    computed: {
        pathUrl() {
            return this.$route.path;
        },
    },
    watch: {
        tabMenuData() {
            if (this.tabMenuData) {
                const titleValue = this.$route.path.split('/')[1];
                if (titleValue === 'information') {
                    this.title = 'INFO.';
                } else {
                    this.title = this.tabMenuData.menuName;
                }
            }
        },
        pathUrl() {
            this.tabMenuFn();
        },
        '$store.state.menuData'() {
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
                if (
                    confirm(
                        '삭제 시 등록한 내용이 전부 삭제 됩니다. 삭제하시겠습니까?'
                    )
                ) {
                    try {
                        const response = await deleteReport(
                            this.$route.params.id
                        );
                        if (response.data.success) {
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
