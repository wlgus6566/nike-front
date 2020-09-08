<template>
    <ul class="nav-tab" v-if="tabMenuData">
        <li
            v-for="menu in tabMenuData"
            :key="menu.menuSeq"
            :class="tabItemClass(menu.menuPathUrl)"
        >
            <router-link :to="menu.menuPathUrl">
                <span v-html="menuTitle(menu.menuName)"></span>
            </router-link>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'nav-item',
    props: ['tabMenuData'],
    created() {},
    computed: {},
    methods: {
        menuTitle(title){
          if(title === 'REPORT UPLOAD'){
            console.log(title)
            return "업로드"
          } else if(title === 'REPORT <span>관리</span>'){
            return "관리"
          } else if(title === 'AGENCY CONTACT'){
            return "AGENCY"
          }
          else{
            return title
          }
        },
        tabItemClass(val) {
            const titleValue = this.$route.path;
            if (titleValue === val) {
                console.log(1);
            }
            return {
                active: titleValue === val,
            };
        },
    },
};
</script>
<style scoped></style>
