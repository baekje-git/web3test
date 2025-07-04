<template>
  <div class="flex_between" style="gap:4px;">
    <div class="">
      <q-input
        v-model="date"
        class="input-common input-default"
        :placeholder="placeholder"

        borderless
        dense
        flat
      />
    </div>
    <div class="date_btn_wrap">
      <q-btn
        class="calendar_icon"
        unelevated
        dense
        style="padding: 0;"
      >
      <img src="/public/assets/icons/calendar-days-solid.svg" alt="calendar">
        <q-popup-proxy @before-show="updateProxy" cover transition-show="scale" transition-hide="scale">
          <q-date v-model="proxyDate" minimal mask="YYYY.MM.DD" >
            <div class="row items-center justify-end q-gutter-sm">
              <q-btn label="Cancel" color="primary" flat v-close-popup />
              <q-btn label="OK" color="primary" flat @click="save" v-close-popup />
            </div>
          </q-date>
        </q-popup-proxy>
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineModel } from 'vue'
import { getToday } from 'src/support/util';

const date = defineModel('input_model_props');

const props = defineProps({
  placeholder: {
    type: String,
    default: getToday("YYYY.MM.DD") ,
  },
  input_model_props: {
    type: String,
  },
})


const proxyDate = ref()

const updateProxy = () => {
  proxyDate.value = date.value
}

const save = () => {
  date.value = proxyDate.value
}
</script>

<style lang="scss">



</style>
