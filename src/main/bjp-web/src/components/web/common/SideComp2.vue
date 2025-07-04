<template>
  <div class="Side_wrap">
    <!-- <div class="banner_wrap" v-for="(item, i) in banners" :key="i">
      <img :src="item.src" :alt="item.alt">
    </div> -->
    <div class="banner_wrap">
      <img src="assets/images/banner_01.png" alt="banner" />
    </div>
    <div class="banner_wrap">
      <img src="assets/images/banner_02.png" alt="banner" />
    </div>
    <div class="banner_wrap">
      <img src="assets/images/banner_03.png" alt="banner" />
    </div>
    <div class="banner_wrap">
      <img src="assets/images/banner_04.png" alt="banner" />
    </div>
    <div class="banner_wrap">
      <img src="assets/images/banner_05.png" alt="banner" />
    </div>
    <div class="banner_wrap">
      <img src="assets/images/banner_06.png" alt="banner" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      banners: [
        {
          src: "assets/images/banner_01.png",
          alt: "banner01",
        },
        {
          src: "assets/images/banner_02.png",
          alt: "banner02",
        },
        {
          src: "assets/images/banner_03.png",
          alt: "banner03",
        },
        {
          src: "assets/images/banner_04.png",
          alt: "banner04",
        },
        {
          src: "assets/images/banner_05.png",
          alt: "banner05",
        },
        {
          src: "assets/images/banner_06.png",
          alt: "banner06",
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.Side_wrap {
  min-width: 200px;
}

.banner_wrap img {
  margin-bottom: 20px;
}
</style>
