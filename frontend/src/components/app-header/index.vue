<template>
    <div class="inner">
        <h1>
            <router-link exact to="/">
                <img alt="" src="@/assets/images/logo-nike.svg" />
            </router-link>
        </h1>
        <nav>
            <ul>
                <navItem class="depth1" v-for="(item, index) in lnb" :key="index" :item="item" />
            </ul>
        </nav>
    </div>
</template>
<script>
//import Sticky from 'vue-sticky-directive';
import navItem from './nav-item';

export default {
    name: 'appHeader',
    data() {
        return {
            lnb: [
                {
                    title: 'HOME',
                    titleKo: '메인',
                    to: '/',
                    exact: true,
                },
                {
                    title: 'ASSET',
                    titleKo: '메인',
                    to: '/asset',
                    exact: false,
                    children: [
                        {
                            title: 'ALL',
                            to: '/asset',
                            exact: false,
                        },
                        {
                            title: 'SP',
                            to: '/asset/sp',
                            exact: false,
                            children: [
                                {
                                    title: '2ALL',
                                    to: '/asset/sp/test1',
                                    exact: false,
                                },
                                {
                                    title: '2SP',
                                    to: '/asset/sp/test2',
                                    exact: false,
                                },
                                {
                                    title: '2SU',
                                    to: '/asset/sp/test3',
                                    exact: false,
                                },
                                {
                                    title: '2FA',
                                    to: '/asset/sp/test4',
                                    exact: false,
                                },
                                {
                                    title: '2HO',
                                    to: '/asset/sp/test5',
                                    exact: false,
                                },
                            ],
                        },
                        {
                            title: 'SU',
                            to: '/asset/su',
                            exact: false,
                        },
                        {
                            title: 'FA',
                            to: '/asset/fa',
                            exact: false,
                        },
                        {
                            title: 'HO',
                            to: '/asset/ho',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'TOOLKIT',
                    titleKo: '메인',
                    to: '/toolkit',
                    exact: true,
                    children: [
                        {
                            title: 'TOOLKIT1-1',
                            to: '/toolkit/all',
                            exact: false,
                            children: [
                                {
                                    title: 'TOOLKIT1-1-1',
                                    to: '/toolkit/test',
                                    exact: false,
                                },
                            ],
                        },
                    ],
                },
                {
                    title: 'FOUNDATION',
                    titleKo: '메인',
                    to: '/foundation',
                    exact: false,
                    children: [
                        {
                            title: 'MAIN',
                            to: '/foundation',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'ORDER',
                    titleKo: '메인',
                    to: '/order',
                    exact: false,
                    children: [
                        {
                            title: 'MAIN',
                            to: '/order',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'REPORT',
                    titleKo: '메인',
                    to: '/report',
                    exact: false,
                    children: [
                        {
                            title: 'MAIN',
                            to: '/report',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'INFORMATION',
                    titleKo: '메인',
                    to: '/information',
                    exact: false,
                    children: [
                        {
                            title: 'MAIN',
                            to: '/information',
                            exact: false,
                        },
                    ],
                },
                {
                    title: 'MANAGEMENT',
                    titleKo: '메인',
                    to: '/management',
                    exact: false,
                    children: [
                        {
                            title: 'MAIN',
                            to: '/management',
                            exact: false,
                        },
                    ],
                },
            ],
        };
    },
    directives: {
        // Sticky,
    },
    computed: {
        isFolder: function () {
            return this.item.children && this.item.children.length;
        },
    },
    components: { navItem },
    methods: {},
};
</script>
<style scoped></style>
