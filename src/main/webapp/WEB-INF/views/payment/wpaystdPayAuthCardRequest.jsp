<%--------------------------------------------------------------------------------------
# 시스템명        : 백제약품 시스템
# 페이지 명       : 간편결재 이니시스
# 담당자          : 
# 변경이력
----------------------------------------------------------------------------------------
    
------------------------------------------------------------------------------------------%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>WPAY 고도화 결제인증 요청</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
        body { background-color: #efefef;}
        body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
        table, img {border:none}
    </style>
</head>

<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 >
<form id="SendAuthForm_id" name="SendAuthForm_id" method="POST" >
    <!-- <br/><b>mid</b> -->
    <br/><input type="hidden" name="mid" value="${param_mid}" >
        
    <!-- <br/><b>wpayUserKey</b> -->
    <br/><input type="hidden" name="wpayUserKey" value="${param_wpayUserKey}" >
    
    <!-- <br/><b>wpayToken</b> -->
    <br/><input type="hidden" name="wpayToken" value="${param_wpayToken}" >
    
    <!-- <br/><b>mKey</b> -->
    <br/><input type="hidden" name="mKey" value="${param_mKey}" >

    <!-- <br/><b>ci</b> -->
    <br/><input type="hidden"  name="ci" value="${param_ci}" >
    
    <!-- <br/><b>payMethod</b> -->
    <br/><input type="hidden"  name="payMethod" value="${param_payMethod}" >
    
    <!-- <br/><b>bankCardCode</b> -->
    <br/><input type="hidden"  name="bankCardCode" value="${param_bankCardCode}" >
    
    <!-- <br/><b>oid</b> -->
    <br/><input type="hidden"  name="oid" value="${param_oid}" >

    <!-- <br/><b>goodsName</b> -->
    <br/><input type="hidden"  name="goodsName" value="${param_goodsName}" >

    <!-- <br/><b>goodsPrice</b> -->
    <br/><input type="hidden"  name="goodsPrice" value="${param_goodsPrice}" >

    <!-- <br/><b>buyerName</b> -->
    <br/><input type="hidden"  name="buyerName" value="${param_buyerName}" >

    <!-- <br/><b>buyerTel</b> -->
    <br/><input type="hidden"   name="buyerTel" value="${param_buyerTel}" >

    <!-- <br/><b>buyerEmail</b> -->
    <br/><input type="hidden"   name="buyerEmail" value="${param_buyerEmail}" >

    <!-- <br/><b>cardQuota</b> -->
    <br/><input type="hidden"   name="cardQuota" value="${param_cardQuota}" >
    
    <!-- <br/><b>cardInterest</b> -->
    <br/><input type="hidden"   name="cardInterest" value="${param_cardInterest}" >

    <!-- <br/><b>couponCode</b> -->
    <br/><input type="hidden"   name="couponCode" value="${param_couponCode}" >

    <!-- <br/><b>flagPin</b> -->
    <br/><input type="hidden"   name="flagPin" value="${param_flagPin}" >
    
    <!-- <br/><b>flagCardPoint</b> -->
    <br/><input type="hidden"   name="flagCardPoint" value="${param_flagCardPoint}" >

    <!-- <br/><b>returnUrl</b> -->
    <br/><input type="hidden"   name="returnUrl" value="${param_returnUrl}" >

    <!-- <br/><b>signature</b> -->
    <br/><input type="hidden"  name="signature" value="${param_signature}" >
    
    <!-- <br/><b>custCd</b> -->
    <br/><input type="hidden"  name="custCd" value="${custCd}" >
    
    <!-- <br/><b>cardGb</b> -->
    <br/><input type="hidden"  name="cardGb" value="${cardGb}" >
    
    <!-- <br/><b>amountTi</b> -->
    <br/><input type="hidden"  name="amountTi" value="${amountTi}" >
    
    
    <div id="lodingImg" style="position:absolute; left:45%; top:40%; dispaly:none;">
        <div class='loader'  style=""></div>
    </div>
</form>
</body>
</html>
<script type="text/javascript">
<!--
goWpay();
function goWpay() {
    var sendfrm = document.getElementById("SendAuthForm_id");
    sendfrm.action = '${requestURL}';
    sendfrm.submit();
}
//-->
</script>


