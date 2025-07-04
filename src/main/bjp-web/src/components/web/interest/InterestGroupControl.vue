<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="groupControl_title"><p>그룹관리</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>그룹관리</span>
        </div>

        <div class="groupControl_section_wrap">
          <div class="groupControl_table_header_wrap">
            <q-input
              v-model="dataParams.groupNm"
              class="groupControl_input"
              placeholder="새로운 그룹명을 입력해 주세요."
              borderless
              style="width: 85%"
              @keyup.enter="onClickAddGroup"
            />

            <BtnComp_default btn-label="추가" @click="onClickAddGroup" />
          </div>
        </div>

        <div class="groupControl_group_wrap">
          <q-table
            class="groupControl_Table_wrap"
            :columns="top_columns"
            :rows="top_rows"
            row-key="name"
            table-header-class="table_header"
            dense
            hide-bottom
            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            hide-header
          >
          </q-table>
        </div>

        <div class="groupControl_group_wrap">
          <q-table
            class="groupControl_Table_wrap"
            :columns="bottom_columns"
            :rows="bottom_rows"
            row-key="GROUP_NO"
            table-header-class="table_header"
            dense
            hide-bottom
            :selected-rows-label="getSelectedString"
            selection="multiple"
            v-model:selected="selected"
            color="blue-10"
            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            hide-header
          >
            <template v-slot:header-cell-관심그룹="props">
              <q-th>
                <div class="flex_between">
                  <span>
                    {{ props.col.label }}
                  </span>
                  <div class="flex_center" style="gap: 16px">
                    <img
                      src="assets/icons/sort-down-solid.svg"
                      alt="icon"
                      style="cursor: pointer"
                    />
                    <img
                      src="assets/icons/sort-up-solid.svg"
                      alt="icon"
                      style="cursor: pointer"
                    />
                  </div>
                </div>
              </q-th>
            </template>

            <template v-slot:body-cell-관심그룹="props">
              <q-td>
                <div class="flex_between">
                  <span>
                    {{ props.row.GROUP_NM }}
                  </span>
                  <div class="flex_center" style="gap: 10px">
                    <img
                      src="assets/icons/sort-down-solid.svg"
                      alt="icon"
                      style="cursor: pointer"
                      @click="onClickSortDown(props)"
                    />
                    <img
                      src="assets/icons/sort-up-solid.svg"
                      alt="icon"
                      style="cursor: pointer"
                      @click="onClickSortUp(props)"
                    />
                  </div>
                </div>
              </q-td>
            </template>
          </q-table>
        </div>

        <q-card-actions class="q-card_btn_wrap" style="margin-top: 50px">
          <BtnComp_default btn-label="순서 저장" @click="saveSortNo" />
          <BtnComp_default
            class="btn-line default"
            btn-label="삭제"
            @click="onClickDeleteGroups"
          />
          <BtnComp_active
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

import { ref, inject, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_red from "src/components/web/common/BtnComp_red.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const isOpenPopup = ref(false);

const selected = ref([]);
const getSelectedString = () => {
  return selected.value.length === 0
    ? ""
    : `${selected.value.length} record${
        selected.value.length > 1 ? "s" : ""
      } selected of ${rows.length}`;
};

const dataParams = ref({
  custCd: userInfo.CUST_CD,
  userId: userInfo.USER_ID,
  groupNm: "",
});

const bottom_rows = ref([]);
const dataList = ref([]);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const { fixedList, list } = await api.get("/interest/interestGroupList", {
    params: dataParams.value,
  });

  dataList.value = list;
  bottom_rows.value = list;
}

// 추가
async function onClickAddGroup() {
  if (dataParams.value.groupNm === "") {
    alert("그룹명을 입력해주세요.");
    return;
  }

  if (dataParams.value.groupNm.length > 10) {
    alert("그룹명은 최대 10자리를 넘을 수 없습니다.");
    dataParams.value.groupNm = "";
    return;
  }

  try {
    await api.post("/interest/addGroup", dataParams.value);
    await fetchData();

    dataParams.value.groupNm = "";
    bus.emit("refreshInterestGrp");

    util.showNotify("관심그룹이 추가되었습니다.");
  } catch (error) {
    alert(error.response.data);
  }
}

// down
async function onClickSortDown(data) {
  const idx = data.rowIndex;

  if (dataList.value[idx + 1]) {
    data.row.SORT_NO++;
    dataList.value[idx + 1].SORT_NO--;

    bottom_rows.value.sort(function (a, b) {
      return a.SORT_NO - b.SORT_NO;
    });
  }
}

// up
async function onClickSortUp(data) {
  const idx = data.rowIndex;

  if (dataList.value[idx - 1]) {
    data.row.SORT_NO--;
    dataList.value[idx - 1].SORT_NO++;

    bottom_rows.value.sort(function (a, b) {
      return a.SORT_NO - b.SORT_NO;
    });
  }
}

// 순서 저장
async function saveSortNo() {
  try {
    const saveData = bottom_rows;

    saveData.value.forEach((m) => {
      Object.assign(m, { USER_ID: userInfo.USER_ID });
    });

    await api.put("/interest/saveSortNo", saveData.value);
    await fetchData();

    bus.emit("refreshInterestGrp");

    util.showNotify("순서가 저장되었습니다.");
  } catch (error) {
    alert(error.response.data);
  }
}

// 삭제
async function onClickDeleteGroups() {
  if (selected.value.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  const confirmMsg =
    "관심그룹 삭제 시 상품이 모두 삭제됩니다.\n삭제 하시겠습니까?";

  if (!confirm(confirmMsg)) return;

  try {
    await api.delete("/interest/removeGroups", { data: selected.value });
    await fetchData();

    selected.value = [];

    bus.emit("refreshInterestGrp");
  } catch (error) {
    alert(error.response.data);
  }
}

watch(isOpenPopup, async () => {
  if (isOpenPopup.value) {
    await fetchData();
  } else {
    selected.value = [];
  }
});

//-------------------------- Grid Setting ----------------------------//

const top_columns = ref([
  {
    name: "기본그룹",
    label: "기본그룹",
    align: "left",
    required: true,
    field: (row) => row.name,
    format: (val) => `${val}`,
  },
]);
const top_rows = ref([
  {
    name: "기본그룹",
  },
  // {
  //   name: "알림품목",
  // },
]);

const bottom_columns = ref([
  {
    name: "관심그룹",
    label: "관심그룹1",
    align: "left",
    required: true,
    field: (row) => row.name,
    format: (val) => `${val}`,
  },
]);
</script>

<style lang="scss">
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 10px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.groupControl_title p {
  @include tblTit;
  padding: 0 0 16px 0;
  border-bottom: solid 1px $line-400;
}
.groupControl_group_wrap,
.groupControl_section_wrap {
  padding: 0 16px;
  width: 480px;
}
.groupControl_group_wrap {
  // margin-bottom: 40px;
}

.groupControl_table_header_wrap {
  @include flex_between;
  padding-bottom: 16px;
}

.groupControl_input {
  height: 32px;
  border: 1px solid $line-400;
  border-radius: 0;
  width: 100%;
}
.groupControl_input .q-field__control {
  padding: 0 8px;
  height: 32px !important;
}

.groupControl_Table_wrap {
  max-height: 125px;
  overflow-y: auto;
}
.groupControl_Table_wrap tr {
  height: 40px !important;
}

.q-card_btn_wrap {
  @include flex_end;
  gap: 8px;
}
</style>
