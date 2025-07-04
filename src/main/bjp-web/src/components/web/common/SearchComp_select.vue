<template>
  <div class="SearchComp_wrap">
    <!-- <q-input
        input-class="input_search"
        v-model="text1"
        borderless
        dense
        :placeholder="placeholder1"
      />
      <q-input
        input-class="input_search"
        v-model="text2"
        borderless
        dense
        :placeholder="placeholder2"
      /> -->

    <q-select
      use-input
      hide-dropdown-icon
      input-class="input_search"
      v-model="text1"
      borderless
      dense
      :placeholder="placeholder1"
    />
    <q-select
      use-input
      hide-dropdown-icon
      input-class="input_search"
      v-model="text2"
      borderless
      dense
      :placeholder="placeholder2"
    />
  </div>
</template>
<script setup>
import { defineProps, ref } from "vue";

const text1 = ref("");
const text2 = ref("");

const props = defineProps({
  // modelValue: String,
  placeholder1: String,
  placeholder2: String,
});
</script>
<style lang="scss">
.SearchComp_wrap {
  position: relative;
  @include flex-start;
  gap: 8px;

  .q-field__native {
    // height: auto !important;
    width: 140px;
    height: 32px;
  }

  .q-field__native > span {
    display: none;
  }

  .input_search,
  .input_search1,
  .input_search2 {
    @include fs-5;
    height: 32px;
    border: 1px solid $line-400;
    background-color: #fff;
    padding: 4px 6px;
    border-radius: 0px;
    .q-field__native,
    input {
      width: 100% !important;
    }
  }

  .input_search {
    width: 110px;
  }
  .input_search1,
  .input_search2 {
    width: 205px;
  }
}

.q-table_header_sub_wrap {
  @include flex_between;
  width: 100%;
  flex-wrap: wrap;
  align-items: flex-start;
  .btn_area {
    @include flex_end;
    flex-wrap: wrap;
    gap: 8px 4px;
    @supports (-webkit-touch-callout: none) and (not (translate: none)) {
      > * + * {
        margin-left: 4px;
      }
    }
  }

  @media screen and (max-width: 1440px) and (min-width: 831px) {
    // $input: 106px;
    // .slct-wrap + .SearchComp_wrap {
    //   width: calc(100% - #{$input});
    // }
    .srch_form {
      gap: 8px 4px !important;
    }
    .SearchComp_wrap {
      gap: 8px 4px;
      .input_search,
      .input_search1,
      .input_search2 {
        padding: 4px;
      }
      .input_search1,
      .input_search2 {
        max-width: 140px;
        width: 50%;
      }
    }
  }

  @media screen and (max-width: 830px) {
    // align-items: flex-start;
    .SearchComp_wrap {
      @include flex_start;
      .autocomplete-container {
        width: auto;
      }
    }
  }
}

// .input_search {
//   @include fs-5;
//   width: 120px;
//   height: 28px;
//   border: 1px solid #666666;
//   padding: 4px 6px;
//   border-radius: 5px;
// }
.srch_form {
  @include flex_start;
  flex-wrap: wrap;
  gap: 8px;

  @supports (-webkit-touch-callout: none) and (not (translate: none)) {
    > * + * {
      margin-left: 8px;
    }
  }
  @media screen and (max-width: 1025px) {
    gap: 8px 6px;

    // width: calc(100% - 160px);
    // + .btn_area {
    //   width: auto;
    // }

    @supports (-webkit-touch-callout: none) and (not (translate: none)) {
      > * + * {
        margin-left: 6px;
      }
    }
  }
}

.q-field__control-container {
  @include flex_center;
}
</style>
