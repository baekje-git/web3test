<template>
  <div class="PageLayout_wrap my">
    <div class="page">
      <div class="left_wrap">
        <div class="left_top">
          <div class="left_top_left">
            <slot name="left_top_left"></slot>
          </div>
          <div class="left_top_right">
            <slot name="left_top_right"></slot>
          </div>
        </div>
        <div class="left_bottom">
          <slot name="left_bottom"></slot>
        </div>
      </div>
      <div class="right_wrap">
        <slot name="right"></slot>
      </div>
    </div>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.PageLayout_wrap.my {
  justify-content: flex-start;
  .page {
    display: flex;
    width: 100%;
    // gap: 8px;
  }
  .page_left {
    .left_top {
      margin-bottom: 0;
    }
  }
  .left_wrap {
    width: 66%;
    padding-right: rem(30px);
  }
  .right_wrap {
    width: 34%;
  }
  .left_top {
    @include flex_start;
    align-items: flex-start;
    gap: rem(20px);
  }
  .left_top_left {
    width: 50%;
  }
  .left_top_right {
    position: relative;
    width: 50%;
    padding-left: rem(20px);
    &::after {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 1px;
      height: 100%;
      background-color: $line-300;
    }
  }
}
@media screen and (max-width: 1270px) {
  .PageLayout_wrap.my {
    .left_wrap {
      // width: 58%;
      padding-right: 10px;
      .left_top {
        gap: 10px;
      }
      .left_top_left {
        width: 45%;
      }
      .left_top_right {
        width: 55%;
      }
    }
    .left_top_right {
      padding-left: 10px;
    }
    .inner_layout.lt {
      padding-right: 10px;
    }

    .inner_layout.rt {
      padding-left: 10px;
    }
  }
}
@media screen and (max-width: 1080px) and (min-width: 831px) {
  .PageLayout_wrap.my {
    .left_wrap {
      width: 58%;
      .left_top {
        flex-wrap: wrap;
      }
      .left_top_left,
      .left_top_right {
        width: 100%;
        padding: 0;
      }
    }
    .left_top_right::after {
      display: none;
    }
    .right_wrap {
      width: 42%;
    }
  }
}
@media screen and (max-width: 830px) {
  .PageLayout_wrap.my {
    .page {
      flex-wrap: wrap;
    }
    .left_wrap,
    .right_wrap {
      width: 100%;
      padding: 0;
    }

    .left_wrap {
      .left_top_left,
      .left_top_right {
        width: 50%;
      }
    }
  }
}
</style>
