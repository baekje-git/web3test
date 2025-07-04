<template>
  <div class="table_sec">
    <q-option-group
      v-model="group"
      :options="options"
      color="blue-10"
      size="xs"
      inline
      dense
    />
    <q-table
      class="table_b_wrap"
      :rows="rows"
      :columns="columns"
      row-key="name"
      :selected-rows-label="getSelectedString"
      selection="multiple"
      color="blue-10"
      v-model:selected="selected"
      table-header-class="table-header-class"
      table-class="table-class_b"
      dense
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="48"
      v-model:pagination="pagination"
    >
      <template v-slot:body-cell-name="props">
        <q-td :props="props" style="padding: 0">
          <div
            class="my-table-details table_font-size-14"
            style="font-weight: bold"
          >
            {{ props.value }}
          </div>
          <p v-if="props.row.수량 === 0" class="table_font-size-12">
            (제약사품절)
          </p>
        </q-td>
      </template>

      <template v-slot:body-cell-수량="props">
        <q-td :props="props">
          <div class="quantity_flex_wrap">
            <div class="quantity_subWrap">
              <p class="table_font-size-14" style="padding: 0 20px">
                {{ props.value }}
              </p>
            </div>
            <q-btn class="quantity_btn" dense> 변경 </q-btn>
          </div>
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

      <template v-slot:body-cell-삭제="props">
        <q-td :props="props">
          <svg-icon
            type="mdi"
            :path="mdiClose"
            style="color: #999999; width: 18px; height: 18px"
          ></svg-icon>
        </q-td>
      </template>

      <template v-slot:bottom>
        <q-input
          class="bottom_input"
          outlined
          v-model="text"
          dense
          placeholder="메모"
        />
        <div class="all_price">
          <div class="all_left">
            <p>총 주문 품목</p>
            <p><span>0</span>건</p>
          </div>
          <div class="all_right">
            <p>
              {{ total.toLocaleString() }}
              <span>원</span>
            </p>
            <BtnComp_default btnLabel="주문등록" />
          </div>
        </div>
      </template>
    </q-table>

    <div class="q-mt-md">Selected: {{ JSON.stringify(selected) }}</div>
  </div>
</template>

<script>
import { ref } from "vue";
import { mdiHeart, mdiHeartOutline, mdiClose } from "@mdi/js";
import BtnComp_default from "./BtnComp_default.vue";
import SvgIcon from "@jamescoyle/vue-icon";

const columns = [
  {
    name: "name",
    required: true,
    label: "품목 및 규격",
    align: "left",
    field: (row) => row.name,
    format: (val) => `${val}`,
    sortable: true,
  },
  { name: "제약사", label: "제약사", field: "제약사", align: "center" },
  { name: "단가", label: "단가", field: "단가", align: "center" },
  { name: "수량", label: "수량", field: "수량", align: "center" },
  { name: "현재고", label: "현재고", field: "현재고", align: "center" },
  { name: "관심", label: "관심", field: "관심", align: "center" },
  { name: "삭제", label: "삭제", field: "삭제", align: "center" },
];

const rows = [
  {
    name: "레스날린페취0.5mg 30P",
    제약사: "대화제약",
    단가: "6870",
    수량: 0,
    현재고: 0,
    관심: false,
    삭제: "",
  },
  {
    name: "플루란캡슐",
    제약사: "대화제약",
    단가: 137956,
    수량: 5,
    현재고: 500,
    관심: false,
    삭제: "",
  },
  {
    name: "클리프서방정375mg",
    제약사: "구주제약",
    단가: 54054,
    수량: 0,
    현재고: 700,
    관심: true,
    삭제: "",
  },
  {
    name: "로수엠젯정10/5mg",
    제약사: "마더스제약",
    단가: 24434,
    수량: 1,
    현재고: 300,
    관심: false,
    삭제: "",
  },
  {
    name: "안플라엑스서방정300mg",
    제약사: "에스케이",
    단가: 27000,
    수량: 1,
    현재고: 200,
    관심: false,
    삭제: "",
  },
  {
    name: "휴메가연질캡슐",
    제약사: "안국약품",
    단가: 13759,
    수량: 1,
    현재고: 100,
    관심: true,
    삭제: "",
  },
  {
    name: "라푸딜정(넥스팜)",
    제약사: "이든파마",
    단가: 42315,
    수량: 1,
    현재고: 100,
    관심: false,
    삭제: "",
  },
  {
    name: "심비단정20mg",
    제약사: "한국프라임제약",
    단가: 18291,
    수량: 0,
    현재고: 500,
    관심: false,
    삭제: "",
  },
];

export default {
  components: { BtnComp_default, SvgIcon },
  data: () => ({
    mdiHeart: mdiHeart,
    mdiHeartOutline: mdiHeartOutline,
    mdiClose: mdiClose,
  }),
  setup() {
    const selected = ref([]);
    return {
      selected,
      columns,
      rows,
      like: ref(true),

      getSelectedString() {
        return selected.value.length === 0
          ? ""
          : `${selected.value.length} record${
              selected.value.length > 1 ? "s" : ""
            } selected of ${rows.value.length}`;
      },

      group: ref("op1"),
      options: [
        {
          label: "전체 10건",
          value: "op1",
        },
        {
          label: "전문급여 3건",
          value: "op2",
        },
        {
          label: "전문비급 2건",
          value: "op4",
        },
        {
          label: "일반급여 4건",
          value: "op5",
        },
        {
          label: "일반비급 1건",
          value: "op6",
        },
      ],
    };
  },
  computed: {
    total() {
      return this.selected.reduce((total, selectedItem) => {
        const selectedRow = this.rows.find(
          (row) => row.name === selectedItem.name
        );
        return total + (selectedRow ? selectedRow.단가 * selectedRow.수량 : 0);
      }, 0);
    },
  },
};
</script>
<style lang="scss">
.table_b_wrap {
  height: 455px;
  border: none;
}
.table_font-size-14 {
  @include fs-5;
}
.table_font-size-12 {
  @include fs-6;
}
.table_sec div {
  padding: 0;
  box-shadow: none !important;
}
.table-header-class th {
  @include fw-7;
  background-color: #eee;
  border-top: 2px solid $b4 !important;
  height: 48px;
}
.q-radio--dense .q-radio__label {
  font-size: 14px !important;
  padding: 10px 5px;
}

.table_b_wrap thead tr th {
  position: sticky;
  z-index: 1;
}
.table_b_wrap thead tr:last-child th {
  top: 48px;
}
.table_b_wrap thead tr:first-child th {
  top: 0;
}
.table_b_wrap tbody {
  scroll-margin-top: 48px;
}

.quantity_subWrap p {
  @include flex_center;
  border: 1px solid #666666;
  border-radius: 2.4px;
  padding: 2px 24px !important;
}
.quantity_flex_wrap {
  @include flex_center;
  gap: 4px;
}
.quantity_btn {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 3px;
}
.quantity_btn span {
  @include fs-5;
}

.like_wrap .q-icon {
  font-size: 20px;
}
.like_wrap .q-icon {
  color: $r2;
}

.x_img {
  cursor: pointer;
}

.bottom_input .q-table--dense .q-table__bottom {
  height: 40px;
}
.q-table__bottom {
  @include flex_between;
  padding-top: 40px !important;
}
.all_price,
.all_right {
  @include flex_center;
  gap: 8px;
}
.all_left {
  text-align: right;
}
.all_left p {
  @include fs-5;
  @include fw-7;
}
.all_left p span {
  @include fs-5;
  @include fw-7;
  color: $b3;
}
.all_right {
  padding: 6px 8px 6px 28px !important;
  background-color: $b5;
}
.all_right p {
  @include fs-3;
  @include fw-7;
  color: $b3;
}
.all_right p span {
  font-size: 16px;
  color: $b;
  margin-left: 2px;
}
</style>
