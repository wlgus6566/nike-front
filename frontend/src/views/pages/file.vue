<template>
    <div>
        <button type="button" class="btn-s-black" @click="getFileDownload">
            DOWNLOAD
        </button>
        <hr style="margin: 50px 0;" />
        <div class="upload-file-box">
            <div class="upload-file-list actice">
                <ul>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <i></i>
                                <span class="txt"
                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpgP20_Nsw_Nike_Gallery_graphic_1_700x1000.jpgP20_N.jpg</span
                                >
                            </span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <i></i>
                                <span class="txt"
                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                >
                            </span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <i></i>
                                <span class="txt"
                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                >
                            </span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <i></i>
                                <span class="txt"
                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                >
                            </span>
                        </label>
                    </li>
                    <li>
                        <label>
                            <span class="checkbox">
                                <input type="checkbox" />
                                <i></i>
                                <span class="txt"
                                    >P20_Nsw_Nike_Gallery_graphic_1_700x1000.jpg</span
                                >
                            </span>
                        </label>
                    </li>
                </ul>
            </div>
            <div class="btn-box">
                <div class="fine-file">
                    <span class="btn-form-gray">
                        <span>찾기</span>
                    </span>
                    <input type="file" />
                </div>
                <button type="button" class="btn-form">
                    <span>삭제</span>
                </button>
            </div>
        </div>
        <hr style="margin: 50px 0;" />
        <!--<FileUpload />-->
    </div>
</template>
<script>
//import FileUpload from '@/components/file-upload';
import axios from 'axios';

export default {
    name: 'file',
    //components: { FileUpload },
    data() {
        return {
            key: 0,
            info: {
                authSeq: 0,
                managerId: '',
                managerName: '',
                password: '',
            },
        };
    },
    methods: {
        getData() {
            let vm = this;
            axios.detail(vm.key).then((response) => {
                /*console.log('=======param start=====');
                console.log('사용하는 데이터부분');
                console.log(response.data.data);*/
                vm.info = response.data.data;
            });
        },
        getFileDownload() {
            // 임시 파일 다운 로드 경로
            let url = '/api/download';
            let dataFile = {};
            // 파일 2개 받는다고 가정함
            for (let i = 0; i < 2; i++) {
                dataFile['data' + i] = {};
                dataFile['data' + i]['size'] = 0;
            }
            for (let i = 0; i < 2; i++) {
                axios
                    .get(url, {
                        responseType: 'blob',
                        timeout: 0,
                        onDownloadProgress: function (progressEvent) {
                            dataFile['data' + i]['totalSize'] =
                                progressEvent.total;
                            dataFile['data' + i]['curSize'] =
                                progressEvent.loaded;
                            let total = 0;
                            let cur = 0;
                            for (let fileData in dataFile) {
                                total += dataFile[fileData]['totalSize'];
                                cur += dataFile[fileData]['curSize'];
                            }
                            /* console.log(
                                '=============i ::::' +
                                    i +
                                    '::::' +
                                    Math.round((cur * 100) / total)
                            );*/
                        },
                    })
                    .then((response) => {
                        const url = window.URL.createObjectURL(
                            new Blob([response.data])
                        );
                        const link = document.createElement('a');
                        link.href = url;
                        link.id = 'id_' + i;
                        link.setAttribute('download', 'test.zip'); //파일명은 따로 입력
                        document.body.appendChild(link);
                        link.click();
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        },
    },
    created() {
        const vm = this;
        /* console.log('created');
        console.log(vm.$route.params);
        console.log(vm.$route.params.key);*/
        vm.key = vm.$route.params.key;
        // vm.$route.params.evntSn

        vm.getData();
    },
};
</script>

<style scoped></style>
