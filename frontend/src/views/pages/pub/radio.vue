<template>
    <div id="wrap" class="main">
        <header>
            <div class="inner">
                <h1>
                    <a href="#"><img alt="" src="@/assets/images/logo-nike.svg" /></a>
                </h1>
                <nav>
                    <ul>
                        <li>
                            <a href="#">TEST</a>
                        </li>
                    </ul>
                    <div class="bg"></div>
                </nav>
            </div>
        </header>
        <section id="container">
            <div class="contents">
                <el-radio v-model="radio" label="1" class="type2">라디오1</el-radio>
                <el-radio v-model="radio" label="2" class="type2">라디오2</el-radio>
                <el-radio v-model="radio" label="3" class="type2">라디오3</el-radio>
                <el-radio v-model="radio" label="3" class="type2" disabled
                    >라디오 disabled</el-radio
                >
            </div>
            <aside>
                <div class="aside-wrap">
                    aside
                </div>
            </aside>
        </section>
        <footer></footer>
    </div>
</template>
<script>
export default {
    name: 'radio',
    data() {
        return {
            radio: '1',
        };
    },
};
</script>
<style scoped></style>
