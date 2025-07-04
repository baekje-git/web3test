<template>
  <div class="">
    <q-table
      class="HelpTable_view"
      :rows="rows"
      :columns="columns"
      row-key="FAQ_NO"
      table-header-class="table_header"
      dense
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <!-- <p>자주 묻는 질문</p> -->
              </div>
            </div>
            <BtnComp_default
              class="btn-fill gray"
              btnLabel="뒤로가기"
              @click="onClickMyPage"
            />
            <!-- <div class="q-table_header_sub_wrap flex_between">
              <div class="flex_center">
                <div class="flex_center">
                  <BtnComp_default
                    class="btn-fill gray"
                    btnLabel="뒤로가기"
                    @click="onClickMyPage"
                  />
                </div>
              </div>
            </div> -->
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td
            :props="props"
            key="Q_TXT"
            style="cursor: pointer"
            @click="props.expand = !props.expand"
            :icon="props.expand ? 'remove' : 'add'"
          >
            <div class="flex_between">
              <div>
                <span class="q-tableQAText">Q</span>
                <!-- <span
                  style="font-size: 14px"
                  v-if="props.row.종류 === '[교환/반품]'"
                  >[교환/반품]{{ props.row.name }}</span
                > -->
                <span style="font-size: 14px">
                  {{ props.row.Q_TXT }}
                </span>
              </div>
              <div class="q-table_sort_icon">
                <!-- <img src="assets/icons/sort-down-solid.svg" alt="icon" /> -->
                <img src="assets/images/arrw_dwn.svg" alt="icon" />
              </div>
            </div>
          </q-td>
        </q-tr>

        <q-tr class="q-table_expend_wrap" v-show="props.expand" :props="props">
          <q-td>
            <div class="flex_between">
              <div class="q-table_text_wrap">
                <div class="">
                  <span class="q-tableQAText">A</span>
                </div>
                <div class="">
                  <sapn
                    class="q-table_view_expendText"
                    style="white-space: pre-line"
                  >
                    {{ props.row.A_TXT }}
                  </sapn>
                </div>
              </div>
              <div
                class="q-table_sort_icon"
                @click="props.expand = !props.expand"
                :icon="props.expand ? 'remove' : 'add'"
              >
                <!-- <img src="assets/icons/sort-up-solid.svg" alt="icon" /> -->
                <img src="assets/images/arrw_up.svg" alt="icon" />
              </div>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, defineProps, onMounted } from "vue";
import { api } from "boot/axios";
import { useRouter } from "vue-router";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
const loading = ref(false);
const router = useRouter();

const rows = ref([]);

//---------------------------- Function ------------------------------//

async function fetchData() {
  loading.value = true;
  const res = await api.get("/myPage/faqList");
  loading.value = false;
  rows.value = res;
}

async function onClickMyPage() {
  await router.push({ path: "/myPage" });
}

onMounted(async () => {
  await fetchData();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "Q_TXT",
    label: "제목",
    field: "Q_TXT",
    align: "left",
  },
]);
</script>

<style lang="scss">
.flex_center {
  @include flex_center;
  gap: 8px;
}
.flex_start {
  @include flex_start;
  gap: 40px;
}
.flex_between {
  @include flex_between;
}

.HelpTable_view {
  height: 726px;
  overflow-y: auto;
  margin-bottom: 32px;

  .q-table {
    margin-top: 8px;
    border-top: $th-line solid 2px;
    td[icon="remove"] {
      // background-color: rgba(0, 0, 0, 0.02);
      background-color: $th-bg;
      border-bottom-color: $th-line;
    }
  }
}
</style>
