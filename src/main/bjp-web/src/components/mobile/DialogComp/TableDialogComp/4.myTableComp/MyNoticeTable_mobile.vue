<template>
  <div>
    <q-table
      class=""
      row-key="SEQ_NO"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:body="props">
        <q-tr @click="clickEvt(props)">
          <div class="table_wrap">
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>{{ props.row.TTL }}</span>
                <span
                  v-if="isVisibleNew(props.row)"
                  class="q-table_newText"
                  style="color: #eb1818"
                >
                  NEW
                </span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>{{
                  moment(props.row.INS_DTM, "YYYY-MM-DD HH:mm:ss").format(
                    "YYYY-MM-DD"
                  )
                }}</span>
              </q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.row.expand" :props="props">
          <div class="expand_table_wrap">
            <div class="expand_table_item">
              <q-td class="table-td_mobile">
                <span v-html="props.row.CTT"></span>
              </q-td>
            </div>
            <div class="expand_table_item">
              <!-- <q-td class="table-td_mobile">
                <span>첨부파일</span>
                <span><img src="/src/assets/icons/file-pdf-solid.svg" /></span>
                <span>[별지 제64호서식]회수확인서.pdf (96kb)</span>
              </q-td> -->
              <span>첨부파일</span>
              <q-td
                v-for="data in boardFileList"
                :key="data.FILE_SEQ"
                class="table-td_mobile"
              >
                <a
                  href="javascript:void(0);"
                  class=""
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
                  }})</a
                >
              </q-td>
            </div>
            <div class="expand_table_item">
              <div class="preview_wrap" v-if="srcUrl !== ''">
                <q-td colspan="2" class="preview">
                  <div class="preview_items">
                    <img
                      v-for="src in srcUrl"
                      :key="src"
                      :src="src"
                      class=""
                      alt="preview"
                    />
                  </div>
                </q-td>
              </div>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//
import moment from "moment";
import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
const rows = ref([]);

const userInfo = util.getUserData();

const props = defineProps({
  rowSno: {
    type: Number,
  },
});
const searchParam = ref({
  seqNo: "",
  brchCd: userInfo.BRCH_CD,
});
// const noticeDtlData = ref({
//   ttl: "",
//   ctt: "",
//   insDtm: "",
// });
const boardFileList = ref([]);

const srcUrl = ref([]);

//---------------------------- Function ------------------------------//

//파일 이미지
async function fetchData2() {
  srcUrl.value = [];
  const { dtl, list } = await api.get("/myPage/noticeDtlPopup", {
    params: searchParam.value,
  });

  // noticeDtlData.value.ttl = dtl.TTL;
  // noticeDtlData.value.ctt = dtl.CTT;
  // noticeDtlData.value.insDtm = dtl.INS_DTM;

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

async function fetchData() {
  const res = await api.get("/myPage/noticePopup");

  let newList = res.map((i) => {
    return { ...i, INS_DTM: i.INS_DTM.replace(/\./g, "/") };
  });
  rows.value = newList;

  if (util.isEmpty(props.rowSno)) {
    for (let i = 0; i < rows.value.length; i++) {
      rows.value[i]["expand"] = false;
    }
  } else {
    let clickIdx = res.findIndex((i) => i.SEQ_NO == props.rowSno);
    rows.value[clickIdx]["expand"] = true;

    searchParam.value.seqNo = props.rowSno;
    await fetchData2();
  }
}

onMounted(async () => {
  await fetchData();
});

const clickEvt = async (props) => {
  if (!props.row.expand) {
    searchParam.value.seqNo = props.row.SEQ_NO;
    await fetchData2();
  }
  for (let i = 0; i < rows.value.length; i++) {
    rows.value[i]["expand"] = false;
  }
  props.row.expand = !props.row.expand;
};

function isVisibleNew(row) {
  const insDtm = moment(row.INS_DTM.replace(/[^0-9]/g, "")).format("YYYYMMDD");
  const today = moment().format("YYYYMMDD");

  const day = today - insDtm;

  if (day <= 7) {
    return true;
  } else {
    return false;
  }
}
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap");
.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 12px 0 !important;
  .table_item {
    td {
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(1) {
    grid-column: span 5;
    td {
      @include flex_start;
      gap: 3px;
      max-width: 245px;
      // display: inline-block;
      // overflow: hidden;
      // text-overflow: ellipsis;
      white-space: normal;
      span:nth-child(3) {
        @include fw-7;
        @include fs-6;
      }
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
      span {
        font-family: "Malgun Gothic";
      }
    }
  }
}

.expand_table_wrap {
  @include border-b;
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  padding: 8px 0 !important;
  .expand_table_item:nth-child(1) {
    grid-column: span 1;
    td {
      @include fs-5;
      white-space: normal;
      span {
        @include fs-5;
      }
    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 1;
    padding: 16px 0 8px !important;

    span {
      // @include fs-5;
      // @include fw-9;
      // color: #999999;
      font-size: 14px;
      font-weight: bold;
    }

    td {
      @include flex_start;
      gap: 8px;
      height: auto !important;
      span {
        @include fs-5;
        @include fw-6;
        color: #999999;
      }
      a {
        @include fs-5;
        @include fw-6;
        color: #999999;
        white-space: normal;
      }
    }
  }
  .expand_table_item:nth-child(3) {
    grid-column: span 1;
    padding: 16px 0 8px !important;
    td {
      img {
        width: 100%;
      }
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
</style>
