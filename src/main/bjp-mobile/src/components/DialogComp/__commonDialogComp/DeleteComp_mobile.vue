<template>
  <div class="Delete_wrap">
    <q-btn class="Delelte" size="sm" flat dense @click="delete_model = true">
      <slot></slot>
    </q-btn>

    <q-dialog v-model="delete_model" persistent class="Delete_dialog">
      <div class="Delete_container">
        <div>
          <div class="Delete_text">
            <p>{{ delete_text1 }} 목록에서 <span>삭제</span>됩니다.</p>
            <p><span>{{ delete_text2 }}</span> 하시겠습니까?</p>
          </div>
          <div class="Delete_btn">
            <BtnComp_mobile
              buttonText="취소"
              btnStyle="white"
              btnSize="17px"
              style="width: 120px;"
              v-close-popup
            />
            <BtnComp_mobile
              :buttonText="delete_btn"
              btnStyle="red"
              btnSize="17px"
              style="width: 120px;"
              @click="onClickDelete()"
            />
          </div>
        </div>
      </div>
    </q-dialog>



  </div>
</template>


<script setup>
import { ref, defineProps, inject } from 'vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
const bus = inject("bus");

const delete_model = ref(false);

const props = defineProps({
  delete_text1 : {
    type: String,
    default: 'TEXT'
  },
  delete_text2 : {
    type: String,
    default: '삭제'
  },
  delete_btn : {
    type: String,
    default: '삭제'
  },
  dataRow : {

  },
  callFunction : {
    type: String,
    default: ''
  }
})

async function onClickDelete() {
  await bus.emit(`${props.callFunction}`, props.dataRow);
}

</script>

<style lang="scss" scoped>


.Delete_wrap {
  .Delelte {
    @include flex_center;
  }
}

.Delete_dialog {
  .absolute-full, .fullscreen, .fixed-full {

  }
  .Delete_container {
    @include border;
    @include boxshadow;
    padding: 20px;
    background-color: $w;
    border-radius: 10px;
    .Delete_text {
      text-align: center;
      p {
        @include fs-5;
        line-height: 18px;
        span {
          @include fs-5;
          @include fw-7;
          color: $r1;
        }
      }
    }
    .Delete_btn {
      @include flex_between;
      margin-top: 20px;
      gap: 40px;
    }
  }
}




</style>
