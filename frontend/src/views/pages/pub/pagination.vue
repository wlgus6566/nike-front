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
                <strong>mobile</strong>
                <el-pagination layout="prev, pager, next" :total="1000" :pager-count="5">
                </el-pagination>
                <strong>PC</strong>
                <el-pagination layout="prev, pager, next" :total="1000" :pager-count="10">
                </el-pagination>
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
