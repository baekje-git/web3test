<template>
  <div class="Select_default_wrap slct-wrap">
    <q-select
      class="select_default"
      v-model="selectModelProp"
      :options="selectOptionProp"
      borderless
      dense
    >
      <template v-slot:option="scope">
        <q-item v-bind="scope.itemProps">
          <q-item-section>
            <q-item-label class="select_label">{{ scope.opt }}</q-item-label>
          </q-item-section>
        </q-item>
      </template>
    </q-select>
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue";

const { selectModel, selectOption } = defineProps([
  "selectModel",
  "selectOption",
]);

const selectModelProp = ref(selectModel);
const selectOptionProp = ref(selectOption);
</script>

<style lang="scss">
.Select_default_wrap {
  position: relative;
}
.select_default {
  width: 100%;
  padding: 0 6px;
  @include fs-5;
  border: 1px solid $line-400;
  border-radius: 0;
  .q-field__control {
    background-color: transparent;
  }
}
// .select_default span {
//   color: #000;
// }

// select
.Select_default_wrap,
.slct-wrap {
  @include flex_start;
  position: relative;
  min-width: rem(110px);
  height: 32px;
  border: 1px solid $line-400;
  > .select_default {
    padding: 0;
    border: none;
    border-radius: 0;
  }
  .q-field__control-container {
    padding-left: 5px !important;
  }
  .q-select__dropdown-icon {
    width: 20px;
    height: 20px;
    font-size: 0;
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="9.914" height="5.467" viewBox="0 0 9.914 5.467"><path id="Vector" d="M8.5,0,4.25,4.26,0,0" transform="translate(0.707 0.707)" fill="none" stroke="%23333" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/></svg>');
    background-position: center;
    background-repeat: no-repeat;
    background-size: 8px auto;
    vertical-align: middle;
    transition: 0.3s;
    overflow: hidden;
  }
}
[role="listbox"].q-position-engine {
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.15), 0 5px 10px rgba(0, 0, 0, 0.1),
    0 3px 0 0 rgba(0, 0, 0, 0.12);
  border-radius: 0;
  transform: translateY(4px);
  .q-item {
    padding: 8px;
    min-height: auto;
    font-size: rem(14px);
  }
}
</style>
