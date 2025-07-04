<template>
  <div class="wrap">
    <div class="main_wrap">
      <div class="">
        <div class="row" style="gap: 30px; margin-bottom: 40px">
          <div class="col-6.5">
            <TableLayout_a />
          </div>
          <div class="col-5">
            <TableLayout_b />
          </div>
        </div>
        <div class="row" style="gap: 30px">
          <div class="col-6.5" style="height: 308px">
            <TableLayout_c />
          </div>
          <div class="col-5">
            <ItemDetail />
          </div>
        </div>
      </div>
    </div>
    <div class="side_wrap">
      <SideComp />
    </div>
  </div>
</template>
<script setup>
import SideComp from "src/components/web/common/SideComp.vue";
import TableLayout_a from "src/layouts/web/fre/FreOrdTableL_a.vue";
import TableLayout_b from "src/layouts/web/fre/FreOrdTableL_b.vue";
import TableLayout_c from "src/layouts/web/fre/FreOrdTableL_c.vue";
import ItemDetail from "src/components/web/common/BjpItemDetail.vue";
</script>
<style lang="scss" scoped>
.wrap {
  display: flex;
  flex-wrap: nowrap;
  margin-top: 40px;
}
.main_wrap {
  min-width: 1600px;
  gap: 40px;
  flex-grow: 8;
  margin: 0 40px 0 80px;
}
.side_wrap {
  flex-grow: 1;
  max-width: 200px;
}
</style>
