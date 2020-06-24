<template>
    <div>
        <h2 class="page-title">SP</h2>
        <ListSorting v-bind:listTypes="listTypes"></ListSorting>
        <Item v-bind:listTypes="listTypes"></Item>
    </div>
</template>
<script>
    import ListSorting from '../../components/asset-list/ListSorting.vue'
    import Item from '../../components/asset-list/Item.vue'
    export default {
        name: "SPList.vue",
        data(){
            return {
                listTypes: [
                    {
                        title: "컬럼타입",
                        active: true
                    },
                    {
                        title: "로우타입",
                        active: false
                    },
                ],
                items :[

                ],
            }
        },
        components : {
            ListSorting,
            Item
        }
    }
</script>
<style scoped></style>
