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
        <q-popup-proxy cover transition-show="scale" transition-hide="scale">
          <q-date
            v-model="date"
            default-view="Years"
            emit-immediately
            @update:model-value="onUpdateMv"
            minimal mask="YYYY/MM"
            ref="dateref"
          >
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
import { ref } from 'vue'

const props = defineProps({
  placeholder: String
})

const date = ref(null)

const dateref = ref(null)
const currentView = ref('Years')

function onUpdateMv(val){
  currentView.value = currentView.value === 'Years' ? 'Months' : 'Years'
  dateref.value.setView('Months')
}


</script>

<style lang="scss">



</style>
