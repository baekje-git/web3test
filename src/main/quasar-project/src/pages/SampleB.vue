<template>
  <section class="content">
    <iframe
      id="if_report"
      name="if_report"
      style="width: 100%; height: 1200px"
    ></iframe>
    <q-page-container class="first-container">
      1-1 그리드
      <div class="">
        <q-option-group
          v-model="separator"
          inline
          class="q-mb-md"
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
      </div>
    </q-page-container>
    <q-page-container>
      1-2 그리드 페이징
      <div class="q-pa-lg flex flex-center">
        <q-pagination v-model="current" :max="5" />
      </div>
    </q-page-container>
    <q-page-container>
      2-1 콤보박스
      <div class="">
        <div class="q-gutter-md row items-start">
          <q-select
            filled
            v-model="model"
            multiple
            :options="options"
            counter
            hint="With counter"
            style="width: 250px"
          />

          <q-select
            filled
            v-model="model2"
            multiple
            :options="options"
            counter
            max-values="2"
            hint="Max 2 selections"
            style="width: 250px"
          />
        </div>
      </div>
    </q-page-container>
    <q-page-container>
      3 라디오 버튼
      <div class="">
        <div class="q-gutter-sm">
          <q-radio v-model="shape" val="line" label="Line" />
          <q-radio v-model="shape" val="rectangle" label="Rectangle" />
          <q-radio v-model="shape" val="ellipse" label="Ellipse" />
          <q-radio v-model="shape" val="polygon" label="Polygon" />
        </div>
      </div>
    </q-page-container>
    <q-page-container>
      4 팝업검색
      <div class="q-gutter-sm btn-pop-open">
        <q-btn label="팝업열기" color="primary" @click="showDialog" />
      </div>
    </q-page-container>
    <q-page-container>
      5 체크박스
      <div class="">
        <q-checkbox v-model="val" />
      </div>
    </q-page-container>
    <q-page-container>
      6 토스트팝업
      <div class="btn-toast">
        <q-btn color="purple" @click="showNotif" label="토스트 팝업열기" />
      </div>
    </q-page-container>
    <q-page-container>
      7 Input
      <div class="">
        <div class="q-gutter-md" style="max-width: 300px">
          <!--<q-input v-model="text" label="Standard" />-->

          <q-input filled v-model="text" label="아이디를 입력해주세요" />

          <!--<q-input outlined v-model="text" label="Outlined" />-->

          <!--<q-input standout="bg-teal text-white" v-model="text" label="Custom standout" />-->
        </div>
      </div>
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
import * as reportUtil from "src/router/report";
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
    roleMenuList.value = [
      {
        PATH: "1_2027",
        USE_YN: "Y",
        LVL: 1,
        MENU_ID: 2027,
        MENU_NM: "시스템",
        P_MENU_ID: 0,
      },
      {
        PATH: "1_2027>0_2028",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 2028,
        ICON: "code",
        MENU_PATH: "/sampleA",
        MENU_NM: "메인페이지",
        P_MENU_ID: 2027,
      },
      {
        PATH: "1_2027>0_2029",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 2029,
        ICON: "manage_accounts",
        MENU_PATH: "/sampleB",
        MENU_NM: "샘플페이지",
        P_MENU_ID: 2027,
      },
      {
        PATH: "2_3732",
        USE_YN: "Y",
        LVL: 1,
        MENU_ID: 3732,
        MENU_NM: "워크플로우관리",
        P_MENU_ID: 0,
      },
      {
        PATH: "2_3732>10_3733",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 3733,
        ICON: "link",
        MENU_PATH: "/admin/chain",
        MENU_NM: "워크플로우관리",
        P_MENU_ID: 3732,
      },
      {
        PATH: "2_3732>20_3734",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 3734,
        ICON: "edit_calendar",
        MENU_PATH: "/admin/schedule",
        MENU_NM: "스케쥴관리",
        P_MENU_ID: 3732,
      },
      {
        PATH: "2_3732>30_3735",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 3735,
        ICON: "holiday_village",
        MENU_PATH: "/admin/holiday",
        MENU_NM: "공휴일관리",
        P_MENU_ID: 3732,
      },
      {
        PATH: "3_3728",
        USE_YN: "Y",
        LVL: 1,
        MENU_ID: 3728,
        MENU_NM: "디바이스관리",
        P_MENU_ID: 0,
      },
      {
        PATH: "3_3728>1_3730",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 3730,
        ICON: "monitor_heart",
        MENU_PATH: "/admin/monitor",
        MENU_NM: "디바이스 모니터링",
        P_MENU_ID: 3728,
      },
      {
        PATH: "3_3728>2_3729",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 3729,
        ICON: "important_devices",
        MENU_PATH: "/admin/device",
        MENU_NM: "디바이스관리",
        P_MENU_ID: 3728,
      },
      {
        PATH: "3_3728>3_3731",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 3731,
        ICON: "content_paste",
        MENU_PATH: "/admin/content",
        MENU_NM: "컨텐츠관리",
        P_MENU_ID: 3728,
      },
      {
        PATH: "3_3728>4_8109",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 8109,
        ICON: "map",
        MENU_PATH: "/admin/floorplans",
        MENU_NM: "평면도관리",
        P_MENU_ID: 3728,
      },
      {
        PATH: "3_3728>5_9133",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 9133,
        ICON: "work_history",
        MENU_PATH: "/admin/deviceWorkMgr",
        MENU_NM: "디바이스 제어 관리",
        P_MENU_ID: 3728,
      },
      {
        PATH: "4_9016",
        USE_YN: "Y",
        LVL: 1,
        MENU_ID: 9016,
        MENU_NM: "이젝스 관리",
        P_MENU_ID: 0,
      },
      {
        PATH: "4_9016>1_9017",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 9017,
        ICON: "router",
        MENU_PATH: "/admin/ezex/gateway",
        MENU_NM: "게이트웨이 관리",
        P_MENU_ID: 9016,
      },
      {
        PATH: "5_11013",
        USE_YN: "Y",
        LVL: 1,
        MENU_ID: 11013,
        MENU_NM: "Tuya 관리",
        P_MENU_ID: 0,
      },
      {
        PATH: "5_11013>0_11014",
        USE_YN: "Y",
        LVL: 2,
        MENU_ID: 11014,
        ICON: "router",
        MENU_PATH: "/admin/tuya/gateway",
        MENU_NM: "게이트웨이 관리",
        P_MENU_ID: 11013,
      },
    ];

    return {
      essentialLinks: linksList,
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
      current: ref(1),
      model: ref(null),
      model2: ref(null),

      options: [
        "제조지시 및 기록서",
        "샘플페이지",
        "품질시스템",
        "Apple",
        "Oracle",
      ],
      shape: ref("line"),
      /* ///////팝업 오픈 Start//////// */
      showDialog() {
        popup
          .dialog({
            title: "재고관리 목록 팝업<em></em>",
            message:
              '<em>I can</em> <span class="text-red">use</span> <strong>HTML</strong>',
            html: true,
          })
          .onOk(() => {
            // console.log('OK')
          })
          .onCancel(() => {
            // console.log('Cancel')
          })
          .onDismiss(() => {
            // console.log('I am triggered on both OK and Cancel')
          });
      },
      /* ///////팝업 오픈 End//////// */
      showNotif() {
        $R.notify({
          message: "Jim pinged you.",
          caption: "5 minutes ago",
          color: "secondary",
        });
      },
    };
  },
  mounted() {
    const param = {
      dataset_0: { col_0: "" },
    };
    const paramList = [];

    reportUtil.ubiReport2(
      param,
      "FDGBatchProductionRecord",
      paramList,
      "if_report"
    );
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
const linksList = [
  {
    title: "Docs",
    caption: "quasar.dev",
    icon: "school",
    link: "https://quasar.dev",
  },
  {
    title: "Github",
    caption: "github.com/quasarframework",
    icon: "code",
    link: "https://github.com/quasarframework",
  },
  {
    title: "Discord Chat Channel",
    caption: "chat.quasar.dev",
    icon: "chat",
    link: "https://chat.quasar.dev",
  },
  {
    title: "Forum",
    caption: "forum.quasar.dev",
    icon: "record_voice_over",
    link: "https://forum.quasar.dev",
  },
  {
    title: "Twitter",
    caption: "@quasarframework",
    icon: "rss_feed",
    link: "https://twitter.quasar.dev",
  },
  {
    title: "Facebook",
    caption: "@QuasarFramework",
    icon: "public",
    link: "https://facebook.quasar.dev",
  },

  {
    title: "Quasar Awesome",
    caption: "Community Quasar projects",
    icon: "favorite",
    link: "https://awesome.quasar.dev",
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
