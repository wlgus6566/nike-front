<template>
    <div>
        <ul>
            <navItem
                v-for="(item, index) in lnb"
                :key="index"
                :navIdx="index"
                :item="item"
                :depth="1"
                :activeIndex="activeIndex"
            />
        </ul>
    </div>
</template>
<script>
import navItem from './nav-item';

export default {
    name: 'nav-wrap',
    data() {
        return {
            lnb: null,
            activeIndex: null,
        };
    },
    watch: {
        '$store.state.gnbMenuListData'() {
            const menu = this.$store.state.gnbMenuListData;
            this.lnb = menu.filter((item) => {
                if (item.menuCode !== 'MYPAGE' && item.pcYn === 'Y')
                    return item;
            });
        },
    },
    components: { navItem },
};
</script>
<style scoped></style>
