<template>
  <div class="table_a_sec">
    <q-table
      class="table_a_wrap"
      :rows="rows"
      :columns="columns"
      row-key="name"
      hide-bottom
      table-header-class="table-header-class"
      :filter="filter"
      :filter2="filter2"
      table-class="table-class_a"
      dense
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="48"
      v-model:pagination="pagination"
    >
      <template v-slot:top-left>
        <div class="top-left_input_wrap">
          <q-input
            v-model="searchOption.keyword"
            input-class="top-left_input"
            borderless
            dense
            debounce="50"
            placeholder="보험코드/품명 입력"
          />
          <q-input
            v-model="searchOption.maker"
            input-class="top-left_input"
            borderless
            dense
            debounce="50"
            placeholder="제약사 입력"
          />
          <BtnComp_default btnLabel="검색" @click="onClickItemSearch" />
          <q-checkbox
            v-model="searchOption.history"
            true-value="Y"
            false-value="N"
            class="top-left_check"
            label="이력"
          />
          <q-checkbox
            v-model="searchOption.excludingOutOfOtock"
            class="top-left_check"
            true-value="Y"
            false-value="N"
            label="품절제외"
          />
          <!-- <q-option-group
            class="top-left_check"
            v-model="top_check"
            :options="searchOption"
            type="checkbox"
            color="blue-10"
            size="xs"
            inline
            dense
          /> -->
        </div>
      </template>

      <template v-slot:top-right>
        <div class="top-right_btn">
          <BtnComp_default btnLabel="동일성분" />
          <BtnComp_default btnLabel="동일효능" />
        </div>
      </template>

      <template v-slot:body-cell-name="props">
        <q-td :props="props" style="padding: 0">
          <div class="cell_name">
            <div class="cell_name_badge">
              <template v-if="props.row.badge === 1">
                <q-badge text-color="red" color="white" class="">전</q-badge>
                <q-badge text-color="blue" color="white" class="">급</q-badge>
              </template>
              <template v-else-if="props.row.badge === 2">
                <q-badge text-color="red" color="white" class="">전</q-badge>
                <q-badge text-color="blue" color="white" class="">비</q-badge>
              </template>
              <template v-else-if="props.row.badge === 3">
                <q-badge text-color="red" color="white" class="">일</q-badge>
                <q-badge text-color="blue" color="white" class="">급</q-badge>
              </template>
              <template v-else-if="props.row.badge === 4">
                <q-badge text-color="red" color="white" class="">일</q-badge>
                <q-badge text-color="blue" color="white" class="">비</q-badge>
              </template>
            </div>
            <div
              class="my-table-details table_font-size-14"
              style="font-weight: bold"
            >
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-반품="props">
        <q-td :props="props" style="padding: 0">
          <div
            class="my-table-details table_font-size-14"
            style="color: #f5536c; font-weight: bold"
          >
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-재고="props">
        <q-td :props="props" style="padding: 0">
          <template v-if="isNumber(props.value)">
            {{ props.value }}
          </template>
          <template v-else>
            <div
              class="my-table-details table_font-size-14"
              style="color: #f5536c; font-weight: bold"
            >
              품절
            </div>
          </template>
        </q-td>
      </template>

      <template v-slot:body-cell-수량="props">
        <q-td :props="props">
          <div class="quantity_wrap">
            <div class="quantity_img_wrap" @click="decrement">
              <svg-icon
                type="mdi"
                :path="mdiMinus"
                style="width: 14px; height: 14px; color: #666666"
              ></svg-icon>
            </div>
            <p class="table_font-size-14">
              <input
                class=""
                v-model="count"
                @input="updateCount"
                style="border: none; width: 16px; text-align: center"
              />
            </p>
            <div class="quantity_img_wrap" @click="increment">
              <svg-icon
                type="mdi"
                :path="mdiPlus"
                style="width: 14px; height: 14px; color: #666666"
              ></svg-icon>
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-담기="props">
        <q-td :props="props">
          <div class="add_wrap table_font-size-14">
            {{ props.row.담기 === "광역" ? "광역" : "" }}
          </div>
          <div class="minus_wrap table_font-size-14">
            {{ props.row.담기 === "담기" ? "담기" : "" }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-이력="props">
        <q-td :props="props">
          <q-checkbox
            class="recode_wrap"
            :checked-icon="mdiCheck"
            v-model="props.row.이력"
            :options="recode_option"
          />
        </q-td>
      </template>

      <template v-slot:body-cell-관심="props">
        <q-td :props="props">
          <div class="">
            <q-checkbox
              class="like_wrap"
              v-model="props.row.관심"
              :checked-icon="mdiHeart"
              :unchecked-icon="mdiHeartOutline"
              :options="like_option"
            />
          </div>
        </q-td>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//------------------- Import & Declaration ---------------------//

import { ref } from "vue";
import {
  mdiHeart,
  mdiHeartOutline,
  mdiCheck,
  mdiPlus,
  mdiMinus,
} from "@mdi/js";
import BtnComp_default from "./BtnComp_default.vue";
import SvgIcon from "@jamescoyle/vue-icon";

const searchOption = ref({
  keyword: "",
  maker: "",
  history: "N",
  excludingOutOfOtock: "N",
});

//------------------------- Function ---------------------------//

async function onClickItemSearch() {
  const keyword = searchOption.value.keyword;
  const maker = searchOption.value.maker;

  if (keyword.length === 0 && maker.length === 0) {
    alert("보험코드/품명을 입력하세요.");
    return;
  } else if (keyword.length === 0 && maker.length !== 0) {
    alert("보험코드/품명과 같이 입력해야합니다.");
    return;
  }
}

//   setup() {
//     return {
//       columns,
//       rows,
//       filter: ref(""),
//       filter2: ref(""),

//       pagination: ref({
//         rowsPerPage: 0,
//       }),

//       top_check: ref(["op1"]),
//       top_check_option: [
//         {
//           label: "이력",
//           value: "op1",
//         },
//         {
//           label: "품절제외",
//           value: "op2",
//         },
//       ],

//       recode: ref(true),
//       like: ref(true),
//     };
//   },

//----------------------- Grid Setting -------------------------//
const columns = [
  {
    name: "name",
    label: "품목",
    sortable: true,
    align: "left",
    required: true,
    field: (row) => row.name,
    format: (val) => `${val}`,
  },
  { name: "규격", label: "규격", field: "규격", align: "center" },
  { name: "반품", label: "반품", field: "반품", align: "center" },
  {
    name: "제약사",
    label: "제약사",
    field: "제약사",
    align: "center",
    sortable: true,
  },
  { name: "단가", label: "단가", field: "단가", align: "center" },
  { name: "재고", label: "재고", field: "재고", align: "center" },
  { name: "수량", label: "수량", field: "수량", align: "center" },
  { name: "담기", label: "담기", field: "담기", align: "center" },
  {
    name: "이력",
    label: "이력",
    field: "이력",
    align: "center",
    sortable: true,
  },
  { name: "관심", label: "관심", field: "관심", align: "center" },
];

const rows = [
  {
    name: "우루사정300mg(병)",
    규격: "30T",
    반품: "",
    제약사: "대웅제약",
    단가: 8190,
    재고: 458,
    수량: "",
    담기: "광역",
    이력: true,
    관심: true,
    badge: 1,
  },
  {
    name: "우루사정100mg",
    규격: "30T",
    반품: "",
    제약사: "대웅제약",
    단가: 27000,
    재고: 426,
    수량: "",
    담기: "담기",
    이력: true,
    관심: false,
    badge: 3,
  },
  {
    name: "우루사정100mg",
    규격: "50T",
    반품: "",
    제약사: "대웅제약",
    단가: 45000,
    재고: 375,
    수량: "",
    담기: "담기",
    이력: true,
    관심: true,
    badge: 3,
  },
  {
    name: "복합우루사연질캡슐",
    규격: "30T",
    반품: "",
    제약사: "대웅제약",
    단가: 31200,
    재고: 124,
    수량: "",
    담기: "광역",
    이력: true,
    관심: false,
    badge: 4,
  },
  {
    name: "우루사정200mg",
    규격: "30T",
    반품: "불가",
    제약사: "대웅제약",
    단가: 90000,
    재고: 95,
    수량: "",
    담기: "담기",
    이력: false,
    관심: false,
    badge: 1,
  },
  {
    name: "복합우루사연질캡슐(PTP)",
    규격: "90T",
    반품: "",
    제약사: "대웅제약",
    단가: 27300,
    재고: 72,
    수량: "",
    담기: "담기",
    이력: false,
    관심: true,
    badge: 4,
  },
  {
    name: "대웅우루사연질캡슐50mg",
    규격: "30T",
    반품: "",
    제약사: "대웅제약",
    단가: 4190,
    재고: 30,
    수량: "",
    담기: "담기",
    이력: false,
    관심: true,
    badge: 4,
  },
  {
    name: "대웅우루사연질캡슐50mg(PTP)",
    규격: "30C",
    반품: "불가",
    제약사: "대웅제약",
    단가: 24500,
    재고: "",
    수량: "",
    담기: "담기",
    이력: false,
    관심: false,
    badge: 4,
  },
  {
    name: "대웅우루사연질캡슐50mg",
    규격: "30T",
    반품: "",
    제약사: "대웅제약",
    단가: 36000,
    재고: "",
    수량: "",
    담기: "담기",
    이력: false,
    관심: false,
    badge: 4,
  },
  {
    name: "대웅우루사연질캡슐50mg",
    규격: "30T",
    반품: "",
    제약사: "대웅제약",
    단가: 36000,
    재고: "",
    수량: "",
    담기: "담기",
    이력: false,
    관심: false,
    badge: 4,
  },
];

// export default {
//   components: { BtnComp_default, SvgIcon },
//   data() {
//     return {
//       count: 0,
//       mdiHeart: mdiHeart,
//       mdiHeartOutline: mdiHeartOutline,
//       mdiCheck: mdiCheck,
//       mdiPlus: mdiPlus,
//       mdiMinus: mdiMinus,
//     };
//   },
//   setup() {
//     return {
//       columns,
//       rows,
//       filter: ref(""),
//       filter2: ref(""),

//       pagination: ref({
//         rowsPerPage: 0,
//       }),

//       top_check: ref(["op1"]),
//       top_check_option: [
//         {
//           label: "이력",
//           value: "op1",
//         },
//         {
//           label: "품절제외",
//           value: "op2",
//         },
//       ],

//       recode: ref(true),
//       like: ref(true),
//     };
//   },
//   methods: {
//     increment() {
//       this.count++;
//     },
//     decrement() {
//       if (this.count > 0) {
//         this.count--;
//       }
//     },
//     updateCount(event) {
//       const newValue = parseInt(event.target.value);
//       if (!isNaN(newValue)) {
//         this.count = newValue;
//       }
//     },

//     isNumber(value) {
//       return typeof value === "number" && !isNaN(value);
//     },

//     onClickItemSearch() {

//     },
//   },
// };
</script>

<style lang="scss">
.table_a_wrap {
  height: 500px;
  border: none;
  overflow-y: auto;

  /* Custom scrollbar styles for .table_a_wrap */
  scrollbar-width: thin;
  scrollbar-color: var(--scrollbar-thumb-dark) var(--scrollbar-track-dark);

  &::-webkit-scrollbar {
    width: var(--scrollbar-width-height);
  }

  &::-webkit-scrollbar-track {
    background: var(--scrollbar-track-dark);
  }

  &::-webkit-scrollbar-thumb {
    background-color: var(--scrollbar-thumb-dark);
    border-radius: 5px;

    &:hover {
      background-color: var(--scrollbar-thumb-hover-dark);
    }
  }
}
.table_font-size-14 {
  @include fs-5;
}
.table_font-size-12 {
  @include fs-6;
}

.table_a_sec div {
  padding: 0;
  box-shadow: none !important;
}
.table-header-class th {
  @include fw-7;
  background-color: #eee;
  border-top: 2px solid $b4 !important;
}

.table_a_wrap thead tr th {
  position: sticky;
  z-index: 1;
}
.table_a_wrap thead tr:last-child th {
  top: 48px;
}
.table_a_wrap thead tr:first-child th {
  top: 0;
}
.table_a_wrap tbody {
  scroll-margin-top: 48px;
}

.top-left_input_wrap {
  @include flex_center;
  gap: 8px;
  margin: 12px 44px;
}
.top-left_input {
  width: 120px;
  border: 1px solid #666666 !important;
  border-radius: 8px;
  padding-left: 6px;
}
.top-left_input::placeholder {
  @include fs-5;
}
.top-left_input_wrap .q-field--dense .q-field__control {
  height: 28px !important;
}
.top-right_btn {
  @include flex_center;
  gap: 16px;
}
.top-left_check .q-checkbox--dense .q-checkbox__label {
  @include fs-6;
  @include fw-7;
}

.cell_name {
  @include flex_start;
  padding: 0 10px !important;
  gap: 5px;
}
.cell_name_badge {
  @include flex_center;
  gap: 4px;
}

.quantity_wrap {
  @include flex_center;
  margin: 0 auto;
  border: 1px solid #666666;
  border-radius: 2.4px;
  width: 55px;
}
.quantity_img_wrap {
  @include flex_center;
  cursor: pointer;
}
.quantity_img {
  padding: 5px 7px;
}

.add_wrap {
  color: $w;
  background-color: $b3;
  border-radius: 2.4px;
}

.minus_wrap {
  color: $w;
  background-color: #999999;
  border-radius: 2.4px;
}

.recode_wrap div {
  border: none !important;
}

.recode_wrap .q-icon,
.like_wrap .q-icon {
  font-size: 20px;
}
.recode_wrap .q-icon {
  color: $b3;
}
.like_wrap .q-icon {
  color: $r2;
}
</style>
