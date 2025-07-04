<template>
  <div>
    <PageLayout_b>
      <template v-slot:pageTit>마이페이지</template>
      <CardLayout>
        <PageLayout_d>
          <template v-slot:left_top_left>
            <CardLayout>
              <myTable_view btn_defaultLink="" />
            </CardLayout>
          </template>
          <template v-slot:left_top_right>
            <CardLayout>
              <myInfoTable_view />
            </CardLayout>
          </template>
          <template v-slot:left_bottom>
            <CardLayout>
              <myOrderTable_view />
            </CardLayout>
          </template>
          <template v-slot:right>
            <div class="inner_layout rt line">
              <CardLayout>
                <myNoticeTable_view btn_defaultLink="" />
                <myBranchTable_view btn_defaultLink="" />
                <myHelpTable_view btn_defaultLink="/mainPage/my/help" />
                <myAnswerTable_view btn_defaultLink="/mainPage/my/answer" />
              </CardLayout>
            </div>
          </template>
        </PageLayout_d>
      </CardLayout>
    </PageLayout_b>
  </div>
</template>
<script setup>
import PageLayout_b from "src/layouts/web/common/PageLayout_b.vue";
import PageLayout_d from "src/layouts/web/common/PageLayout_d.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import myTable_view from "src/components/web/myPage/myTable_view.vue";
import myInfoTable_view from "src/components/web/myPage/myInfoTable_view.vue";
import myOrderTable_view from "src/components/web/myPage/myOrderTable_view.vue";
import myNoticeTable_view from "src/components/web/myPage/myNoticeTable_view.vue";
import myBranchTable_view from "src/components/web/myPage/myBranchTable_view.vue";
import myHelpTable_view from "src/components/web/myPage/myHelpTable_view.vue";
import myAnswerTable_view from "src/components/web/myPage/myAnswerTable_view.vue";
</script>
<style lang="scss" scoped>
@media screen and (max-width: 830px) {
  .PageLayout_wrap.my {
    .inner_layout.lt {
      padding-right: 0;
    }
    .inner_layout.rt {
      padding-left: 0 !important;
    }
    .inner_layout.line::after {
      display: none !important;
    }
  }
}
</style>
