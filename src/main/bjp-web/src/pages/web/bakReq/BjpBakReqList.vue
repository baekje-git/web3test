<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>반품현황</template>
      <CardLayout>
        <BakReqTable_a />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import BakReqTable_a from "src/components/web/bakReq/BakReqTable_a.vue";
</script>

<style lang="scss" scoped></style>
