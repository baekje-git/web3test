<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>질의 게시판</h2>
    </div>
    <q-page-container>
      <div class="tit-sub-wrap">
        <h3>문의사항</h3>
      </div>
      <div class="reg-wrap">
        <table>
          <colgroup>
            <col width="10%" />
            <col width="" />
          </colgroup>
          <tbody>
            <tr>
              <th>작성자</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="inquirerNm" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>작성일시</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="inquireDtm" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>제조번호</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="prodNo" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>제조지시일</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="mandateDt" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>제조지시자</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="mandateNm" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>문서</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="process" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>문의 제목</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="inquireTitle" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>문의 내용</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input
                      filled
                      type="textarea"
                      v-model="inquireContent"
                      class="w100p"
                      disable
                    />
                  </div>
                </q-banner>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      &nbsp;
      <div class="tit-sub-wrap">
        <h3>답변</h3>
      </div>
      <div class="reg-wrap">
        <table>
          <colgroup>
            <col width="10%" />
            <col width="" />
          </colgroup>
          <tbody>
            <tr>
              <th>작성자</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="responserNm" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>작성일시</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="responseDtm" disable />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>답변 내용</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input
                      filled
                      type="textarea"
                      v-model="responseContent"
                      class="w100p"
                      disable
                    />
                  </div>
                </q-banner>
              </td>
            </tr>
          </tbody>
        </table>
        &nbsp;
        <div class="btn-wrap btn-bottom">
          <q-btn @click="this.$router.go(-1)" class="btn-point" label="목록" />
        </div>
      </div>
    </q-page-container>
  </section>
</template>
<script>
import { defineComponent, ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { api } from "boot/axios";

export default defineComponent({
  name: "MainLayout",

  components: {},

  setup() {
    const router = useRouter();
    const route = useRoute();

    const inquirerNm = ref("");
    const inquireDtm = ref("");
    const prodNo = ref("");
    const mandateDt = ref("");
    const mandateNm = ref("");
    const process = ref("");
    const inquireTitle = ref("");
    const inquireContent = ref("");
    const responserNm = ref("");
    const responseDtm = ref("");
    const responseContent = ref("");

    const searchList = (seq) => {
      return api
        .post("/fdg/qna/fdgQnaData", {
          SEQ: seq,
        })
        .then((response) => {
          inquirerNm.value = response.data.INQUIRER_NM;
          inquireDtm.value = response.data.INQUIRE_DTM;
          prodNo.value = response.data.PROD_NO;
          mandateDt.value = response.data.MANDATE_DT;
          mandateNm.value = response.data.MANDATE_NM;
          process.value = response.data.PROCESS_NM;
          inquireTitle.value = response.data.INQUIRE_TITLE;
          inquireContent.value = response.data.INQUIRE_CONTENT;
          responserNm.value = response.data.RESPONSER_NM;
          responseDtm.value = response.data.RESPONSE_DTM;
          responseContent.value = response.data.RESPONSE_CONTENT;
        });
    };

    onMounted(() => {
      searchList(route.query.seq);
    });

    return {
      inquirerNm,
      inquireDtm,
      prodNo,
      mandateDt,
      mandateNm,
      process,
      inquireTitle,
      inquireContent,
      responserNm,
      responseDtm,
      responseContent,
    };
  },

  mounted() {},
});
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
