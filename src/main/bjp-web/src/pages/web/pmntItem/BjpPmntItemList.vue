<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>품목수불</template>
      <CardLayout>
        <SalesTable_a />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import SalesTable_a from "src/components/web/pmntItem/PmntItemTable.vue";
</script>
<style lang="scss" scoped></style>
