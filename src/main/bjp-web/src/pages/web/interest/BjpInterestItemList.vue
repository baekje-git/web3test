<template>
  <div>
    <PageLayout_e>
      <template v-slot:pageTit>관심상품</template>
      <template v-slot:left_left>
        <CardLayout>
          <div class="inner_layout lt">
            <InterestTable_a />
          </div>
        </CardLayout>
        <CardLayout>
          <div class="inner_layout lt">
            <InterestTable_c />
          </div>
        </CardLayout>
      </template>
      <template v-slot:left_right>
        <CardLayout>
          <div class="inner_layout">
            <InterestTable_b />
          </div>
        </CardLayout>
      </template>
    </PageLayout_e>
  </div>
</template>
<script setup>
import PageLayout_e from "src/layouts/web/common/PageLayout_e.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import InterestTable_a from "src/components/web/interest/InterestTable_a.vue";
import InterestTable_b from "src/components/web/interest/InterestTable_b.vue";
import InterestTable_c from "src/components/web/interest/InterestTable_c.vue";
</script>
<style lang="scss" scoped>
.left_left .CardLayout_wrap:nth-last-child(1):before {
  content: "";
  display: block;
  margin: rem(28px) 0;
  width: calc(100% - #{rem(30px)});
  height: 12px;
  background-color: #f8f8f8;
}
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
