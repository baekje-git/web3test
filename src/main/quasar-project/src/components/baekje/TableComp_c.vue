<template>

  <div class="table_sec">
    <q-table
      class="table_wrap"
      :rows="rows"
      :columns="columns"
      row-key="name"
      hide-bottom
      table-header-class="table-header-class"
    >

    <template v-slot:body-cell-name="props">
        <q-td :props="props" style="padding: 0;">
          <div class="table_font-size-14" style="font-weight: bold; padding: 0 10px;">
            {{ props.value }}
          </div>
        </q-td>
      </template>


    </q-table>

  </div>

</template>

<script>
import { ref } from 'vue'
import { mdiHeart, mdiHeartOutline, mdiCheck   } from '@mdi/js';



const columns = [
{
  name: 'name',
  label: '품목 및 규격',
  sortable: true,
  align: 'left',
  required: true,
  field: row => row.name,
  format: val => `${val}`,
},
{ name: '주문일자',   label: '주문일자',   field: '주문일자', align: 'center'},
{ name: '단가',   label: '단가',   field: '단가', align: 'center'},
{ name: '주문수량',   label: '주문수량',   field: '주문수량', align: 'center'},
{ name: '제조번호', label: '제조번호', field: '제조번호', align: 'center',},
{ name: '유효기간',   label: '유효기간',   field: '유효기간', align: 'center'},
]

const rows = [
{
  name: '우루사정300mg 500T',
  주문일자: '2023.09.13',
  단가: 136500,
  주문수량: 1,
  제조번호: '',
  유효기간: '',
},
{
  name: '우루사정300mg 500T',
  주문일자: '2023.09.13',
  단가: 136500,
  주문수량: -1,
  제조번호: '',
  유효기간: '',
},
{
  name: '우루사정300mg 500T',
  주문일자: '2023.09.13',
  단가: 136500,
  주문수량: 3,
  제조번호: '',
  유효기간: '',
},
]



export default {
  data: () => ({
      mdiHeart: mdiHeart,
      mdiHeartOutline: mdiHeartOutline,
      mdiCheck : mdiCheck ,
  }),
  setup() {
      return {
        columns,
        rows,
        filter: ref(''),

        top_check: ref(['op1']),
        top_check_option: [
          {
            label: '이력',
            value: 'op1'
          },
          {
            label: '품절제외',
            value: 'op2'
          },
        ],

        recode: ref(true),
        like: ref(true)


      };
  },
  components: { }
}
</script>

<style lang="scss">



.table_c_wrap {
  border: none;
}

</style>
