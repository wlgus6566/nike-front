<template>
    <div @close="thisClose">
        <template v-slot:modal-header>
            <div class="modal-header">
                <h1>권한 설정</h1>
            </div>
        </template>
        <template v-slot:modal-content>
            <GroupTreeListTable
                :groupTreeData="groupTreeData"
                :groupTreeActive="groupTreeActive"
                :groupTreeOpen="groupTreeOpen"
                :groupTreeAddItem="groupTreeAddItem"
                :checks="checks"
            />
        </template>
        <template v-slot:modal-footer>123</template>
    </div>
</template>
<script>
import GroupTreeListTable from '@/components/group-tree/tree-list-table';
import { getAuthList } from '@/api/auth';
import bus from '@/utils/bus';
export default {
    mounted() {
        this.getAuthList();

        bus.$on('selectActive', (item) => {
            this.groupTreeAddItem = null;
            if (this.groupTreeActive.authSeq === item.authSeq) {
                this.groupTreeActive = {};
            } else {
                this.groupTreeActive = item;
            }
        });

        bus.$on('groupTreeAdd', () => {
            this.groupTreeAddItem = this.groupTreeActive.authSeq;
            this.groupTreeOpen.push(this.groupTreeActive.authSeq);
            this.groupTreeActive = {};
        });
        bus.$on('groupTreeDel', () => {
            this.delAuth(this.groupTreeActive.authSeq);
        });
        bus.$on('groupTreeToggle', (authSeq) => {
            if (this.groupTreeOpen.some((el) => el === authSeq)) {
                this.groupTreeOpen = this.groupTreeOpen.filter(
                    (el) => el !== authSeq
                );
            } else {
                this.groupTreeOpen.push(authSeq);
            }
        });
    },
    data() {
        return {
            groupTreeData: [],
            groupTreeActive: {},
            groupTreeOpen: [],
            groupTreeAddItem: null,
            groupDetailData: [],
        };
    },
    props: ['checks'],
    methods: {
        thisClose() {
            this.$emit('close');
        },

        async getAuthList() {
            try {
                const {
                    data: { data: response },
                } = await getAuthList();
                this.groupTreeData = response;
                console.log('getAuthList', response);
            } catch (e) {
                console.log(e);
            }
        },
    },
    components: { GroupTreeListTable },
};
</script>
<style scoped></style>
