<script setup>
import { onMounted } from "vue";

const props = defineProps(["title"]);

onMounted(() => {
  console.log("HeaderCmp mounted...", props.title);
});
</script>
<template>
  <div>header@@@@</div>
</template>
