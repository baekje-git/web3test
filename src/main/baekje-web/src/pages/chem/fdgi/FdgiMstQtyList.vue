<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>재고 조회</h2>
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
            <span>재료코드</span>
            <q-input filled v-model="itemCd" label="재료코드" />
          </dev>
          <dev>
            <span>재료명</span>
            <q-input filled v-model="itemNm" label="재료명" />
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
        <template v-slot:body-cell-item-mngn-yn="props">
          <q-td :props="props">
            <q-checkbox @click.stop="openDialog" label="조회" />
          </q-td>
        </template>
      </q-table>
    </div>
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import { useQuasar } from "quasar";

export default defineComponent({
  name: "MainLayout",

  components: {},

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);
    const itemCd = ref("");
    const itemNm = ref("");

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgi/mst/fdgiMstQtyList", {
          ITEM_CD: itemCd.value,
          ITEM_NM: itemNm.value,
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
      itemNm,

      openDialog(data) {
        console.log(data);
        popup
          .dialog({
            component: QnaDialog,
          })
          .onOk((result) => {
            console.log(result);
          })
          .onCancel(() => {
            // console.log('Cancel')
          })
          .onDismiss(() => {
            // console.log('I am triggered on both OK and Cancel')
          });
      },
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
  { name: "LOT_CNT", label: "보유 Lot수", field: "LOT_CNT" },
  { name: "QTY", label: "현재재고", field: "QTY" },
];
/* ///////그리드 스크립트 End //////// */
</script>
