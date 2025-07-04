<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenNoticeDtlPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenNoticeDtlPopup">
      <q-card style="width: 700px; padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>공지사항</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>공지사항</span>
        </div>
        <div class="myInfoTable_view">
          <q-markup-table separator="noen" flat dense>
            <tbody class="myInfoTable_view_tbody view">
              <tr>
                <th style="width: 120px">제목</th>
                <td class="myInfoTable_td_wrap" style="white-space: normal">
                  {{ noticeDtlData.ttl }}
                </td>
              </tr>
              <tr>
                <th>작성일</th>
                <td class="myInfoTable_td_wrap">
                  {{
                    moment(noticeDtlData.insDtm, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  }}
                </td>
              </tr>
              <tr>
                <th>내용</th>
                <td
                  v-html="noticeDtlData.ctt"
                  class="myInfoTable_td_wrap"
                  style="white-space: normal"
                ></td>
              </tr>
            </tbody>
          </q-markup-table>
          <div class="attached_file">
            <p class="tit mb10"><b>첨부파일</b></p>
            <td
              v-for="data in boardFileList"
              :key="data.FILE_SEQ"
              class="flex_start download"
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
                >{{ data.ORG_FILE_NM }}({{
                  util.getFileSize(data.FILE_SIZE)
                }})</a
              >
            </td>
            <!-- 미리보기 -->
            <tr class="preview_wrap">
              <td colspan="2" class="preview">
                <div class="preview_items">
                  <img
                    v-for="src in srcUrl"
                    :key="src"
                    :src="src"
                    class=""
                    alt="preview"
                  />
                </div>
              </td>
            </tr>
          </div>
          <div class="flex_end">
            <BtnComp_default
              btn-label="목록"
              style="margin: 5px 0"
              class="btn-line default"
              v-close-popup
              @click="onClickToList"
            />
          </div>
        </div>
        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default
            class="btn-fill gray"
            btn-label="닫기"
            v-close-popup
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import moment from "moment";

const isOpenNoticeDtlPopup = ref(false);
const bus = inject("bus");
const rows = ref([]);

const searchParam = ref({
  seqNo: "",
});

const noticeDtlData = ref({
  ttl: "",
  ctt: "",
  insDtm: "",
});
const boardFileList = ref([]);

const srcUrl = ref([]);

//---------------------------- Function ------------------------------//

async function fetchData() {
  srcUrl.value = [];
  const { dtl, list } = await api.get("/myPage/noticeDtlPopup", {
    params: searchParam.value,
  });

  noticeDtlData.value.ttl = dtl.TTL;
  noticeDtlData.value.ctt = dtl.CTT;
  noticeDtlData.value.insDtm = dtl.INS_DTM;

  if (list) {
    boardFileList.value = list;
  }

  boardFileList.value.forEach(async (m) => {
    if (
      m.FILE_EXT == "png" ||
      m.FILE_EXT == "jpg" ||
      m.FILE_EXT == "jpeg" ||
      m.FILE_EXT == "PNG" ||
      m.FILE_EXT == "JPG" ||
      m.FILE_EXT == "JPEG"
    ) {
      const res = await api.get("/myPage/getImg", {
        params: { FILE_SEQ: m.FILE_SEQ },
      });
      srcUrl.value.push("data:image/;base64," + res);
    }
  });
}

function onClickToList() {
  bus.emit("noticePopupOpen");
}

bus.on("noticeDtlPopupOpen", async (data) => {
  searchParam.value.seqNo = data;
  await fetchData();
  isOpenNoticeDtlPopup.value = true;

  bus.emit("noticePopupClose");
});
</script>

<style lang="scss">
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
  display: flex;
  align-items: flex-start;
  gap: 16px;
}

.q-card_a_title p {
  font-weight: 700;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}

.q-card_a_section_wrap {
  // padding: 0 16px;
  margin: 16px 0;
  padding: 0;
  width: 640px;
}

.q-card_a_section_wrap tr:nth-child(1) td {
  font-weight: 700;
  // background-color: rgba(31, 63, 130, 0.1) !important;
}
.q-card_a_section_wrap {
  border-bottom: 1px solid $td-line;
}
.q-card_a_section_wrap tr td {
  padding: 0 16px;
  border-bottom: 1px solid $td-line;
}
.q-card_a_section_wrap tr:nth-last-child(1) td {
  border-bottom: none;
}
.q-card_a_section_wrap tr td p {
  margin-bottom: 12px;
}
.q-card_a_section_wrap tr td > p:nth-child(1) {
  margin-top: 12px;
}

.q-card_a_section_wrap tr td:nth-child(1) {
  text-align: center;
  background-color: #f8f8f8;
}

.notice_Dialog_btn_wrap {
  display: flex;
  justify-content: flex-end;
  gap: 18px;
  padding: 16px !important;
}

.q-card_a_input_textarea {
  margin: 16px 0;
  border: 1px solid #666666;
}

.q-card_a_input_textarea textarea {
  height: 80px !important;
  padding: 8px !important;
}

.download_btn {
  color: #000;
}

// .preview {
//   margin-top: 10px;
//   max-width: 100%;
//   height: auto;
// }
.attached_file {
  border: 1px solid $line-300;
  background-color: #f8f8f8;
  padding: 12px !important;
  margin: 16px 0 !important;
  font-size: 14px !important;
}
// .attached_file > span.tit {
//   margin-right: 12px;
// }
// .attached_file > a.file {
//   font-weight: 600;
//   text-decoration: underline;
// }
// .attached_file > a.btn {
//   height: 20px;
//   line-height: 18px;
//   padding: 0 6px;
//   font-size: 11px;
//   margin-left: 14px;
// }
// .attached_file img {
//   max-width: 100%;
// }

/** 미리보기 **/
.file_wrap {
  height: auto !important;
  td {
    height: auto !important;
    a {
      color: $b;
    }
  }
  .download {
    display: flex;
    justify-content: start;
    flex-direction: column;
    a {
    }
  }
}

.preview_wrap {
  overflow: hidden;
  .preview {
    padding: 0 5px;
    .preview_items {
      display: flex;
      justify-content: start;
      align-items: center;
      flex-direction: column;
      max-height: 300px !important;
      overflow-y: auto;
      img {
        width: 100%;
        padding: 18px 0;
        // border-bottom: 1px solid rgba(0,0,0,0.2);
      }
    }
  }
}

.myInfoTable_view {
  border-top: 1px solid $gray-9;
  th {
    background-color: #f8f8f8;
  }
  th,
  td,
  .myInfoTable_td_wrap {
    // padding: 14px 8px;
    // height: auto !important;
    // min-height: 48px !important;
    font-size: 14px;
    border-bottom: 1px solid $td-line;
  }
  th + td {
    border-left: 1px solid $td-line;
  }
}
.myInfoTable_view_tbody.view tr {
  height: 48px !important;
}
.q-card_btn_wrap {
  @include flex_end;
  margin: 0;
  padding: 16px 0 !important;
  gap: 8px;
  justify-content: flex-end !important;
}
</style>
