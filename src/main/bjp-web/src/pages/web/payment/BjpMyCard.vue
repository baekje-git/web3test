<template>
  <div>
    <PageLayout_c>
      <template v-slot:pageTit>마이카드</template>
      <template v-slot:leftSlot>
        <CardLayout>
          <div>
            <PaymentTable_1b />
          </div>
        </CardLayout>
      </template>
      <template v-slot:rightSlot>
        <CardLayout>
          <div class="inner_layout rt">
            <PaymentTable_2b />
          </div>
        </CardLayout>
      </template>
    </PageLayout_c>
  </div>
</template>
<script setup>
import PageLayout_c from "src/layouts/web/common/PageLayout_c.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import PaymentTable_1b from "src/components/web/payment/MyCardTable_a.vue";
import PaymentTable_2b from "src/components/web/payment/MyCardTable_b.vue";
</script>
<style lang="scss" scoped>
@media screen and (max-width: 830px) {
  .left_left .CardLayout_wrap:nth-last-child(1):before {
    display: none;
  }
  .left_right .CardLayout_wrap:nth-last-child(1):before {
    content: "";
    display: block;
    margin: rem(28px) 0;
    width: 100%;
    height: 12px;
    background-color: #f8f8f8;
  }
}
</style>
