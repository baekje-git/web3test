<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    <script language="javascript" type="text/javascript" src="https://stgstdpay.inicis.com/stdjs/INIStdPay.js" charset="UTF-8"></script>
    <title>간편결제 테스트</title>
    <style type="text/css">
    iframe.inipay_iframe {
	  background: #fff !important;
	}
	
	.inipay_modal-backdrop,
	.inipay_modal-backdrop.fade.in {
	  opacity: 0 !important;
	  filter: alpha(opacity=30) !important;
	}
    
    </style>
</head>
<body>
	<form name="SendPayForm_id" id="SendPayForm_id" method="post">
		<input type="hidden" name="version" value="1.0">
		<!-- <input type="hidden" name="gopaymethod" value="Card:Directbank:vbank"> -->
		<input type="hidden" name="gopaymethod" value="onlykakaopay ">
        <input type="hidden" name="mid" value="${hMap.mid}">
        <input type="hidden" name="oid" value="${hMap.orderNumber}">
        <input type="hidden" name="price" value="${hMap.price}">
        <input type="hidden" name="timestamp" value="${hMap.timestamp}">
		<input type="hidden" name="use_chkfake" value="${hMap.use_chkfake}">
		<input type="hidden" name="signature" value="${hMap.signature}">
		<input type="hidden" name="verification" value="${hMap.verification}">
		<input type="hidden" name="mKey" value="${hMap.mKey}">
		<input type="hidden" name="currency" value="WON">
		<input type="hidden" name="goodname" value="${hMap.goodname}">
		<input type="hidden" name="buyername" value="${hMap.buyername}">
		<input type="hidden" name="buyertel" value="${hMap.buyertel}">
		<input type="hidden" name="buyeremail" value="${hMap.buyeremail}">
		<input type="hidden" name="returnUrl" value="http://localhost:1010/inicis/resPayment">
		<input type="hidden" name="closeUrl" value="http://localhost:1010/inicis/closePayment">
        <!-- <input type="hidden" name="acceptmethod" value="HPP(1):below1000:centerCd(Y)"> -->
        <input type="hidden" name="acceptmethod" value="cardonly">
	</form>
	<script type="text/javascript">
        INIStdPay.pay('SendPayForm_id');
    </script>
</body>
</html>