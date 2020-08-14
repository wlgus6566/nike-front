<template>
    <div>
        <h3 class="form-title">파일 설정</h3>
        <hr class="hr-black" />
        <div class="btn-area-file">
            <button class="btn-file-full" v-on:click.prevent="fileAdd">
                <i class="icon-add"></i>
                <span>파일 추가하기</span>
            </button>
        </div>
        <div>
            <input
                type="file"
                ref="uploadIpt"
                multiple
                @change="uploadIptChange"
            />
            <draggable
                ref="fileListUl"
                v-model="FileList"
                v-bind="dragOptions"
                @start="isDragging = true"
                @end="isDragging = false"
                tag="ul"
            >
                <FileItem
                    v-for="file in FileList"
                    :listLength="FileList.length"
                    :file="file"
                    :key="file.fileOrder"
                    @fileSelect="fileSelect"
                    @fileDelete="fileDelete(file)"
                />
            </draggable>
        </div>
        <div class="btn-area-file">
            <button class="btn-file-full" v-on:click.prevent="fileAdd">
                <i class="icon-add"></i>
                <span>파일 추가하기</span>
            </button>
        </div>
    </div>
</template>
<script>
import draggable from 'vuedraggable';
import { fileUpLoad } from '@/api/file';
import { getContentsViewFile } from '@/api/contents';
export default {
    name: 'FileSettings',
    data() {
        return {
            uploadFile: [],
            test2: {
                registerSeq: 1,
                updaterSeq: 22,
                registrationDt: '2020.08.14 14:49:11',
                updateDt: '2020.08.14 16:36:11',
                contentsSeq: 61,
                topMenuCode: 'ASSET',
                menuCode: 'SP',
                imageFilePhysicalName:
                    'https://d3a4mwwf905xos.cloudfront.net/https://d3a4mwwf905xos.cloudfront.net/contents/20200814326000cudSIx3fQR.jpg?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvY29udGVudHMvMjAyMDA4MTQzMjYwMDBjdWRTSXgzZlFSLmpwZyIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5NzM5MDcwMH0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA2NDB9fX1dfQ__&Signature=l619LvHnMXJwIkXzcFgJ61K-3GAFyzR8kHOYEW1LcZ9eIU5lS6pVWbZQyTAxqpxbnPorLKK6Gj-jha88Vk0aBltJO0h5qIo0LRTKRLP~lxmqby7SIat3Rw~NHtIFwo3le8p7dLcAIE-X9QN6KqaJW3u0nvFLmugMxYZU3N2oKdiZjhu2m5OO-rGK1sY0-GQS7aTWw-rsi7t6E6shYLtir5mzJ4d6vb0-pb3Y~7pJhXWCXADJxU0EYDik19maDY0ecUn3iBepQbdG6HzxJ0yTXiNtBQ-vr9ctIcPPpZEUk6F3B5E5t3--3U~SYlSz~gJIoY4MLrqv84JU1M-o71dwRw__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA&Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvaHR0cHM6Ly9kM2E0bXd3ZjkwNXhvcy5jbG91ZGZyb250Lm5ldC9jb250ZW50cy8yMDIwMDgxNDMyNjAwMGN1ZFNJeDNmUVIuanBnP1BvbGljeT1leUpUZEdGMFpXMWxiblFpT2lCYmV5SlNaWE52ZFhKalpTSTZJbWgwZEhCek9pOHZaRE5oTkcxM2QyWTVNRFY0YjNNdVkyeHZkV1JtY205dWRDNXVaWFF2WTI5dWRHVnVkSE12TWpBeU1EQTRNVFF6TWpZd01EQmpkV1JUU1hnelpsRlNMbXB3WnlJc0lrTnZibVJwZEdsdmJpSTZleUpFWVhSbFRHVnpjMVJvWVc0aU9uc2lRVmRUT2tWd2IyTm9WR2x0WlNJNk1UVTVOek01TURjd01IMHNJa2x3UVdSa2NtVnpjeUk2ZXlKQlYxTTZVMjkxY21ObFNYQWlPaUl3TGpBdU1DNHdMekFpZlN3aVJHRjBaVWR5WldGMFpYSlVhR0Z1SWpwN0lrRlhVenBGY0c5amFGUnBiV1VpT2pFMU9UY3pPVEEyTkRCOWZYMWRmUV9fJlNpZ25hdHVyZT1sNjE5THZIbk1YSndJa1h6Y0ZnSjYxSy0zR0FGeXpSOGtIT1lFVzFMY1o5ZUlVNWxTNnBWV2JaUXlUQXhxcHhiblBvckxLSzZHai1qaGE4OFZrMGFCbHRKTzBoNXFJbzBMUlRLUkxQfmx4bXFieTdTSWF0M1J3fk5IdElGd28zbGU4cDdkTGNBSUUtWDlRTjZLcWFKVzN1MG52RkxtdWdNeFlaVTNOMm9LZGlaamh1Mm01T08tckdLMXNZMC1HUVM3YVRXdy1yc2k3dDZFNnNoWUx0aXI1bXpKNGQ2dmIwLXBiM1l-N3BKaFhXQ1hBREp4VTBFWURpazE5bWFEWTBlY1VuM2lCZXBRYmRHNkh6eEoweVRYaU50QlEtdnI5Y3RJY1BQcFpFVWs2RjNCNUU1dDMtLTNVflNZbFN6fmdKSW9ZNE1McnF2ODRKVTFNLW83MWR3UndfXyZLZXktUGFpci1JZD1BUEtBSk5ZRkU0U1pINlJTV1ZNQSIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5NzM5MDcwMH0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA2NDB9fX1dfQ__&Signature=iY5zUT~4z9Scy0Z~EWuG679RYcNtErzUcBMV1Qtf8q9w10wg6KUhYucJ-HDwVeSWDBCR-HdKogJeko0EWx6XMdkGEYsPptUVjEjWgNKvbgxDvAQ7DdhpwdJzBbvZh3lCpFer2nlmqpI5QzlFNvuK5e2dWetEL9JgA5OuokIs1xdDEupdaebJjSfwxJ7PPGLYQ1vo1iDGMQFBuiicuqBBFPS1jlv29NRUBH~L2HZgZo9NDJRRNls3WnP4CS-SqH~GiQcsobENOVkJv17ry8r2nC0cu9T3sLtxDkB1IFFacSBqBGNw~0LLu7oz43NTZFVK3aY6fIPiOfzdHAKJYIZo0w__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA',
                folderName: 'SP20 NSW NIKE DIRECT AM90',
                folderContents:
                    'SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료',
                campaignPeriodSectionCode: 'EVERY',
                memo: '메모 입력\n메모 입력\n메모 입력\n',
                readCount: 30,
                detailAuthYn: 'N',
                exposureYn: 'Y',
                checks: [
                    {
                        authSeq: 1,
                        authName: 'ADMINISTRATOR',
                        roleType: 'ROLE_ADMIN',
                        authDepth: 1,
                        subAuths: [
                            {
                                authSeq: 2,
                                upperAuthSeq: 1,
                                authName: 'string11111111111',
                                roleType: 'ROLE_USER1',
                                authDepth: 2,
                                subAuths: [
                                    {
                                        authSeq: 11,
                                        upperAuthSeq: 2,
                                        authName: 'string',
                                        roleType: 'ROLE_2_1594608511249',
                                        authDepth: 3,
                                        detailAuthYn: 'N',
                                        emailReceptionYn: 'N',
                                        viewYn: 'Y',
                                        checkBoxYn: 'Y',
                                    },
                                    {
                                        authSeq: 12,
                                        upperAuthSeq: 2,
                                        authName: 'string',
                                        roleType: 'ROLE_2_1594608524743',
                                        authDepth: 3,
                                        detailAuthYn: 'N',
                                        emailReceptionYn: 'N',
                                        viewYn: 'Y',
                                        checkBoxYn: 'Y',
                                    },
                                    {
                                        authSeq: 13,
                                        upperAuthSeq: 2,
                                        authName: 'string',
                                        roleType: 'ROLE_2_1594608526571',
                                        authDepth: 3,
                                        detailAuthYn: 'N',
                                        emailReceptionYn: 'N',
                                        viewYn: 'Y',
                                        checkBoxYn: 'Y',
                                    },
                                    {
                                        authSeq: 14,
                                        upperAuthSeq: 2,
                                        authName: 'string',
                                        roleType: 'ROLE_2_1594608528061',
                                        authDepth: 3,
                                        detailAuthYn: 'N',
                                        emailReceptionYn: 'N',
                                        viewYn: 'Y',
                                        checkBoxYn: 'Y',
                                    },
                                    {
                                        authSeq: 15,
                                        upperAuthSeq: 2,
                                        authName: 'string',
                                        roleType: 'ROLE_2_1594609122663',
                                        authDepth: 3,
                                        detailAuthYn: 'N',
                                        emailReceptionYn: 'N',
                                        viewYn: 'N',
                                        checkBoxYn: 'N',
                                    },
                                ],
                                detailAuthYn: 'N',
                                emailReceptionYn: 'N',
                                viewYn: 'Y',
                                checkBoxYn: 'Y',
                            },
                            {
                                authSeq: 3,
                                upperAuthSeq: 1,
                                authName: 'USER1',
                                roleType: 'ROLE_USER2',
                                authDepth: 2,
                                subAuths: [
                                    {
                                        authSeq: 6,
                                        upperAuthSeq: 3,
                                        authName: 'string',
                                        roleType: 'ROLE_USER1-1',
                                        authDepth: 3,
                                        detailAuthYn: 'N',
                                        emailReceptionYn: 'N',
                                        viewYn: 'Y',
                                        checkBoxYn: 'Y',
                                    },
                                ],
                                detailAuthYn: 'N',
                                emailReceptionYn: 'N',
                                viewYn: 'Y',
                                checkBoxYn: 'N',
                            },
                            {
                                authSeq: 4,
                                upperAuthSeq: 1,
                                authName: 'USER2',
                                roleType: 'ROLE_USER3',
                                authDepth: 2,
                                detailAuthYn: 'N',
                                emailReceptionYn: 'N',
                                viewYn: 'N',
                                checkBoxYn: 'N',
                            },
                            {
                                authSeq: 5,
                                upperAuthSeq: 1,
                                authName: 'USER3',
                                roleType: 'ROLE_USER4',
                                authDepth: 2,
                                detailAuthYn: 'N',
                                emailReceptionYn: 'N',
                                viewYn: 'N',
                                checkBoxYn: 'N',
                            },
                        ],
                        detailAuthYn: 'Y',
                        emailReceptionYn: 'N',
                        viewYn: 'Y',
                        checkBoxYn: 'Y',
                    },
                    {
                        authSeq: 7,
                        authName: 'string',
                        roleType: 'ROLE_테스트 그룹1',
                        authDepth: 1,
                        subAuths: [
                            {
                                authSeq: 8,
                                upperAuthSeq: 7,
                                authName: 'string',
                                roleType: 'ROLE_테스트 그룹2',
                                authDepth: 2,
                                subAuths: [
                                    {
                                        authSeq: 10,
                                        upperAuthSeq: 8,
                                        authName: 'string',
                                        roleType: 'ROLE_테스트 그룹3',
                                        authDepth: 3,
                                        detailAuthYn: 'N',
                                        emailReceptionYn: 'N',
                                        viewYn: 'Y',
                                        checkBoxYn: 'Y',
                                    },
                                ],
                                detailAuthYn: 'N',
                                emailReceptionYn: 'N',
                                viewYn: 'Y',
                                checkBoxYn: 'N',
                            },
                        ],
                        detailAuthYn: 'N',
                        emailReceptionYn: 'N',
                        viewYn: 'Y',
                        checkBoxYn: 'Y',
                    },
                ],
                imageBase64:
                    'https://d3a4mwwf905xos.cloudfront.net/https://d3a4mwwf905xos.cloudfront.net/contents/20200814326000cudSIx3fQR.jpg?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvY29udGVudHMvMjAyMDA4MTQzMjYwMDBjdWRTSXgzZlFSLmpwZyIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5NzM5MDcwMH0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA2NDB9fX1dfQ__&Signature=l619LvHnMXJwIkXzcFgJ61K-3GAFyzR8kHOYEW1LcZ9eIU5lS6pVWbZQyTAxqpxbnPorLKK6Gj-jha88Vk0aBltJO0h5qIo0LRTKRLP~lxmqby7SIat3Rw~NHtIFwo3le8p7dLcAIE-X9QN6KqaJW3u0nvFLmugMxYZU3N2oKdiZjhu2m5OO-rGK1sY0-GQS7aTWw-rsi7t6E6shYLtir5mzJ4d6vb0-pb3Y~7pJhXWCXADJxU0EYDik19maDY0ecUn3iBepQbdG6HzxJ0yTXiNtBQ-vr9ctIcPPpZEUk6F3B5E5t3--3U~SYlSz~gJIoY4MLrqv84JU1M-o71dwRw__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA&Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvaHR0cHM6Ly9kM2E0bXd3ZjkwNXhvcy5jbG91ZGZyb250Lm5ldC9jb250ZW50cy8yMDIwMDgxNDMyNjAwMGN1ZFNJeDNmUVIuanBnP1BvbGljeT1leUpUZEdGMFpXMWxiblFpT2lCYmV5SlNaWE52ZFhKalpTSTZJbWgwZEhCek9pOHZaRE5oTkcxM2QyWTVNRFY0YjNNdVkyeHZkV1JtY205dWRDNXVaWFF2WTI5dWRHVnVkSE12TWpBeU1EQTRNVFF6TWpZd01EQmpkV1JUU1hnelpsRlNMbXB3WnlJc0lrTnZibVJwZEdsdmJpSTZleUpFWVhSbFRHVnpjMVJvWVc0aU9uc2lRVmRUT2tWd2IyTm9WR2x0WlNJNk1UVTVOek01TURjd01IMHNJa2x3UVdSa2NtVnpjeUk2ZXlKQlYxTTZVMjkxY21ObFNYQWlPaUl3TGpBdU1DNHdMekFpZlN3aVJHRjBaVWR5WldGMFpYSlVhR0Z1SWpwN0lrRlhVenBGY0c5amFGUnBiV1VpT2pFMU9UY3pPVEEyTkRCOWZYMWRmUV9fJlNpZ25hdHVyZT1sNjE5THZIbk1YSndJa1h6Y0ZnSjYxSy0zR0FGeXpSOGtIT1lFVzFMY1o5ZUlVNWxTNnBWV2JaUXlUQXhxcHhiblBvckxLSzZHai1qaGE4OFZrMGFCbHRKTzBoNXFJbzBMUlRLUkxQfmx4bXFieTdTSWF0M1J3fk5IdElGd28zbGU4cDdkTGNBSUUtWDlRTjZLcWFKVzN1MG52RkxtdWdNeFlaVTNOMm9LZGlaamh1Mm01T08tckdLMXNZMC1HUVM3YVRXdy1yc2k3dDZFNnNoWUx0aXI1bXpKNGQ2dmIwLXBiM1l-N3BKaFhXQ1hBREp4VTBFWURpazE5bWFEWTBlY1VuM2lCZXBRYmRHNkh6eEoweVRYaU50QlEtdnI5Y3RJY1BQcFpFVWs2RjNCNUU1dDMtLTNVflNZbFN6fmdKSW9ZNE1McnF2ODRKVTFNLW83MWR3UndfXyZLZXktUGFpci1JZD1BUEtBSk5ZRkU0U1pINlJTV1ZNQSIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5NzM5MDcwMH0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA2NDB9fX1dfQ__&Signature=iY5zUT~4z9Scy0Z~EWuG679RYcNtErzUcBMV1Qtf8q9w10wg6KUhYucJ-HDwVeSWDBCR-HdKogJeko0EWx6XMdkGEYsPptUVjEjWgNKvbgxDvAQ7DdhpwdJzBbvZh3lCpFer2nlmqpI5QzlFNvuK5e2dWetEL9JgA5OuokIs1xdDEupdaebJjSfwxJ7PPGLYQ1vo1iDGMQFBuiicuqBBFPS1jlv29NRUBH~L2HZgZo9NDJRRNls3WnP4CS-SqH~GiQcsobENOVkJv17ry8r2nC0cu9T3sLtxDkB1IFFacSBqBGNw~0LLu7oz43NTZFVK3aY6fIPiOfzdHAKJYIZo0w__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA',
                campaignBeginDt: 'Invalid date',
                campaignEndDt: 'Invalid date',
                contentsFileList: [
                    {
                        contentsFileSeq: 92,
                        contentsSeq: 61,
                        fileSectionCode: 'GUIDE',
                        fileKindCode: 'FILE',
                        fileOrder: 0,
                        detailThumbnailFileName:
                            'KakaoTalk_20200623_115540789_detail.jpg',
                        detailThumbnailFileSize: '89627',
                        detailThumbnailFilePhysicalName:
                            'https://d3a4mwwf905xos.cloudfront.net/https://d3a4mwwf905xos.cloudfront.net/contents/20200814719000vEoBqs6lpF_detail.jpg?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvY29udGVudHMvMjAyMDA4MTQ3MTkwMDB2RW9CcXM2bHBGX2RldGFpbC5qcGciLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA3MDB9LCJJcEFkZHJlc3MiOnsiQVdTOlNvdXJjZUlwIjoiMC4wLjAuMC8wIn0sIkRhdGVHcmVhdGVyVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNTk3MzkwNjQwfX19XX0_&Signature=ekREqpBFLFnKaXPMouzApPAMVulGuFgIu8p06ii1B30mESkgAKQovUP3IstQGd7hG2Te7H2PKYcnb0~0kqNIBD1lYHlA7KZtWyJtw9NHEzjHwgJp3LVpgLJ4QGaPalmykwnufD3S7gZv5wUcxwK6~m34F3fw5HeER9cnTdKXFtz~Lpf9we8sH5vQ5~10Myb4O6XPQzHeQNxLsdNUuZ~AkqzKdUjQgG1gY4W32~FKYlhizBIsvrzlKDwnFDt5Acep6XXY7tM-UdBoZst6QqU0BcS4m1JpcjcMZYnUPNZe9Uc9qaQ2Pamu2fCp0A6sS8DIrYe7Od3lu1uYe6qkKQDW3A__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA&Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvaHR0cHM6Ly9kM2E0bXd3ZjkwNXhvcy5jbG91ZGZyb250Lm5ldC9jb250ZW50cy8yMDIwMDgxNDcxOTAwMHZFb0JxczZscEZfZGV0YWlsLmpwZz9Qb2xpY3k9ZXlKVGRHRjBaVzFsYm5RaU9pQmJleUpTWlhOdmRYSmpaU0k2SW1oMGRIQnpPaTh2WkROaE5HMTNkMlk1TURWNGIzTXVZMnh2ZFdSbWNtOXVkQzV1WlhRdlkyOXVkR1Z1ZEhNdk1qQXlNREE0TVRRM01Ua3dNREIyUlc5Q2NYTTJiSEJHWDJSbGRHRnBiQzVxY0djaUxDSkRiMjVrYVhScGIyNGlPbnNpUkdGMFpVeGxjM05VYUdGdUlqcDdJa0ZYVXpwRmNHOWphRlJwYldVaU9qRTFPVGN6T1RBM01EQjlMQ0pKY0VGa1pISmxjM01pT25zaVFWZFRPbE52ZFhKalpVbHdJam9pTUM0d0xqQXVNQzh3SW4wc0lrUmhkR1ZIY21WaGRHVnlWR2hoYmlJNmV5SkJWMU02UlhCdlkyaFVhVzFsSWpveE5UazNNemt3TmpRd2ZYMTlYWDBfJlNpZ25hdHVyZT1la1JFcXBCRkxGbkthWFBNb3V6QXBQQU1WdWxHdUZnSXU4cDA2aWkxQjMwbUVTa2dBS1FvdlVQM0lzdFFHZDdoRzJUZTdIMlBLWWNuYjB-MGtxTklCRDFsWUhsQTdLWnRXeUp0dzlOSEV6akh3Z0pwM0xWcGdMSjRRR2FQYWxteWt3bnVmRDNTN2dadjV3VWN4d0s2fm0zNEYzZnc1SGVFUjljblRkS1hGdHp-THBmOXdlOHNINXZRNX4xME15YjRPNlhQUXpIZVFOeExzZE5VdVp-QWtxektkVWpRZ0cxZ1k0VzMyfkZLWWxoaXpCSXN2cnpsS0R3bkZEdDVBY2VwNlhYWTd0TS1VZEJvWnN0NlFxVTBCY1M0bTFKcGNqY01aWW5VUE5aZTlVYzlxYVEyUGFtdTJmQ3AwQTZzUzhESXJZZTdPZDNsdTF1WWU2cWtLUURXM0FfXyZLZXktUGFpci1JZD1BUEtBSk5ZRkU0U1pINlJTV1ZNQSIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5NzM5MDcwMH0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA2NDB9fX1dfQ__&Signature=lRozw0fXwAu-HR-cYI1VAiCu-k7jQtDTRC-E9Sskdu6DqlCKTfRJmsbBmesn-hy6OoqgE4orDI2qUxXaZVK3W1lchbxUQ0X11WUSvr-aykKqEspEcr3qwUkifCYP8VDOT-8DAwixFHvrCsLll4ql77KiVxOoUo9sO0Txbtal-A7xbGb7~qjPRApp-STxKFiyOnVYsIZz-jFAcN~tBhA4vQJ7grvgytENoS1YLW7AOQeE8DW0wr~183sQWGm~SJ--X2kP5pqZHhbCYIs1ZIace9~-cDTVPfUYiaEk7SL~Ybd6E9X-Kqon25COIGxccPWlTwpw5fenyJhDhLx58ibYUw__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA',
                        thumbnailFileName:
                            'KakaoTalk_20200623_115540789_thumbnail.jpg',
                        thumbnailFileSize: '4607',
                        thumbnailFilePhysicalName:
                            'https://d3a4mwwf905xos.cloudfront.net/https://d3a4mwwf905xos.cloudfront.net/contents/20200814719000vEoBqs6lpF_thumbnail.jpg?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvY29udGVudHMvMjAyMDA4MTQ3MTkwMDB2RW9CcXM2bHBGX3RodW1ibmFpbC5qcGciLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA3MDB9LCJJcEFkZHJlc3MiOnsiQVdTOlNvdXJjZUlwIjoiMC4wLjAuMC8wIn0sIkRhdGVHcmVhdGVyVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNTk3MzkwNjQwfX19XX0_&Signature=oVd82I6rdoptOCpzGOvnQhzcCuIPjy0C90JfUMm8pghEx6z9YNEo0-D5N7Re2GLws9CcD0yWKWcQv9KhHur8WdofzUy5wuT8BOUglZxIkNsH8LLa~j7wnlQAwqnIuOM6lN7bzeRXHV7yaiYhsNXolqo86LeKqNOeiYuA-x7q3Pz9M~JOZO4gzJfWlyrUJOmCarkJtGOs-J1axvIpgiNmYGJpq9LmIVkdtJXY6HRjso5e9Gm6OarPxMpqqlzyy1VwcKKeWKeHRef~JF2kmPVhrmXibiYh1NzRI1FrWPXIU861g0GjPnnr8vOYa4YS~FUrAJAOyPkmOMhJK6NDTM~pmA__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA&Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvaHR0cHM6Ly9kM2E0bXd3ZjkwNXhvcy5jbG91ZGZyb250Lm5ldC9jb250ZW50cy8yMDIwMDgxNDcxOTAwMHZFb0JxczZscEZfdGh1bWJuYWlsLmpwZz9Qb2xpY3k9ZXlKVGRHRjBaVzFsYm5RaU9pQmJleUpTWlhOdmRYSmpaU0k2SW1oMGRIQnpPaTh2WkROaE5HMTNkMlk1TURWNGIzTXVZMnh2ZFdSbWNtOXVkQzV1WlhRdlkyOXVkR1Z1ZEhNdk1qQXlNREE0TVRRM01Ua3dNREIyUlc5Q2NYTTJiSEJHWDNSb2RXMWlibUZwYkM1cWNHY2lMQ0pEYjI1a2FYUnBiMjRpT25zaVJHRjBaVXhsYzNOVWFHRnVJanA3SWtGWFV6cEZjRzlqYUZScGJXVWlPakUxT1Rjek9UQTNNREI5TENKSmNFRmtaSEpsYzNNaU9uc2lRVmRUT2xOdmRYSmpaVWx3SWpvaU1DNHdMakF1TUM4d0luMHNJa1JoZEdWSGNtVmhkR1Z5VkdoaGJpSTZleUpCVjFNNlJYQnZZMmhVYVcxbElqb3hOVGszTXprd05qUXdmWDE5WFgwXyZTaWduYXR1cmU9b1ZkODJJNnJkb3B0T0NwekdPdm5RaHpjQ3VJUGp5MEM5MEpmVU1tOHBnaEV4Nno5WU5FbzAtRDVON1JlMkdMd3M5Q2NEMHlXS1djUXY5S2hIdXI4V2RvZnpVeTV3dVQ4Qk9VZ2xaeElrTnNIOExMYX5qN3dubFFBd3FuSXVPTTZsTjdiemVSWEhWN3lhaVloc05Yb2xxbzg2TGVLcU5PZWlZdUEteDdxM1B6OU1-Sk9aTzRnekpmV2x5clVKT21DYXJrSnRHT3MtSjFheHZJcGdpTm1ZR0pwcTlMbUlWa2R0SlhZNkhSanNvNWU5R202T2FyUHhNcHFxbHp5eTFWd2NLS2VXS2VIUmVmfkpGMmttUFZocm1YaWJpWWgxTnpSSTFGcldQWElVODYxZzBHalBubnI4dk9ZYTRZU35GVXJBSkFPeVBrbU9NaEpLNk5EVE1-cG1BX18mS2V5LVBhaXItSWQ9QVBLQUpOWUZFNFNaSDZSU1dWTUEiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA3MDB9LCJJcEFkZHJlc3MiOnsiQVdTOlNvdXJjZUlwIjoiMC4wLjAuMC8wIn0sIkRhdGVHcmVhdGVyVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNTk3MzkwNjQwfX19XX0_&Signature=FQ5Q0qiImJzLmpxU-keg-oX83z~qE02OKnXoHkXs5mULYJMLeUO9dm~VgzRnggctYQRX6~1jBoYMfsikcj63exG9PrTO2onZUnn6uHMdiMEY9JSu-cMhmD3eWebmahfJjVSN~BxyQ9MExsPWKHdU~ePPAywAyXGpoZV7KmStYgqryvQXEL4oyTb9aOO0-qC9dyCEC6tsVcCRJGPM8yOCOZ6kKuL6ZLFWykRdf0hI508JnjYpfwK00vmJLASHyLSU3TVfeeQt7PtFdt1YXpA6aY9oPby~2DUROrkuaGX9xc532-5eicfeIHY3qUL5IiPS9gH8HXdxrudLpnsogGA2Fw__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA',
                        fileContentType: 'image/jpeg',
                        fileExtension: 'JPG',
                        fileName: 'KakaoTalk_20200623_115540789.jpg',
                        fileSize: 130996,
                        filePhysicalName:
                            'https://d3a4mwwf905xos.cloudfront.net/https://d3a4mwwf905xos.cloudfront.net/contents/20200814719000vEoBqs6lpF.jpg?Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvY29udGVudHMvMjAyMDA4MTQ3MTkwMDB2RW9CcXM2bHBGLmpwZyIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5NzM5MDcwMH0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA2NDB9fX1dfQ__&Signature=Bkg165hejiC11hdcFse5aFvx-hSpQkPqTL6pR4dcJOW~oun-sZqhXLTPbi~eVIOjp3wsSk0Wapx17cXXK8O78bkma9E53zsdw1YgWME6PtG4a09N8bMAvBGNfptJ9r3mPtUUAxu-99f7o~UL97jfKRJNf7uiTATjRt0wLOiRReXGbECa0UQ2OJh3Fc7YSspxE8rmq~kjNqxh3OlDpRqPJ~9T18Hi2ao7Sy116YVn4uKqwKY9nXY~-0FOxUfhMCP8RO4~26HpLXorT5efjOTKS7ZLm72PmVuzZqwKUijlzaqiuFbW5Wxc1Xf3yKs2DamhHvsosF~e5Co~HN7g3GvZPg__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA&Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6Imh0dHBzOi8vZDNhNG13d2Y5MDV4b3MuY2xvdWRmcm9udC5uZXQvaHR0cHM6Ly9kM2E0bXd3ZjkwNXhvcy5jbG91ZGZyb250Lm5ldC9jb250ZW50cy8yMDIwMDgxNDcxOTAwMHZFb0JxczZscEYuanBnP1BvbGljeT1leUpUZEdGMFpXMWxiblFpT2lCYmV5SlNaWE52ZFhKalpTSTZJbWgwZEhCek9pOHZaRE5oTkcxM2QyWTVNRFY0YjNNdVkyeHZkV1JtY205dWRDNXVaWFF2WTI5dWRHVnVkSE12TWpBeU1EQTRNVFEzTVRrd01EQjJSVzlDY1hNMmJIQkdMbXB3WnlJc0lrTnZibVJwZEdsdmJpSTZleUpFWVhSbFRHVnpjMVJvWVc0aU9uc2lRVmRUT2tWd2IyTm9WR2x0WlNJNk1UVTVOek01TURjd01IMHNJa2x3UVdSa2NtVnpjeUk2ZXlKQlYxTTZVMjkxY21ObFNYQWlPaUl3TGpBdU1DNHdMekFpZlN3aVJHRjBaVWR5WldGMFpYSlVhR0Z1SWpwN0lrRlhVenBGY0c5amFGUnBiV1VpT2pFMU9UY3pPVEEyTkRCOWZYMWRmUV9fJlNpZ25hdHVyZT1Ca2cxNjVoZWppQzExaGRjRnNlNWFGdngtaFNwUWtQcVRMNnBSNGRjSk9Xfm91bi1zWnFoWExUUGJpfmVWSU9qcDN3c1NrMFdhcHgxN2NYWEs4Tzc4YmttYTlFNTN6c2R3MVlnV01FNlB0RzRhMDlOOGJNQXZCR05mcHRKOXIzbVB0VVVBeHUtOTlmN29-VUw5N2pmS1JKTmY3dWlUQVRqUnQwd0xPaVJSZVhHYkVDYTBVUTJPSmgzRmM3WVNzcHhFOHJtcX5rak5xeGgzT2xEcFJxUEp-OVQxOEhpMmFvN1N5MTE2WVZuNHVLcXdLWTluWFl-LTBGT3hVZmhNQ1A4Uk80fjI2SHBMWG9yVDVlZmpPVEtTN1pMbTcyUG1WdXpacXdLVWlqbHphcWl1RmJXNVd4YzFYZjN5S3MyRGFtaEh2c29zRn5lNUNvfkhON2czR3ZaUGdfXyZLZXktUGFpci1JZD1BUEtBSk5ZRkU0U1pINlJTV1ZNQSIsIkNvbmRpdGlvbiI6eyJEYXRlTGVzc1RoYW4iOnsiQVdTOkVwb2NoVGltZSI6MTU5NzM5MDcwMH0sIklwQWRkcmVzcyI6eyJBV1M6U291cmNlSXAiOiIwLjAuMC4wLzAifSwiRGF0ZUdyZWF0ZXJUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTczOTA2NDB9fX1dfQ__&Signature=dN~ZDl7iz8nnhBtHSH9ZJafuZIrdm26SXbzczZ-ODxUT7s9pFIexiW9WDr6v-RIniz~U7dM~bDN05A4CdDbrEPySXLwuLlSAYhLMewQ~EXm16E3dngajXxUKHlY-Pe6xogJ6Vg0kmUHkARZDtwH~4nL3Wf1U7ZaMEiNoFssiuSlL2dQy27yy-v~YjjHMPxmsIhifo6SWxuNVQCV4BQwhEZaLclaIYwcY3SRBM2Yph~yU4bIwQ-DFhT8ARuJxPmlEpVBpvKxEz6pMKmUqYMBLUFhhkqehzYWJ83~8fUoln5ZB-XnobdZ-U439MgRBSnZ1C6jKpJy7EvYDhaBw2qD-FA__&Key-Pair-Id=APKAJNYFE4SZH6RSWVMA',
                        downloadCount: 0,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '1279-reflection-1920x1080-digital-art-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814608000M0RlP46Fyn.jpg',
                        fileSize: 665571,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '1279-reflection-1920x1080-digital-art-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814608000M0RlP46Fyn_thumbnail.jpg',
                        thumbnailFileSize: 4853,
                        detailThumbnailFileName:
                            '1279-reflection-1920x1080-digital-art-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 115132,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814608000M0RlP46Fyn_detail.jpg',
                        fileOrder: 1,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '1472-honda-repsol-1920x1080-motorcycle-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814907000mf7xgnhDEF.jpg',
                        fileSize: 287397,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '1472-honda-repsol-1920x1080-motorcycle-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814907000mf7xgnhDEF_thumbnail.jpg',
                        thumbnailFileSize: 3645,
                        detailThumbnailFileName:
                            '1472-honda-repsol-1920x1080-motorcycle-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 77126,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814907000mf7xgnhDEF_detail.jpg',
                        fileOrder: 2,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName: '1920x1080dfredf.jpg',
                        filePhysicalName: '/temp/20200814938000sUNafBxCrN.jpg',
                        fileSize: 1118192,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName: '1920x1080dfredf_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814938000sUNafBxCrN_thumbnail.jpg',
                        thumbnailFileSize: 8117,
                        detailThumbnailFileName: '1920x1080dfredf_detail.jpg',
                        detailThumbnailFileSize: 220366,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814938000sUNafBxCrN_detail.jpg',
                        fileOrder: 3,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '5569-night-lights-on-the-road-1920x1080-artistic-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814913000IwgKNJlWKi.jpg',
                        fileSize: 228955,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '5569-night-lights-on-the-road-1920x1080-artistic-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814913000IwgKNJlWKi_thumbnail.jpg',
                        thumbnailFileSize: 2482,
                        detailThumbnailFileName:
                            '5569-night-lights-on-the-road-1920x1080-artistic-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 44768,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814913000IwgKNJlWKi_detail.jpg',
                        fileOrder: 4,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName: '5579-road-1920x1080-artistic-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814917000pBfJgjybtZ.jpg',
                        fileSize: 373670,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '5579-road-1920x1080-artistic-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814917000pBfJgjybtZ_thumbnail.jpg',
                        thumbnailFileSize: 15222,
                        detailThumbnailFileName:
                            '5579-road-1920x1080-artistic-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 83536,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814917000pBfJgjybtZ_detail.jpg',
                        fileOrder: 5,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '12440-rose-and-jack-titanic-1920x1080-movie-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814943000kydoERgjjR.jpg',
                        fileSize: 1032875,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '12440-rose-and-jack-titanic-1920x1080-movie-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814943000kydoERgjjR_thumbnail.jpg',
                        thumbnailFileSize: 25792,
                        detailThumbnailFileName:
                            '12440-rose-and-jack-titanic-1920x1080-movie-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 165087,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814943000kydoERgjjR_detail.jpg',
                        fileOrder: 6,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '12784-rain-clouds-over-the-sea-1920x1080-beach-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814450000tkSh4mURUd.jpg',
                        fileSize: 645815,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '12784-rain-clouds-over-the-sea-1920x1080-beach-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814450000tkSh4mURUd_thumbnail.jpg',
                        thumbnailFileSize: 3868,
                        detailThumbnailFileName:
                            '12784-rain-clouds-over-the-sea-1920x1080-beach-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 114917,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814450000tkSh4mURUd_detail.jpg',
                        fileOrder: 7,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '13618-winter-night-in-the-park-1920x1080-artistic-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814641000hh72Asf4cZ.jpg',
                        fileSize: 710641,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '13618-winter-night-in-the-park-1920x1080-artistic-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814641000hh72Asf4cZ_thumbnail.jpg',
                        thumbnailFileSize: 2115,
                        detailThumbnailFileName:
                            '13618-winter-night-in-the-park-1920x1080-artistic-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 69313,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814641000hh72Asf4cZ_detail.jpg',
                        fileOrder: 8,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '15669-girl-playing-the-guitar-1920x1080-girl-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814723000RGNRzBVNkK.jpg',
                        fileSize: 607720,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '15669-girl-playing-the-guitar-1920x1080-girl-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814723000RGNRzBVNkK_thumbnail.jpg',
                        thumbnailFileSize: 4445,
                        detailThumbnailFileName:
                            '15669-girl-playing-the-guitar-1920x1080-girl-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 111132,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814723000RGNRzBVNkK_detail.jpg',
                        fileOrder: 9,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '16429-smoking-girl-1920x1080-girl-wallpaper.jpg',
                        filePhysicalName: '/temp/2020081476400042xBd3wmwY.jpg',
                        fileSize: 269058,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '16429-smoking-girl-1920x1080-girl-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/2020081476400042xBd3wmwY_thumbnail.jpg',
                        thumbnailFileSize: 3158,
                        detailThumbnailFileName:
                            '16429-smoking-girl-1920x1080-girl-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 59681,
                        detailThumbnailFilePhysicalName:
                            '/temp/2020081476400042xBd3wmwY_detail.jpg',
                        fileOrder: 10,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '18297-geiranger-fjord-1920x1080-nature-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814820000aCIz4rIFwf.jpg',
                        fileSize: 852252,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '18297-geiranger-fjord-1920x1080-nature-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814820000aCIz4rIFwf_thumbnail.jpg',
                        thumbnailFileSize: 3451,
                        detailThumbnailFileName:
                            '18297-geiranger-fjord-1920x1080-nature-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 111597,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814820000aCIz4rIFwf_detail.jpg',
                        fileOrder: 11,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '18558-attack-on-titan-1920x1080-anime-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814836000e0JDhgiDe7.jpg',
                        fileSize: 442820,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '18558-attack-on-titan-1920x1080-anime-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814836000e0JDhgiDe7_thumbnail.jpg',
                        thumbnailFileSize: 27502,
                        detailThumbnailFileName:
                            '18558-attack-on-titan-1920x1080-anime-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 119710,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814836000e0JDhgiDe7_detail.jpg',
                        fileOrder: 12,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '18854-caitlyn-league-of-legends-1920x1080-game-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814100000uzrIXiNV8o.jpg',
                        fileSize: 138220,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '18854-caitlyn-league-of-legends-1920x1080-game-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814100000uzrIXiNV8o_thumbnail.jpg',
                        thumbnailFileSize: 2034,
                        detailThumbnailFileName:
                            '18854-caitlyn-league-of-legends-1920x1080-game-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 33331,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814100000uzrIXiNV8o_detail.jpg',
                        fileOrder: 13,
                    },
                    {
                        fileKindCode: 'FILE',
                        fileSectionCode: 'GUIDE',
                        title: '',
                        url: '',
                        fileName:
                            '19127-blurred-city-lights-over-the-cars-1920x1080-photography-wallpaper.jpg',
                        filePhysicalName: '/temp/20200814363000A9Q1hYs4PJ.jpg',
                        fileSize: 281789,
                        fileContentType: 'image/jpeg',
                        fileExtension: 'jpg',
                        thumbnailFileName:
                            '19127-blurred-city-lights-over-the-cars-1920x1080-photography-wallpaper_thumbnail.jpg',
                        thumbnailFilePhysicalName:
                            '/temp/20200814363000A9Q1hYs4PJ_thumbnail.jpg',
                        thumbnailFileSize: 3237,
                        detailThumbnailFileName:
                            '19127-blurred-city-lights-over-the-cars-1920x1080-photography-wallpaper_detail.jpg',
                        detailThumbnailFileSize: 57678,
                        detailThumbnailFilePhysicalName:
                            '/temp/20200814363000A9Q1hYs4PJ_detail.jpg',
                        fileOrder: 14,
                    },
                ],
            },
            test: {
                detailThumbnailFileName: '',
                detailThumbnailFilePhysicalName: '',
                detailThumbnailFileSize: '',
                fileExtension: '',
                fileKindCode: 'FILE',
                filePhysicalName: '',
                fileSectionCode: 'GUIDE',
                thumbnailFileName: '',
                thumbnailFilePhysicalName: '',
                thumbnailFileSize: '',
                title: '',
                url: '',
            },
            FileList: [
                {
                    detailThumbnailFileName: '',
                    detailThumbnailFilePhysicalName: '',
                    detailThumbnailFileSize: '',
                    fileContentType: '',
                    fileExtension: '',
                    fileKindCode: 'FILE',
                    fileName: '',
                    fileOrder: 0,
                    filePhysicalName: '',
                    fileSectionCode: 'GUIDE',
                    fileSize: 0,
                    thumbnailFileName: '',
                    thumbnailFilePhysicalName: '',
                    thumbnailFileSize: '',
                    title: '',
                    url: '',
                },
            ],
        };
    },
    computed: {
        dragOptions() {
            return {
                animation: 100,
                group: 'description',
                disabled: false,
                ghostClass: 'ghost',
            };
        },
    },
    created() {
        if (this.$route.params.id) {
            this.getFolderDetailFile();
        }
    },
    components: {
        FileItem: () => import('@/components/file-settings/file-item.vue'),
        draggable,
    },
    methods: {
        progress(file) {
            return file;
        },
        emitFileList() {
            this.FileList.forEach((el, index) => {
                el.fileOrder = index;
            });
            this.$emit('FileListUpdate', this.FileList);
        },
        uploadIptChange(e) {
            const files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;

            let mergeArray = Array.from(files).filter((item) => {
                return this.FileList.every((el) => {
                    return (
                        item.name !== el.fileName &&
                        item.type !== el.fileExtension &&
                        item.size !== el.fileSize
                    );
                });
            });

            mergeArray.forEach((el) => {
                const idx = this.FileList.findIndex((el) => {
                    return el.fileKindCode === 'FILE' && !el.fileName;
                });

                if (idx !== -1) {
                    alert(1);
                    this.FileList[idx].fileContentType = el.type;
                    this.FileList[idx].fileName = el.name;
                    this.FileList[idx].fileSize = el.size;
                } else {
                    this.FileList.push({
                        fileContentType: el.type,
                        fileName: el.name,
                        fileOrder: this.FileList.length,
                        fileSize: el.size,
                        title: '',
                        url: '',
                        ...this.test,
                    });
                }
            });
            this.emitFileList();
            this.uploadFile = this.uploadFile.concat(mergeArray);
            //this.uploadFiles(mergeArray);
        },
        async uploadFiles() {
            await Promise.all(
                this.uploadFile.map(async (el, idx) => {
                    try {
                        const formData = new FormData();
                        formData.append('uploadFile', el);
                        const config = {
                            onUploadProgress: (progressEvent) => {
                                const percentCompleted = Math.round(
                                    (progressEvent.loaded * 100) /
                                        progressEvent.total
                                );
                                this.FileList.forEach((item) => {
                                    if (
                                        item.fileName === el.name &&
                                        item.fileContentType === el.type &&
                                        item.fileSize === el.size
                                    ) {
                                        item.progress = percentCompleted;
                                        console.log(idx, percentCompleted);
                                    }
                                });
                                this.emitFileList();
                            },
                        };
                        const {
                            data: { data: response },
                        } = await fileUpLoad(formData, config);

                        this.FileList.forEach((item, idx, array) => {
                            if (
                                item.fileName === el.name &&
                                item.fileContentType === el.type &&
                                item.fileSize === el.size
                            ) {
                                array[idx] = {
                                    fileKindCode: 'FILE',
                                    fileSectionCode: 'GUIDE',
                                    title: '',
                                    url: '',
                                    ...response,
                                };
                                this.emitFileList();
                                //console.log(item);
                            }
                        });
                    } catch (e) {
                        console.log(e);
                    }
                })
            );
            this.$emit('submitForm');
            console.log(123123);
        },
        fileAdd() {
            console.log('fileAdd');
            this.FileList.push({
                fileKindCode: 'FILE',
                fileName: '',
                fileSectionCode: 'ASSET',
                fileSize: 600,
                title: '',
                url: '',
            });
            this.emitFileList();
        },
        fileDelete(file) {
            console.log('fileDelete');
            const idx = this.FileList.findIndex((el) => {
                return (
                    el.fileName === file.fileName &&
                    el.fileExtension === file.fileExtension &&
                    el.fileSize === file.fileSize
                );
            });
            this.FileList.splice(idx, 1);
            this.emitFileList();
        },
        fileSelect() {
            console.log('fileSelect');
            this.$refs.uploadIpt.value = null;
            this.$refs.uploadIpt.click();
        },

        async getFolderDetailFile() {
            this.loadingData = true;
            this.checkAll = false;
            try {
                const {
                    data: { data: response },
                } = await getContentsViewFile(
                    this.$route.meta.topMenuCode,
                    this.$route.meta.menuCode,
                    this.$route.params.id,
                    {
                        page: this.page,
                        size: this.itemLength,
                    }
                );
                this.FileList = response.content;
                this.emitFileList();
                this.loadingData = false;
            } catch (error) {
                console.log(error);
            }
        },
    },
};
</script>
<style scoped>
.flip-list-move {
    transition: transform 0.5s;
}
.no-move {
    transition: transform 0s;
}
.ghost {
    opacity: 0.5;
    background: #fff;
}
</style>
