<template>
  <q-list class="rounded-borders">
    <q-expansion-item
      :icon="!!menuItem.ICON ? menuItem.ICON : 'menu'"
      :label="menuItem.MENU_NM"
      v-for="menuItem in rootMenus"
      :key="menuItem.MENU_ID"
      group="roleMenuGroup"
    >
      <q-expansion-item
        :header-inset-level="0.3"
        :icon="!!subMenuItem.ICON ? subMenuItem.ICON : 'receipt'"
        :label="subMenuItem.MENU_NM"
        :to="subMenuItem.ROUTE_NM"
        expand-icon="none"
        v-for="subMenuItem in subMenus.filter(
          (item) => item.MENU_UPPER === menuItem.MENU_ID
        )"
        :key="subMenuItem.MENU_ID"
      />
    </q-expansion-item>
  </q-list>
</template>

<script>
import { defineComponent, computed, toRaw } from "vue";

export default defineComponent({
  name: "RoleMenu",
  props: {
    menus: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    /**
     * 2단계 서브메뉴까지만 표시
     *
     */
    const rootMenus = computed(() => {
      //return props.menus.filter((item) => item.LVL === 1);
      return toRaw(props.menus).filter((item) => item.MENU_LVL === 1);
    });

    const subMenus = computed(() => {
      //return props.menus.filter((item) => item.LVL === 2);
      return toRaw(props.menus).filter((item) => item.MENU_LVL === 2);
    });

    return {
      rootMenus,
      subMenus,
    };
  },
});
</script>
