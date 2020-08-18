<template>
    <section class="modal-list" :class="{ active: showList }">
        <div class="dimmed" @click="$emit('closeModal')"></div>
        <div class="modal-list-contents">
            <div class="select-list-wrap">
                <ListItem
                    :cascaderList="cascaderList"
                    :options="cascaderList.options"
                />
            </div>
        </div>
    </section>
</template>
<script>
import ListItem from '@/components/cascader-select/list-item';
export default {
    name: 'list-modal',
    data() {
        return {
            selectedIndex: null,
        };
    },
    components: {
        ListItem,
    },
    props: ['cascaderList', 'showList'],
    mounted() {
        this.modal();
    },
    methods: {
        modal() {
            const app = document.querySelector('#app');
            const modal = document.querySelector('.modal-list');
            app.appendChild(modal);
        },
    },
};
</script>
<style scoped>
.modal-list {
    z-index: 2;
    position: fixed;
    bottom: -100%;
    right: 0;
    width: 100vw;
    height: 100vh;
}
.active .dimmed {
    transition: all 600ms ease;
}
.modal-list.active {
    bottom: 0;
}
.active .dimmed {
    position: absolute;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.6);
}
.modal-list-contents {
    position: absolute;
    bottom: -100%;
    left: 0;
    width: 100%;
    background: #fff;
    transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
}
.modal-list.active .modal-list-contents {
    bottom: 0;
}
.modal-list-contents {
    padding: 20px 0;
}
.select-list-wrap {
    max-height: 360px;
    overflow: auto;
}
</style>
