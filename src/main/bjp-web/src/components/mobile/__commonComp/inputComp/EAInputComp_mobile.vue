<template>
  <div>
    <q-input
      class="input-common"
      :class="inputClass"
      borderless
      dense
      flat
      v-model="count"
      @keyup="onKeyup"
    >
      <template v-slot:prepend>
        <!-- <div class="EAInput_minus" @click="minus">
          <img src="/src/assets/icons/minus-solid.svg" alt="minus" />
        </div> -->
        <button type="button" class="btn_qty" @click="minus">
          <i class="ico qty-minus"></i>
        </button>
      </template>
      <template v-slot:append>
        <!-- <div class="EAInput_plus" @click="plus">
          <img src="/src/assets/icons/plus-solid.svg" alt="plus" />
        </div> -->
        <button type="button" class="btn_qty" @click="plus">
          <i class="ico qty-plus"></i>
        </button>
      </template>
    </q-input>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, inject } from "vue";

const bus = inject("bus");

const props = defineProps({
  inputStyle: {
    type: String,
    default: "EA",
  },
  modelValue: {
    type: [String, Number],
    default: "",
  },
});

const count = ref(props.modelValue);
const emit = defineEmits(["update:modelValue"]);

const plus = () => {
  count.value++;
  emit("update:modelValue", count.value);
};

const minus = () => {
  if (count.value > 0) {
    count.value--;
    emit("update:modelValue", count.value);
  }
};

// const update = () => {
//   emit('update:value', count.value);
// };
//v-model로의 변화
// <!-- -   :model-value="count"
//      -   @input="update"
//      +   v-model="count"  --> (vue 3.3부터 defineModel 가능)

const inputClass = ref("");
inputClass.value += ` input-${props.inputStyle}`;

const onKeyup = (evt) => {
  const isNotNull = evt.target.value !== "" ? true : false;

  if (!isNaN(evt.target.value) && isNotNull) {
    count.value = parseInt(evt.target.value);
  } else {
    count.value = 0;
  }

  emit("update:modelValue", count.value);
};

bus.on("EAcount", () => {
  count.value = 0;
});
</script>

<style lang="scss" scoped>
.input-common {
  background-color: #fff;
}

.btn_qty {
  @include flex_center;
  width: 20px;
  height: 100%;
  background-color: #fff;
  border: none;
  cursor: pointer;
  .ico {
    width: 9px;
    height: 9px;
  }
  .qty-minus {
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 12 12"><g id="Group" transform="translate(-2 -2)"><path id="Vector" d="M0,0H12V12H0Z" transform="translate(2 2)" fill="none"/><g id="Group-2" data-name="Group"><path id="Vector-2" data-name="Vector" d="M0,0H9" transform="translate(3.5 8)" fill="none" stroke="%23333" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/></g></g></svg>');
  }
  .qty-plus {
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 10 10"><g id="Group" transform="translate(-3 -3)"><path id="Vector" d="M0,0H9" transform="translate(3.5 8)" fill="none" stroke="%23333" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/><path id="Vector-2" data-name="Vector" d="M0,9V0" transform="translate(8 3.5)" fill="none" stroke="%23333" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/></g></svg>');
  }
}
</style>
