<template>
  <div class="">
    <q-table
      class="interestTable_a"
      :columns="columns"
      :rows="rows"
      row-key="GROUP_NO"
      table-header-class="table_header"
      dense
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :selected="selected"
    >
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="">
            <div
              class="q-table_header_wrap flex_between"
              style="margin-bottom: 12px"
            >
              <div class="q-table_header_title">
                <p>관심그룹</p>
              </div>
              <div class="">
                <DialogComp_groupControl>
                  <BtnComp_default
                    class="btn-line default"
                    btn-label="그룹관리"
                    @click="onClickGroupControl"
                  />
                </DialogComp_groupControl>
              </div>
            </div>
          </div>
        </div>
      </template>

      <!-- 할당재고 프로젝트 완료 후 해당 내용 제거 현재는 임시로 입고알림품목, 알림품목 조회 안되게 처리함(6.17)
            .filter((item)=>item.GROUP_NO==0)만 제거하면 원복됨 -->
      <template v-slot:top-row>
        <q-tr
          v-for="(items, i) in top_rows.filter((item) => item.GROUP_NO == 0)"
          :key="i"
        >
          <q-td
            class="btm-line td-grp"
            style="font-weight: bold; cursor: pointer"
            @click="onRowClick(items)"
          >
            {{ items.GROUP_NM }}
          </q-td>
          <q-td
            class="btm-line td-qty"
            style="text-align: center"
            @click="onRowClick(items)"
          >
            {{ items.ITEM_QTY }}
          </q-td>
          <!-- <q-td class="btm-line" /> -->
          <q-td class="btm-line td-edit">
            <div
              class="flex_center"
              v-if="items.GROUP_NM === '알림품목' && items.ITEM_QTY > 0"
            >
              <img
                src="assets/icons/bell-solid.svg"
                alt="icon"
                style="cursor: pointer"
                @click="onClickAlarm"
              />
            </div>
            <div v-else>&#160;</div>
          </q-td>
          <q-td @click="onClickDelete(items)" class="btm-line td-del">
            <div
              class="q-table_del flex_center"
              v-if="items.GROUP_NM === '기본그룹'"
            >
              <img src="assets/images/ico_delete.svg" alt="비우기" />
            </div>
          </q-td>
        </q-tr>
      </template>

      <!-- <template v-slot:bottom-row>
        <q-tr v-for="(items, i) in rows" :key="i">
          <q-td style="font-weight: bold" @click="onRowClick(items)">
            {{ items.GROUP_NM }}
          </q-td>
          <q-td style="text-align: center" @click="onRowClick(items)">
            {{ items.ITEM_QTY }}
          </q-td>
          <q-td>
            <div class="flex_center">
              <BtnComp_active_small btn_activeLabel="수정" style="padding: 0" />
              <BtnComp_default_small btn_defaultLabel="확인" />
            </div>
          </q-td>
          <q-td class="">
            <div
              class="flex_center"
              v-if="items.GROUP_NM === '알림품목' && items.ALARM_YN === 'Y'"
            >
              <img src="assets/icons/bell-solid.svg" alt="icon" />
            </div>
          </q-td>
          <q-td @click="onClickDelete(items)">
            <div class="q-table_del flex_center">
              <img src="assets/icons/x-solid.svg" alt="x" />
            </div>
          </q-td>
        </q-tr>
      </template> -->

      <template v-slot:body-cell-GROUP_NM="props">
        <q-td
          :class="{ 'q-tr--selected': isSelected(props.row) }"
          class="td-grp"
          @click="onRowClick(props.row)"
        >
          <p v-if="props.row.EDIT_YN === 'N'">
            {{ props.row.GROUP_NM }}
          </p>
          <q-input
            v-if="props.row.EDIT_YN === 'Y'"
            :model-value="props.row.GROUP_NM"
            dense
            outlined
            @update:model-value="(val) => onUpdateName(props.row, val)"
            @keyup.enter="(val) => onUpdateName(props.row, val.target.value)"
          />
        </q-td>
      </template>

      <template v-slot:body-cell-EDIT_YN="props">
        <q-td :props="props" class="td-edit">
          <BtnComp_active_small
            class="btn-line default"
            v-if="props.row.EDIT_YN === 'N'"
            btn_activeLabel="수정"
            style="padding: 0"
            @click="onClickGroupNmEdit(props.row)"
          />
          <BtnComp_active_small
            class="btn-line default strong"
            v-if="props.row.EDIT_YN === 'Y'"
            btn_activeLabel="확인"
            @click="onClickGroupNmSave(props.row)"
            :is-disable="updateBtnDisable(props.row)"
          />
        </q-td>
      </template>

      <!-- <template v-slot:body-cell-ALARM_YN="props">
        <q-td :props="props" />
      </template> -->

      <template v-slot:body-cell-비우기="props">
        <q-td :props="props" @click="onClickDelete(props.row)" class="td-del">
          <div class="q-table_del flex_center">
            <img src="assets/images/ico_delete.svg" alt="비우기" />
          </div>
        </q-td>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_options_month from "src/components/web/common/BtnComp_options_month.vue";
import Pagination_default from "src/components/web/common/Pagination_default.vue";
import DialogComp_groupControl from "src/components/web/interest/InterestGroupControl.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const top_rows = ref([]);
const rows = ref([]);
const selected = ref([]);

const beforeGnm = ref("");

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  brchCd: userInfo.BRCH_CD,
});

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const { fixedList, list } = await api.get("/interest/interestGroupList", {
    params: searchParam.value,
  });

  top_rows.value = fixedList;
  rows.value = list;
}

// 그룹관리
async function onClickGroupControl() {
  bus.emit("searchItemGroups");
}

const isSelected = (row) => {
  return selected.value.includes(row);
};

// row 클릭
async function onRowClick(data) {
  const index = selected.value.indexOf(data);

  if (index === -1) {
    selected.value = [];
    selected.value.push(data);
  } else {
    selected.value.splice(index, 1);
  }

  await bus.emit("searchGroupItems", data);
  await bus.emit("initInterestItemDtl");
}

async function onClickRow(data) {
  console.log(data);
}

// 입고알림 클릭
async function onClickAlarm() {
  if (!confirm("모든 품목에 대한 입고알림을 해제하시겠습니까?")) return;

  try {
    await api.put("/interest/saveAllAlarmYn", searchParam.value);

    util.showNotify("모든 입고알림이 해제되었습니다.");

    await fetchItemList();
    await bus.emit("refreshGroupItems");
  } catch (error) {
    alert(error.response.data);
  }
}

// 수정 버튼 클릭
async function onClickGroupNmEdit(row) {
  beforeGnm.value = row.GROUP_NM;
  row.EDIT_YN = "Y";
}

// 확인 버튼 diable 여부
function updateBtnDisable(row) {
  if (beforeGnm.value !== row.GROUP_NM) {
    return false;
  } else {
    return true;
  }
}

// 확인 버튼 클릭
async function onClickGroupNmSave(row) {
  if (row.GROUP_NM.length > 10) {
    alert("그룹명은 최대 10자리를 넘을 수 없습니다.");
    return;
  }

  row.EDIT_YN = "N";

  try {
    await api.put("/interest/editGroupNm", {
      groupNm: row.GROUP_NM,
      groupNo: row.GROUP_NO,
      custCd: userInfo.CUST_CD,
      userId: userInfo.USER_ID,
    });

    util.showNotify("그룹명이 수정되었습니다.");

    await fetchItemList();
  } catch (error) {
    alert(error.response.data);
  }
}

const onUpdateName = (row, val) => {
  row.GROUP_NM = val;
};

// 비우기 클릭
async function onClickDelete(data) {
  const confirmMsg = `${data.GROUP_NM} 그룹의 관심상품(입고알림품목 제외)을 비우시겠습니까?`;

  if (!confirm(confirmMsg)) return;

  try {
    await api.delete("/interest/removeMyItems", { params: data });

    util.showNotify("비워졌습니다.");
    // await bus.emit("searchGroupItems", data);
    await fetchItemList();
    await onRowClick(top_rows.value[2]);
  } catch (error) {
    alert(error.response.data);
  }
}

bus.on("refreshInterestGrp", async () => {
  await fetchItemList();
});

onMounted(async () => {
  await fetchItemList();
  await onRowClick(top_rows.value[2]);
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "GROUP_NM",
    label: "그룹명",
    field: "GROUP_NM",
    align: "left",
  },
  {
    name: "ITEM_QTY",
    label: "품목수",
    field: "ITEM_QTY",
    align: "center",
    sortable: true,
  },
  {
    name: "EDIT_YN",
    label: "수정",
    field: "EDIT_YN",
    align: "center",
  },
  //{ name: "ALARM_YN", label: "입고알림", field: "ALARM_YN", align: "center" },
  {
    name: "비우기",
    label: "비우기",
    field: "비우기",
    align: "center",
  },
]);
</script>

<style lang="scss" scoped>
.flex_between {
  @include flex_between;
}

.interestTable_a {
  // height: 495px;
  height: calc(400px - #{rem(28px)});
  overflow-y: auto;
  @media screen and (max-width: 830px) {
    height: 195px;
  }
}
</style>
