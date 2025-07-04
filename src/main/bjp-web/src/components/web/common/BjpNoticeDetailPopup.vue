<template>
  <div class="DialogComp_wrap">
    <q-dialog v-model="isDialogVisible" @hide="onClickClose">
      <q-card style="width: 700px; padding: 32px">
        <div class="dialog_header">
          <span>지점공지사항</span>
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

                <td>
                  {{
                    noticeDtlData.insDtm
                      ? moment(noticeDtlData.insDtm).format("YYYY-MM-DD")
                      : "-"
                  }}
                </td>
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
            @update:model-value="onChangeCheckBox($event, 'branchTodayDisable')"
          >
            <template v-slot>
              <span style="font-weight: 400; font-size: 14px"
                >오늘 하루동안 보지 않기</span
              >
            </template>
          </q-checkbox>
          <q-checkbox
            v-model="isWeekDisable"
            color="blue-10"
            size="md"
            dense
            @update:model-value="onChangeCheckBox($event, 'branchWeekDisable')"
          >
            <template v-slot>
              <span style="font-weight: 400; font-size: 14px"
                >7일동안 보지 않기</span
              >
            </template>
          </q-checkbox>

          <BtnComp_active btnLabel="닫기" @click="onClickClose" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { api } from "boot/axios";
import { Cookies } from "quasar";
import * as util from "src/support/util";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import moment from "moment";

const emit = defineEmits(["close"]);
const props = defineProps({
  isOpen: { type: Boolean, default: false },
});

const userInfo = util.getUserData();
const isTodayDisable = ref(Cookies.get("branchTodayDisable") ? true : false);
const isWeekDisable = ref(Cookies.get("branchWeekDisable") ? true : false);

const noticeDtlData = ref({ ttl: "", ctt: "", insDtm: "" });
const boardFileList = ref([]);

function onChangeCheckBox(e, trg) {
  const cookieKey =
    trg === "todayDisable" ? "branchTodayDisable" : "branchWeekDisable";

  if (e) {
    Cookies.set(cookieKey, "true", { expires: trg === "todayDisable" ? 1 : 7 });
  } else {
    Cookies.remove(cookieKey);
  }
}
function onClickClose() {
  isDialogVisible.value = false; // ✅ 이거 꼭 추가!!
  emit("close");

  noticeDtlData.value = { ttl: "", ctt: "", insDtm: "" };
  boardFileList.value = [];
}

const isDialogVisible = ref(false);

watch(
  () => props.isOpen,
  async (val) => {
    if (!val) {
      isDialogVisible.value = false; // ← 꺼졌을 때 명시적으로 닫아줌
      return;
    }

    const { dtl, list } = await fetchNoticeDetail();

    if (!dtl || !dtl.TTL) {
      emit("close");
      return;
    }

    noticeDtlData.value.ttl = dtl.TTL;
    noticeDtlData.value.ctt = dtl.CTT;
    noticeDtlData.value.insDtm = dtl.INS_DTM;
    boardFileList.value = list || [];

    isDialogVisible.value = true;
  }
);
async function fetchNoticeDetail() {
  const { dtl, list } = await api.get("/myPage/branchNoticeDtlPopup2", {
    params: { BRCH_CD: userInfo.BRCH_CD },
  });
  return { dtl, list };
}
onMounted(() => {});
</script>

<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.flex_start {
  @include flex_start;
  gap: 16px;
}
.q-card_a_section_wrap {
  margin: 16px 0;
  padding: 0;
  width: 640px;
  border-bottom: 1px solid $td-line;
}
.q-card_a_section_wrap tr td {
  padding: 0 16px;
  border-bottom: 1px solid $td-line;
}
.q-card_a_section_wrap tr:last-child td {
  border-bottom: none;
}
.q-card_a_section_wrap tr td:nth-child(1) {
  text-align: center;
  background-color: #f8f8f8;
}
.q-card_btn_wrap {
  @include flex_end;
  margin: 0;
  padding: 16px 0 !important;
  gap: 8px;
  justify-content: flex-end !important;
}
.download_btn {
  color: #000;
}
</style>
