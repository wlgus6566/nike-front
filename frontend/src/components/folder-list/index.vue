<template>
    <ul
        v-bind:class="[
            { 'folder-list': listTypes[0].active },
            { 'folder-list-row': listTypes[1].active },
        ]"
    >
        <li class="folder-list-item" v-for="(item, index) in items" :key="index">
            <a href="#">
                <div class="thumbnail">
                    <img src="item.img" alt="" />
                </div>
                <div class="info-box">
                    <strong class="title">{{ item.title }}</strong>
                    <p class="txt">{{ item.desc }}</p>
                    <p class="date">{{ item.date }}</p>
                </div>
                <div class="view-area">
                    <span class="view">10,000</span>
                </div>
            </a>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'Item.vue',
    props: ['listTypes', 'items'],
};
</script>
<style scoped></style>
