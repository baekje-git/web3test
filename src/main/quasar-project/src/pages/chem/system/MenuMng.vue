<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>메뉴 관리</h2>
    </div>
    <div class="main-notice marginbtm10">
      <div>
        <div class="btn-wrap btn-bottom">
          <q-btn @click="onMstRegClick" class="btn-point" label="추가" />
          &nbsp;
          <q-btn @click="onMstUdtClick" class="btn-point" label="수정" />
        </div>
        <div class="tit-sub-wrap">
          <h3>대메뉴</h3>
        </div>
      </div>
      <q-table
        flat
        :bordered="false"
        :rows="mstRows"
        :columns="mstColumns"
        :loading="mstLoading"
        row-key="MENU_ID"
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
    </div>
    &nbsp;
    <div class="main-notice">
      <div class="btn-wrap btn-bottom">
        <q-btn @click="onDtlRegClick" class="btn-point" label="추가" />
        &nbsp;
        <q-btn @click="onDtlUdtClick" class="btn-point" label="수정" />
      </div>
      <div class="tit-sub-wrap">
        <h3>서브메뉴</h3>
      </div>
      <q-table
        flat
        :bordered="false"
        :rows="dtlRows"
        :columns="dtlColumns"
        :loading="dtlLoading"
        row-key="MENU_ID"
        hide-pagination
        :rows-per-page-options="[0]"
        selection="single"
        v-model:selected="dtlSelected"
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
    </div>
    <!-- main-notice S -->
    <q-dialog v-model="dialog">
      <q-card style="width: 500px">
        <section class="content">
          <q-page-container>
            <div class="tit-sub-wrap">
              <h3>공통코드 관리</h3>
            </div>
            <div class="reg-wrap">
              <table>
                <colgroup>
                  <col width="20%" />
                  <col width="" />
                </colgroup>
                <tbody>
                  <tr v-if="isMst">
                    <th>코드</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input
                            filled
                            v-model="codeMstCd"
                            :disable="isUdt"
                            maxlength="20"
                          />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr v-if="!isMst">
                    <th>코드</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input
                            filled
                            v-model="codeDtlCd"
                            :disable="isUdt"
                            maxlength="20"
                          />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr v-if="isMst">
                    <th>코드명</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="codeMstNm" maxlength="20" />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr v-if="!isMst">
                    <th>코드명</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="codeDtlNm" maxlength="30" />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>정렬순서</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="sort" maxlength="10" />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>사용여부</th>
                    <td>
                      <q-banner dense class="w100p">
                        <CmmnCode
                          code="USE_YN"
                          v-model="useYn"
                          :all="true"
                          @loadEnd="fnLoadEnd"
                          class="w100p"
                          allTitle="선택"
                          :defaultValue="useYnData"
                        />
                      </q-banner>
                    </td>
                  </tr>
                </tbody>
              </table>
              &nbsp;
              <div class="btn-wrap btn-bottom">
                <q-btn class="btn-point" label="취소" v-close-popup />
                &nbsp;
                <q-btn @click="registData" class="btn-point" label="저장" />
              </div>
            </div>
          </q-page-container>
        </section>
      </q-card>
    </q-dialog>
  </section>
</template>

<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import { useQuasar } from "quasar";
import CmmnCode from "components/CmmnCode.vue";
import * as util from "src/support/util";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  setup() {
    const dialog = ref(false);
    const isMst = ref(false);
    const isUdt = ref(false);

    const mstLoading = ref(true);
    const dtlLoading = ref(false);
    /* 테이블 */

    const mstRows = ref([]);
    const dtlRows = ref([]);

    const mstSelected = ref([]);
    const dtlSelected = ref([]);

    const codeMstCd = ref("");
    const codeDtlCd = ref("");
    const codeMstNm = ref("");
    const codeDtlNm = ref("");
    const sort = ref("");
    const useYn = ref("");
    const useYnData = ref("");

    function searchList() {
      mstLoading.value = true;
      return api
        .post("/system/menuMstList", {})
        .then((response) => {
          mstRows.value = response.data;
        })
        .finally(() => {
          mstLoading.value = false;
          mstSelected.value = [];
          dtlSelected.value = [];
          dtlRows.value = [];
        });
    }

    function fnLoadEnd(option) {
      useYn.value = option;
    }

    function onMstRowClick(data) {
      dtlLoading.value = true;
      return api
        .post("/system/menuDtlList", {
          MENU_UPPER: data.keys[0],
        })
        .then((response) => {
          dtlRows.value = response.data;
        })
        .finally(() => {
          dtlLoading.value = false;
          dtlSelected.value = [];
        });
    }

    function onMstRegClick() {
      isMst.value = true;
      isUdt.value = false;

      codeMstCd.value = "";
      codeMstNm.value = "";
      sort.value = "";
      useYnData.value = "";

      dialog.value = true;
    }

    function onMstUdtClick() {
      const mstData = mstSelected.value[0];
      if (util.isEmpty(mstData)) {
        util.errorNotify("마스터코드를 선택하세요.");
        return;
      }
      isMst.value = true;
      isUdt.value = true;

      codeMstCd.value = mstData.CODE_MST_CD;
      codeMstNm.value = mstData.CODE_MST_NM;
      sort.value = mstData.SORT;
      useYnData.value = mstData.USE_YN;

      dialog.value = true;
    }

    function onDtlRegClick() {
      const mstData = mstSelected.value[0];
      if (util.isEmpty(mstData)) {
        util.errorNotify("마스터코드를 선택하세요.");
        return;
      }

      isMst.value = false;
      isUdt.value = false;

      codeDtlCd.value = "";
      codeDtlNm.value = "";
      sort.value = "";
      useYnData.value = "";

      dialog.value = true;
    }

    function onDtlUdtClick() {
      const mstData = mstSelected.value[0];
      if (util.isEmpty(mstData)) {
        util.errorNotify("마스터코드를 선택하세요.");
        return;
      }
      const dtlData = dtlSelected.value[0];
      if (util.isEmpty(dtlData)) {
        util.errorNotify("디테일코드를 선택하세요.");
        return;
      }
      isMst.value = false;
      isUdt.value = true;

      codeDtlCd.value = dtlData.CODE_DTL_CD;
      codeDtlNm.value = dtlData.CODE_DTL_NM;
      sort.value = dtlData.SORT;
      useYnData.value = dtlData.USE_YN;

      dialog.value = true;
    }

    async function registData() {
      try {
        if (isMst.value && util.isEmpty(codeMstCd.value)) {
          util.errorNotify("코드를 입력하세요.");
          return;
        }
        if (isMst.value && util.isEmpty(codeMstNm.value)) {
          util.errorNotify("코드명을 입력하세요.");
          return;
        }
        if (!isMst.value && util.isEmpty(codeDtlCd.value)) {
          util.errorNotify("코드를 입력하세요.");
          return;
        }
        if (!isMst.value && util.isEmpty(codeDtlNm.value)) {
          util.errorNotify("코드명을 입력하세요.");
          return;
        }
        if (util.isEmpty(sort.value)) {
          util.errorNotify("정렬순서를 입력하세요.");
          return;
        }
        if (util.isEmpty(useYn.value.value)) {
          util.errorNotify("사용여부를 선택하세요.");
          return;
        }

        const path = ref("");
        if (isMst.value) {
          if (isUdt.value) {
            path.value = "/system/updateCodeMstData";
          } else {
            path.value = "/system/insertCodeMstData";
          }
        } else {
          if (isUdt.value) {
            path.value = "/system/updateCodeDtlData";
          } else {
            path.value = "/system/insertCodeDtlData";
          }
        }

        await api.post(path.value, {
          CODE_MST_CD: codeMstCd.value,
          CODE_MST_NM: codeMstNm.value,
          CODE_DTL_CD: codeDtlCd.value,
          CODE_DTL_NM: codeDtlNm.value,
          SORT: sort.value,
          USE_YN: useYn.value.value,
          userData: util.getUserData(),
        });
        util.showNotify("정상처리 되었습니다.");
        dialog.value = false;
        searchList();
      } catch (error) {
        util.errorNotify("오류가 발생하였습니다.");
      }
    }

    onMounted(() => {
      searchList();
    });

    return {
      val: ref(true),
      dialog,
      isMst,
      isUdt,
      /* ///////그리드 스크립트 Start//////// */
      mstColumns,
      dtlColumns,
      mstLoading,
      dtlLoading,
      mstRows,
      dtlRows,
      mstSelected,
      dtlSelected,
      /* ///////그리드 스크립트 End//////// */
      codeMstCd,
      codeDtlCd,
      codeMstNm,
      codeDtlNm,
      sort,
      useYn,
      useYnData,
      fnLoadEnd,
      searchList,
      onMstRowClick,
      onMstRegClick,
      onMstUdtClick,
      onDtlRegClick,
      onDtlUdtClick,
      registData,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const mstColumns = [
  {
    name: "MENU_ID",
    label: "메뉴ID",
    field: "MENU_ID",
    sortable: true,
  },
  {
    name: "MENU_NM",
    label: "메뉴명",
    field: "MENU_NM",
    sortable: true,
  },
  { name: "MENU_SORT", label: "메뉴순서", field: "MENU_SORT" },
  { name: "USE_YN", label: "사용여부", field: "USE_YN" },
];

const dtlColumns = [
  {
    name: "MENU_ID",
    label: "메뉴ID",
    field: "MENU_ID",
    sortable: true,
  },
  {
    name: "MENU_NM",
    label: "메뉴명",
    field: "MENU_NM",
    sortable: true,
  },
  { name: "MENU_SORT", label: "메뉴순서", field: "MENU_SORT" },
  { name: "USE_YN", label: "사용여부", field: "USE_YN" },
];
/* ///////그리드 스크립트 End //////// */
</script>
