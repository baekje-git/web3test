<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>WPAY 표준 결제정보(신용카드) 등록</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body { background-color: #efefef;}
		body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
		table, img {border:none}
	</style>
</head>


<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 >
<form id="SendPayForm_id" name="SendPayForm_id" method="POST" >
	
	<!-- <br/><b>mid</b> -->
	<br/><input type="hidden" name="mid" value="${param_mid}" >
		
	<!-- <br/><b>wpayUserKey</b> -->
	<br/><input type="hidden" name="wpayUserKey" value="${param_wpayUserKey}" >
	
	<!-- <br/><b>ci</b> -->
	<br/><input type="hidden"  name="ci" value="${param_ci}" >
	
	<!-- <br/><b>returnUrl</b> -->
	<br/><input type="hidden"   name="returnUrl" value="${param_returnUrl}" >

	<!-- <br/><b>signature</b> -->
	<br/><input type="hidden"  name="signature" value="${param_signature}" >
	
    <!-- <br/><b>custCd</b> -->
    <br/><input type="hidden"  name="custCd" value="${custCd}" >
    
    <!-- <br/><b>cardGb</b> -->
    <br/><input type="hidden"  name="cardGb" value="${cardGb}" >
    
    <!-- <br/><b>custCd</b> -->
    <br/><input type="hidden"  name="amountTi" value="${amountTi}" >
    
	
	<div id="lodingImg" style="position:absolute; left:45%; top:40%; dispaly:none;">
		<div class='loader'  style=""></div>
	</div>

</form>
</body>
</html>

<script language="javascript">
<!--
	goWpay();
	function goWpay() {
		var sendfrm = document.getElementById("SendPayForm_id");
		sendfrm.action = "${requestURL}";
		sendfrm.submit();
	}
-->
</script>
