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

    <!-- <br/><b>ci</b> -->
    <br/><input type="hidden"  name="ci" value="${param_ci}" >
    
    <!-- <br/><b>payMethod</b> -->
    <br/><input type="hidden"  name="wtid" value="${param_wtid}" >    
	
	<!-- <br/><b>payMethod</b> -->
    <br/><input type="hidden"  name="tax" value="${param_tax}" >    
	
	<!-- <br/><b>payMethod</b> -->
    <br/><input type="hidden"  name="taxFree" value="${param_taxFree}" >    

	<!-- <br/><b>payMethod</b> -->
    <br/><input type="hidden"  name="cupDeposit" value="${param_cupDeposit}" >    

    <!-- <br/><b>returnUrl</b> -->
    <br/><input type="hidden"   name="returnUrl" value="${param_returnUrl}" >

    <!-- <br/><b>signature</b> -->
    <br/><input type="hidden"  name="signature" value="${param_signature}" >
    
    
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


