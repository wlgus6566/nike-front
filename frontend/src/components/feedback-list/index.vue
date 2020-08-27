<template>
    <ul class="feedback-list">
        <li
            class="feedback-item"
            v-for="item in answerList"
            :key="item.answerSeq"
        >
            <p class="txt">
                {{ item.answerContents }}
            </p>
            <div class="info">
                <span class="name">
                    {{ item.nickname }}
                </span>
                <span class="date">
                    {{ $moment(item.registrationDt).format('YYYY.MM.DD') }}
                </span>
            </div>
            <button
                type="button"
                class="del"
                v-if="userId === item.userId"
                @click="$emit('reportAnswerDelete', item.answerSeq)"
            >
                <span>삭제</span>
            </button>
        </li>
    </ul>
</template>
<script>
export default {
    name: 'feedback-list',
    props: ['answerList', 'userId'],
};
</script>
<style scoped></style>
