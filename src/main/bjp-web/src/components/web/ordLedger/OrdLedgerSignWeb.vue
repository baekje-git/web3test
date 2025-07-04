<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card>
      <div class="dialog_header" style="width: 700px">
        <span>잔고확인 사인등록</span>
      </div>
      <div>
        <!-- <div style="margin-top: 5px; height: 700px"> -->
        <SignaturePad v-model:dataUrl="dataUrl" style="padding: 5px" />
        <!-- </div> -->
        <div>
          <div align="right" style="margin: 10px 8px 0">
            <q-btn
              class="btn_close"
              label="닫기"
              color="primary"
              @click="onClickClose"
            />
          </div>
        </div>
      </div>
    </q-card>
  </q-dialog>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, defineEmits, inject, onMounted, watch } from "vue";
import * as util from "src/support/util";
import { api } from "boot/axios";
import SignaturePad from "../common/SignaturePad.vue";

const emit = defineEmits(["close"]);
const bus = inject("bus");

const props = defineProps({
  isOpen: { type: Boolean },
  // dataUrl: { type: String, default: "" },
});

const dataUrl = ref("");

const dataParams = ref({
  dataURL: "",
});

const isOpen = computed(() => {
  return props.isOpen;
});

// const dataUrl = computed(() => {
//   return props.dataUrl;
// });

bus.on("saveSign", async (data) => {
  alert(data);
});

watch(
  () => dataUrl.value,
  async () => {
    if (dataUrl.value) {
      alert(dataUrl.value);
    } else {
      alert("no!!!!");
    }
  }
);

//---------------------------- Function ------------------------------//

bus.on("signWeb", async (data) => {
  console.log(data);
});

async function onClickClose() {
  emit("close");
}
</script>
<style lang="scss" scoped>
// .dialog_header {
//   font-size: 13px;
//   line-height: 23px;
//   font-weight: bold;
//   box-sizing: border-box;
//   padding: 5px 10px;
//   position: relative;
//   background-color: #1d4c9e;
//   color: #ffffff;
// }
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.btn_trans {
  width: 100%;
  background-color: #1f3f82 !important;
  border-radius: 5px !important;
}

.btn_trans::v-deep span {
  color: #ffffff !important;
}

.btn_close {
  margin: 8px 0 10px 0;
  border-radius: 5px !important;
}

.q-input {
  padding: 0 10px 0 5px;
}

.table_wrap {
  padding: 0 9px 0 9px;
}
</style>
