<template>
  <div class="SelectYearComp_wrap">
    <q-input
      v-model="data.count"
      input-class="input_Year"
      suffix="년"
      @input="update"
      borderless
      dense
    >
      <template v-slot:prepend>
        <div class="input_Year_icon" @click="minus">
          <!-- <img src="assets/icons/caret-left-solid.svg" alt="icon" /> -->
          <img src="assets/icons/arrow-prev01.svg" alt="icon" />
        </div>
      </template>
      <template v-slot:append>
        <div class="input_Year_icon" @click="plus">
          <!-- <img src="assets/icons/caret-right-solid.svg" alt="icon" /> -->
          <img src="assets/icons/arrow-next01.svg" alt="icon" />
        </div>
      </template>
    </q-input>
  </div>
</template>
<script setup>
import { reactive } from "vue";
import * as util from "src/support/util";

const data = reactive({
  count: util.getFromMonth(-12, "YYYY"),
});

const plus = () => {
  data.count++;
};

const minus = () => {
  if (data.count > 0) {
    data.count--;
  }
};

const update = (event) => {
  const newValue = parseInt(event.target.value);
  if (!isNaN(newValue)) {
    data.count = newValue;
  }
};
</script>
<style lang="scss">
.input_Year {
  @include fs-5;
  text-align: center;
  width: 45px;
  height: 32px;
  padding: 0 !important;
  margin-top: -2px;
  border-radius: 0;
  border: 1px solid $line-400;
}
.input_Year_icon {
  cursor: pointer;
}
.q-field__suffix {
  font-size: 14px;
  // margin: -4px 0 0 -1px;
  padding: 0;
}
</style>
