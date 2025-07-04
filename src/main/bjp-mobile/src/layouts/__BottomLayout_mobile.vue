<template>
  <div class="BottomLayout_wrap">
    <div class="Bottom_wrap">
      <ul>
        <li v-for="(items, i) in menuItems" :key="i">
          <div class="Bottom_btn_wrap">
            <q-btn flat dense :to="items.link" @click="dialog_model =  items.dialog_on ">
              <img :src="items.icon" alt="icon">
              <q-badge color="red" rounded floating v-if="items.badge_on === true">{{ items.badge }}</q-badge>
            </q-btn>
            <p>{{ items.title }}</p>
          </div>
        </li>
      </ul>
    </div>
  </div>


  <DialogLayout_mobile
    dialog_title="전체 메뉴"
    v-model:dialog_model_prop="dialog_model"
    :dialog_full="dialog_full"
    dialog_position="bottom"
    dialog_style="Dialog_menu"

  >
    <MenuComp_mobile/>
  </DialogLayout_mobile>


</template>
<script setup>
import { ref } from 'vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import MenuComp_mobile from 'src/components/MenuComp/MenuComp_mobile.vue';


const dialog_model = ref(false)
const dialog_full = ref(true)



const menuItems = [
  {
    link: '',
    title: '전체메뉴',
    icon: '/public/assets/icons/bars-solid.svg',
    badge: '',
    badge_on: false,
    dialog_on: true,
  },
  {
    link: '/mobile/mainOrder',
    title: '통합주문',
    icon: '/public/assets/icons/mobile-screen-solid.svg',
    badge: '',
    badge_on: false,
  },
  {
    link: '/mobile/mainCart',
    title: '장바구니',
    icon: '/public/assets/icons/cart-shopping-solid.svg',
    badge: '3',
    badge_on: true,
  },
  {
    link: '/mobile/mainMyInterest',
    title: '관심상품',
    icon: '/public/assets/icons/heart-solid.svg',
    badge: '2',
    badge_on: true,
  },
  {
    link: '/mobile/mainMy',
    title: '3층파란문약국',
    icon: '/public/assets/icons/user-solid.svg',
    badge: '3',
    badge_on: true,
  },
]


</script>


<style lang="scss" scoped>

.BottomLayout_wrap {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: $w;
}
.Bottom_wrap {
  min-width: 360px;
  height: 56px;
  padding: 7px 0 3px;
  box-shadow: 0 -1px 6px 0 rgba(0, 0, 0, 0.16);
  border-top: solid 1px #ccc;
}
.Bottom_wrap ul {
  @include flex_center;
  gap: 20px;
}
.Bottom_wrap ul li:last-child p {
  @include fs-7;
}
.Bottom_btn_wrap p {
  @include fs-6;
  @include fw-7;
  color: #999999;
  line-height: 14px;
}
.Bottom_btn_wrap {
  text-align: center;
}
</style>
