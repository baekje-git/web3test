<%--------------------------------------------------------------------------------------
# 시스템명        : 백제약품 시스템
# 페이지 명       : 매출원장 출력
# 담당자          : 
# 변경이력
----------------------------------------------------------------------------------------
    2024-04-12 |  | 최초 작성
------------------------------------------------------------------------------------------%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="/dist/assets/css/style6.css" rel="stylesheet">
  <title>매출원장</title>
  <script>
		fnPrint();
		// 출력
		function fnPrint(){
		   window.onbeforeprint = function(){
		    	document.getElementById("printBtn").style.display = "none";
		    }
		    window.onafterprint = function(){
		    	document.getElementById("printBtn").style.display = "";
		//     	setTimeout(function(){self.close()},100);
		    }
		
			window.print();
		}

	</script>
</head>

<body>
  <div class="wrap">
  	<img id="printBtn" class="print" src="/dist/assets/images/print.png" alt="logo" onclick="fnPrint();" title="인쇄하기">   
    <div class="flex-center">
      <h1>반품요청서</h1>
    </div>   

    <div class="flex gap-10 mb-20 align-end">
      <div class="font-bold font-big">
        거래처명 :
        <span><c:out value="${hMap.custNm}"/></span>
      </div>
      <div>
        거래처코드 :
        <span><c:out value="${hMap.custCd}"/></span>
      </div>
      <div>
        지점명 :
        <span><c:out value="${hMap.brchSnm}"/></span>
      </div>
      <div class="flex gap-20 font-small" style="margin-left: auto; padding-right: 10px;">
        <span class="font-bold font-gray">발행일자</span>
        <jsp:useBean id="today" class="java.util.Date" />
	  	<fmt:formatDate value='${today}' pattern='yyyy-MM-dd' var="nowDate"/>
        <span><c:out value="${nowDate }"/></span>
      </div>
    </div>
    <div class="flex gap-10 mb-10">
      <div class="font-bold">
        담당자 :  
        <span><c:out value="${hMap.empNm}"/></span>
      </div>
      <div>
        연락처 : 
        <span><c:out value="${hMap.empHpNo}"/></span>
      </div>
    </div>





    
    <div class="table-wrap">
      <table class="type_01">
        <thead>
          <tr>
            <th>반품일자</th>
            <th width="16%">제약회사</th>
            <th>품목 및 규격</th>
            <th>반품<br>수량</th>
            <th>승인<br>수량</th>
            <th>불가<br>수량</th>
            <th>유효기간</th>
            <th>제조번호</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${list }" var="item" >
          <tr>
            <td><c:out value="${item.BACK_DT_FM}"/></td>
            <td><c:out value="${item.MAKER_NM}"/></td>
            <td style="text-align: left;"><strong style="color: red;"><c:out value="${item.MED_NM }"/></strong> <strong style="color: blue;"><c:out value="${item.PAY_ITEM_NM }"/></strong> <c:out value="${item.ITEM_NM_UNIT }"/></td>
            <td><c:out value="${item.ITEM_QTY_REQ}"/></td>
            <td><c:out value="${item.ITEM_QTY}"/></td>
            <td><c:out value="${item.ITEM_QTY_NO}"/></td>
            <td><c:out value="${item.EXP_DT}"/></td>
            <td><c:out value="${item.PROD_NO}"/></td>
          </tr>
          </c:forEach>
          <tr class="barcode_area">
            <td colspan="8">
              <img src="/print/barcode?BARCODE=<c:out value="${hMap.sn}"/>&TYPE=CODE128&w=350&h=50" alt="">
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="flex-between font-small">
      <span class="flex"><img src="../images/icon_check.svg" alt="">비고 : ${list[0].BACK_MEMO}</span>
      <span class="flex" style="gap:5px;">건수  <b><c:out value="${list.size()}"/></b>건</span>
    </div>

    <div class="text-box-wrap" style="margin-top:auto;">
      <div class="text-box flex-between align-center">
        <div class="left font-small">
          <div>
            보내시는 반품리스트입니다.
            <br>
            저희 직원과 보내시는 물건을 확인했다는 증명서이오니
            <br>
            [약국 상호인]을 필히 찍어주시기 바랍니다.
          </div>
          <div class="write">
            <div class="flex-between font-bold">
              <span>영</span>
              <span>업</span>
              <span>담</span>
              <span>당</span>
              <span>자 :</span>
            </div>
            <div class="flex-between font-bold">
              <span>배</span>
              <span>송</span>
              <span>기</span>
              <span>사 :</span>
            </div>
            <div class="flex-between font-bold">
              <span>반</span>
              <span>품</span>
              <span>처</span>
              <span>리</span>
              <span>자 :</span>
            </div>
            <div class="flex-between font-bold">
              <span>반</span>
              <span>품</span>
              <span>처</span>
              <span>리</span>
              <span>일</span>
              <span>자 :</span>
            </div>
          </div>
        </div>
        <div class="right">
          <div class="table_wrap">
            <table>
              <tr>
                <td width="35%" height="36">일자</td>
                <td></td>
              </tr>
              <tr>
                <td height="120">상호인</td>
                <td></td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>          
    
  </div>

</body>
</html>
