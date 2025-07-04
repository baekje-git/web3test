<template>
  <div class="MoreReceipt_wrap">
    <q-markup-table separator="noen" flat dense>
      <tbody>
        <tr>
          <td>가용재고</td>
          <td>{{ 가용재고 }}</td>
        </tr>
        <tr>
          <td>기간</td>
          <td>{{ 기간 }}</td>
        </tr>
        <tr>
          <td>제약사</td>
          <td>{{ 제약사 }}</td>
        </tr>
      </tbody>
    </q-markup-table>
    <div class="">
      <slot></slot>
    </div>
  </div>
  <div class="Receipt_Table_wrap">
    <MoreReceiptDetailTable_mobile/>
  </div>
</template>

<script setup>
import MoreReceiptDetailTable_mobile from 'src/components/DialogComp/TableDialogComp/5.moreTableComp/MoreReceiptDetailTable_mobile.vue';


const props = defineProps({
  가용재고: {
    type: String,
    default: '가용재고'
  },
  기간: {
    type: String,
    default: '20231114 ~ 20240214'
  },
  제약사: {
    type: String,
    default: '제약사'
  },
})



</script>

<style lang="scss" scoped>

.MoreReceipt_wrap {
  td {
    @include fs-5;
  }
  td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
    padding-right: 2px !important;
  }
}

</style>
