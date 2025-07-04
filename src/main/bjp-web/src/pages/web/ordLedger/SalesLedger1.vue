<template lang="">
  <div class="wrap">
    <img class="logo" src="assets/images/printlogo.png" alt="logo" />
    <div class="flex-center">
      <h1>거래원장</h1>
    </div>
    <div class="flex-center">
      <h2>(NEW)(여의도동) 대교약국</h2>
    </div>
    <div class="headTop">
      <div class="headItem"><span>발행일자</span><span>2024.04.11</span></div>
      <div class="headItem">
        <span>조회일자</span>
        <div class="headItem-sub">
          <span>20240411</span><span>~</span><span>20240411</span>
        </div>
      </div>
      <div class="headItem"><span>판매금액</span><span>18,598,370</span></div>
      <div class="headItem"><span>입금액</span><span>0</span></div>
    </div>
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>일자</th>
            <th>승인번호</th>
            <th>주문서종류</th>
            <th>종류</th>
            <th style="width: 180px">적요</th>
            <th>품목수</th>
            <th>판매금액</th>
            <th>입금액</th>
            <th>어음종류</th>
            <th>만기일</th>
            <th>잔고</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="i in 15" :key="i">
            <td>2024.01.19</td>
            <td>1234</td>
            <td>
              일반주문<br />
              (인터넷)
            </td>
            <td>반품</td>
            <td>구구정10mg(오남용)8T<br />(이노트로닉) 8T</td>
            <td>외5종</td>
            <td>123,000</td>
            <td>0</td>
            <td></td>
            <td></td>
            <td>55,521,664</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script setup></script>

<style scoped>
* {
  box-sizing: border-box;
  -moz-box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
}

h1 {
  text-align: center;
  font-size: 22px;
  border-bottom: 2px solid;
  line-height: 30px;
  font-weight: bold;
  margin-bottom: 14px;
}
h2 {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  line-height: 30px;
  margin-bottom: 18px;
}

thead tr th {
  position: relative;
}

/* A4 설정 */
@page {
  size: A4;
  margin: 0;
}

@media print {
  .wrap {
    margin: 0;
    border: initial;
    border-radius: initial;
    width: initial;
    min-height: initial;
    box-shadow: initial;
    background: initial;
    page-break-after: always;
  }
}

.space {
  padding: 0 130px;
}
.flex-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
.flex-end {
  display: flex;
  justify-content: end;
  align-items: center;
}
.text-center {
  text-align: center;
}
.text-left {
  text-align: left;
}
.text-right {
  text-align: right;
}
.font-bold {
  font-weight: bold;
}
.font-nomal {
  font-weight: normal;
}
.font-small {
  font-size: 12px;
}

.logo {
  position: absolute;
  top: 2%;
  right: 6%;
}

.wrap {
  position: relative;
  width: 21cm;
  min-width: 21cm;
  min-height: 29.7cm;
  max-height: 29.7cm;
  padding: 1.5cm 1.5cm 1cm 1.5cm;
  font-size: 16px;
  letter-spacing: -1px;
  border: 1px solid rgba(0, 0, 0, 0.1); /* 구분선 */
}

.headTop {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.headItem {
  display: flex;
  justify-content: start;
  align-items: center;
  gap: 20px;
  margin-bottom: 12px;
}
.headItem span {
  font-size: 14px;
}
.headItem span:nth-child(1) {
  color: #999999;
}
.headItem-sub {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}
.headItem-sub span {
  color: #000 !important;
}
table {
  text-align: center;
  border-collapse: collapse;
  width: 100%;
}
thead th {
  border: 1.82px solid rgba(0, 0, 0, 0.2);
  border-bottom: none;
  color: #454e62;
  font-size: 12px;
  padding: 8px 0px;
}
thead th:first-child,
thead th:last-child,
tbody td:first-child,
tbody td:last-child {
  border-left: none;
  border-right: none;
}
tbody td {
  border: 1.82px solid rgba(0, 0, 0, 0.2);
  padding: 10px 4px;
  font-size: 12px;
}
tbody td:nth-child(5) {
  text-align: left;
}
tbody td:nth-child(7) {
  text-align: right;
}
tbody td:nth-child(8) {
  text-align: right;
}
tbody td:nth-child(11) {
  text-align: right;
}
</style>
