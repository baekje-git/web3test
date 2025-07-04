<template>
  <div class="MyActivity_Expand_wrap">
    <q-expansion-item hide-expand-icon v-for="(expandItems, i) in row" :key="i" style="margin-bottom: 4px;">
      <template v-slot:header>
        <div class="Expand_head">
          {{ expandItems.year }}
        </div>
      </template>
      <div class="Expand_container">
        <div class="table_list_wrap">
          <div class="table_list" v-for="(expandItem, j) in expandItems.ExpandData" :key="j">
            <div class="table_list_label">
              <ul>
                <li>{{ expandItem.Label }}</li>
                <li>{{ expandItem.Total }} 원</li>
              </ul>
            </div>
            <div class="table_list_data">
              <ul v-for="(item, k) in expandItem.subData" :key="k">
                <li>{{ item.subLabel}}</li>
                <li>
                  {{ item.subData }}
                  <span>
                    <template v-if="typeof item.subData === 'string' && item.subData.trim() !== ''">일</template>
                    <template v-else-if="typeof item.subData === 'number'">원</template>
                    <template v-else>-</template>
                  </span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </q-expansion-item>
  </div>
</template>

<script setup>
import { ref  } from 'vue';


const row = [
  {
    year: '2024년 8월',
    ExpandData: [
      {
        Label: '주문',
        Total: 25000000,
        subData: [
          {
            subLabel: '매출',
            subData: 25000000
          },
          {
            subLabel: '매출(16_15)',
            subData: 25000000
          },
          {
            subLabel: '순매출액',
            subData: 25000000
          },
        ]
      },
      {
        Label: '반품',
        Total: 25000000,
        subData: [
          {
            subLabel: '반품',
            subData: 25000000
          },
          {
            subLabel: '소분반품',
            subData: 25000000
          },
          {
            subLabel: '약가보상',
            subData: ''
          },
          {
            subLabel: '기타',
            subData: ''
          },
          {
            subLabel: '수금할인',
            subData: 25000000
          },
        ]
      },
      {
        Label: '입금',
        Total: 25000000,
        subData: [
          {
            subLabel: '결제',
            subData: 25000000
          },
          {
            subLabel: '회전일',
            subData: '34'
          },
        ]
      },
      {
        Label: '잔고',
        Total: 25000000,
        subData: [
          {
            subLabel: '전월잔고',
            subData: 25000000
          },
        ]
      },
    ]
  },
  {
    year: '2024년 7월',
    ExpandData: [
      {
        Label: '주문',
        Total: 25000000,
        subData: [
          {
            subLabel: '매출',
            subData: 25000000
          },
          {
            subLabel: '매출(16_15)',
            subData: 25000000
          },
          {
            subLabel: '순매출액',
            subData: 25000000
          },
        ]
      },
      {
        Label: '반품',
        Total: 25000000,
        subData: [
          {
            subLabel: '반품',
            subData: 25000000
          },
          {
            subLabel: '소분반품',
            subData: 25000000
          },
          {
            subLabel: '약가보상',
            subData: ''
          },
          {
            subLabel: '기타',
            subData: ''
          },
          {
            subLabel: '수금할인',
            subData: 25000000
          },
        ]
      },
      {
        Label: '입금',
        Total: 25000000,
        subData: [
          {
            subLabel: '결제',
            subData: 25000000
          },
          {
            subLabel: '회전일',
            subData: '34'
          },
        ]
      },
      {
        Label: '잔고',
        Total: 25000000,
        subData: [
          {
            subLabel: '전월잔고',
            subData: 25000000
          },
        ]
      },
    ]
  },
  {
    year: '2024년 6월',
    ExpandData: [
      {
        Label: '주문',
        Total: 25000000,
        subData: [
          {
            subLabel: '매출',
            subData: 25000000
          },
          {
            subLabel: '매출(16_15)',
            subData: 25000000
          },
          {
            subLabel: '순매출액',
            subData: 25000000
          },
        ]
      },
      {
        Label: '반품',
        Total: 25000000,
        subData: [
          {
            subLabel: '반품',
            subData: 25000000
          },
          {
            subLabel: '소분반품',
            subData: 25000000
          },
          {
            subLabel: '약가보상',
            subData: ''
          },
          {
            subLabel: '기타',
            subData: ''
          },
          {
            subLabel: '수금할인',
            subData: 25000000
          },
        ]
      },
      {
        Label: '입금',
        Total: 25000000,
        subData: [
          {
            subLabel: '결제',
            subData: 25000000
          },
          {
            subLabel: '회전일',
            subData: '34'
          },
        ]
      },
      {
        Label: '잔고',
        Total: 25000000,
        subData: [
          {
            subLabel: '전월잔고',
            subData: 25000000
          },
        ]
      },
    ]
  },
]



</script>

<style lang="scss" scoped>


.MyActivity_Expand_wrap {


  .Expand_head {
    @include flex_center;
    @include fs-5;
    @include fw-7;
    width: 100%;
    color: #fff;
    border-radius: 5px;
    background-color: #999999;
  }
  .q-expansion-item--expanded {
    .Expand_head {
      @include gradation;
    }
  }
  .Expand_container:last-child {
    margin-bottom: 12px;
  }
  .Expand_container {
    .table_list_wrap {
      padding: 0 8px;
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
          width: 45%;
          ul {
            @include flex_start;
            li {
              @include fw-7;
            }
            li:nth-child(1) {
              width: 30%;
            }
            li:nth-child(2) {
              width: 70%;
            }
          }
        }
        .table_list_data {
          width: 55%;
          ul {
            @include flex_end;
            margin-bottom: 4px;
            gap: 14px;
            li {
              @include flex_end;
            }
            li:nth-child(1) {
              width: 45%;
            }
            li:nth-child(2) {
              @include fw-7;
              width: 55%;
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
  }
}



</style>
