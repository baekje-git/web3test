<template>
  <div class="Stock_wrap">
    <q-dialog
      :model-value="stock_model"
      class="Stock_dialog"
      persistent
      @update:model-value="$emit('update:stock_model', $event)"
    >
      <div class="Stock_container">
        <div>
          <div class="Stock_text">
            <p><span><slot></slot></span>의 재고가 부족합니다.</p>
            <p>주문 수량을 확인해 주시기 바랍니다.</p>
          </div>
          <div class="Stock_btn">
            <BtnComp_mobile
              buttonText="확인"
              btnStyle="white"
              btnSize="17px"
              style="width: 100%;"
              v-close-popup
              />
          </div>
        </div>
      </div>
    </q-dialog>



  </div>
</template>


<script setup>
import { ref, defineProps } from 'vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';


const emits = defineEmits(['update:dialog_model_prop']);


const props = defineProps({
  stock_model: {
    type: Boolean
  },
  stock_text1 : {
    type: String,
    default: 'TEXT'
  },
  stock_text2 : {
    type: String,
    default: '삭제'
  },
  stock_btn : {
    type: String,
    default: '삭제'
  },
})

</script>

<style lang="scss" scoped>


.Stock_dialog {
  .Stock_container {
    @include border;
    @include boxshadow;
    padding: 20px;
    background-color: $w;
    border-radius: 10px;
    .Stock_text {
      text-align: center;
      p {
        @include fs-5;
        @include fw-7;
      }
    }
    .Stock_btn {
      margin-top: 16px;
    }
  }
}




</style>
