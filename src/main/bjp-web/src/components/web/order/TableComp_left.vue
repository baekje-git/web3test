<template>
  <div class="Table_wrap" style="width: 900px;">
    <slot></slot>
  </div>
</template>

<script>

export default {
  data: ()=> ({

  }),

  setup () {
    return {

    }
  }
}
</script>

<style lang="scss">

.Table_wrap {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.33);
}


</style>
