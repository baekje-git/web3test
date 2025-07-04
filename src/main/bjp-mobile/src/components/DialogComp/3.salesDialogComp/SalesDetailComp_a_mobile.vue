<template>
  <div class="SalesDetail_wrap">
    <q-markup-table separator="noen" flat dense>
      <tbody>
        <tr>
          <td>주문일자</td>
          <td>{{ APRV_DT }}</td>
          <td>주문서 구분</td>
          <td>{{ ORD_TYPE_NM }}</td>
        </tr>
        <tr>
          <td>종류</td>
          <td>{{ GUBUN_NM }}</td>
          <td>승인번호</td>
          <td>{{ APRV_NO }}</td>
        </tr>
        <tr>
          <td>금액</td>
          <td>{{ TOTAL_AMT }}원</td>
          <td>잔고</td>
          <td>{{ BALANCE_A_AMT }}원</td>
        </tr>
      </tbody>
    </q-markup-table>
  </div>
  <div class="Detail_Table_wrap">
    <SalesDetailTable_a_mobile  :SN="props.SN"/>
  </div>
</template>

<script setup>
import SalesDetailTable_a_mobile from 'src/components/DialogComp/TableDialogComp/3.salesTableComp/SalesDetailTable_a_mobile.vue';


const props = defineProps({
  APRV_DT: {
    type: String,
    default: '-'
  },
  ORD_TYPE_NM: {
    type: String,
    default: '-'
  },
  GUBUN_NM: {
    type: String,
    default: '-'
  },
  APRV_NO: {
    type: String,
    default: '-'
  },
  TOTAL_AMT: {
    type: String,
    default: '0'
  },
  BALANCE_A_AMT: {
    type: String,
    default: '0'
  },
  SN: {
    type: String,
    default: '-'
  },
})



</script>

<style lang="scss" scoped>

.SalesDetail_wrap {

  td {
    @include fs-5;
  }
  td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
    padding-right: 2px !important;
  }
  tr:last-child td:last-child {
    @include fw-7;
  }
}

</style>
