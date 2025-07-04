<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>월간조회</template>
      <CardLayout style="width: 100%">
        <SalesTable_a />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import SalesTable_a from "src/components/web/custMonth/CustMonthTable.vue";
</script>
<style lang="scss" scoped></style>
