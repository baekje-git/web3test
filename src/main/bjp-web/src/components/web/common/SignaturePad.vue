<template>
  <div>
    <canvas
      :value="dataURL"
      ref="signatureCanvas"
      v-on:mousedown="onBegin"
      v-on:mouseup="onEnd"
    ></canvas>
    <button @click="clearCanvas">지우기</button>
    <button @click="saveCanvas">저장</button>
  </div>
</template>

<script>
import { inject } from "vue";
import SignaturePad from "signature_pad";

export default {
  props: ["modelValue"],
  emits: ["update:modelValue"],

  setup() {
    const bus = inject("bus");
  },
  data() {
    return {
      signaturePad: null,
      dataURL: "",
    };
  },
  mounted() {
    const canvas = this.$refs.signatureCanvas;

    this.signaturePad = new SignaturePad(canvas);
  },
  methods: {
    clearCanvas() {
      this.signaturePad.clear();
    },
    onBegin() {
      this.signaturePad.onBegin();
    },
    onEnd() {
      this.signaturePad.onEnd();
    },
    saveCanvas() {
      const data = this.$refs.signatureCanvas.toDataURL();

      this.dataURL = data;
      // this.$emit("closeSign");

      this.bus.emit("saveSign", data);
    },
  },
};
</script>

<style scoped>
canvas {
  border: 1px solid black;
  width: 100%;
  height: 240px;
}
</style>
