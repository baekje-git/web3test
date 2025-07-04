<template>
  <div class="TabLayout_wrap">
    <div class="tab_sticky">
      <div class="Tab_title">
        <span>{{ TabTitle }}</span>
      </div>

      <div class="Tab_btn">
        <q-tabs
          v-model="dialog_tab"
          dense
          content-class="tab-menu"
          active-class="tab-active"
          @update:model-value="(val) => onChangeValue(val)"
        >
          <q-tab name="first_tab" class="">
            <template v-slot>
              <span>
                {{ First_btn }}
              </span>
            </template>
          </q-tab>
          <q-tab name="second_tab" class="">
            <template v-slot>
              <span>
                {{ Second_btn }}
              </span>
            </template>
          </q-tab>
        </q-tabs>

        <slot name="table_top"> </slot>
      </div>
    </div>

    <q-tab-panels v-model="dialog_tab" animated>
      <q-tab-panel name="first_tab" class="tab_table_wrap">
        <slot name="first_tab"></slot>
      </q-tab-panel>
      <q-tab-panel name="second_tab" class="tab_table_wrap">
        <slot name="second_tab"></slot>
      </q-tab-panel>
    </q-tab-panels>
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";

//Tab 변경시 emit
const emit = defineEmits(["onChangeTab"]);

const dialog_tab = ref("first_tab");

const props = defineProps({
  TabTitle: {
    type: String,
    default: "TabTitle",
  },
  First_btn: {
    type: String,
    default: "First_btn",
  },
  Second_btn: {
    type: String,
    default: "Second_btn",
  },
});

//Tab 변경시 이벤트
const onChangeValue = (val) => {
  emit("onChangeTab", val);
};
</script>

<style lang="scss" scoped>
.TabLayout_wrap {
  position: relative;
  height: auto;

  .tab_sticky {
    position: sticky;
    top: 0;
    z-index: 1;
    background-color: #fff;

    .Tab_title {
      padding: 16px 20px;
      span {
        @include fw-7;
      }
    }
    .tab_table_wrap {
      padding: 16px 0 0;
    }
  }
}
.q-tabs {
  margin-bottom: 28px;
  padding: 0 20px;
  // border-bottom: 1px solid $line-300;
}
.q-tab {
  color: $gray-7;
  // background-color: #f8f8f8;
  border: 1px solid $line-300;
}
.tab-active {
  border-color: $primary;
  color: $primary;
  font-weight: 600;
  // background-color: #fff;
}
</style>
