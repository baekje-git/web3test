<template>
  <q-dialog v-model="isOpen" persistent @show="onShow">
    <q-card>
      <q-card-section class="row items-center">
        <q-space />
        <q-btn icon="close" flat round dense @click="onClose" />
      </q-card-section>
      <q-card-section class="text-center">
        <!-- 확인 누를 때 video 영역을 img 영역으로 덮는다-->
        <video
          style="width: 500px; height: 300px"
          v-if="enableCamera"
          autoplay
          ref="videoplay"
        ></video>
        <q-img
          v-if="!enableCamera"
          :src="imgsrc"
          style="width: 500px; height: 300px"
        />
      </q-card-section>
      <q-card-actions align="right">
        <q-btn
          v-if="!enableCamera"
          flat
          label="재촬영"
          color="primary"
          @click="fnTakePicture"
        />
        <q-btn
          v-if="enableCamera"
          flat
          label="확인"
          color="primary"
          @click="takePhoto"
        />
        <q-btn
          v-if="!enableCamera"
          flat
          label="저장"
          color="primary"
          @click="sendData"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>
<script setup>
import {
  defineProps,
  defineEmits,
  computed,
  ref,
  onMounted,
} from "vue";

const props = defineProps({
  isOpen: { type: Boolean },
});
const isOpen = computed(() => {
  return props.isOpen;
});
const emit = defineEmits(["close"]);
const enableCamera = ref(false);
const videoplay = ref(HTMLVideoElement);
const imageCapture = ref(null);
const imgsrc = ref(null);

onMounted(() => {
  if (navigator.mediaDevices.getUserMedia) {
    enableCamera.value = true;
  }
});

function onShow() {
  //console.log("onShow::::::::::");
  navigator.mediaDevices.getUserMedia({ video: true }).then((mediaStream) => {
    videoplay.value.srcObject = mediaStream;
    imageCapture.value = new ImageCapture(mediaStream.getVideoTracks()[0]);
  });
}

function onClose() {
  emit("close");
}

//재촬영버튼
function fnTakePicture() {
  enableCamera.value = true;
  navigator.mediaDevices.getUserMedia({ video: true }).then((mediaStream) => {
    videoplay.value.srcObject = mediaStream;
    imageCapture.value = new ImageCapture(mediaStream.getVideoTracks()[0]);
  });
}

//확인버튼
function takePhoto() {
  enableCamera.value = false;
  imageCapture.value
    .takePhoto()
    .then((blob) => {
      createImageBitmap(blob);
      const reader = new FileReader();
      reader.readAsDataURL(blob);
      reader.onload = () => {
        imgsrc.value = reader.result;
      };
    })
    .catch((error) => console.log(error));
}

//저장버튼 - imgsrc.value값을 server에 보내면 된다.
function sendData() {
  console.log("imgsrc.value::::::::::", imgsrc.value);
}
</script>
<style scoped></style>
