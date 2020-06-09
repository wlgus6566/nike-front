<template>
    <div id="login">
        <section>
            <h1 class="logo">
                <a href="javascript:void(0)">
                    <svg xmlns="http://www.w3.org/2000/svg" width="250" height="90" viewBox="0 0 100 36">
                        <defs>
                            <filter id="prefix__a" width="100%" height="100%" x="0%" y="0%" filterUnits="objectBoundingBox">
                                <feGaussianBlur in="SourceGraphic"/>
                            </filter>
                        </defs>
                        <path fill-rule="evenodd" d="M11.833 34.986c-2.926-.117-5.32-.917-7.192-2.404-.357-.284-1.209-1.136-1.494-1.495-.76-.955-1.275-1.884-1.62-2.914C.468 25 1.013 20.837 3.087 16.268 4.862 12.356 7.6 8.476 12.38 3.103 13.084 2.313 15.18 0 15.194 0c.005 0-.11.198-.253.439-1.243 2.081-2.306 4.533-2.885 6.655-.93 3.406-.818 6.328.328 8.595.792 1.561 2.148 2.913 3.673 3.66 2.67 1.31 6.58 1.417 11.353.318.33-.077 16.616-4.4 36.194-9.608C83.182 4.849 99.204.59 99.206.595c.005.004-45.485 19.47-69.1 29.569-3.74 1.598-4.74 2.002-6.498 2.62-4.495 1.578-8.52 2.331-11.775 2.202" filter="url(#prefix__a)"/>
                    </svg>
                </a>
            </h1>
            <div class="login-form">
                <form action="" method="">
                    <fieldset>
                        <legend>NIKE ND&P SERVICE</legend>
                        <!--<h2 class="title">NIKE ND&P<br>SERVICE</h2>-->
                         <h2 class="title">NIKE KOREA<span>는</span><br>PARTNER<span>와 함께합니다.</span></h2>
                        <div class="radio-group">
                            <label for="nd" :class="{on:loginType=='nd'}"><span class="radio"><input v-model="loginType" value="nd" type="radio" id="nd" name="login" ></span>ND</label>
                            <label for="partner" :class="{on:loginType=='partner'}"><span class="radio"><input v-model="loginType" value="partner" type="radio" id="partner" name="login" ></span>PARTNER</label>
                            <label for="agency" :class="{on:loginType=='agency'}"><span class="radio"><input v-model="loginType" value="agency" type="radio" id="agency" name="login"></span>AGENCY</label>
                        </div>
                        <div class="input-group">
                            <label for="id"></label><input type="text" id="id" value="" placeholder="ID">
                            <label for="pw"></label><input type="password" id="pw" value="" placeholder="PW">
                        </div>
                        <button type="button" class="btn-login">LOGIN</button>
                    </fieldset>
                </form>
            </div>
             <p class="text">계정관련/요청이나 문의는<strong> RDD, RDM</strong>에게 문의 바랍니다.</p>
        </section>
    </div>
</template>

<script>
    export default {
        name: "login",
        data: function () {
            return {
                loginType : "nd"
            }
        }
    }
</script>

<style scoped>
    #login {position:fixed; width:100%; height:100%; font-family:'Noto Sans KR',sans-serif; background-color:#f0f0f0; }
    .logo a {display:block; text-align:center;}
    section {position:absolute; left:50%; top:50%; transform:translate(-50%,-50%);}
    section .text {margin-top:23px; font-size:14px; text-align:center; color:#888; letter-spacing:-0.5px;}
    section .text strong {color:#000;}

    .login-form {margin-top:60px; padding:50px 45px 45px; background-color:#fff; box-shadow:10px 10px 10px 0 rgba(0, 0, 0, 0.1); text-align:left;}
    .login-form .title {margin-bottom:30px; font-weight:400; font-size:40px; font-family:'Bebas Neue'; line-height:1; letter-spacing:0.56px;}
    .login-form .title span {font-weight:400; font-size:24px; letter-spacing:-0.5px;}
    .login-form .title + .radio-group{margin-top:-10px;}

    .radio-group {display:flex; justify-content:space-between; align-items:center; padding-bottom:20px; font-size:15px;}
    .radio-group label {display:flex; align-items:center; color:#888;}
    .radio-group label .radio{display:inline-block; position:relative; width:20px; height:20px; margin-right:8px; border:1px solid #ccc; border-radius:50%; vertical-align:middle; box-sizing:border-box;}
    .radio-group label .radio input {position:absolute; left:0; top:0; width:100%; height:100%; opacity:0;}
    .radio-group label.on {font-weight:700; color:#000; letter-spacing:-1px;}
    .radio-group label.on .radio {border:1px solid#000; background:#fff;}
    .radio-group label.on .radio:after {content:""; display:block; position:absolute; left:50%; top:50%; transform:translate(-50%, -50%); width:10px; height:10px; border-radius:50%; background-color:#000;}

    .input-group {width:100%;}
    .input-group input {display:block; width:100%; padding:10px 15px; border:0; font-size:15px; background-color:#eee;}
    .input-group input ~ input {margin-top:7px;}
    .input-group input::-webkit-input-placeholder {color:#888; font-family:'Bebas Neue';}
    .input-group input:-ms-input-placeholder {color:#888; font-family:'Bebas Neue';}

    .btn-login {width:280px; height:45px; line-height:45px; margin-top:20px; border:0; background-color:#000; font-size:20px; font-family:'Bebas Neue'; color:#fff; letter-spacing:0.5px}


</style>