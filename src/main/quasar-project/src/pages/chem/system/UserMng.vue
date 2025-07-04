<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>사용자관리</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn
            @click="searchList"
            class="btn-point ico-search"
            label="조회"
          />
          &nbsp;
          <q-btn @click="onRegClick" class="btn-point" label="등록" />
        </div>
      </div>
    </div>
    <q-page-container class="first-container">
      <q-banner dense :class="$q.dark.isActive ? '' : ''" class="search-wrap">
        <div class="q-gutter-md row items-start">
          <dev>
            <span>이름</span>
            <q-input filled v-model="userNm" label="이름" />
          </dev>
          <dev>
            <span>아이디</span>
            <q-input filled v-model="userId" label="아이디" />
          </dev>
          <dev>
            <span>전화번호</span>
            <q-input filled v-model="userPh" label="전화번호" />
          </dev>
          <dev>
            <span>권한</span>
            <CmmnCode
              code="AUTH"
              v-model="userAuth"
              :all="true"
              @loadEnd="fnLoadEnd"
              class="w130"
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
        :visible-columns="visibleColumns"
        :loading="loading"
        row-key="USER_ID"
        hide-pagination
        :rows-per-page-options="[0]"
        @row-click="onRowClick"
      >
        <template #body-cell-USE_YN="props">
          <q-td :props="props">
            <q-checkbox
              v-model="props.row.USE_YN"
              @click.stop="updateUseYn(props)"
              true-value="Y"
              false-value="N"
            />
          </q-td>
        </template>
      </q-table>
      <q-dialog v-model="dialog">
        <q-card style="width: 500px">
          <section class="content">
            <q-page-container>
              <div class="tit-sub-wrap">
                <h3>사용자관리</h3>
              </div>
              <div class="reg-wrap">
                <table>
                  <colgroup>
                    <col width="20%" />
                    <col width="" />
                  </colgroup>
                  <tbody>
                    <tr>
                      <th>이름</th>
                      <td>
                        <q-banner dense class="w100p">
                          <div class="row items-start">
                            <q-input
                              filled
                              v-model="userNmDial"
                              maxlength="20"
                            />
                          </div>
                        </q-banner>
                      </td>
                    </tr>
                    <tr>
                      <th>ID</th>
                      <td>
                        <q-banner dense class="w100p">
                          <div class="row items-start">
                            <q-input
                              filled
                              v-model="userIdDial"
                              maxlength="20"
                              :disable="flag"
                            />
                          </div>
                        </q-banner>
                      </td>
                    </tr>
                    <tr>
                      <th>PW</th>
                      <td>
                        <q-banner dense class="w100p">
                          <div class="row items-start">
                            <q-input
                              filled
                              v-model="userPwDial"
                              maxlength="20"
                            />
                          </div>
                        </q-banner>
                      </td>
                    </tr>
                    <tr>
                      <th>전화번호</th>
                      <td>
                        <q-banner dense class="w100p">
                          <div class="row items-start">
                            <q-input
                              filled
                              v-model="userPhDial"
                              maxlength="11"
                            />
                          </div>
                        </q-banner>
                      </td>
                    </tr>
                    <tr>
                      <th>권한</th>
                      <td>
                        <q-banner dense class="w100p">
                          <div class="row items-start">
                            <CmmnCode
                              code="AUTH"
                              v-model="userAuthDial"
                              :all="true"
                              @loadEnd="fnDailLoadEnd"
                              class="w100p"
                              allTitle="선택"
                              :defaultValue="userAuthDialData"
                            />
                          </div>
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
    </div>
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { useQuasar } from "quasar";
import { api } from "boot/axios";
import * as util from "src/support/util";
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  setup() {
    const q$ = useQuasar();
    const loading = ref(true);
    const rows = ref([]);
    const dialog = ref(false);

    const userNm = ref("");
    const userId = ref("");
    const userPh = ref("");
    const userAuth = ref("");

    const userNmDial = ref("");
    const userIdDial = ref("");
    const userPwDial = ref("");
    const userPhDial = ref("");
    const userAuthDial = ref("");
    const userAuthDialData = ref("");

    const flag = ref(true);

    function fnLoadEnd(option) {
      userAuth.value = option;
    }

    function fnDailLoadEnd(option) {
      userAuthDial.value = option;
    }

    function searchList() {
      loading.value = true;
      return api
        .post("/system/userList", {
          USER_ID: userId.value,
          USER_NM: userNm.value,
          USER_PH: userPh.value,
          USER_LVL: userAuth.value.value,
        })
        .then((response) => {
          rows.value = response.data;
        })
        .finally(() => {
          loading.value = false;
        });
    }

    async function updateUseYn(props) {
      try {
        await api.post("/system/updateUserUseYn", {
          USER_ID: props.key,
          USE_YN: props.row.USE_YN,
          userData: util.getUserData(),
        });
        util.showNotify(props.key + "의 사용여부가 변경되었습니다.");
      } catch (error) {
        util.errorNotify("오류가 발생하였습니다.");
      }
    }

    function onRowClick(evt, row) {
      userNmDial.value = row.USER_NM;
      userIdDial.value = row.USER_ID;
      userPwDial.value = "";
      userPhDial.value = row.USER_PH;
      userAuthDialData.value = row.USER_LVL;
      dialog.value = true;
      flag.value = true;
    }

    function onRegClick() {
      userNmDial.value = "";
      userIdDial.value = "";
      userPwDial.value = "";
      userPhDial.value = "";
      userAuthDialData.value = "";
      dialog.value = true;
      flag.value = false;
    }

    async function registData() {
      try {
        if (util.isEmpty(userNmDial.value)) {
          util.errorNotify("이름을 입력하세요.");
          return;
        }
        if (util.isEmpty(userIdDial.value)) {
          util.errorNotify("아이디를 입력하세요.");
          return;
        }
        if (util.isEmpty(userPwDial.value)) {
          util.errorNotify("비밀번호를 입력하세요.");
          return;
        }
        if (util.isEmpty(userAuthDial.value.value)) {
          util.errorNotify("권한을 선택하세요.");
          return;
        }
        await api.post(
          flag.value ? "/system/updateUserData" : "/system/insertUserData",
          {
            USER_ID: userIdDial.value,
            USER_NM: userNmDial.value,
            USER_PWD: userPwDial.value,
            USER_PH: userPhDial.value,
            USER_LVL: userAuthDial.value.value,
            userData: util.getUserData(),
          }
        );
        util.showNotify("정상처리 되었습니다.");
        dialog.value = false;
        searchList();
      } catch (error) {
        util.errorNotify(error.response.data);
      }
    }

    onMounted(() => {
      searchList();
    });

    return {
      columns,
      visibleColumns,
      loading,
      rows,
      userNmDial,
      userIdDial,
      userPwDial,
      userPhDial,
      userAuthDial,
      userNm,
      userId,
      userPh,
      userAuth,
      updateUseYn,
      searchList,
      onRowClick,
      onRegClick,
      registData,
      dialog,
      flag,
      fnLoadEnd,
      fnDailLoadEnd,
      userAuthDialData,
    };
  },
});
const columns = [
  { name: "USER_LVL", label: "레벨", field: "USER_LVL" },
  {
    name: "USER_NM",
    label: "이름",
    field: "USER_NM",
    sortable: true,
  },
  {
    name: "USER_ID",
    required: true,
    label: "아이디",
    field: (row) => row.USER_ID,
    sortable: true,
  },
  { name: "USER_PH", label: "전화번호", field: "USER_PH" },
  { name: "USER_AUTH", label: "권한", field: "USER_AUTH" },
  { name: "USE_YN", label: "사용여부", field: "USE_YN" },
];
const visibleColumns = ["USER_NM", "USER_ID", "USER_PH", "USER_AUTH", "USE_YN"];
</script>
<style scoped></style>
