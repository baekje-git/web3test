<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>카메라 샘플</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn @click="fnOpenModal" class="btn-point" label="팝업" />
          <CameraCmp :isOpen="isOpenModal" @close="fnCloseModal" />
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import { ref } from "vue";
import CameraCmp from "src/components/CameraCmp.vue";

const isOpenModal = ref(false);

function fnOpenModal() {
  isOpenModal.value = true;
}

function fnCloseModal() {
  isOpenModal.value = false;
}
</script>

<style scoped></style>
