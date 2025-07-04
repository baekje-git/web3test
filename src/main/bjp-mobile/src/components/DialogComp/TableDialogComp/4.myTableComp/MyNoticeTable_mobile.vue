<template lang="">
  <div>
    <q-table
      class=""
      row-key="제목"
      :rows="rows"

      flat

      hide-header
      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:body="props">
        <q-tr @click="props.expand = !props.expand">
          <div class="table_wrap">
            <div class="table_item">
              <q-td>
                <span v-if="props.row.badge === '필독'" class="blue-text">필독</span>
                <span>{{ props.row.제목 }}</span>
                <span v-if="props.row.new === true" class="red-text">NEW</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.날짜 }}</span></q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.expand" :props="props">
          <div class="expand_table_wrap">
            <div class="expand_table_item">
              <q-td>
                <span>
                  최근 당사의 주문프로그램이 아닌
                </span>
                <span class="red-text bold-text">
                  “타사이트”
                </span>
                <span>
                  를 통한 주문으로 인하여 당사의 전산 시스템에 많은 문제점이
                  발생하고 있어 약사님들께 협조의 말씀을 드립니다.
                  당사의 주문프로그램에 접속
                </span>
                <span class="red-text bold-text">
                  시필요한 ID와 PW는 당사의 회원이신 약사님에게만 부여
                </span>
                <span>
                  되고 있으므로
                </span>
                <span class="red-text bold-text">
                  절대로 제3자에게 알려주시거나 사용하게 하시면 안되는 정보입니다.
                </span>
                <span class="bold-text">
                  약사님의 ID와 PW는 주문을 위하여 주문프로그램에
                  접속하실 때에만 사용해 주시고,제3자에게 알려주지 마시기를
                  부탁드립니다. <br/>
                </span>
                <span>
                  앞으로도 보다 나은 서비스 제공을 위하여 노력하겠습니다.
                </span>
              </q-td>
            </div>
            <div class="expand_table_item">
              <q-td>
                <span>첨부파일</span>
                <span><img src="/public/assets/icons/file-pdf-solid.svg"></span>
                <span>[별지 제64호서식]회수확인서.pdf (96kb)</span>
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>
<script setup>
import { ref } from 'vue';


const rows = ref([
  {
    new: true,
    badge: '필독',
    제목: '타사이트를 통한 주문 관련 공지사항',
    날짜: '2022.04.21',
  },
  {
    new: false,
    badge: '필독',
    제목: '타사이트를 통한 주문 관련 공지사항2',
    날짜: '2022.04.21',
  },
  {
    new: false,
    badge: '필독',
    제목: '타사이트를 통한 주문 관련 공지사항3',
    날짜: '2022.04.21',
  },
]);





</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 12px 0 !important;
  .table_item {
    td {
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(1){
    grid-column: span 5;
    td {
      @include flex_start;
      gap: 3px;
      span:nth-child(1) {
        @include fw-7;
      }
      span:nth-child(3) {
        @include fw-7;
        @include fs-6;
      }
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
}

.expand_table_wrap {
  @include border-b;
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  padding: 8px 0 !important;
  .expand_table_item:nth-child(1) {
    grid-column: span 1;
    td {
      @include fs-5;
      white-space: normal;
      span {
        @include fs-5;
      }
    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 1;
    padding: 16px 0 8px !important;
    td {
      @include flex_start;
      gap: 8px;
      span {
        @include fs-5;
        @include fw-6;
        color: #999999;
      }
    }
  }
}



</style>
