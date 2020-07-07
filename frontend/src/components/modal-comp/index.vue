<template>
    <transition name="modal">
        <div class="modal-wrap modal-overlay" @click.self="this.closeModal" v-if="modal">
            <section class="modal">
                <div class="modal-header">
                    <slot name="header" />
                </div>
                <div class="modal-content">
                    <slot name="content" />
                </div>
                <div class="modal-footer">
                    <slot name="footer" />
                </div>
                <button class="modal-close" @click="this.closeModal">Close</button>
            </section>
        </div>
    </transition>
</template>
<script>
export default {
    name: 'modal-comp',
    data() {
        return {
            modal: true,
        };
    },
    methods: {
        openModal() {
            this.modal = true;
        },
        closeModal() {
            this.modal = false;
        },
    },
};
</script>
<style scoped>
.modal-wrap.modal-overlay {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    z-index: 30;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
}
.modal {
    position: relative;
    background: #fff;
    border-radius: 4px;
    overflow: hidden;
}
.modal-content {
    padding: 50px;
}
.modal-footer {
}
.modal-close {
    position: absolute;
    top: 20px;
    right: 20px;
    height: 30px;
    width: 30px;
    text-indent: -9999px;
    background: url('../../assets/images/svg/icon-form-close.svg');
}

.modal-enter-active,
.modal-leave-active {
    transition: opacity 0.4s;
}
.modal-enter-active .modal-window,
.modal-leave-active .modal-window {
    transition: opacity 0.4s, transform 0.4s;
}
.modal-leave-active {
    transition: opacity 0.6s ease 0.4s;
}
.modal-enter,
.modal-leave-to {
    opacity: 0;
}
.modal-enter .modal-window,
.modal-leave-to .modal-window {
    opacity: 0;
    transform: translateY(-20px);
}
</style>
