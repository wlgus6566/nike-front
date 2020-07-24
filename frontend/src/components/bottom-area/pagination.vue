<template>
    <div>
        <slot name="btn-bottom-area"> </slot>
        <div class="pagination">
            <button type="button" class="prev"><span>이전</span></button>
            <ul>
                <li><em>1</em></li>
                <li>
                    <button type="button"><span>2</span></button>
                </li>
                <li>
                    <button type="button"><span>3</span></button>
                </li>
                <li>
                    <button type="button"><span>4</span></button>
                </li>
                <li>
                    <button type="button"><span>5</span></button>
                </li>
                <li>
                    <button type="button"><span>6</span></button>
                </li>
                <li>
                    <button type="button"><span>7</span></button>
                </li>
                <li>
                    <button type="button"><span>8</span></button>
                </li>
                <li>
                    <button type="button"><span>9</span></button>
                </li>
                <li>
                    <button type="button"><span>10</span></button>
                </li>
            </ul>
            <button type="button" class="next"><span>다음</span></button>
        </div>
    </div>
</template>
<script>
export default {
    name: 'pagination',
    data() {
        return {};
    },
    methods: {},
};
</script>
<style scoped></style>
