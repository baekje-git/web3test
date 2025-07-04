<template>
<section class="content">
  <div class="tit-wrap">
    <h2>제조지시 및 기록서</h2>
    <div class="btn-wrap">
      <div class="btn-toast">
        <q-btn @click="showNotif" class="btn-point ico-search" label="조회" />
      </div>
    </div>
  </div>
    <q-page-container class="first-container">
      <q-banner dense :class="$q.dark.isActive ? '' : ''" class="search-wrap">
          <div class="q-gutter-md row items-start">
            <q-select
              filled
              v-model="model"
              :options="options"
              style="width: 250px"
            />
            <q-input filled v-model="text" label="아이디를 입력해주세요" />
            <q-checkbox v-model="val" />
            
          </div>
      </q-banner>
    </q-page-container>
    <q-page-container>
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
</template>
<script>
import { defineComponent, ref } from "vue";
import EssentialLink from "components/EssentialLink.vue";
import { useQuasar } from "quasar";
import RoleMenu from "components/RoleMenu.vue";
import HeaderCmp from "components/HeaderCmp.vue";
import FooterCmp from "components/FooterCmp.vue";

const temp = "Sample b header";
const temp2 = "Sample b footer";

export default defineComponent({
  name: "MainLayout",

  components: {},

  setup() {
    const leftDrawerOpen = ref(false);
    const popup = useQuasar();
    const $R = useQuasar();
    const roleMenuList = ref([]);
    // 메뉴데이터
    roleMenuList.value = [];

    return {
      roleMenuList,
      val: ref(true),
      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value;
      },
      /* ///////그리드 스크립트 Start//////// */
      separator: ref("vertical"),
      columns,
      rows,
      /* ///////그리드 스크립트 End//////// */
    };
  },
});
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
<style scoped></style>
