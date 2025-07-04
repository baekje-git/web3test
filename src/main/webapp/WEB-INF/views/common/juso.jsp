<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%
Map<String, String> mapJUSO = (Map<String, String>) request.getAttribute("mapJUSO");
if(mapJUSO!=null){
out.println("siNm;"+mapJUSO.get("siNm"));
out.println("sggNm;"+mapJUSO.get("sggNm"));
out.println("rn;"+mapJUSO.get("buldMnnm")+"-"+mapJUSO.get("buldSlno"));
out.println("addrDetail;"+mapJUSO.get("addrDetail"));
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>도핑방지위원회</title>
</head>
<script language="javascript">
function init(){
	var url = location.href;
	var confmKey = "U01TX0FVVEgyMDE1MTIxMDE0MjIwMzA=";
	var inputYn= "N";
	if(inputYn != "Y"){
		alert(url)
		document.form.confmKey.value = confmKey;
		document.form.returnUrl.value = url;
		document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do"; //인터넷망
		//document.form.action="http://10.182.60.22/addrlink/addrLinkUrl.do"; //내부행망
		document.form.submit();		
	}else{	
		alert("@@")
		// window.open('','_self').close();
	}
}
</script>
<body onload="init();">
	<form id="form" name="form" method="post">
		<input type="hidden" id="confmKey" name="confmKey" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
		<!-- 
		<input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
		 -->
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
	</form>
</body>
</html>