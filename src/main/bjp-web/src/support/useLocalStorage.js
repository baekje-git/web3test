import { ref, computed, watch, onMounted, onUnmounted } from "vue";

export default function useLocalStorage(localStorageKey, initValue) {
  if (localStorage.getItem(localStorageKey) == null) {
    localStorage.setItem(localStorageKey, JSON.stringify(initValue));
  }
  const storageValue = ref(JSON.parse(localStorage.getItem(localStorageKey)));

  const storageComputedValue = computed({
    get() {
      return storageValue.value;
    },
    set(newValue) {
      storageValue.value = newValue;
      localStorage.setItem(localStorageKey, JSON.stringify(newValue));
    },
  });

  // storageValue 변경 감지 시 localStorage에 저장
  watch(storageValue, (newValue, oldValue) => {
    localStorage.setItem(localStorageKey, JSON.stringify(newValue));
    const e = new StorageEvent("storage", {
      storageArea: window.localStorage,
      localStorageKey,
      oldValue,
      newValue: JSON.stringify(newValue),
      url: window.location.href,
    });
    window.dispatchEvent(e);
  });

  // localStorage 변경 이벤트 핸들러
  const handleStorageChange = (event) => {
    const newValue = JSON.parse(event.newValue);
    console.log(
      "handleStorageChange",
      event,
      storageValue.value,
      newValue,
      storageValue.value !== newValue
    );
    if (event.key === localStorageKey && storageValue.value !== newValue) {
      storageValue.value = newValue;
    }
  };

  // 컴포넌트 마운트 시 이벤트 리스너 등록
  onMounted(() => {
    window.addEventListener("storage", handleStorageChange);
  });

  // 컴포넌트 언마운트 시 이벤트 리스너 제거
  onUnmounted(() => {
    window.removeEventListener("storage", handleStorageChange);
  });

  return {
    storageValue,
    storageComputedValue,
  };
}
