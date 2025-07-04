<script setup>
import { onMounted } from "vue";

const props = defineProps(["title"]);

onMounted(() => {
  console.log("FooterCmp mounted...", props.title);
});
</script>
<template>
  <div>footer####</div>
</template>
