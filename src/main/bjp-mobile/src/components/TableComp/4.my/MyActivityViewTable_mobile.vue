<template lang="">
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="My활동"
      expand_btn="+ more"
      expand_btnWidth="64"
      @expanded_btnClick="expanded_btn"
    >
      <DialogLayout_mobile
          dialog_title="월간조회"
          v-model:dialog_model_prop="dialog_MyPage"
        >
          <div class="">
            <MyActivityComp_mobile/>
          </div>
      </DialogLayout_mobile>

      <div class="table_list_wrap">
        <div class="table_list" v-for="(items, i) in row" :key="i">
          <div class="table_list_label">
            <ul>
              <li>{{ items.Label }}</li>
              <li>{{ items.mon }}</li>
            </ul>
          </div>
          <div class="table_list_data">
            <ul v-for="(subItems, j) in items.subData" :key="j">
              <li>{{ subItems.subLabel }}</li>
              <li>
                <span>
                  {{ subItems.subData }}
                  <template v-if="typeof subItems.subData === 'string'">건</template>
                  <template v-else>원</template>
                </span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </ExpandLayout_mobile>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import ExpandLayout_mobile from 'src/layouts/ExpandLayouts/ExpandLayout_mobile.vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import MyActivityComp_mobile from 'src/components/DialogComp/4.myDialogComp/MyActivityComp_mobile.vue';


const expanded_model = ref(true)

const dialog_MyPage = ref(false);
const expanded_btn = () => {dialog_MyPage.value = true;};


const row = [
  {
    Label: '매출원장',
    mon: '2월',
    subData: [
      {
        subLabel: '잔고',
        subData: 25000000
      },
      {
        subLabel: '구매',
        subData: 25000000
      },
    ]
  },
  {
    Label: '주문건수',
    mon: '2월',
    subData: [
      {
        subLabel: '전체',
        subData: '28'
      },
      {
        subLabel: '구매',
        subData: 25000000
      },
    ]
  },
  {
    Label: '반품현황',
    mon: '3개월',
    subData: [
      {
        subLabel: '미등록',
        subData: '1'
      },
      {
        subLabel: '담당자',
        subData: '1'
      },
      {
        subLabel: '처리중',
        subData: '1'
      },
      {
        subLabel: '검수완료',
        subData: '1'
      },
    ]
  },
  {
    Label: '결제현황',
    mon: '2월',
    subData: [
      {
        subLabel: '결제',
        subData: '1'
      },
      {
        subLabel: '금액',
        subData: 25000000
      },
    ]
  },
  {
    Label: '입금현황',
    mon: '2월',
    subData: [
      {
        subLabel: '입금',
        subData: '1'
      },
      {
        subLabel: '금액',
        subData: 25000000
      },
      {
        subLabel: '수금할인',
        subData: 25000000
      },
    ]
  },
]



</script>
<style lang="scss" scoped>

.table_list_wrap {
  padding: 0 8px;
  margin-top: 10px;
  background-color: #eeeeee;
  border-radius: 5px;
  .table_list {
    display: flex;
    padding: 16px 0;
    @include border-b;
    li {
      @include fs-5;
    }
    .table_list_label {
      width: 50%;
      ul {
        @include flex_start;
        li:nth-child(1) {
          @include fw-7;
          width: 40%;
        }
        li:nth-child(2) {
          width: 60%;
        }
      }
    }
    .table_list_data {
      width: 50%;
      ul {
        @include flex_end;
        margin-bottom: 4px;
        gap: 14px;
        li {
          @include flex_end;
        }
        li:nth-child(1) {
          width: 35%;
        }
        li:nth-child(2) {
          @include fw-7;
          width: 65%;
          gap: 2px;
          span {
            @include fs-5;
          }
        }
      }
      ul:last-child {
        margin-bottom: 0;
      }
    }
  }
  .table_list:last-child {
    border: none;
  }
}



</style>
