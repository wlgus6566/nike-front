<template>
    <div class="folder-wrap">
        <h2 class="folder-title">
            {{ folderDetail.folderName }}
        </h2>
        <div class="inner">
            <p class="folder-desc">
                {{ folderDetail.folderContents }}
            </p>
            <span class="folder-date">
                <em>{{ folderDetail.campaignBeginDt }}</em>
                -
                <em>{{ folderDetail.campaignEndDt }}</em>
            </span>
            <p class="folder-name">{{ folderDetail.userId }}</p>
            <p class="folder-memo">{{ folderDetail.memo }}</p>
        </div>

        <div class="noti-box">
            <ul class="noti-item-list">
                <li class="noti-item">
                    KEEP IT TIGHT! 본 자료는 NIKE.INC.와 NIKE KOREA LLC.의
                    자산입니다. 보안 규정을 준수하시기 바랍니다.
                </li>
                <li class="noti-item">
                    자료의 조회와 다운로드 이력이 추적 / 관리됩니다. 자료의
                    불법적인 유출은 관련 법에 의거 처벌될 수 있습니다.
                </li>
                <li class="noti-item">
                    이용 약관은 페이지 하단에서 확인 할 수 있습니다.
                </li>
            </ul>
        </div>
    </div>
</template>
<script>
export default {
    name: 'folder',
    props: ['folderDetail'],
    created() {},
};
</script>
<style scoped></style>
