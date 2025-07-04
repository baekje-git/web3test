<template>
  <section class="content">
    <div class="tit-wrap">
      <div class="btn-wrap">
        <q-btn @click="fn_eformCall_data" class="btn-point" label="저장" />
        &nbsp;
        <q-btn @click="this.$router.go(-1)" class="btn-point" label="목록" />
      </div>
    </div>

    <iframe
      id="if_report"
      name="if_report"
      style="width: 100%; height: 1250px"
    ></iframe>
  </section>
</template>
<script>
import { defineComponent, ref } from "vue";
import { useQuasar } from "quasar";
import { useRoute, useRouter } from "vue-router";
import { api } from "boot/axios";
import * as reportUtil from "src/router/report";
import * as util from "src/support/util";

export default defineComponent({
  name: "FdgpBprDetail",

  components: {},

  setup() {
    const leftDrawerOpen = ref(false);
    const route = useRoute();
    const router = useRouter();

    console.log("user:::::::::::", util.getUserData());

    async function eventFunction(e) {
      console.log(e.data);
      if (!util.isEmpty(e.data.datasetData)) {
        window.removeEventListener("message", eventFunction);
        try {
          //save api
          await api.post("/bindInsert/fdgp.bpr/insertFdgpBpr", {
            eData: e.data.datasetData.dataset_0[0],
            userData: util.getUserData(),
          });
          util.showNotify("정상처리 되었습니다.");
          //list 이동
          router.push({ path: "/fdgp-bpr" });
        } catch (error) {
          util.errorNotify("오류가 발생하였습니다.");
        }
      }
    }
    return {
      val: ref(true),
      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value;
      },
      fn_eformCall_data() {
        window.addEventListener("message", eventFunction);
        reportUtil.fn_eformCall_data("if_report");
      },
    };
  },

  mounted() {
    const param = {
      dataset_0: {
        //col_0: ""
      },
    };
    this.prodNo = this.$route.query.prodNo;
    const paramList = [];
    paramList.USE_DOCUMENT_SAVEAS = "true";
    paramList.PROD_NO = this.prodNo;
    reportUtil.ubiReport2(param, "T_FDGP_BPR", paramList, "if_report");
    //add eventEventListener
  },
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
