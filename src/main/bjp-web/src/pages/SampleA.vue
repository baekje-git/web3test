<template>
  <!-- content S  -->
  <section class="content">
    <q-page-container class="first-container">
      <div class="dashboard">
        <q-card class="month" flat>
          <q-card-section>
            <!--<div>월간 생산 실적</div>
            <div>1425건</div>
            <div>8월 생산 실적</div>-->
            <ul>
              <li>월간 생산 실적</li>
              <li>
                <span>8월<br />생산 실적</span>
                <strong>145<span>건</span></strong>
              </li>
            </ul>
          </q-card-section>
        </q-card>
        <q-card class="week" flat>
          <q-card-section>
            <ul>
              <li>주간 생산 실적</li>
              <li>
                <span>4주<br />생산 실적</span>
                <strong>12<span>건</span></strong>
              </li>
            </ul>
          </q-card-section>
        </q-card>
      </div>
    </q-page-container>
    <q-page-container>
      1-1 그리드
      <q-option-group
        v-model="separator"
        inline
        class=""
        :options="[
          { label: 'Horizontal (default)', value: 'horizontal' },
          { label: 'Vertical', value: 'vertical' },
          { label: 'Cell', value: 'cell' },
          { label: 'None', value: 'none' },
        ]"
      />

      <q-table
        flat
        bordered
        title="FDG 제조지시 및 기록서"
        :rows="rows"
        :columns="columns"
        row-key="name"
        :separator="separator"
      />
    </q-page-container>
  </section>
  <!--// content E  -->
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import EssentialLink from "components/EssentialLink.vue";
import { useQuasar } from "quasar";
import HeaderCmp from "components/HeaderCmp.vue";
import FooterCmp from "components/FooterCmp.vue";
export default defineComponent({
  name: "MainLayout",

  components: {},
  setup() {
    return {
      /* ///////그리드 스크립트 Start//////// */
      separator: ref("vertical"),
      columns,
      rows,
      /* ///////그리드 스크립트 End//////// */
      current: ref(1),
      model: ref(null),
      model2: ref(null),

      options: ["Google", "Facebook", "Twitter", "Apple", "Oracle"],
      shape: ref("line"),
    };
  },
}); //export default
/* ///////그리드 스크립트 Start//////// */
const columns = [
  {
    name: "name",
    required: true,
    label: "제조일자",
    align: "left",
    field: (row) => row.name,
    format: (val) => `${val}`,
    sortable: true,
  },
  {
    name: "calories",
    align: "center",
    label: "제조번호",
    field: "calories",
    sortable: true,
  },
  { name: "fat", label: "현황정보", field: "fat", sortable: true },
  { name: "carbs", label: "Carbs (g)", field: "carbs" },
  { name: "protein", label: "Protein (g)", field: "protein" },
  { name: "sodium", label: "Sodium (mg)", field: "sodium" },
  {
    name: "calcium",
    label: "Calcium (%)",
    field: "calcium",
    sortable: true,
    sort: (a, b) => parseInt(a, 10) - parseInt(b, 10),
  },
  {
    name: "iron",
    label: "Iron (%)",
    field: "iron",
    sortable: true,
    sort: (a, b) => parseInt(a, 10) - parseInt(b, 10),
  },
];

const rows = [
  {
    name: "2023.08.08",
    calories: 159,
    fat: 6.0,
    carbs: 24,
    protein: 4.0,
    sodium: 87,
    calcium: "14%",
    iron: "1%",
  },
  {
    name: "Ice cream sandwich",
    calories: 237,
    fat: 9.0,
    carbs: 37,
    protein: 4.3,
    sodium: 129,
    calcium: "8%",
    iron: "1%",
  },
  {
    name: "Eclair",
    calories: 262,
    fat: 16.0,
    carbs: 23,
    protein: 6.0,
    sodium: 337,
    calcium: "6%",
    iron: "7%",
  },
  {
    name: "Cupcake",
    calories: 305,
    fat: 3.7,
    carbs: 67,
    protein: 4.3,
    sodium: 413,
    calcium: "3%",
    iron: "8%",
  },
  {
    name: "Gingerbread",
    calories: 356,
    fat: 16.0,
    carbs: 49,
    protein: 3.9,
    sodium: 327,
    calcium: "7%",
    iron: "16%",
  },
  {
    name: "Jelly bean",
    calories: 375,
    fat: 0.0,
    carbs: 94,
    protein: 0.0,
    sodium: 50,
    calcium: "0%",
    iron: "0%",
  },
  {
    name: "Lollipop",
    calories: 392,
    fat: 0.2,
    carbs: 98,
    protein: 0,
    sodium: 38,
    calcium: "0%",
    iron: "2%",
  },
  {
    name: "Honeycomb",
    calories: 408,
    fat: 3.2,
    carbs: 87,
    protein: 6.5,
    sodium: 562,
    calcium: "0%",
    iron: "45%",
  },
  {
    name: "Donut",
    calories: 452,
    fat: 25.0,
    carbs: 51,
    protein: 4.9,
    sodium: 326,
    calcium: "2%",
    iron: "22%",
  },
  {
    name: "KitKat",
    calories: 518,
    fat: 26.0,
    carbs: 65,
    protein: 7,
    sodium: 54,
    calcium: "12%",
    iron: "6%",
  },
];
/* ///////그리드 스크립트 End //////// */
</script>
<style scoped>
/* 여기서 선언한 CSS가 우선 적용 
   그 다음은 app.scss가 우선 적용 (ctrl+p 눌러서 파일 검색)
*/
body,
html {
  font-family: "Pretendard" !important;
  letter-spacing: 0;
  font-size: 14px;
}
</style>
