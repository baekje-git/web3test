<template>
  <div class="MyActivity_Expand_wrap">
    <q-expansion-item
      v-for="(row, i) in rows"
      :key="i"
      hide-expand-icon
      style="margin-bottom: 4px"
    >
      <template v-slot:header>
        <div class="Expand_head">
          {{ moment(row.BALANCE_YM).format("YYYY년 MM월") }}
        </div>
      </template>
      <div class="Expand_container">
        <div class="table_list_wrap">
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>주문</li>
                <li>{{ util.getNumberFormat(row.SALE_AMT) }} 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>매출</li>
                <li>
                  {{ util.getNumberFormat(row.SALE_AMT) }}
                  <span>원</span>
                </li>
              </ul>
              <ul>
                <li>매출(16_15)</li>
                <li>
                  {{ util.getNumberFormat(row.PRE_TOTAL_AMT) }}
                  <span>원</span>
                </li>
              </ul>
              <ul>
                <li>순매출액</li>
                <li>
                  {{ util.getNumberFormat(row.TOTAL_AMT) }}
                  <span>원</span>
                </li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>반품</li>
                <li>{{ util.getNumberFormat(row.BACK_AMT) }} 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>반품</li>
                <li>
                  {{ util.getNumberFormat(row.BACK_AMT) }}
                  <span>원</span>
                </li>
              </ul>
              <ul>
                <li>소분반품</li>
                <li>
                  {{ util.getNumberFormat(row.SUBDIV_BACK_AMT) }}
                  <span>원</span>
                </li>
              </ul>
              <!-- <ul>
                <li>약가보상</li>
                <li>-<span></span></li>
              </ul> -->
              <!-- <ul>
                <li>기타</li>
                <li>-<span></span></li>
              </ul> -->
              <ul>
                <li>수금할인</li>
                <li>
                  {{ util.getNumberFormat(row.PAYBACK_AMT) }}
                  <span>원</span>
                </li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>입금</li>
                <li>{{ util.getNumberFormat(row.PAY_CASH_AMT) }} 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>결제</li>
                <li>
                  {{ util.getNumberFormat(row.PAY_CASH_AMT) }}
                  <span>원</span>
                </li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>잔고</li>
                <li>{{ util.getNumberFormat(row.BALANCE_A_AMT) }} 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <!-- <ul>
                <li>전월잔고</li>
                <li>25000000<span>원</span></li>
              </ul> -->
            </div>
          </div>
        </div>
      </div>
    </q-expansion-item>
    <div class="m-loading_wrap" v-if="loading === true">
      <q-inner-loading class="loading" :showing="loading" color="primary" />
    </div>

    <!-- <q-expansion-item hide-expand-icon style="margin-bottom: 4px">
      <template v-slot:header>
        <div class="Expand_head">2024년 8월</div>
      </template>
      <div class="Expand_container">
        <div class="table_list_wrap">
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>주문</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>매출</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>매출(16_15)</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>순매출액</li>
                <li>25000000<span>원</span></li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>반품</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>반품</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>소분반품</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>약가보상</li>
                <li>-<span></span></li>
              </ul>
              <ul>
                <li>기타</li>
                <li>-<span></span></li>
              </ul>
              <ul>
                <li>수금할인</li>
                <li>25000000<span>원</span></li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>입금</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>결제</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>회전일</li>
                <li>34<span>일</span></li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>잔고</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>전월잔고</li>
                <li>25000000<span>원</span></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </q-expansion-item>

    <q-expansion-item hide-expand-icon style="margin-bottom: 4px">
      <template v-slot:header>
        <div class="Expand_head">2024년 8월</div>
      </template>
      <div class="Expand_container">
        <div class="table_list_wrap">
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>주문</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>매출</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>매출(16_15)</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>순매출액</li>
                <li>25000000<span>원</span></li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>반품</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>반품</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>소분반품</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>약가보상</li>
                <li>-<span></span></li>
              </ul>
              <ul>
                <li>기타</li>
                <li>-<span></span></li>
              </ul>
              <ul>
                <li>수금할인</li>
                <li>25000000<span>원</span></li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>입금</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>결제</li>
                <li>25000000<span>원</span></li>
              </ul>
              <ul>
                <li>회전일</li>
                <li>34<span>일</span></li>
              </ul>
            </div>
          </div>
          <div class="table_list">
            <div class="table_list_label">
              <ul>
                <li>잔고</li>
                <li>25000000 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul>
                <li>전월잔고</li>
                <li>25000000<span>원</span></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </q-expansion-item> -->
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";
const userInfo = util.getUserData();
const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  year: "",
  endDt: "",
});

//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  loading.value = true;
  const res = await api.get("/custMonth/listSearch", {
    params: searchParam.value,
  });

  rows.value = res;
  loading.value = false;
}

async function setDate(val) {
  if (val) {
    searchParam.value.year = val;
    searchParam.value.endDt = `${val}1231`;

    if (searchParam.value.year.length === 4) {
      await fetchItemList();
    }
  }
}

defineExpose({ setDate });

onMounted(async () => {
  await setDate(util.getToday("YYYY"));
});
</script>

<style lang="scss" scoped>
.MyActivity_Expand_wrap {
  .Expand_head {
    @include flex_center;
    @include fs-5;
    @include fw-5;
    width: 100%;
    // color: #fff;
    border-radius: 0;
    // background-color: $th-bg;
    border: $line-400 solid 1px;
  }
  .q-expansion-item--expanded {
    .Expand_head {
      // @include gradation;
      color: #fff;
      background: $primary;
      border-color: $primary;
    }
  }
  .Expand_container:last-child {
    margin-bottom: 12px;
  }
  .Expand_container {
    .table_list_wrap {
      padding: 0 8px;
      border-radius: 0;
      .table_list {
        display: flex;
        padding: 16px 0;
        @include border-b;
        border-color: $td-line;
        li {
          @include fs-5;
          color: black;
        }
        .table_list_label {
          width: 45%;
          ul {
            @include flex_start;
            li {
              @include fw-7;
            }
            li:nth-child(1) {
              width: 30%;
            }
            li:nth-child(2) {
              width: 70%;
            }
          }
        }
        .table_list_data {
          width: 55%;
          ul {
            @include flex_end;
            margin-bottom: 4px;
            gap: 14px;
            height: 21px;
            li {
              @include flex_end;
            }
            li:nth-child(1) {
              width: 50%;
            }
            li:nth-child(2) {
              @include fw-7;
              width: 50%;
              gap: 4px;
              span {
                @include fs-5;
                white-space: normal;
                word-break: break-all;
              }
            }
          }
          ul:last-child {
            margin-bottom: 0;
          }
        }
      }
      .table_list:last-child {
        border: none;
      }
    }
  }
}
</style>
