<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>고시정보</template>
      <CardLayout>
        <PillPriceTable_a />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import PillPriceTable_a from "src/components/web/insurance/NtfctnInfTable.vue";
</script>
<style lang="scss" scoped></style>
