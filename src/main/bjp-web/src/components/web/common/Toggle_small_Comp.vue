<template>
  <div class="Toggle_wrap">
    <label class="toggle">
      <input id="toggle" type="checkbox" v-model="toggleValue" />
      <span class="slider"></span>
    </label>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      toggleValue: false,
    };
  },
};
</script>

<style lang="scss" scoped>
.Toggle_wrap {
  display: flex;
  align-items: center;
  gap: 4px;
}
.Toggle_label {
  cursor: pointer;
}
.toggle {
  position: relative;
  display: inline-block;
  width: 28px;
  height: 16px;
}
.toggle input {
  opacity: 0;
  width: 0;
  height: 0;
}
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: $gray-c;
  transition: 0.4s;
  border-radius: 20px;
}
.slider:before {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  left: 0;
  bottom: 0;
  background-color: #fff;
  border: 1px solid $gray-a;
  transition: 0.4s;
  border-radius: 50%;
}
.toggle input:checked + .slider {
  background-color: $primary;
}
.toggle input:checked + .slider:before {
  transform: translateX(12px);
  border-color: $primary;
}
</style>
