<template>
  <q-select
    filled
    :name="name"
    :options="options"
    :loading="isLoading"
    :readonly="isLoading"
    v-model="selected"
  />
</template>

<script>
import { defineComponent, ref, onMounted } from "vue";
import * as util from "support/util";

export default defineComponent({
  name: "CmmnCode",
  props: {
    code: {
      type: String,
      required: true,
      default: "",
    },
    all: {
      type: Boolean,
      required: false,
      default: false,
    },
    allTitle: {
      type: String,
      required: false,
      default: "전체",
    },
    defaultValue: {
      type: String,
      required: false,
      default: "",
    },
    label: {
      type: String,
      required: false,
      default: "",
    },
    size: {
      type: String,
      required: false,
      default: "전체",
    },
    value: {
      type: [String, Number],
      required: false,
      default: "",
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false,
    },
    name: {
      type: String,
      required: false,
      default: "",
    },
    useYn: {
      type: String,
      required: false,
      default: "",
    },
  },
  emits: ["loadEnd"],
  setup(props, { emit }) {
    const options = ref([]);
    const selected = ref();
    const isLoading = ref(false);

    onMounted(() => {
      //console.log(props.label);
      selectCodeList();
    });

    function selectCodeList() {
      isLoading.value = true;

      util
        .bindList("cmmn", "selectCmmnCodeList", {
          CODE_MST_CD: props.code,
          USE_YN: "Y",
        })
        .then((response) => {
          //console.log("response:::::::::", response);
          options.value = util.getDataForSelect(
            response,
            "CODE_DTL_CD",
            "CODE_DTL_NM",
            props.all,
            props.allTitle
          );
          //console.log("options.value:::::::", options.value);
        })
        .catch((error) => {
          console.error("CodeSelect component에러", error);
        })
        .finally(() => {
          isLoading.value = false;
          //load 후 초기값 전달 >> load 후 defaultValue 옵션값 전달 (231103 변경)

          const optionValue = ref();

          if (!util.isEmpty(props.defaultValue)) {
            optionValue.value = options.value.find(
              (e) => e.value == props.defaultValue
            );
          }

          emit(
            "loadEnd",
            util.isEmpty(optionValue.value)
              ? options.value[0]
              : optionValue.value
          );
        });
    }

    return {
      options,
      isLoading,
      selected,
    };
  },
});
</script>
