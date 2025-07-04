<template>
  <div class="Toggle_wrap">
    <div class="Toggle_Style">
      <label class="toggle">
        <!-- <input id="toggle" type="checkbox" @input="$emit('update:toggleValue', $event.target.value)"> -->
        <input id="toggle" type="checkbox" v-model="model">
        <span class="slider"></span>
      </label>
      <label class="Toggle_label">{{ toggleLabel }}</label>
    </div>
  </div>
</template>

<script setup>
const toggleLabel = defineModel("toggleLabel");
const model = defineModel(false);
</script>

<script>
export default {
  // props: {
  //   Toggle_Style: {
  //     type:String,
  //     default: 'Toggle_wrap'
  //   },
  //   toggleLabel: {
  //     type: String,
  //     default: 'Label',
  //   }
  // },
  // data() {
  //   return {
  //     toggleValue: false
  //   };
  // },
}
</script>

<style lang="scss" scoped>

.Toggle_wrap{
  display: flex;
  align-items: center;
  .Toggle_label {
    @include fs-5;
    color: #666666;
    cursor: pointer;
    margin-left: 8px;
  }
  .toggle {
    position: relative;
    display: inline-block;
    width: 44px;
    height: 20px;
    input {
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
      transition: .4s;
      border-radius: 20px;
      &::before {
        position: absolute;
        content: "";
        height: 16px;
        width: 16px;
        left: 2px;
        bottom: 2px;
        background-color: white;
        transition: .4s;
        border-radius: 50%;
      }
    }
  }
  .toggle input:checked + .slider {
    background-color: $b3;
  }
  .toggle input:checked + .slider:before {
    transform: translateX(24px);
  }
}

.Toggle_Switch_wrap{
  display: flex;
  align-items: center;
  .Toggle_label {
    @include fs-5;
    color: #666666;
    cursor: pointer;
    margin-left: 8px;
  }
  .toggle {
    position: relative;
    display: inline-block;
    width: 44px;
    height: 20px;
    input {
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
      transition: .4s;
      border-radius: 20px;
      &::before {
        position: absolute;
        content: "";
        height: 16px;
        width: 16px;
        left: 2px;
        bottom: 2px;
        background-color: white;
        transition: .4s;
        border-radius: 50%;
      }
      &::after {
        position: absolute;
        right: 3px;
        top: 4px;
        font-size: 12px;
        color: #fff;
        content: "OFF";
      }
    }
  }
  .toggle input:checked + .slider {
    background-color: $b3;
  }
  .toggle input:checked + .slider:before {
    transform: translateX(24px);
  }
  .toggle input:checked + .slider:after {
    position: absolute;
    left: unset;
    left: 3px;
    top: 4px;
    font-size: 12px;
    color: #fff;
    content: "ON";
  }
}
</style>
