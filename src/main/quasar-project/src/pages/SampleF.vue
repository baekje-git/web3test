<template>
  <div>
    <apexchart
      width="500"
      type="bar"
      :options="chartOptions"
      :series="series"
    ></apexchart>
  </div>
</template>
<script>
import { defineComponent, ref, reactive } from "vue";
import EssentialLink from "components/EssentialLink.vue";
import { useQuasar } from "quasar";
import RoleMenu from "components/RoleMenu.vue";
import HeaderCmp from "components/HeaderCmp.vue";
import FooterCmp from "components/FooterCmp.vue";
import Datepicker from "vue3-datepicker";
import { ko } from "date-fns/locale";
const temp = "Sample b header";
const temp2 = "Sample b footer";
import VueApexCharts from "vue3-apexcharts";
export default {
  components: {
    apexchart: VueApexCharts,
  },
  data: function () {
    return {
      chartOptions: {
        chart: {
          id: "vuechart-example",
        },
        xaxis: {
          categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998],
        },
      },
      series: [
        {
          name: "series-1",
          data: [30, 40, 35, 50, 49, 60, 70, 91],
        },
      ],
    };
  },
};

/* ///////그리드 스크립트 End //////// */
</script>
<style scoped></style>
