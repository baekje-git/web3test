<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>문의/답변</template>
      <CardLayout>
        <AnswerTable />
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import AnswerTable from "src/components/web/myPage/AnswerTable.vue";
</script>
<style lang="scss" scoped></style>
