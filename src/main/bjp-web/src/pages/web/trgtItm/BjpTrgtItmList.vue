<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>대상품목</template>
      <CardLayout>
        <PillPriceTable_a />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import PillPriceTable_a from "src/components/web/trgtItm/TrgtItmTable.vue";
</script>
<style lang="scss" scoped></style>
