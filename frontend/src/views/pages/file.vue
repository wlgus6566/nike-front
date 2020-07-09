<template>
    <div>
        <button type="button" class="btn-s-black">DOWNLOAD</button>
        <hr style="margin: 50px 0;" />
        <div class="upload-file-box">
            <div class="upload-file-list actice">
                <ul>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                            <span class="txt"
                                >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpgP20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpg</span
                            >
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <span></span>
                            </span>
                            <span class="txt">P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span>
                        </label>
                    </li>
                </ul>
            </div>
            <div class="btn-box">
                <div class="fine-file">
                    <span class="btn-form-gray"><span>찾기</span></span>
                    <input type="file" />
                </div>
                <button type="button" class="btn-form">
                    <span>삭제</span>
                </button>
            </div>
        </div>
        <hr style="margin: 50px 0;" />
        <FileUpload />
    </div>
</template>
<script>
import FileUpload from '@/components/file-upload';
export default {
    name: 'file',
    components: { FileUpload },
};
</script>
<style scoped></style>
