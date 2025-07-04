<template>
  <div>
    <PageLayout_a>
      <template v-slot:pageTit>최근주문내역</template>
      <template v-slot:leftSlot>
        <div
          v-for="table in allTables"
          :key="table.name"
          v-show="leftSlots.includes(table)"
          class="card-wrap"
        >
          <CardLayout>
            <KeepAlive>
              <div>
                <component :is="table.component" />
              </div>
            </KeepAlive>
          </CardLayout>
        </div>
      </template>
      <template v-slot:rightSlot>
        <div
          v-for="table in allTables"
          :key="table.name"
          v-show="rightSlots.includes(table)"
          class="card-wrap"
        >
          <CardLayout>
            <KeepAlive>
              <div class="inner_layout rt">
                <component :is="table.component" />
              </div>
            </KeepAlive>
          </CardLayout>
        </div>
      </template>
    </PageLayout_a>
  </div>
</template>
<script setup>
import { ref, computed, onMounted, onBeforeUnmount, createApp } from "vue";
import PageLayout_a from "src/layouts/web/common/PageLayout_a.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import SalesTable_1g from "src/components/web/recently/RecentlyTable_a.vue";
import SalesTable_2g from "src/components/web/recently/RecentlyTable_b.vue";
import SalesTable_3g from "src/components/web/recently/RecentlyTable_c.vue";
import SalesTable_4g from "src/components/web/recently/RecentlyTable_d.vue";

// 화면 크기에 따라 레이아웃을 변경 추가 240213
const isSmallScreen = ref(window.innerWidth < 830);
const updateScreenSize = () => {
  isSmallScreen.value = window.innerWidth < 830;
};
onMounted(() => {
  window.addEventListener("resize", updateScreenSize);
});
onBeforeUnmount(() => {
  window.removeEventListener("resize", updateScreenSize);
});

// 테이블 목록 (객체 유지)
const allTables = [
  { name: "SalesTable_1g", component: SalesTable_1g },
  { name: "SalesTable_2g", component: SalesTable_2g },
  { name: "SalesTable_3g", component: SalesTable_3g },
  { name: "SalesTable_4g", component: SalesTable_4g },
];

// 왼쪽 슬롯
const leftSlots = computed(() => {
  return isSmallScreen.value
    ? [allTables[0], allTables[1]] // 작은 화면에서 순서 변경
    : [allTables[0], allTables[2]]; // 기본 순서
});

// 오른쪽 슬롯
const rightSlots = computed(() => {
  return isSmallScreen.value
    ? [allTables[2], allTables[3]] // 작은 화면에서 순서 변경
    : [allTables[1], allTables[3]]; // 기본 순서
});
</script>
<style lang="scss" scoped>
.card-wrap > div {
  padding: 0;
}
.card-wrap::before {
  content: "";
  display: block;
  margin: rem(28px) 0;
  width: 100%;
  height: 12px;
  background-color: #f8f8f8;
}
.left_left {
  .card-wrap:nth-child(1):before {
    display: none;
  }
}
@media screen and (min-width: 831px) {
  .left_right {
    .card-wrap:nth-child(2):before {
      display: none;
    }
  }
}
@media screen and (max-width: 830px) {
  .left_right {
    .card-wrap:nth-child(1):before {
      display: none;
    }
  }
}
</style>
