<template>
  <section class="content">
    <q-form
      id="testForm"
      action="http://localhost:9090/inicis/reqPayment"
      method="post"
      target="PayForm"
    >
      <!-- <q-input filled name="goodname" v-model="this.$route.query.goodname" />
      <q-input filled name="buyername" v-model="this.$route.query.buyername" />
      <q-input filled name="buyertel" v-model="this.$route.query.buyertel" />
      <q-input
        filled
        name="buyeremail"
        v-model="this.$route.query.buyeremail"
      />
      <q-input filled name="price" v-model="this.$route.query.price" /> -->
      <!-- <q-btn unelevated type="submit" label="결제" /> -->
      <div class="q-gutter-md row items-start">
        <q-input
          filled
          name="goodname"
          label="상품명"
          model-value="테스트상품"
        />
        <q-input filled name="buyername" label="구매자" model-value="테스터" />
        <q-input
          filled
          name="buyertel"
          label="구매자 전화번호"
          model-value="01012345678"
        />
        <q-input
          filled
          name="buyeremail"
          label="구매자 이메일"
          model-value="test@test.com"
        />
        <q-input filled name="price" label="가격" model-value="1000" />
      </div>
    </q-form>

    <div class="btn-toast">
      <q-btn unelevated type="button" @click="onSubmit" label="결제" />
    </div>

    <!-- <q-btn unelevated type="button" @click="onSubmit" label="결제" /> -->
    <iframe
      id="PayForm"
      name="PayForm"
      style="display: block; width: 80vw; height: 80vh; border: none"
    ></iframe>
  </section>
</template>
<script setup>
import { onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import * as util from "src/support/util";
const route = useRoute();
const router = useRouter();

onMounted(() => {
  if (!util.isEmpty(route.query.MSG)) {
    const paymentMSG =
      route.query.MSG == "success"
        ? "결제가 완료되었습니다."
        : "결제가 취소되었습니다.";

    util.alert("확인", paymentMSG, function () {
      router.push({ path: "/inicisTest" });
    });
  }

  //전달 받은 파라미터 확인
  //console.log("route::::::::", route.query);
  // goodname = route.query.goodname;
  // buyername = route.query.buyername;
  // buyertel = route.query.buyertel;
  // buyeremail = route.query.buyeremail;
  // price = route.query.price;

  //자식창에서 넘어오는 거 확인
  window.addEventListener("message", (e) => {
    // 전달 된 데이터
    //console.log(e.data.functionName);

    // 부모창의 함수 실행
    if (e.data.functionName === "inicisClose") {
      util.alert("확인", "결제를 취소하였습니다.", function () {
        router.go(router.currentRoute);
      });
    }
    //결제 후에는 redirect만 허용되므로 callback 실행 x
    // if (e.data.functionName === "inicisComplete") {
    //   util.alert("확인", "결제가 완료되었습니다.", function () {
    //     router.go(router.currentRoute);
    //   });
    // }
  });
});
function onSubmit(evt) {
  //evt.target.submit();
  document.getElementById("testForm").submit();
}
</script>

<style scoped></style>
