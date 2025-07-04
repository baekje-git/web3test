<template>
  <div class="Filter_wrap">
    <div class="Filter_Btn_wrap" v-for="(group, i) in btnGroups" :key="i">
      <div class="Filter_Title">
        <span>{{ group.title }}</span>
        <span>{{ group.subtitle }}</span>
      </div>
      <div class="Filter_Btn_Grid_wrap">
        <q-btn-group
          class="Filter_Btn_Grid"
          :style="{
            gridTemplateColumns: 'repeat(' + group.gridStyle + ', 1fr)',
          }"
        >
          <q-btn
            v-for="(btngroup, j) in group.buttons"
            :key="j"
            class="btn-common btn-gray Filter_Btn"
            size="lg"
            flat
            dense
            no-caps
            :class="{ active: activeBtn[i] === j }"
            @click="btnActive(i, j)"
          >
            {{ btngroup.btnLabel }}
            {{ btngroup.btnSubLabel }}
          </q-btn>
        </q-btn-group>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  ref,
  defineProps,
  defineEmits,
  onMounted,
  computed,
  watch,
  inject,
} from "vue";

const bus = inject("bus");

const emit = defineEmits(["selectedSingleBtn"]);
const props = defineProps({
  btnGroups: Array,
  isActiveBtn: String,
});

const activeBtn = ref([]);

const resetBtn = () => {
  let btnIdx = props.btnGroups[0].buttons.findIndex((i)=> i.btnLabel == props.isActiveBtn);
  activeBtn.value = props.btnGroups.map(() => btnIdx);
};

const btnActive = (groupIndex, buttonIndex) => {
  activeBtn.value[groupIndex] = buttonIndex;

  let btnLabel = props.btnGroups[0].buttons[activeBtn.value[groupIndex]].btnLabel;

  emit("selectedSingleBtn", btnLabel);
};

onMounted(() => {
  resetBtn();
});


</script>

<style lang="scss" scoped></style>
