<template>
  <div class="DialogComp_wrap">
    <q-dialog v-model="isDialogVisible">
      <q-card style="width: 700px; padding: 32px">
        <div class="dialog_header">
          <span>공지사항</span>
        </div>

        <div class="q-card_a_section_wrap">
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 120px">제목</td>
                <td>{{ noticeDtlData.ttl }}</td>
              </tr>
              <tr>
                <td>작성일</td>
                <td>{{ moment(noticeDtlData.insDtm).format("YYYY-MM-DD") }}</td>
              </tr>
              <tr>
                <td>내용</td>
                <td style="white-space: wrap" v-html="noticeDtlData.ctt"></td>
              </tr>
              <tr>
                <td>첨부파일</td>
                <td
                  v-for="data in boardFileList"
                  :key="data.FILE_SEQ"
                  class="flex_start"
                >
                  <a
                    href="javascript:void(0);"
                    class="download_btn"
                    download
                    @click="
                      util.noticeFileDownload({
                        FILE_SEQ: data.FILE_SEQ,
                        FILE_NAME: data.ORG_FILE_NM,
                      })
                    "
                  >
                    {{ data.ORG_FILE_NM }}({{
                      util.getFileSize(data.FILE_SIZE)
                    }})
                  </a>
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <q-checkbox
            v-model="isTodayDisable"
            color="blue-10"
            size="md"
            dense
            @update:model-value="onChangeCheckBox($event, 'noticeTodayDisable')"
          >
            <template v-slot>
              <span style="font-weight: 400; font-size: 14px">
                오늘 하루동안 보지 않기
              </span>
            </template>
          </q-checkbox>
          <q-checkbox
            v-model="isWeekDisable"
            color="blue-10"
            size="md"
            dense
            @update:model-value="onChangeCheckBox($event, 'noticeWeekDisable')"
          >
            <template v-slot>
              <span style="font-weight: 400; font-size: 14px">
                7일동안 보지 않기
              </span>
            </template>
          </q-checkbox>

          <BtnComp_active btnLabel="닫기" @click="onClickClose" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { Cookies } from "quasar";
import { api } from "boot/axios";
import * as util from "src/support/util";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import moment from "moment";

const emit = defineEmits(["close"]);
const props = defineProps({ isOpen: Boolean });
const isDialogVisible = ref(false);
const userInfo = util.getUserData();

const isTodayDisable = ref(Cookies.get("noticeTodayDisable") ? true : false);
const isWeekDisable = ref(Cookies.get("noticeWeekDisable") ? true : false);

const noticeDtlData = ref({ ttl: "", ctt: "", insDtm: "" });
const boardFileList = ref([]);

watch(
  () => props.isOpen,
  async (val) => {
    isDialogVisible.value = val;
    if (val) await fetchData();
  }
);

function onClickClose() {
  console.log("[BjpNoticePopup] 닫기 버튼 클릭됨");
  isDialogVisible.value = false; // ✅ 로컬 dialog 상태 조정
  emit("close"); // ✅ 부모에 알림
  noticeDtlData.value = { ttl: "", ctt: "", insDtm: "" };
  boardFileList.value = [];
}

function onChangeCheckBox(e, trg) {
  const cookieKey =
    trg === "todayDisable" ? "noticeTodayDisable" : "noticeWeekDisable";

  if (e) {
    Cookies.set(cookieKey, "true", { expires: trg === "todayDisable" ? 1 : 7 });
  } else {
    Cookies.remove(cookieKey);
  }
}

async function fetchData() {
  const { dtl, list } = await api.get("/myPage/noticeDtlPopup2");
  noticeDtlData.value = {
    ttl: dtl.TTL,
    ctt: dtl.CTT,
    insDtm: dtl.INS_DTM,
  };
  boardFileList.value = list || [];
}
</script>

<style scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid #ccc;
}
.flex_start {
  display: flex;
  gap: 16px;
}
.q-card_a_section_wrap {
  margin: 16px 0;
  padding: 0;
  width: 640px;
  border-bottom: 1px solid #ddd;
}
.q-card_a_section_wrap tr td {
  padding: 0 16px;
  border-bottom: 1px solid #ddd;
}
.q-card_a_section_wrap tr:last-child td {
  border-bottom: none;
}
.q-card_a_section_wrap tr td:nth-child(1) {
  text-align: center;
  background-color: #f8f8f8;
}
.q-card_btn_wrap {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  padding: 16px 0 !important;
}
.download_btn {
  color: #000;
}
</style>
