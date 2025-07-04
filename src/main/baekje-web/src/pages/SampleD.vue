<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>제조지시 및 기록서</h2>
    </div>
    <!---- q-page-container S --->
    <q-page-container class="first-container">
      <!-- tit-sub-wrap S -->
      <div class="tit-sub-wrap">
        <h3>FDG 제조지시 및 기록서</h3>
      </div>
      <!--// tit-sub-wrap E -->
      <!-- reg-wrap S -->
      <div class="reg-wrap">
        <table>
          <caption>
            FDG 제조지시 및 기록서
          </caption>
          <colgroup>
            <col width="8%" />
            <col width="" />
          </colgroup>
          <tbody>
            <tr>
              <th>제조단위</th>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-input
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
                <q-btn
                  @click="showNotif"
                  class="btn-in ico-search"
                  label="검색"
                /><!-- btn -->
                <q-btn
                  @click="showNotif"
                  class="btn-in"
                  label="초기화"
                /><!-- btn -->
              </td>
            </tr>
            <tr>
              <th>제조일시</th>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-select
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>당일출고여부</th>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-input
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-select
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-input
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>유효기간</th>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-input
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!--// reg-wrap E -->
    </q-page-container>
    <!-----// q-page-contaniner E ------>
    <!---- q-page-container S --->
    <q-page-container>
      <!-- tit-sub-wrap S -->
      <div class="tit-sub-wrap">
        <h3>환경모니터링(내부 td제목이 있을경우)</h3>
      </div>
      <!--// tit-sub-wrap E -->
      <!-- reg-wrap S -->
      <div class="reg-wrap">
        <table>
          <caption>
            환경모니터링
          </caption>
          <colgroup>
            <col width="8%" />
            <col width="4%" />
            <col width="18%" />
            <col width="5%" />
            <col width="15%" />
            <col width="5%" />
            <col width="" />
          </colgroup>
          <tbody>
            <tr>
              <th>제조단위</th>
              <td class="al-r">구분</td>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-input
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 150px"
                    />
                  </div>
                </q-banner>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-input
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 100px"
                    />
                  </div>
                </q-banner>
              </td>
              <td class="al-r">낙하군</td>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-select
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
              </td>
              <td class="al-r">표면균1</td>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-input
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 250px"
                    />
                  </div>
                </q-banner>
                <q-btn
                  @click="showNotif"
                  class="btn-in"
                  label="등록"
                /><!-- btn -->
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!--// reg-wrap E -->
    </q-page-container>
    <!-----// q-page-contaniner E ------>
    <!---- q-page-container S --->
    <q-page-container>
      <!-- tit-sub-wrap S -->
      <div class="tit-sub-wrap">
        <h3>원료약품 및 그 분량(form태그 width100%로 지정예시)</h3>
      </div>
      <!--// tit-sub-wrap E -->
      <!-- reg-wrap S -->
      <div class="reg-wrap">
        <table>
          <caption>
            원료약품 및 그 분량
          </caption>
          <colgroup>
            <col width="7%" />
            <col width="18%" />
            <col width="7%" />
            <col width="15%" />
            <col width="7%" />
            <col width="" />
          </colgroup>
          <tbody>
            <tr>
              <th>제조단위</th>
              <td>
                <q-banner
                  dense
                  :class="$q.dark.isActive ? '' : ''"
                  class="w100p"
                >
                  <div class="row items-start">
                    <q-input filled v-model="model" :options="options" />
                  </div>
                </q-banner>
              </td>
              <th>제조일시</th>
              <td>
                <q-banner
                  dense
                  :class="$q.dark.isActive ? '' : ''"
                  class="w100p"
                >
                  <div class="row items-start">
                    <q-select filled v-model="model" :options="options" />
                  </div>
                </q-banner>
              </td>
              <th>당일출고여부</th>
              <td>
                <q-banner
                  dense
                  :class="$q.dark.isActive ? '' : ''"
                  class="w100p"
                >
                  <div class="row items-start">
                    <q-input filled v-model="model" :options="options" />
                  </div>
                </q-banner>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!--// reg-wrap E -->
    </q-page-container>
    <!-----// q-page-contaniner E ------>
    <q-page-container>
      <div class="tit-sub-wrap">
        <h3>input(Disabled),textarea</h3>
      </div>
      <div class="reg-wrap">
        <table>
          <caption>
            TextArea,Input(Disabled) 예시
          </caption>
          <colgroup>
            <col width="8%" />
            <col width="" />
          </colgroup>
          <tbody>
            <tr>
              <th>TextArea</th>
              <td>
                <q-input
                  v-model="text"
                  filled
                  type="textarea"
                  disable
                  class="w400"
                />
              </td>
            </tr>
            <tr>
              <th>Input(Disabled)</th>
              <td>
                <q-input
                  outlined
                  v-model="text"
                  :dense="dense"
                  disable
                  class="w400"
                />
              </td>
            </tr>
            <tr>
              <th>Select(Disabled)</th>
              <td>
                <q-banner dense :class="$q.dark.isActive ? '' : ''">
                  <div class="row items-start">
                    <q-select
                      filled
                      v-model="model"
                      :options="options"
                      style="width: 400px"
                      disable
                    />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>Radio(Disabled)</th>
              <td>
                <q-radio v-model="shape" val="line" label="Line" disable />
                <q-radio
                  v-model="shape"
                  val="rectangle"
                  label="Rectangle"
                  disable
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </q-page-container>
    <!------ 분할화면 S --------->
    <!---- q-page-container S --->
    <q-page-container>
      <!-- tit-sub-wrap S -->
      <div class="tit-sub-wrap">
        <h3>분할화면(2분할)</h3>
      </div>
      <!--// tit-sub-wrap E -->
      <ul class="div-col">
        <li class="w40p">
          <!-- reg-wrap S -->
          <div class="reg-wrap">
            <table>
              <caption>
                분할화면
              </caption>
              <colgroup>
                <col width="15%" />
                <col width="" />
              </colgroup>
              <tbody>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!--// reg-wrap E -->
        </li>
        <li class="w60p">
          <!-- reg-wrap S -->
          <div class="reg-wrap">
            <table>
              <caption>
                분할화면
              </caption>
              <colgroup>
                <col width="12%" />
                <col width="" />
              </colgroup>
              <tbody>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!--// reg-wrap E -->
        </li>
      </ul>
    </q-page-container>
    <!-----// q-page-contaniner E ------>
    <!---- q-page-container S --->
    <q-page-container>
      <!-- tit-sub-wrap S -->
      <div class="tit-sub-wrap">
        <h3>분할화면(3분할)</h3>
      </div>
      <!--// tit-sub-wrap E -->
      <ul class="div-col">
        <li class="w30p">
          <!-- reg-wrap S -->
          <div class="reg-wrap">
            <table>
              <caption>
                분할화면
              </caption>
              <colgroup>
                <col width="15%" />
                <col width="" />
              </colgroup>
              <tbody>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!--// reg-wrap E -->
        </li>
        <li class="w30p">
          <!-- reg-wrap S -->
          <div class="reg-wrap">
            <table>
              <caption>
                분할화면
              </caption>
              <colgroup>
                <col width="15%" />
                <col width="" />
              </colgroup>
              <tbody>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!--// reg-wrap E -->
        </li>
        <li class="w40p">
          <!-- reg-wrap S -->
          <div class="reg-wrap">
            <table>
              <caption>
                분할화면
              </caption>
              <colgroup>
                <col width="15%" />
                <col width="" />
              </colgroup>
              <tbody>
                <tr>
                  <th>제조단위</th>
                  <td>
                    <q-banner
                      dense
                      :class="$q.dark.isActive ? '' : ''"
                      class="w100p"
                    >
                      <div class="row items-start">
                        <q-input filled v-model="model" :options="options" />
                      </div>
                    </q-banner>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!--// reg-wrap E -->
        </li>
      </ul>
    </q-page-container>
    <!-----// q-page-contaniner E ------>
    <q-page-container>
      <div class="tit-sub-wrap">
        <h3>DatePicker</h3>
      </div>
      <div class="reg-wrap">
        <table>
          <caption>
            DatePicker 예시
          </caption>
          <colgroup>
            <col width="8%" />
            <col width="" />
          </colgroup>
          <tbody>
            <tr>
              <th>DatePicker</th>
              <td>
                <Datepicker
                  v-model="picked"
                  :locale="locale"
                  :weekStartOn="0"
                  :inputFormat="inputFormat"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </q-page-container>
  </section>
</template>
<script>
import { defineComponent, ref, reactive } from "vue";
import EssentialLink from "components/EssentialLink.vue";
import { useQuasar } from "quasar";
import RoleMenu from "components/RoleMenu.vue";
import HeaderCmp from "components/HeaderCmp.vue";
import FooterCmp from "components/FooterCmp.vue";
import Datepicker from "vue3-datepicker";
import { ko } from "date-fns/locale";
const temp = "Sample b header";
const temp2 = "Sample b footer";

export default defineComponent({
  name: "MainLayout",

  components: { Datepicker },

  setup() {
    const leftDrawerOpen = ref(false);
    const popup = useQuasar();
    const $R = useQuasar();
    const roleMenuList = ref([]);
    // 메뉴데이터
    roleMenuList.value = [];
    const picked = ref(new Date());
    const locale = reactive(ko);
    const inputFormat = ref("yyyy-MM-dd");

    return {
      roleMenuList,
      val: ref(true),
      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value;
      },
      picked,
      locale,
      inputFormat,
    };
  },
});

/* ///////그리드 스크립트 End //////// */
</script>
<style scoped></style>
