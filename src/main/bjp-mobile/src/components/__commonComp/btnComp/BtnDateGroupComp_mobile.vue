<template>
  <div class="btn-group-wrap" :style="{ maxWidth: maxWidth + 'px' }" ref="buttonGroupWrap">
    <div class="button-container" ref="buttonContainer">
      <q-btn
        v-for="item in items"
        :key="item.title"
        :class="[btnCommon, btnClass]"
        :size="btnSize"
        flat
        dense
        style="min-width: 64px;"
        @click="onClickPeriod(item.value)"
      >
        <span>{{ item.title }}</span>
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted, onUpdated, inject } from 'vue';

const bus = inject("bus");

const props = defineProps({
  btnCommon: {
    type: String,
    default: 'btn-common',
  },
  btnStyle: {
    type: String,
    default: 'default',
  },
  btnSize: {
    type: String,
    default: 'sm',
  },
  callFunction: {
    type: String,
    default: 'onClickPeriod',
  },
});

const items = ref([{title : '당월', value : 0},
    {title : '전월', value : 1},
    {title : '3개월', value : 3},
    {title : '6개월', value : 6},
    {title : '1년', value : 12},
    {title : '2년', value : 24},
    {title : '3년', value : 36}]);

const btnClass = ref('');
btnClass.value += ` btn-${props.btnStyle}`;

const maxWidth = ref(344); // 기본값 설정

const buttonGroupWrap = ref(null);
const buttonContainer = ref(null);

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//---------------------------- Function ------------------------------//

onMounted(() => {
  updateButtonContainerWidth();
});

onUpdated(() => {
  updateButtonContainerWidth();
});

function updateButtonContainerWidth() {
  if (buttonGroupWrap.value && buttonContainer.value) {
    const wrapWidth = buttonGroupWrap.value.clientWidth;
    const buttonCount = buttonContainer.value.children.length;
    const buttonWidth = 64; // 버튼의 고정 너비

    // 버튼 그룹의 너비가 버튼 너비보다 작으면 버튼이 너비를 초과하여 보여지지 않아야 함
    if (wrapWidth < buttonWidth * buttonCount) {
      buttonContainer.value.style.width = `${buttonWidth * buttonCount}px`;
      maxWidth.value = buttonWidth * buttonCount + 8 * (buttonCount - 1);
    } else {
      buttonContainer.value.style.width = 'auto';
      maxWidth.value = 344; // 기본값으로 되돌림
    }
  }
}


function getYYYYMMDD(date) {
  return (
    date.getFullYear() +
    "." +
    (date.getMonth() + 1).toString().padStart(2, "0") +
    "." +
    date.getDate().toString().padStart(2, "0")
  );
}

function setDate(val) {
  if (!val) {
    val = 1;
  } else {
    val++;
  }

  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const strtDt = new Date(year, month - val, 1);
  const endDt = new Date(year, month, 0);
  const date1 = getYYYYMMDD(strtDt);
  const date2 = getYYYYMMDD(endDt);

  const params = ref({
    startDt : date1.replace(match, "")
    , endDt : date2.replace(match, "")
  });

  return params;
}

async function onClickPeriod(val) {
  const params = setDate(val);
  await bus.emit(`${props.callFunction}`, params);
}
</script>

<style lang="scss" scoped>

</style>
