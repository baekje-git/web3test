<template>
  <div class="" style="padding: 10px 0">
    <q-table
      class=""
      row-key="APRV_DTTM"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>
      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <p>[{{ props.row.DEPT_NM }}] {{ props.row.CUST_NM }}</p>
                <!-- {{ props.row.구 }} -->
              </q-td>
            </div>
            <!-- <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.거래상태 }} {{ props.row.CUST_NM }}
                {{ props.row.동 }}
              </q-td>
            </div> -->
            <!-- <div class="table_item">
              <q-td>
                {{ props.row.CUST_NM }}
              </q-td>
            </div> -->
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.APRV_DTTM }}</q-td>
            </div>
            <!-- <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.시간 }}
              </q-td>
            </div> -->
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.APRV_NO }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td v-if="props.row.QTY <= 0" class="table-td_mobile">
                <span class="red-400 table_item_mobile">
                  {{ util.getNumberFormat(props.row.QTY) }}
                </span>
                <span>개</span>
              </q-td>
              <q-td v-else class="table-td_mobile"
                >{{ util.getNumberFormat(props.row.QTY) }}개</q-td
              >
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">단가</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td v-if="props.row.WP2 <= 0" class="table-td_mobile"
                ><span class="red-400">{{
                  util.getNumberFormat(props.row.WP2)
                }}</span
                ><span>원</span></q-td
              >
              <q-td v-else class="table-td_mobile"
                >{{ util.getNumberFormat(props.row.WP2) }}원</q-td
              >
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.SUBUL_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">금액</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td v-if="props.row.AMT <= 0" class="table-td_mobile"
                ><span class="red-400">{{
                  util.getNumberFormat(props.row.AMT)
                }}</span
                ><span>원</span></q-td
              >
              <q-td v-else class="table-td_mobile"
                >{{ util.getNumberFormat(props.row.AMT) }}원</q-td
              >
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

const props = defineProps({
  itemInfo: Object,
});

//사용자정보
const userInfo = util.getUserData();

//제품수불t상세내역 검색조건
const searchParam = ref({
  brchCd: userInfo.BRCH_CD,
  itemCd: props.itemInfo.itemCd,
  startYmd: moment(props.itemInfo.startYmd).format("YYYYMMDD"),
  endYmd: moment(props.itemInfo.endYmd).format("YYYYMMDD"),
  deptNm: props.itemInfo.deptNm,
  sortType: props.itemInfo.sortType,
});

//제품수불 상세내역 그리드
const rows = ref([]);

//제품수불 상세내역 조회
async function searchList() {
  return api
    .get("/ordLedger/selectItemReceivePayDetailList", {
      params: searchParam.value,
    })
    .then((response) => {
      rows.value = response;
    })
    .finally(() => {
      console.log(rows.value);
    });
}

//팝업 뜨면 조회
onMounted(() => {
  searchList();
});

//제품수불내역 조회
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(11, 1fr);
  padding: 6px 0 !important;
  gap: 8px;
  .table_item {
    td {
      @include fs-6;
    }
  }
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    align-items: flex-start;
    grid-column: span 9;
    max-width: 328px;
    gap: 8px;
    height: auto !important;
    td {
      // overflow: hidden;
      // text-overflow: ellipsis;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 4;
    td {
      @include flex_start;
      /* @include fw-7; */
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 2;
    td {
      @include flex_end;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      // color: #666666;
      @include flex_end;
      // font-size: 12px !important;
      // span {
      //   font-size: 12px !important;
      //   height: 20px !important;
      // }
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 2;
    td {
      @include flex_end;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 2;
    td {
      @include flex_end;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 7;
    td {
      @include flex_end;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 2;
    td {
      @include flex_end;
      // font-size: 12px !important;
      // span {
      //   font-size: 12px !important;
      // }
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 2;
    td {
      @include flex_end;
      // font-size: 12px !important;
      // span {
      //   font-size: 12px !important;
      // }
    }
  }
  /* .table_item:nth-child(10) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(11) {
    grid-column: span 2;
    td {
      @include flex_end;
      color: #666666;
      span {
        @include fs-6;
      }
    }
  } */
}
</style>
