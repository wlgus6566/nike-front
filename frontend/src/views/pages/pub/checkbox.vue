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
                <el-checkbox v-model="checkbox">checkbox1</el-checkbox>
                <el-checkbox v-model="checkbox">checkbox2</el-checkbox>
                <el-checkbox v-model="checkbox">checkbox3</el-checkbox>
                <el-checkbox v-model="checkbox" disabled>checkbox disabled</el-checkbox>
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
    name: 'checkbox',
    data() {
        return {
            checked: true,
        };
    },
};
</script>
<style scoped></style>
