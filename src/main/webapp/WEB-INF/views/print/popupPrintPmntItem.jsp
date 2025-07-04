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
  <link href="/dist/assets/css/style5.css" rel="stylesheet">
  <title>품목수불</title>
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
	
  <div class="wrap1">
  	<img id="printBtn" class="print" src="/dist/assets/images/print.png" alt="logo" onclick="fnPrint();" title="인쇄하기">
  	<c:if test="${hMap.companyYn != 'Y'}">
  	<img class="logo" src="/dist/assets/images/logo_print.png" alt="logo">
  	</c:if>
    <div class="flex-center">
      <h1>품목수불</h1>
    </div>
    <div class="flex-center">
      <h2><c:out value="${hMap.custNm }"/></h2>
    </div>
    <div class="headTop">
      <jsp:useBean id="today" class="java.util.Date" />
	  <fmt:formatDate value='${today}' pattern='yyyy.MM.dd' var="nowDate"/>
      <div class="headItem"><span>발행일자</span><span><c:out value="${nowDate }"/></span>
      </div>
      <div class="headItem"><span>조회일자</span>
      	<fmt:parseDate var="dateStart" value="${hMap.startDt}" pattern="yyyyMMdd" />
		<fmt:parseDate var="dateEnd" value="${hMap.endDt}" pattern="yyyyMMdd" />
        <div class="headItem-sub"><span><fmt:formatDate value="${dateStart}" pattern="yyyy.MM.dd" /></span><span>~</span><span><fmt:formatDate value="${dateEnd}" pattern="yyyy.MM.dd" /></span>
        </div>
      </div>
      <div class="headItem"><span>수불건수</span><span><fmt:formatNumber value="${count.CNT}" pattern="#,###" />건</span></div>
      <div class="headItem"><span>수불금액</span><span><fmt:formatNumber value="${count.TOTAL_AMT}" pattern="#,###" />원</span></div>      
    </div>
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>일자</th>
            <th>승인번호</th>
            <th>종류</th>
            <th style="width: 180px;">품목 및 규격</th>
            <th style="width: 150px;">제약회사</th>
            <th>수량</th>
            <th>단가</th>
            <th>금액</th>
            <th>표준코드</th>
            <th>보험코드</th>
          </tr>
        </thead>
        <tbody>
       		<c:forEach items="${list }" var="item" >
			<tr>
	            <td><c:out value="${item.APRV_DT_FM }"/></td>
	            <td><c:out value="${item.APRV_NO }"/></td>
	            <td><c:out value="${item.TYPE_NM }"/></td>
	            <td style="text-align: left;"><strong style="color: red;"><c:out value="${item.MED_NM }"/></strong> <strong style="color: blue;"><c:out value="${item.PAY_ITEM_NM }"/></strong> <c:out value="${item.ITEM_NM_UNIT }"/></td>
	            <td><c:out value="${item.MAKER_NM }"/></td>
	            <td><fmt:formatNumber value="${item.ITEM_QTY}" pattern="#,###" /></td>
	            <td style="text-align: right;"><fmt:formatNumber value="${item.ORD_WP2_AMT}" pattern="#,###" /></td>
	            <td  style="text-align: right; <c:if test="${item.SALE_AMT < 0}">color: red;</c:if>"><fmt:formatNumber value="${item.SALE_AMT}" pattern="#,###" /></td>
	            <td><c:out value="${item.ITEM_PYOJUN_CD }"/></td>
	            <td><c:out value="${item.BOHUM_CD }"/></td>
	          </tr>  
        	</c:forEach>     
        </tbody>
      </table>
    </div>
  </div>

</body>
</html>
