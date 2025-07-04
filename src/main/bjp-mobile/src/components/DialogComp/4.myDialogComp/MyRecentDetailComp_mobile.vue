<template>
  <div class="RecentDetail_wrap">
    <q-markup-table separator="noen" flat dense>
      <tbody>
        <tr>
          <td>등록</td>
          <td>{{ 등록 }} {{ 등록시간 }}</td>
          <td>구분</td>
          <td>{{ 구분 }}</td>
        </tr>
        <tr>
          <td>승인</td>
          <td>{{ 승인 }} {{ 승인시간 }}</td>
          <td>승인번호</td>
          <td>{{ 승인번호 }}</td>
        </tr>
      </tbody>
    </q-markup-table>
  </div>
  <div class="RecentDetail_Table_wrap">
    <MyRecentDetailTable_mobile/>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import MyRecentDetailTable_mobile from 'src/components/DialogComp/TableDialogComp/4.myTableComp/MyRecentDetailTable_mobile.vue';


const props = defineProps({
  등록: {
    type: String,
    default: '2024.01.01'
  },
  등록시간: {
    type: String,
    default: '12:25:45'
  },
  구분: {
    type: String,
    default: '구분'
  },
  승인: {
    type: String,
    default: '승인'
  },
  승인시간: {
    type: String,
    default: '17:25:45'
  },
  승인번호: {
    type: String,
    default: '승인번호'
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
