<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>LOT 재고 조회</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn
            @click="searchList"
            class="btn-point ico-search"
            label="조회"
          />
        </div>
      </div>
    </div>
    <q-page-container class="first-container">
      <q-banner dense :class="$q.dark.isActive ? '' : ''" class="search-wrap">
        <div class="q-gutter-md row items-start">
          <dev>
            <span>재료</span>
            <CmmnCode
              code="ITEM"
              name="code"
              v-model="itemCd"
              :all="true"
              @loadEnd="fnLoadEnd"
              class="w200"
            />
          </dev>
          <dev>
            <span>Lot NO</span>
            <q-input filled v-model="lotNo" label="Lot NO" />
          </dev>
          <dev>
            <span>시험번호</span>
            <q-input filled v-model="testNo" label="시험번호" />
          </dev>
        </div>
      </q-banner>
    </q-page-container>
    <div class="main-notice">
      <q-table
        flat
        :bordered="false"
        :rows="rows"
        :columns="columns"
        :loading="loading"
        row-key="ITEM_CD"
        hide-pagination
        :rows-per-page-options="[0]"
        class="hover-none"
      >
      </q-table>
    </div>
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import { useQuasar } from "quasar";
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);
    const itemCd = ref("");
    const lotNo = ref("");
    const testNo = ref("");

    function fnLoadEnd(option) {
      itemCd.value = option;
    }

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgi/lot/fdgiLotList", {
          ITEM_CD: itemCd.value.value,
          LOT_NO: lotNo.value,
          TEST_NO: testNo.value,
        })
        .then((response) => {
          console.log(response);
          rows.value = response.data;
        })
        .finally(() => {
          loading.value = false;
        });
    };

    onMounted(() => {
      // get initial data from server (1st page)
      searchList();
    });

    return {
      val: ref(true),
      /* ///////그리드 스크립트 Start//////// */
      columns,
      loading,
      rows,
      /* ///////그리드 스크립트 End//////// */
      searchList,
      itemCd,
      lotNo,
      testNo,
      fnLoadEnd,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const columns = [
  {
    name: "ITEM_CD",
    required: true,
    label: "재료코드",
    field: (row) => row.ITEM_CD,
    sortable: true,
  },
  { name: "ITEM_NM", label: "재료명", field: "ITEM_NM" },
  { name: "LOT_NO", label: "Lot NO", field: "LOT_NO" },
  { name: "QTY", label: "재고", field: "QTY" },
  { name: "TEST_NO", label: "시험번호", field: "TEST_NO" },
];
/* ///////그리드 스크립트 End //////// */
</script>
