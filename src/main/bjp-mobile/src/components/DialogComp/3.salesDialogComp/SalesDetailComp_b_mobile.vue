<template>
  <div class="SalesDetail_wrap">
    <q-markup-table separator="noen" flat dense>
      <tbody>
        <tr>
          <td>보험코드</td>
          <td>{{ BOHUM_CD }}</td>
          <td>표준코드</td>
          <td>{{ ITEM_PYOJUN_CD }}</td>
        </tr>
      </tbody>
    </q-markup-table>
    <div class="">
      <slot></slot>
    </div>
  </div>
  <div class="Detail_Table_wrap">
    <SalesDetailTable_b_mobile
      :SN="props.SN"
      :SNO="props.SNO"
    />
  </div>
</template>

<script setup>
import SalesDetailTable_b_mobile from 'src/components/DialogComp/TableDialogComp/3.salesTableComp/SalesDetailTable_b_mobile.vue';


const props = defineProps({
  BOHUM_CD: {
    type: String,
    default: '-'
  },
  ITEM_PYOJUN_CD: {
    type: String,
    default: '-'
  },
  SN: {
    type: String,
    default: '-'
  },
  SNO: {
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
}

</style>
