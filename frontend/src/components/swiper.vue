<template>
    <swiper :options="swiperOption">
        <swiper-slide v-for="(slide, index) in swiperSlides" :key="index"
            >I'm Slide {{ slide }}</swiper-slide
        >
        <div class="swiper-pagination" slot="pagination"></div>
    </swiper>
</template>

<script>
import { Swiper, SwiperSlide, directive } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'

export default {
    components: {
        Swiper,
        SwiperSlide,
    },
    directives: {
        swiper: directive,
    },
    data() {
        return {
            swiperOption: {
                pagination: {
                    el: '.swiper-pagination',
                },
            },
            swiperSlides: [1, 2, 3, 4, 5],
        }
    },
}
</script>
