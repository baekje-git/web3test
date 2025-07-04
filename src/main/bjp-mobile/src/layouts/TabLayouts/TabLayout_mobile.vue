<template>
  <div class="TabLayout_wrap">
    <div class="Tab_title">
      <span>{{ TabTitle }}</span>
    </div>

    <div class="Tab_btn">
      <q-tabs
        v-model="dialog_tab"
        dense
        content-class="btn-gray"
        active-class="btn-default"
        style="border-radius: 5px;"
        >
        <q-tab name="first_tab">
          <template v-slot>
            <span style="font-size: 14px;">
              {{ First_btn }}
            </span>
          </template>
        </q-tab>
        <q-tab name="second_tab">
          <template v-slot>
            <span style="font-size: 14px;">
              {{ Second_btn }}
            </span>
          </template>
        </q-tab>
      </q-tabs>

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
import { ref, defineProps } from 'vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';

const dialog_tab = ref('first_tab');

const props = defineProps({
  TabTitle: {
    type: String,
    default: 'TabTitle'
  },
  First_btn: {
    type: String,
    default: 'First_btn'
  },
  Second_btn: {
    type: String,
    default: 'Second_btn'
  },

})


</script>

<style lang="scss" scoped>

.TabLayout_wrap {
  .Tab_title {
    padding: 16px 0;
    span {
      @include fw-7;
    }
  }
  .tab_table_wrap {
    padding: 16px 0 0;
  }
}

</style>
