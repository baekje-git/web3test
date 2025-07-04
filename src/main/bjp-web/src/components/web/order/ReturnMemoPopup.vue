<template>
  <div class="DialogComp_wrap">
    <!-- <div class="" @click="isOpenRetrunMemoPopup = true">
      <slot></slot>
    </div> -->

    <q-dialog v-model="isOpen">
      <q-card style="padding: 16px">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>반품정보</p></div>
        </q-card-section> -->

        <div class="q-card_c_section_wrap">
          <div class="q-card_c_table_header_wrap">
            <p>반품정보</p>
          </div>
          <q-markup-table>
            <tbody>
              <tr>
                <q-input
                  v-model="takebackInfo"
                  filled
                  type="textarea"
                  disable
                />
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_active btn-label="닫기" @click="onClickClose" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, computed, defineEmits, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";

const emit = defineEmits(["close"]);
const takebackInfo = ref("");

const props = defineProps({
  isOpen: { type: Boolean },
  text: { type: String },
});

const isOpen = computed(() => {
  return props.isOpen;
});

watch(isOpen, () => {
  if (isOpen.value) {
    takebackInfo.value = props.text;
  }
});

//---------------------------- Function ------------------------------//

// 닫기
async function onClickClose() {
  emit("close");
}
</script>

<style lang="scss" scoped>
.q-card_c_title p {
  @include fw-7;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}
.q-card_c_user_wrap,
.q-card_c_section_wrap {
  width: 640px;
}

.q-card_c_table_header_wrap {
  @include flex_between;
  @include fw-7;
  padding-bottom: 8px;
  // border-bottom: 2px solid $b3;
  border-bottom: 1px solid $gray-9;
}
.q-card_c_section_wrap {
  margin-bottom: 40px;
  tr:last-child,
  td {
    border-bottom: 1px solid $td-line;
  }
  tr td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
  }
  td {
    height: 40px !important;
  }
}
.q-card_c_input {
  @include fs-5;
  border: 1px solid $line-400;
  border-radius: 0;
  max-width: 140px;
}
.q-card_c_input .q-field__control {
  padding: 0 8px;
  height: 32px !important;
}
.q-card_btn_wrap {
  @include flex_end;
}

.q-card_c_Table_wrap {
  max-height: 120px;
  overflow-y: auto;
}
.q-card_c_Table_wrap tr {
  height: 40px !important;
}
</style>
