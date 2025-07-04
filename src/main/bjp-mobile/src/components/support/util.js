import { LocalStorage, Notify, Dialog } from "quasar";
import config from "src/support/config";
import CryptoJS from "crypto-js";
import { api } from "boot/axios";
import moment from "moment";

/**************************************************
 * 사용법
 * import * as util from 'support/util'
 *
 * util.xxx()
 *
 **************************************************/

/**
 * 오늘날짜를 format에 따라서 구한다.
 * @param {string} format - 날짜포멧
 * @returns {string}
 */
export function getToday(format = "YYYY-MM-DD") {
  const d = new Date();
  return moment(d).format(format);
}

/**
 * 오늘날짜에서 gap만큼 이전/이후 날짜를 format에 따라서 구한다.
 * gap이 음수면 과거, 양수면 미래날짜를 구한다.
 * @param {number} gap - 오늘과 구하고자 날짜의 차이
 * @param {string} format - 날짜포멧
 * @returns {string}
 */
export function getFromToday(gap, format = "YYYY-MM-DD") {
  const d = new Date();
  return moment(d.setDate(d.getDate() + gap)).format(format);
}

export function getDay(gap) {
  const d = moment(gap).day();
  if(d === 0){
    return "일";
  } else if(d === 1){
    return "월";
  } else if(d === 2){
    return "화";
  } else if(d === 3){
    return "수";
  } else if(d === 4){
    return "목";
  } else if(d === 5){
    return "금";
  } else if(d === 6){
    return "토";
  }
  return "";
}


export function errorNotify(message) {
  Notify.create({
    color: "red-5",
    textColor: "white",
    icon: "warning",
    message,
  });
}

export function showNotify(message) {
  Notify.create({
    color: "green-8",
    textColor: "white",
    icon: "cloud_done",
    message,
  });
}

export function notify(response) {
  if (response.result) {
    Notify.create({
      color: "green-8",
      textColor: "white",
      icon: "cloud_done",
      message: response.result_msg,
    });
  } else {
    Notify.create({
      color: "red-5",
      textColor: "white",
      icon: "warning",
      message: response.result_msg,
    });
  }
}

export function alert(title, message, fnOk) {
  Dialog.create({
    dark: false,
    title,
    message,
  }).onOk(() => {
    // console.log('OK')
    if (typeof fnOk === "function") fnOk();
  });
}

export function confirm(title, message, fnOk, fnCancel) {
  Dialog.create({
    title,
    message,
    cancel: true,
    persistent: true,
  })
    .onOk(() => {
      // console.log('OK')
      if (typeof fnOk === "function") fnOk();
    })
    .onCancel(() => {
      // console.log('Cancel')
      if (typeof fnCancel === "function") fnCancel();
    })
    .onDismiss(() => {
      console.log("I am triggered on both OK and Cancel");
    });
}

export function clone(obj) {
  return JSON.parse(JSON.stringify(obj));
}

export function isEmpty(value) {
  if (value === null) return true;
  if (typeof value === "undefined") return true;
  if (typeof value === "string" && value === "") return true;
  if (Array.isArray(value) && value.length < 1) return true;
  if (
    typeof value === "object" &&
    value.constructor.name === "Object" &&
    Object.keys(value).length < 1 &&
    Object.getOwnPropertyNames(value) < 1
  ) {
    return true;
  }
  if (
    typeof value === "object" &&
    value.constructor.name === "String" &&
    Object.keys(value).length < 1
  ) {
    return true;
  }
  return false;
}

/**
 * @description : value로 들어온값이 empty인지 체크해서 true이면 defaultValue를 리턴
 */
export function defaultValue(value, defaultValue) {
  return isEmpty(value) ? defaultValue : value;
}

/**
 * @description : value로 들어온값이 empty인지 체크해서 true이면 defaultValue를 리턴
 */
export function nvl(value, defaultValue) {
  return isEmpty(value) ? defaultValue : value;
}

export function getToken() {
  return LocalStorage.getItem(config.storageTokenKeyName);
}

export function getRefreshToken() {
  return LocalStorage.getItem(config.storageRefreshTokenKeyName);
}

export function setToken(value) {
  LocalStorage.set(config.storageTokenKeyName, value);
}

export function setRefreshToken(value) {
  LocalStorage.set(config.storageRefreshTokenKeyName, value);
}

const secret = "a2sad34!cqewqgsdhbe!@VDS$%"; /*  암호화 키 */

/**
 * 암호화
 *
 */
function getEncode(data) {
  return CryptoJS.AES.encrypt(data, secret).toString();
}

/**
 * 복호화
 *
 */
function getDecode(data) {
  const bytes = CryptoJS.AES.decrypt(data, secret);
  const originalText = bytes.toString(CryptoJS.enc.Utf8);
  return originalText;
}

/**
 * 현재 브라우저에 사용자의 로그인 여부 리턴
 *
 */
export function isUserLoggedIn() {
  return (
    !isEmpty(LocalStorage.getItem(config.userData)) &&
    !isEmpty(LocalStorage.getItem(config.storageTokenKeyName))
  );
}

export function getUserData() {
  const data = LocalStorage.getItem(config.userData);
  if (isEmpty(data)) {
    return {};
  }
  return JSON.parse(getDecode(data));
}

export function setUserData(data) {
  LocalStorage.set(config.userData, getEncode(JSON.stringify(data)));
}

export function logout() {
  LocalStorage.remove(config.userData);
  LocalStorage.remove(config.storageTokenKeyName);
  LocalStorage.remove(config.storageRefreshTokenKeyName);
}

/**
 * 공통 controller호출용
 *
 */
export function bindList(namespance, queryId, payload) {
  return api.post(`/bindList/${namespance}/${queryId}`, payload);
}

export function bindRow(namespance, queryId, payload) {
  return api.post(`/bindRow/${namespance}/${queryId}`, payload);
}

/**
 * 빈값을 제외한 날짜형식 체크
 */
export function dateValidator(value) {
  if (!value) {
    // 값이 빈 값인 경우, 유효성 검증을 하지 않음
    return true;
  }

  // 값이 빈 값이 아닌 경우, 포멧을 검증함
  const valid = /\d{4}-\d{2}-\d{2}/.test(value);
  return (
    valid || "유효한 날짜 형식이 아닙니다. YYYY-MM-DD 형식으로 입력해주세요."
  );
}

/**
 * q-date의 rules에 지정
 */
export const ruleDate = [(value) => dateValidator(value)];

/**
 * 빈값을 제외한 시간형식 체크
 */
export function timeValidator(value) {
  if (!value) {
    // 값이 빈 값인 경우, 유효성 검증을 하지 않음
    return true;
  }

  // 값이 빈 값이 아닌 경우, 포멧을 검증함
  const valid = /^([01]\d|2[0-3]):([0-5]\d)$/.test(value);
  return valid || "유효한 시간 형식이 아닙니다. HH:mm 형식으로 입력해주세요.";
}

/**
 * q-time의 rules에 지정
 */
export const ruleTime = [(value) => timeValidator(value)];

/**
 * 필수입력 체크
 */
export const required = (val) =>
  (val && String(val).length > 0) || "필수입력값입니다.";

export const requiredSelect = (val) => !!val || "값을 선택해 주세요";

/**
 * 오늘날짜에서 gap만큼 이전/이후 날짜를 format에 따라서 구한다.
 * gap이 음수면 과거, 양수면 미래날짜를 구한다.
 * @param {number} gap - 오늘과 구하고자 날짜의 차이
 * @param {string} format - 날짜포멧
 * @returns {string}
 */

/**
 * q-select에서 사용되는 포멧으로 데이터 변환 {value:"",label:""}
 * @param {array} data - 객체를 저장한 배열
 * @param {string} valueColumn - data배열에서 value값으로 사용될 column 이름
 * @param {string} labelColumn - data배열에서 label값으로 사용될 column 이름
 * @param {boolean} isAll - 전체를 추가할 여부
 *
 * https://quasar.dev/vue-components/select#affecting-model
 */
export function getDataForSelect(
  data,
  valueColumn,
  labelColumn,
  isAll,
  allTitle
) {
  if (isEmpty(data)) return [];

  const rtn = [];
  if (isAll) rtn.push({ value: "", label: allTitle });

  for (let i = 0; i < data.length; i++) {
    rtn.push({ value: data[i][valueColumn], label: data[i][labelColumn] });
  }

  return rtn;
}

/**
 * q-select에서 선택한 데이터의 label값 리턴
 * @param {array} data - select에 표시되는 데이터(배열)
 * @param {string} value - 선택한 행의 value
 *
 */
export function getLabelForSelectChoose(data, value) {
  const findRow = data.value.filter((el) => el.value === value);
  const findLabel = findRow.length === 0 ? "" : findRow[0].label;
  return findLabel;
}

/**
 * key=value,key=value형식의 문자열을 json으로 변환
 */
export function stringToJson(str) {
  const json = {};
  str.split(",").forEach((item) => {
    const [key, value] = item.split("=");
    json[key] = value;
  });
  return json;
}

/**
 * 해당문자열이 json형식인지 검사
 */
export function isJsonString(str) {
  try {
    const json = JSON.parse(str);
    return typeof json === "object";
  } catch (e) {
    return false;
  }
}

function forceFileDownload(response, fileName) {
  console.log("forceFileDownload==>", response);
  const url = window.URL.createObjectURL(new Blob([response.data]));
  const link = document.createElement("a");
  link.href = url;
  link.setAttribute("download", fileName); // or any other extension
  document.body.appendChild(link);
  link.click();
}

export function fileDelete(payload) {
  return api.post("/fileDelete", payload);
}

export function fileDownload(payload) {
  return api
    .post("/fileDownload", payload, { responseType: "arraybuffer" })
    .then((res) => forceFileDownload(res, payload.FILE_NAME))
    .catch((error) => console.error("fileDownload==>", error));
}

/**
 * 이미지 url 리턴
 */
export function getImageUrl(fileSeq, seqNo) {
  return `${process.env.API_URL}/getImage?FILE_SEQ=${fileSeq}&SEQ_NO=${seqNo}`;
}

/**
 * 파일업로드 url 리턴
 */
export function getUrl() {
  const apiUrl = process.env.API_URL;
  return `${apiUrl}/fileUpload`;
}

/**
 *  이미지 로딩 비동기 처리
 *
 */
export const loadImage = (src) => {
  return new Promise((resolve, reject) => {
    const image = new Image();
    image.onload = () => resolve(image);
    image.onerror = () => reject(new Error(`Failed to load image from ${src}`));
    image.src = src;
  });
};

/**
 *  멀티 이미지 로딩 비동기 처리
 *
 */
export const loadImages = async (deviceTypeSvg) => {
  const deviceTypeImg = {};

  try {
    const promises = Object.entries(deviceTypeSvg).map(async ([key, value]) => {
      const image = await loadImage(value);
      // console.log('loadImages', image)
      deviceTypeImg[key] = image;
    });

    await Promise.all(promises);
    return deviceTypeImg;
  } catch (error) {
    console.error("loadImages error===>", error);
    throw error;
  }
};

/**
 *  json array에서 key로 검색후 데이터를 리턴
 *
 */
export function getJsonArrayByKey(jsonArray, key, value) {
  const findRow = jsonArray.value.filter((el) => el[key] === value);
  return findRow.length === 0 ? null : findRow[0];
}

/**
 *  Vue The road to enterprise : 91page 참조
 *
 */
export const withAsync = async (fn, ...args) => {
  try {
    const response = await fn(...args);
    return {
      response,
      error: null,
    };
  } catch (error) {
    return {
      response: null,
      error,
    };
  }
};

//------------------------- 백제약품 추가 -------------------------//

export function getNumberFormat(val) {
  return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

export function textShortter(val, len, sliceLen) {
  if (val.length > len) {
    return val.substring(0, sliceLen) + "...";
  } else {
    return val;
  }
}
