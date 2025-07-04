<template>
  <div>
    <PageLayout_c>
      <template v-slot:pageTit>회수품목내용</template>
      <template v-slot:leftSlot>
        <CardLayout>
          <div class="inner_layout lt">
            <SalesTable_1e />
          </div>
        </CardLayout>
      </template>
      <template v-slot:rightSlot>
        <CardLayout>
          <div class="inner_layout rt">
            <SalesTable_2e />
          </div>
        </CardLayout>
      </template>
    </PageLayout_c>
  </div>
</template>
<script setup>
import PageLayout_c from "src/layouts/web/common/PageLayout_c.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import SalesTable_1e from "src/components/web/clctItem/ClctTable_a.vue";
import SalesTable_2e from "src/components/web/clctItem/ClctTable_b.vue";
</script>

<style lang="scss" scoped>
.inner_layout {
  &.lt {
    padding-right: rem(15px);
  }

  &.rt {
    padding-left: rem(15px);
  }
}
@media screen and (max-width: 830px) {
  .left_right .CardLayout_wrap:nth-last-child(1):before {
    content: "";
    display: block;
    margin: rem(28px) 0;
    width: 100%;
    height: 12px;
    background-color: #f8f8f8;
  }

  .inner_layout {
    &.lt {
      padding-right: 0;
    }

    &.rt {
      padding-left: 0;
    }
  }
}
</style>
