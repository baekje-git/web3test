<template>
  <div class="">
    <q-table
      class="myHelpTable_view my_view"
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
    >
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between q-table_noheader">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>자주 묻는 질문</p>
              </div>
            </div>
            <div class="q-table_btn_wrap">
              <BtnComp_default_small
                class="btn-line default"
                btn_defaultLabel="+ more"
                :btn_defaultLink="btn_defaultLink"
                @click="onClickFaq"
              />
            </div>
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
                <span style="font-size: 14px; white-space: pre-line">
                  {{ props.row.Q_TXT }}
                </span>
              </div>
              <div class="q-table_sort_icon">
                <!-- <img src="assets/icons/sort-down-solid.svg" alt="icon" /> -->
                <i class="ico arrw-dwn h8"></i>
              </div>
            </div>
          </q-td>
        </q-tr>

        <q-tr class="q-table_expend_wrap" v-show="props.expand" :props="props">
          <q-td>
            <div class="flex_between">
              <div class="q-table_view_text_wrap">
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
                <i class="ico arrw-up h8"></i>
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

import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";

const router = useRouter();

const props = defineProps({
  btn_defaultLink: {
    type: String,
    default: "",
  },
});

const rows = ref([]);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/faq");

  rows.value = res;
}

async function onClickFaq() {
  await router.push({ path: "/faq" });
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
.flex_between {
  @include flex_between;
}
.myHelpTable_view {
  height: 159px;
  overflow-y: auto;
  margin-bottom: 25px;
}

.myHelpTable_view tr {
  height: 36px !important;
}
</style>
