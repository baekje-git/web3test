<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="groupMove_title">
            <p>그룹 이동</p>
            <q-btn class="flex_center" flat dense @click="onClickClose">
              <img src="assets/icons/x-solid.svg" alt="icon" />
            </q-btn>
          </div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>그룹 이동</span>
        </div>

        <div class="groupMove_group_wrap">
          <q-table
            class="groupMove_Table_wrap"
            :columns="columns"
            :rows="rows"
            row-key="GROUP_NO"
            table-header-class="table_header"
            dense
            hide-bottom
            hide-header
            selection="single"
            v-model:selected="selected"
            color="blue-10"
            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
          >
            <template v-slot:top>
              <div class="groupmove_table_top_wrap">
                <p>
                  품목
                  <span>
                    {{ JSON.stringify(selectedItems.length) }}
                  </span>
                  개 선택
                </p>
              </div>
            </template>

            <template v-slot:body-cell-GROUP_NM="props">
              <!-- <q-tr :props="props"> -->
              <q-td colspan="100%" :props="props" style="text-align: left">
                {{ props.row.GROUP_NM }}
              </q-td>
              <!-- </q-tr> -->
            </template>
          </q-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default btn-label="저장" @click="onClickSave" />
          <BtnComp_active btn-label="닫기" @click="onClickClose" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, watch, computed, defineEmits } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import { data } from "autoprefixer";

const userInfo = util.getUserData();
const bus = inject("bus");
const emit = defineEmits(["close"]);

const selected = ref([]);

const dataParams = ref({
  custCd: userInfo.CUST_CD,
  userId: userInfo.USER_ID,
  groupNm: "",
});

const props = defineProps({
  isOpen: { type: Boolean, default: false },
});

const isOpenPopup = computed(() => {
  return props.isOpen;
});

// 부모창에서 선택한 품목
const selectedItems = ref([]);

const saveData = ref([]);
const groupNm = ref("");

//---------------------------- Function ------------------------------//

async function fetchData() {
  const { fixedList, list } = await api.get("/interest/interestGroupList", {
    params: dataParams.value,
  });

  rows.value.push(...list);
}

// 저장
async function onClickSave() {
  if (selected.value.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  if (groupNm.value === selected.value[0].GROUP_NM) {
    alert("동일한 그룹을 선택하였습니다.");
    return;
  }

  selectedItems.value.forEach((m) => {
    saveData.value.push({ ITEM_CD: m.ITEM_CD });
  });

  saveData.value.forEach((m) => {
    Object.assign(m, { CUST_CD: selected.value[0].CUST_CD });
    Object.assign(m, { GROUP_NO: selected.value[0].GROUP_NO });
    Object.assign(m, { USER_ID: userInfo.USER_ID });
  });

  try {
    await api.put("/interest/moveGroup", saveData.value);
    util.showNotify("이동되었습니다.");

    bus.emit("refreshInterestGrp");
    bus.emit("refreshGroupItems");

    selectedItems.value = [];
    saveData.value = [];

    emit("close");
  } catch (error) {
    alert(error.response.data);
  }
}

// 닫기
async function onClickClose() {
  emit("close");
  rows.value = [
    {
      CUST_CD: userInfo.CUST_CD,
      GROUP_NM: "기본그룹",
      GROUP_NO: 0,
    },
  ];
}

bus.on("groupMove", (data) => {
  selectedItems.value = data.list;
  groupNm.value = data.groupNm;
});

watch(isOpenPopup, async () => {
  if (isOpenPopup.value) {
    await fetchData();
  } else {
    selected.value = [];
    rows.value = [
      {
        CUST_CD: userInfo.CUST_CD,
        GROUP_NM: "기본그룹",
        GROUP_NO: 0,
      },
    ];
  }
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "GROUP_NM",
    label: "기본그룹",
    field: "GROUP_NM",
    align: "center",
  },
]);

const rows = ref([
  {
    CUST_CD: userInfo.CUST_CD,
    GROUP_NM: "기본그룹",
    GROUP_NO: 0,
  },
]);
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
.groupMove_title {
  @include flex_between;
  border-bottom: solid 1px #999 !important;
  padding-bottom: 16px;
  p {
    @include fw-7;
  }
}
.groupMove_group_wrap {
  padding: 0 16px;
  width: 480px;
}

.groupMove_Table_wrap {
  max-height: 195px;
  overflow-y: auto;
  tr {
    max-height: 40px !important;
  }
  td {
    border: none !important;
  }
}
.groupmove_table_top_wrap {
  width: 100%;
  padding: 4px 16px;
  margin-bottom: 8px;
  border: 1px solid #999;
  p {
    @include fs-5;
    span {
      @include fs-5;
      @include fw-7;
    }
  }
}

.q-card_btn_wrap {
  @include flex_end;
  gap: 8px;
}
</style>
