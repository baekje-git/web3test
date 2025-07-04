<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>권한 관리</h2>
    </div>
    <ul class="div-col">
      <li class="w30p">
        <q-table
          flat
          :bordered="false"
          title="권한 목록"
          :rows="mstRows"
          :columns="mstColumns"
          :visible-columns="visibleMstColumns"
          :loading="mstLoading"
          row-key="CODE_DTL_CD"
          hide-pagination
          :rows-per-page-options="[0]"
          selection="single"
          v-model:selected="mstSelected"
          @selection="onMstRowClick"
          hide-selected-banner
        >
          <template v-slot:body="props">
            <q-tr :props="props" @click="props.selected = true">
              <q-td>
                <q-checkbox v-model="props.selected" />
              </q-td>
              <q-td v-for="col in props.cols" :key="col.name" :props="props">
                {{ col.value }}
              </q-td>
            </q-tr>
          </template>
        </q-table>
      </li>
      <li class="w70p">
        <q-table
          flat
          :bordered="false"
          title="권한별 메뉴 및 결재 가능 여부 목록"
          :rows="dtlRows"
          :columns="dtlColumns"
          :visible-columns="visibleDtlColumns"
          :loading="dtlLoading"
          row-key="CODE_DTL_CD"
          hide-pagination
          :rows-per-page-options="[0]"
        >
          <template v-slot:body-cell-MENU_YN="props">
            <q-td :props="props">
              <q-checkbox
                v-model="props.row.MENU_YN"
                @click.stop="updateData(props)"
                true-value="Y"
                false-value="N"
              />
            </q-td>
          </template>
        </q-table>
      </li>
    </ul>
  </section>
</template>

<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

export default defineComponent({
  name: "MainLayout",

  components: {},

  setup() {
    const mstLoading = ref(true);
    const dtlLoading = ref(false);
    /* 테이블 */

    const mstRows = ref([]);
    const dtlRows = ref([]);

    const mstSelected = ref([]);

    function searchList() {
      mstLoading.value = true;
      return api
        .post("/system/codeDtlList", {
          CODE_MST_CD: "AUTH_CD",
        })
        .then((response) => {
          mstRows.value = response.data;
        })
        .finally(() => {
          mstLoading.value = false;
        });
    }

    function onMstRowClick(data) {
      dtlLoading.value = true;
      return api
        .post("/system/authList", {
          AUTH_CD: data.keys[0],
        })
        .then((response) => {
          dtlRows.value = response.data;
        })
        .finally(() => {
          dtlLoading.value = false;
        });
    }

    async function updateData(props) {
      const path = ref("");
      if (props.row.MENU_YN == "Y") {
        path.value = "/system/insertAuthData";
      } else {
        path.value = "/system/deleteAuthData";
      }
      try {
        await api.post(path.value, {
          AUTH_CD: mstSelected.value[0].CODE_DTL_CD,
          MENU_ID: props.row.MENU_ID,
          userData: util.getUserData(),
        });
        util.showNotify(
          mstSelected.value[0].CODE_DTL_NM +
            "의 " +
            props.row.MENU_NM +
            "메뉴 권한이 변경되었습니다."
        );
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
      mstColumns,
      dtlColumns,
      visibleMstColumns,
      visibleDtlColumns,
      mstLoading,
      dtlLoading,
      mstRows,
      dtlRows,
      mstSelected,
      /* ///////그리드 스크립트 End//////// */
      searchList,
      onMstRowClick,
      updateData,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const mstColumns = [
  {
    name: "SORT",
    label: "순번",
    field: "SORT",
  },
  {
    name: "CODE_DTL_NM",
    label: "권한명",
    field: "CODE_DTL_NM",
  },
  { name: "CODE_DTL_CD", label: "CODE_DTL_CD", field: "CODE_DTL_CD" },
];
const visibleMstColumns = ["SORT", "CODE_DTL_NM"];

const dtlColumns = [
  {
    name: "MENU_NM",
    label: "메뉴명",
    field: "MENU_NM",
    sortable: true,
  },
  {
    name: "MENU_YN",
    label: "메뉴권한",
    field: "MENU_YN",
  },
  {
    name: "MENU_LVL",
    label: "메뉴레벨",
    field: "MENU_LVL",
  },
  { name: "MENU_ID", label: "MENU_ID", field: "MENU_ID" },
];
const visibleDtlColumns = ["MENU_NM", "MENU_YN", "MENU_LVL"];
/* ///////그리드 스크립트 End //////// */
</script>
