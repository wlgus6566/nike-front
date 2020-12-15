<template>
  <el-dialog
      title=""
      class="modal-wrap"
      width="600px"
      :visible="visible"
      :append-to-body="true"
      @close="$emit('update:visible', false)"
  >
    <h3 class="form-title mt0">알림메일 전송</h3>
    <el-scrollbar wrap-class="wrap" view-class="view-box" :native="false">
      <div class="el-dialog__inner">
        <h4>NIKE SPACE 컨텐츠 업데이트 알림</h4>
        <p>
          ASSET(SP21 NSW MULTI AM EXCEE)이(가) 업데이트 되었습니다.
        </p>
      </div>
      <h5>comment</h5>
      <span class="textarea">
        <textarea></textarea>
      </span>
    </el-scrollbar>

    <div slot="footer" class="dialog-footer">
      <button type="button" class="btn-s-black">
        <span>알림메일 전송</span>
      </button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: "modalAlarm",
  props: ['visible'],
}
</script>
<style scoped></style>
