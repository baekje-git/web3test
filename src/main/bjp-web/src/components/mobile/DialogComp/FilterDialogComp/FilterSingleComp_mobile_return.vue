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
            :class="{ active: singleBtn === j }"
            @click="btnActive(j, btngroup)"
          >
            {{ btngroup.btnLabel }}
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
  isActiveBtn: Number,
});

const btnGroups = computed(() => {
  return props.btnGroups;
});

//날짜 변경시 직접입력으로 변경
const isActiveBtnChg = ref(props.isActiveBtn);
watch(isActiveBtnChg, () => {
  let index = btnGroups.value[0].buttons.findIndex(
    (obj) => obj.btnLabel == "직접입력"
  );
  if (index !== -1) {
    let label = btnGroups.value[0].buttons[index]?.btnLabel;
    if (label === "직접입력") {
      singleBtn.value = isActiveBtnChg.value;
    }
  }
});

const singleBtn = ref(-1);
const activeBtn = ref("");

const btnActive = (index, btngroup) => {
  if (singleBtn.value === index) {
    singleBtn.value = -1;
    activeBtn.value = "";

    isActiveBtnChg.value = -1;
  } else {
    singleBtn.value = index;
    activeBtn.value = btngroup.btnLabel;

    isActiveBtnChg.value = index;
  }

  emit("selectedSingleBtn", activeBtn.value);
};

//날짜 임의변경
bus.on("onOnChangeDate", async (params) => {
  isActiveBtnChg.value = params;
});

//필터 표시
onMounted(() => {
  singleBtn.value = props.isActiveBtn;
});

bus.on("updateChk",async(data)=>{
  singleBtn.value = data;
});



</script>

<style lang="scss" scoped></style>
