<template>
    <div>
        <h2 class="page-title">UPLOAD</h2>
        <form action="">
            <h3 class="form-title mt20">캠페인 설정</h3>
            <hr class="hr-black" />
            <ul class="form-list-thumb">
                <li class="form-row thumb-row">
                    <thumbnail />
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">캠페인 상태</span>
                    </div>
                    <div class="form-column">
                        <el-radio v-model="exposure" label="Y">
                            노출
                        </el-radio>
                        <el-radio v-model="exposure" label="N">
                            미노출
                        </el-radio>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title required">업로드 위치</span>
                    </div>
                    <div class="form-column">
                        <el-radio v-model="uploadFolder" label="SP">
                            SP
                        </el-radio>
                        <el-radio v-model="uploadFolder" label="SU">
                            SU
                        </el-radio>
                        <el-radio v-model="uploadFolder" label="FA">
                            FA
                        </el-radio>
                        <el-radio v-model="uploadFolder" label="HO">
                            HO
                        </el-radio>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">캠페인 명</label>
                    </div>
                    <div class="form-column">
                        <el-input v-model="campaignTitle"></el-input>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title required">캠페인 상세</label>
                    </div>
                    <div class="form-column">
                        <el-input type="textarea" :rows="2" v-model="campaignDesc"> </el-input>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <span class="label-title">캠페인 기간</span>
                    </div>
                    <div class="form-column">
                        <div>
                            <el-radio v-model="campaignPeriodRadio" label="selectDate">
                                기간선택
                            </el-radio>
                            <el-radio v-model="campaignPeriodRadio" label="365">
                                365
                            </el-radio>
                        </div>
                        <!-- todo 추가 스크립트 작업 필요  -->
                        <div class="data-picker">
                            <el-date-picker
                                v-model="campaignDate"
                                type="daterange"
                                range-separator="-"
                                start-placeholder="Start date"
                                end-placeholder="End date"
                            >
                            </el-date-picker>
                        </div>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">메모</label>
                    </div>
                    <div class="form-column">
                        <!-- todo 추가 스크립트 작업 필요  -->
                        <span class="textarea">
                            <textarea cols="100" rows="2" style="height: 80px;">
나이키 다이렉트 캠페인 자료와 동영상, 스타일가이드, 기타 그래픽자료가 업데이트 되었습니다. SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료가 업데이트 되었습니다.</textarea
                            >
                            <span class="count"><strong>100</strong> / <em>150</em>byte</span>
                        </span>
                    </div>
                </li>
                <li class="form-row">
                    <div class="form-column">
                        <label class="label-title">권한 설정</label>
                    </div>
                    <div class="form-column">
                        <button type="button" class="btn-form-gray min100">
                            <span>권한설정</span>
                        </button>
                    </div>
                </li>
            </ul>
            <hr class="hr-gray" />

            <FileSettings></FileSettings>
            <div class="btn-area">
                <button type="button" class="btn-s-white"><span>취소</span></button>
                <button type="submit" class="btn-s-black"><span>저장</span></button>
            </div>
        </form>
    </div>
</template>
<script>
//import CampaignSettings from '@/components/campaign-settings/index.vue';
import thumbnail from '@/components/thumbnail/index';
import FileSettings from '@/components/file-settings/index.vue';
export default {
    name: 'upload',
    data() {
        return {
            exposure: 'Y',
            uploadFolder: 'SP',
            campaignTitle: '',
            campaignDesc: '',
            campaignPeriodRadio: 'selectDate',
            campaignDate: '',
        };
    },
    components: {
        thumbnail,
        FileSettings,
    },
};
</script>
<style scoped></style>
