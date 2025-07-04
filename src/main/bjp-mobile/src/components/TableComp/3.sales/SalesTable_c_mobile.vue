<template>
  <div class="">
    <q-table
      class=""
      row-key="name"
      :rows="rows"

      flat

      hide-header
      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
    <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title" style="padding: 16px 0 12px;">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0;">
            <div class="flex_between">
              <DatePickerComp_mobile
                style="width: 105px;"
                v-model:input_model_props="searchParam.startDt"
              />
              <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px;">-</span>
              <DatePickerComp_mobile
                style="width: 105px;"
                v-model:input_model_props="searchParam.endDt"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              style="width: 64px;"
              @click="onClickSearch"
            />
          </div>
          <div class="" style="padding: 0 0 8px 0;">
            <BtnDateGroupComp_mobile btnStyle="active" />
          </div>
          <div class="" style="padding: 0 0 16px 0;">
            <!-- <BtnGroupComp_mobile
              :buttons="['전체', '미등록 n', '담당자 n', '처리중 n', '검수완료 n']"
              btnStyle="active"
            /> -->
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <div class="table_badge_wrap">
                <q-badge class="table_badge_red">{{ props.row.MED_NM }}</q-badge>
                <q-badge class="table_badge_blue">{{ props.row.PAY_ITEM_NM }}</q-badge>
              </div>
              <q-td>{{ props.row.ITEM_NM_UNIT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.BACK_EXAM_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td><span>등록</span><span>{{ props.row.BACK_DT }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.ITEM_CNT }}</span><span>종</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{  util.getNumberFormat(props.row.TOTAL_AMT) }}</span><span>원</span></q-td>
            </div>
            <div class="table_item">
              <q-td>
                <div class="">
                  <BtnComp_mobile
                    buttonText="반품 메모"
                    btnStyle="icon-white"
                    btnAlign="between"
                    :btnIcon="btnIcon"
                    @click="props.row.expand1 = !props.row.expand1"
                    style="width: 88px;"
                  />
                  <BtnComp_mobile
                    buttonText="담당자 메모"
                    btnStyle="icon-white"
                    btnAlign="between"
                    :btnIcon="btnIcon"
                    @click="props.row.expand2 = !props.row.expand2"
                    style="width: 100px;"
                  />
                </div>
                <div class="">
                  <DeleteComp_mobile
                    v-if="props.row.BACK_EXAM_CD === '01'"
                    delete_text1="반품현황"
                    delete_text2="반품 취소"
                    delete_btn="반품 취소"
                    :dataRow="props.row"
                    callFunction="onClickReturnCancel"
                  >
                    <BtnComp_mobile
                      buttonText="반품취소"
                      btnStyle="transparent-black"
                      style="width: 64px;"
                    />
                  </DeleteComp_mobile>

                  <BtnComp_mobile
                    buttonText="상세"
                    btnStyle="transparent-black"
                    style="width: 32px;"
                    @click="onViewDtl(props.row)"
                  />
                  <DialogLayout_mobile
                    dialog_title="반품현황 > 반품상세"
                    v-model:dialog_model_prop="props.row.dtl_model"
                    :dialog_full="dialog_full"
                  >
                    <div class="">
                      <SalesDetailComp_c_mobile :sn="props.row.SN" />
                    </div>
                  </DialogLayout_mobile>
                </div>
              </q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.row.expand1" :props="props">
          <div class="expand_table_wrap">
            <div class="expand_table_item">
              <q-td>반품 메모</q-td>
              <q-td>
                <BtnComp_mobile
                  buttonText="편집"
                  btnStyle="transparent-black"
                  style="width: 64px;"
                  @click="onViewMemo(props.row)"
                />
                <DialogLayout_mobile
                  dialog_title="반품현황 > 반품메모"
                  v-model:dialog_model_prop="props.row.memo_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <TextAreaComp_mobile
                      :memo="props.row.ORD_MEMO"
                      :sn="props.row.SN"
                      :is-visible="true"
                      submit="반품메모 수정"

                    />
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
          </div>
        </q-tr>
        <q-tr v-show="props.row.expand2" :props="props">
          <div class="expand_table_wrap">
            <div class="expand_table_item">
              <q-td>담당자 메모</q-td>
              <q-td>
                <BtnComp_mobile
                  buttonText="편집"
                  btnStyle="transparent-black"
                  style="width: 64px;"
                  @click="onViewManagerMemo(props.row)"
                />
                <DialogLayout_mobile
                  dialog_title="반품현황 > 담당자메모"
                  v-model:dialog_model_prop="props.row.manager_memo_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <TextAreaComp_mobile
                      :memo="props.row.TREAT_MEMO"
                      submit="담당자메모 수정"
                    />
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
          </div>
        </q-tr>


      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BtnDateGroupComp_mobile from'src/components/__commonComp/btnComp/BtnDateGroupComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import SalesDetailComp_c_mobile from 'src/components/DialogComp/3.salesDialogComp/SalesDetailComp_c_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';
import DeleteComp_mobile from 'src/components/DialogComp/__commonDialogComp/DeleteComp_mobile.vue';
import TextAreaComp_mobile from 'src/components/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue';


const dialog_full = ref(true)

const btnIcon = ref("img:/public/assets/icons/sort-down-black.svg");

const bus = inject("bus");
const userInfo = util.getUserData();
const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  startDt: "",
  endDt: "",
});

const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})

//---------------------------- Function ------------------------------//
// 검색버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

async function fetchItemList() {
  searchParam.value.startDt = moment(searchParam.value.startDt).format("YYYYMMDD");
  searchParam.value.endDt = moment(searchParam.value.endDt).format("YYYYMMDD");

  const res = await api.get("/bakReq/listSearch", {
    params: searchParam.value,
  });

  searchParam.value.startDt = moment(searchParam.value.startDt).format("YYYY.MM.DD");
  searchParam.value.endDt = moment(searchParam.value.endDt).format("YYYY.MM.DD");

  rows.value = res;
}

//기간 클릭
bus.off("onClickPeriod");
bus.on("onClickPeriod", async (params) => {
  searchParam.value.startDt = params.value.startDt
  searchParam.value.endDt = params.value.endDt

  await fetchItemList();
});


async function onViewDtl(row) {
  for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].dtl_model = false;
    }
    row.dtl_model = true;

    //await bus.emit("pmntItemDetail", props.row);
}

async function onViewMemo(row) {
  for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].memo_model = false;
    }
    row.memo_model = true;

    //await bus.emit("pmntItemDetail", props.row);
}

async function onViewManagerMemo(row) {
  for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].manager_memo_model = false;
    }
    row.manager_memo_model = true;

    //await bus.emit("pmntItemDetail", props.row);
}

bus.on("onClickReturnCancel", async (row) => {
  try {
    Object.assign(row, { USER_ID: userInfo.USER_ID });

    await api.delete("/bakReq/cancelBakReq", { params: row });

    util.showNotify("취소되었습니다.");

    await fetchItemList();
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
});

bus.off("fetchItemList");
bus.on("fetchItemList", async () => {
  await fetchItemList();
});

</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 8px 0 !important;
  gap: 4px;
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 5;
    gap: 2px;
    td {
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 8px;
      span {
        @include fs-5;
      }
      span:nth-child(2) {
        color: #666666;
      }
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 2px;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(5){
    grid-column: span 2;
    td {
      @include flex_end;
      gap: 2px;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(6){
    grid-column: span 6;
    td {
      @include flex_between;
      div:nth-child(1) {
        @include flex_start;
        gap: 8px;
      }
      div:nth-child(2) {
        @include flex_end;
        gap: 8px;
      }
    }
  }
}




.expand_table_wrap {
  .expand_table_item {
    @include flex_between;
    padding: 8px !important;
    border-bottom: 1px solid rgba(0,0,0,0.2);
    td {

    }
  }
}




</style>
