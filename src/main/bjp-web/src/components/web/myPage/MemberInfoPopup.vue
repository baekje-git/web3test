<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenMemberInfoPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenMemberInfoPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>회원정보수정</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>회원정보수정</span>
        </div>

        <div class="q-card_c_section_wrap">
          <div class="q-card_c_table_header_wrap">
            <p>기본정보</p>
            <BtnComp_default
              btn-label="거래약정서/개인정보이용동의서 확인"
              class="btn-line default"
              @click="onClickDocument"
            />
            <BtnComp_default
              v-if="userInfo.USER_GB_CD === '30'"
              btn-label="수정"
              class="btn-line default"
              @click="onClickEditMember"
            />
          </div>
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 15%">약국명</td>
                <td style="width: 35%">
                  <!-- <q-input
                    class="q-card_c_input"
                    borderless
                  /> -->
                  {{ custDataParam.custNm }}
                </td>
                <td style="width: 15%">약사명</td>
                <td style="width: 35%">
                  <!-- <q-input
                    class="q-card_c_input"
                    borderless
                  /> -->
                  {{ custDataParam.ceoNm }}
                </td>
              </tr>
              <tr>
                <td>전화번호</td>
                <td>
                  <q-input
                    v-model="custDataParam.telNo"
                    class="q-card_c_input"
                    borderless
                  />
                </td>
                <td>휴대전화</td>
                <td>
                  <q-input
                    v-model="custDataParam.smsNo"
                    class="q-card_c_input"
                    borderless
                  />
                </td>
              </tr>
              <tr>
                <td>이메일</td>
                <td colspan="3">
                  <q-input
                    v-model="custDataParam.email"
                    class="q-card_c_input"
                    borderless
                    style="max-width: 200px"
                  />
                </td>
              </tr>
              <tr>
                <td>약국담당자</td>
                <td>
                  <q-input
                    v-model="custDataParam.empNm"
                    class="q-card_c_input"
                    borderless
                    disable
                  />
                </td>
                <td>담당연락처</td>
                <td>
                  <q-input
                    v-model="custDataParam.empHp"
                    class="q-card_c_input"
                    borderless
                    disable
                  />
                </td>
              </tr>
              <tr>
                <td>주소</td>
                <td colspan="3">
                  <q-input
                    v-model="custDataParam.custAddress"
                    class="q-card_c_input"
                    borderless
                    style="max-width: 445px"
                    disable
                  />
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <div class="q-card_c_user_wrap">
          <q-table
            class="q-card_c_Table_wrap"
            :rows="rows"
            :columns="columns"
            row-key="name"
            table-header-class="table_header"
            dense
            hide-bottom
            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
          >
            <template v-slot:top>
              <div class="q-table_wrap">
                <div class="flex_between" style="margin-bottom: 8px">
                  <div class="q-table_header_wrap">
                    <div class="q-table_header_title">
                      <p style="margin-bottom: 5px">사용자 정보</p>
                    </div>
                  </div>
                  <div class="">
                    <MemberInfoAdd>
                      <BtnComp_default
                        class="btn-line default"
                        btn-label="추가"
                      />
                    </MemberInfoAdd>
                  </div>
                </div>
              </div>
            </template>

            <template v-slot:body-cell-수정="props">
              <q-td :props="props">
                <MemberInfoEdit
                  :login-id="props.row.LOGIN_ID"
                  :user-id="props.row.USER_ID"
                  :user-nm="props.row.USER_NM"
                >
                  <BtnComp_active_small
                    class="btn-line default"
                    btn_activeLabel="수정"
                  />
                </MemberInfoEdit>
              </q-td>
            </template>

            <template v-slot:body-cell-비밀번호="props">
              <q-td :props="props">
                <ChangePwd :user-id="props.row.USER_ID">
                  <BtnComp_active_small
                    class="btn-line default"
                    btn_activeLabel="변경"
                  />
                </ChangePwd>
              </q-td>
            </template>

            <template v-slot:body-cell-사용여부변경="props">
              <q-td :props="props">
                <BtnComp_active_small
                  class="btn-line default"
                  :btn_activeLabel="
                    props.row.USE_YN === 'Y' ? '미사용' : '사용'
                  "
                  @click="onClickUseYn(props.row)"
                />
              </q-td>
            </template>
          </q-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default
            class="btn-fill gray"
            btn-label="닫기"
            v-close-popup
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
  <DialogComp_document />
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import MemberInfoEdit from "src/components/web/myPage/MemberInfoEdit.vue";
import ChangePwd from "./ChangePwd.vue";
import MemberInfoAdd from "./MemberInfoAdd.vue";
import DialogComp_document from "src/components/web/myPage/documentPopup.vue";

const isOpenMemberInfoPopup = ref(false);
const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
});

const custDataParam = ref({
  ceoNm: "",
  custAddress: "",
  custNm: "",
  email: "",
  empHp: "",
  empNm: "",
  smsNo: "",
  smsYn: "",
  telNo: "",
  custCd: userInfo.CUST_CD,
});

const memberInfo = ref({
  custCd: userInfo.CUST_CD,
  useYn: "",
  modId: userInfo.USER_ID,
  userId: "",
  userNm: "",
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const { custData, list } = await api.get("/myPage/memberInfo", {
    params: searchParam.value,
  });

  custDataParam.value.ceoNm = custData.CEO_NM;
  custDataParam.value.custAddress = custData.CUST_ADDRESS;
  custDataParam.value.custNm = custData.CUST_NM;
  custDataParam.value.email = custData.EMAIL;
  custDataParam.value.empHp = custData.EMP_HP;
  custDataParam.value.empNm = custData.EMP_NM;
  custDataParam.value.smsNo = custData.SMS_NO;
  custDataParam.value.smsYn = custData.SMS_YN;
  custDataParam.value.telNo = custData.TEL_NO;

  rows.value = list;
}

async function onClickUseYn(row) {
  if (userInfo.CUST_CD === row.USER_ID) {
    alert("대표사용자는 변경 할 수 없습니다.");
    return;
  }

  if (userInfo.CUST_CD === row.USER_ID) {
    alert("본인 아이디는 변경 할 수 없습니다.");
    return;
  }

  if (!confirm("변경 하시겠습니까?")) return;

  memberInfo.value.useYn = row.USE_YN === "Y" ? "N" : "Y";
  memberInfo.value.userId = row.USER_ID;

  await api.put("/myPage/editUseYn", memberInfo.value);
  util.showNotify("변경 되었습니다.");
  await fetchData();
}

async function onClickEditMember() {
  const telNo = custDataParam.value.telNo;
  const smsNo = custDataParam.value.smsNo;
  const email = custDataParam.value.email;

  if (telNo === "") {
    alert("전화번호를 입력해 주세요.");
    return;
  } else if (smsNo === "") {
    alert("휴대전화를 입력해 주세요.");
    return;
  } else if (email === "") {
    alert("이메일을 입력해 주세요.");
    return;
  }

  // const regex = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

  // custDataParam.value.telNo = telNoV.replace(regex, "");
  // custDataParam.value.smsNo = smsNoV.replace(regex, "");

  await api.put("/myPage/editCustInfo", custDataParam.value);
  util.showNotify("수정되었습니다.");
  await fetchData();
}

async function onClickDocument() {
  bus.emit("documentInfo");
}

bus.on("memberInfo", async () => {
  await fetchData();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  { name: "USER_NM", label: "사용자명", field: "USER_NM", align: "center" },
  {
    name: "LOGIN_ID",
    label: "로그인아이디",
    field: "LOGIN_ID",
    align: "center",
  },
  { name: "수정", label: "수정", field: "수정", align: "center" },
  { name: "비밀번호", label: "비밀번호", field: "비밀번호", align: "center" },
  { name: "USE_YN_NM", label: "사용여부", field: "USE_YN_NM", align: "center" },
  {
    name: "사용여부변경",
    label: "사용여부변경",
    field: "사용여부변경",
    align: "center",
  },
]);
</script>

<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 10px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.q-card_c_title p {
  @include fw-7;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}
.q-card_c_user_wrap,
.q-card_c_section_wrap {
  padding: 0 16px;
  width: 640px;
}

.q-card_c_table_header_wrap {
  @include flex_between;
  @include fw-7;
  font-size: 19px;
  padding-bottom: 8px;
  // border-bottom: 2px solid $b3;
  border-bottom: 1px solid $gray-9;
}
.q-card_c_section_wrap {
  margin-bottom: 40px;
  tr:last-child,
  td {
    // border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    border-bottom: 1px solid $td-line;
  }
  tr td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
  }
  td {
    height: 40px !important;
  }
}
.q-card_c_input {
  @include fs-5;
  border: 1px solid $line-400;
  border-radius: 0;
  max-width: 140px;
}
.q-card_c_input .q-field__control {
  padding: 0 8px;
  height: 32px !important;
}
.q-card_btn_wrap {
  @include flex_center;
}

.q-card_c_Table_wrap {
  max-height: 180px;
  overflow-y: auto;
}
.q-card_c_Table_wrap tr {
  height: 48px !important;
}
</style>
