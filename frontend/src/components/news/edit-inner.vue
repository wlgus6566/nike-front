<template>
    <div>
        <ul class="form-list" v-show="$route.meta.title == '공지사항'">
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title">상단 고정 여부</span>
                </div>
                <div class="form-column">
                    <label class="check-label">
                        <span class="
                            <input type="radio" name="show" checked />
                            <span></span>
                        </span>
                        <span>고정</span>
                    </label>
                    <label class="check-label">
                        <span class="
                            <input type="radio" name="show" />
                            <span></span>
                        </span>
                        <span>비고정</span>
                    </label>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">제목</label>
                </div>
                <div class="form-column">
                    <input type="text" />
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">내용</label>
                </div>
                <div class="form-column">
                    <span class="textarea">
                        <textarea cols="100" rows="2" style="height: 300px;"></textarea>
                    </span>
                </div>
            </li>
        </ul>
        <ul class="form-list" v-show="$route.meta.title == 'NEWS'">
            <li class="form-row">
                <div class="form-column">
                    <span class="label-title required">썸네일 이미지</span>
                </div>
                <div class="form-column">
                    <span class="thumb-file new-upload">
                        <input type="file" />
                        <span class="thumb"> </span>
                        <span class="txt">
                            NEWS 이미지 등록
                            <span class="sub">
                                권장 사이즈는 최소 660*330,<br />
                                최대 3000*1500입니다.
                            </span>
                        </span>
                    </span>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">제목</label>
                </div>
                <div class="form-column">
                    <span class="textarea">
                        <textarea cols="100" rows="2" style="height: 60px;"></textarea>
                    </span>
                </div>
            </li>
            <li class="form-row">
                <div class="form-column">
                    <label class="label-title required">내용</label>
                </div>
                <div class="form-column">
                    <span class="textarea">
                        <textarea cols="100" rows="2" style="height: 300px;"></textarea>
                    </span>
                </div>
            </li>
        </ul>
    </div>
</template>
<script>
export default {
    name: 'Edit',
    data() {
        return {};
    },
    methods: {},
};
</script>
<style scoped></style>
