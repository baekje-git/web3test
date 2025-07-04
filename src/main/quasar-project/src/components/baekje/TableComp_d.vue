<template>
  <div class="Table_d_wrap" style="width: 660px; height: 270px">
    <div class="table_header">
      <div class="table_title">
        <p>제품정보</p>
        <p>{{ pillTitle }}</p>
      </div>
      <div class="table_btn">
        <BtnComp_default btn-label="상세정보" />
      </div>
    </div>

    <div class="table_sec">
      <q-markup-table separator="noen" flat dense>
        <tbody>
          <tr>
            <td style="width: 70px" class="text-left">규격</td>
            <td class="text-left">30T</td>
            <td style="width: 70px" class="text-left">박스입수</td>
            <td class="text-left spanbold">
              <span>(소)</span>
              12
              <span>(중)</span>
              48
              <span>(대)</span>
              144
            </td>
          </tr>
          <tr>
            <td class="text-left">제약사</td>
            <td class="text-left">대웅제약</td>
            <td class="text-left">최소주문</td>
            <td class="text-left">-</td>
          </tr>
          <tr>
            <td class="text-left">보험코드</td>
            <td class="text-left">641602040</td>
            <td class="text-left">최대주문</td>
            <td class="text-left">10</td>
          </tr>
          <tr>
            <td class="text-left">반품</td>
            <td class="text-left" style="font-weight: bold; color: #384dd6">
              반품제약
            </td>
            <td class="text-left">보험약가</td>
            <td class="text-left">24.01.01 | 25,000원</td>
          </tr>
          <tr>
            <td class="text-left">반품조건</td>
            <td class="text-left">유효기간 6개월 미만 불가</td>
            <td class="text-left">입고일정</td>
            <td class="text-left">24년 1월 중순 ~ 2월 초 예정</td>
          </tr>
        </tbody>
      </q-markup-table>
    </div>
  </div>
</template>

<script>
import BtnComp_default from "src/components/baekje/BtnComp_default.vue";

export default {
  components: {
    BtnComp_default,
  },
  props: {
    pillTitle: {
      tpye: String,
      default: "우루사정300mg(병)",
    },
    btnLabel: {
      type: String,
      default: "버튼",
    },
  },
  data: () => ({}),
  setup() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.Table_d_wrap {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.33);
}
.table_header {
  @include flex_between;
  width: 100%;
  padding-bottom: 8px;
  border-bottom: 2px solid $b4;
}
.table_title {
  @include flex_start;
}
.table_title p {
  @include fw-7;
}
.table_title p:nth-child(2) {
  color: $b3;
  margin-left: 10px;
}
.spanbold span,
.table_sec tr td:nth-child(1),
.table_sec tr td:nth-child(3) {
  @include fs-5;
  @include fw-7;
  line-height: 24px;
}
.table_sec {
  padding: 10px 0;
}
</style>
