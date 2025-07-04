<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>자주묻는질문</template>
      <CardLayout>
        <HelpTable />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import HelpTable from "src/components/web/myPage/HelpTable.vue";
</script>
<style lang="scss" scoped></style>
