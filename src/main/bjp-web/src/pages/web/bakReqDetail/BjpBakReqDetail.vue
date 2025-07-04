<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>반품상세</template>
      <CardLayout class="bakReqDetail_wrap">
        <SalesTable_a />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import SalesTable_a from "src/components/web/bakReqDetail/BakReqDetailTable.vue";
</script>
<style lang="scss" scoped>
.bakReqDetail_wrap {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.bakReqDetail_wrap::-webkit-scrollbar {
  display: none;
}
</style>
