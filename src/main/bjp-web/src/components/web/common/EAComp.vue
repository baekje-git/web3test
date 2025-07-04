<template>
  <div class="EAComp_wrap">
    <div class="EA_minus" @click="minus">
      <i class="ico qty-minus"></i>
      <!-- <img src="assets/icons/minus-solid.svg" alt="minus" /> -->
    </div>
    <div class="EA_result">
      <q-input
        class="EA_input"
        input-class="text-center"
        borderless
        reverse-fill-mask
        mask="###,###,###"
        v-model="data.count"
        @update:model-value="update"
        @focus="(input) => input.target.select()"
      />
    </div>
    <div class="EA_plus" @click="plus">
      <i class="ico qty-plus"></i>
      <!-- <img src="assets/icons/plus-solid.svg" alt="plus" /> -->
    </div>
  </div>
</template>
<script setup>
import { reactive, defineEmits, inject } from "vue";
import * as util from "src/support/util";
import { date, event } from "quasar";
const emit = defineEmits(["updateReqQty"]);
const bus = inject("bus");

const data = reactive({
  count: 0,
});

const plus = () => {
  data.count++;

  emit("updateReqQty", data.count);
};

const minus = () => {
  if (data.count > 0) {
    data.count--;
  }
  emit("updateReqQty", data.count);
};

const update = () => {
  let newValue = data.count.replace(/,/g, "");
  newValue = newValue.replace(/^0+(?!$)/, "");
  newValue = parseInt(newValue);
  data.count = newValue;

  emit("updateReqQty", data.count);
};

bus.on("initEAount", () => {
  data.count = 0;
});
</script>
<style lang="scss" scoped>
.EAComp_wrap {
  @include flex_between;
  // width: 70px;
  width: 64px;
  height: 24px;
  background-color: transparent;
  border: 1px solid $line-400;
  overflow: hidden;
}
.EA_minus,
.EA_plus {
  @include flex_center;
  width: 20px;
  height: 100%;
  background-color: transparent;
  border: none;
  cursor: pointer;

  $ico-size: 7px;
  .ico {
    // width: 9px;
    // height: 9px;
    width: $ico-size;
    height: $ico-size;
  }
  .qty-minus {
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 12 12"><g id="Group" transform="translate(-2 -2)"><path id="Vector" d="M0,0H12V12H0Z" transform="translate(2 2)" fill="none"/><g id="Group-2" data-name="Group"><path id="Vector-2" data-name="Vector" d="M0,0H9" transform="translate(3.5 8)" fill="none" stroke="%23333" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/></g></g></svg>');
  }

  .qty-plus {
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 10 10"><g id="Group" transform="translate(-3 -3)"><path id="Vector" d="M0,0H9" transform="translate(3.5 8)" fill="none" stroke="%23333" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/><path id="Vector-2" data-name="Vector" d="M0,9V0" transform="translate(8 3.5)" fill="none" stroke="%23333" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/></g></svg>');
  }
}
.q-input {
  @include fs-5;
  width: 32px;
  text-align: center;
  border: none;
}
</style>
