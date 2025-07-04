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
            class="btn-common Filter_Btn"
            :class="{ active: multipulBtn.includes(j) }"
            size="lg"
            flat
            dense
            no-caps
            @click="btnSelect(j, btngroup)"
          >
            {{ btngroup.btnLabel }}
          </q-btn>
        </q-btn-group>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, onMounted } from "vue";

const emit = defineEmits(["selectedMultiBtn"]);

const props = defineProps({
  btnGroups: Array,
  isActiveBtns: Array,
});

const multipulBtn = ref([]);
const activeBtn = ref([]);

const btnSelect = (index, btngroup) => {
  const idx = multipulBtn.value.indexOf(index);
  const btnIdx = activeBtn.value.indexOf(btngroup.btnLabel);

  if (btngroup.btnLabel === "전체") {
    const tempData = props.btnGroups[0].buttons;

    multipulBtn.value = [];
    activeBtn.value = [];

    if (idx === -1) {
      for (let i = 0; i < props.btnGroups[0].buttons.length; i++) {
        multipulBtn.value.push(i);
      }

      tempData.forEach((m) => {
        activeBtn.value.push(m.btnLabel);
      });
    } else {
      multipulBtn.value = [];
      activeBtn.value = [];
    }
  } else {
    if (idx === -1) {
      multipulBtn.value.push(index);
      activeBtn.value.push(btngroup.btnLabel);

      //전부 체크되면 전체도 체크
      if (
        multipulBtn.value.length ===
        props.btnGroups[0].buttons.filter((obj) => obj.btnLabel !== "전체")
          .length
      ) {
        let allIndex = props.btnGroups[0].buttons.findIndex(
          (val) => val.btnLabel === "전체"
        );
        multipulBtn.value.push(allIndex);
        activeBtn.value.push("전체");
      }
    } else {
      multipulBtn.value.splice(idx, 1);
      activeBtn.value.splice(btnIdx, 1);

      //하나라도 해제 하면 전체 해제
      let allIndex = props.btnGroups[0].buttons.findIndex(
        (val) => val.btnLabel === "전체"
      );
      if (multipulBtn.value.includes(allIndex)) {
        let index = multipulBtn.value.findIndex((val) => val === allIndex);
        multipulBtn.value.splice(index, 1);
      }

      if (activeBtn.value.includes("전체")) {
        activeBtn.value.splice(
          activeBtn.value.findIndex((val) => val === "전체"),
          1
        );
      }
    }
  }

  emit("selectedMultiBtn", activeBtn.value);
};

//필터 표시
onMounted(() => {
  multipulBtn.value = props.isActiveBtns || [];
  for (let i = 0; i < props.btnGroups[0].buttons.length; i++) {
    if (multipulBtn.value.indexOf(i) !== -1) {
      activeBtn.value.push(props.btnGroups[0].buttons[i].btnLabel);
    }
  }
});
</script>

<style lang="scss" scoped></style>
