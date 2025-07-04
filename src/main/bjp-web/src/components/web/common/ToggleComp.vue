<template>
  <div class="switch-wrap">
    <label class="switch">
      <input id="toggle" type="checkbox" v-model="toggleValue" />
      <span class="slider"></span>
    </label>
    <!-- <label class="switch-label" for="toggle">{{ toggleLabel }}</label> -->
    <label class="switch-label">{{ toggleLabel }}</label>
  </div>
</template>

<script>
export default {
  props: {
    toggleLabel: {
      type: String,
      default: "Label",
    },
  },
  data() {
    return {
      toggleValue: false,
    };
  },
};
</script>

<style lang="scss" scoped>
/* Add your styling here */
.switch-wrap {
  display: flex;
  align-items: center;
}

.switch-label {
  color: #666666;
  cursor: pointer;
  margin-left: 8px;
}

.switch {
  position: relative;
  display: inline-block;
  width: 40px;
  height: 20px;
}

.switch input {
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
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 20px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

.switch input:checked + .slider {
  background-color: $b3;
}

.switch input:checked + .slider:before {
  transform: translateX(20px);
}
</style>
