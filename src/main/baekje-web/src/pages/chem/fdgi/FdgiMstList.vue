<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>재고 마스터 관리</h2>
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
          <dev>
            <span>규격</span>
            <q-input filled v-model="unit" label="규격" />
          </dev>
          <dev>
            <span>제조사</span>
            <q-input filled v-model="manuCom" label="제조사" />
          </dev>
          <dev>
            <span>재고관리여부</span>
            <CmmnCode
              code="ITEM_MNGN_YN"
              name="code"
              v-model="itemMngnYn"
              :all="true"
              @loadEnd="fnLoadEnd"
              class="w80"
            />
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
      >
        <template v-slot:body-cell-ITEM_MNGN_YN="props">
          <q-td :props="props">
            <q-checkbox
              v-model="props.row.ITEM_MNGN_YN"
              @click.stop="updateData(props)"
              true-value="Y"
              false-value="N"
            />
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
import * as util from "src/support/util";
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
    const itemNm = ref("");
    const manuCom = ref("");
    const unit = ref("");
    const itemMngnYn = ref("");

    function fnLoadEnd(option) {
      itemMngnYn.value = option;
    }

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgi/mst/fdgiMstList", {
          ITEM_CD: itemCd.value,
          ITEM_NM: itemNm.value,
          MANU_COM: manuCom.value,
          UNIT: unit.value,
          ITEM_MNGN_YN: itemMngnYn.value.value,
        })
        .then((response) => {
          console.log(response);
          rows.value = response.data;
        })
        .finally(() => {
          loading.value = false;
        });
    };

    async function updateData(props) {
      try {
        await api.post("/fdgi/mst/updateFdgiMstData", {
          ITEM_CD: props.key,
          ITEM_MNGN_YN: props.row.ITEM_MNGN_YN,
          userData: util.getUserData(),
        });
        util.showNotify(props.key + "의 재고관리여부가 변경되었습니다.");
      } catch (error) {
        util.errorNotify("오류가 발생하였습니다.");
      }
    }

    onMounted(() => {
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
      manuCom,
      unit,
      itemMngnYn,
      updateData,
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
  { name: "UNIT", label: "규격", field: "UNIT" },
  { name: "MANU_COM", label: "제조사", field: "MANU_COM" },
  { name: "ITEM_MNGN_YN", label: "재고관리여부", field: "ITEM_MNGN_YN" },
];
/* ///////그리드 스크립트 End //////// */
</script>
