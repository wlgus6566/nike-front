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
                <!-- todo// input 삭제 작업필요 -->
                <h2>disabled</h2>
                <input type="text" placeholder="텍스트를 입력해 주세요" disabled />
                <br /><br />

                <h2>기본</h2>
                <span class="input-box">
                    <input type="text" placeholder="텍스트를 입력해 주세요" />
                    <button type="button" class="del"><span>삭제</span></button>
                    <span class="state">오류 메세지 텍스트</span>
                </span>
                <br /><br />

                <h2>focus</h2>
                <span class="input-box focus">
                    <input type="text" placeholder="텍스트를 입력해 주세요" value="남남" />
                    <button type="button" class="del"><span>삭제</span></button>
                    <span class="state">오류 메세지 텍스트</span>
                </span>
                <br />
                <br />

                <h2>입력완료</h2>
                <span class="input-box">
                    <input type="text" placeholder="텍스트를 입력해 주세요" value="남남" />
                    <button type="button" class="del"><span>삭제</span></button>
                    <span class="state">오류 메세지 텍스트</span>
                </span>
                <br />
                <br />

                <h2>error</h2>
                <span class="input-box error">
                    <input type="text" placeholder="텍스트를 입력해 주세요" />
                    <button type="button" class="del"><span>삭제</span></button>
                    <span class="state">오류 메세지 텍스트</span>
                </span>
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
    name: 'input',
};
</script>
<style scoped></style>
