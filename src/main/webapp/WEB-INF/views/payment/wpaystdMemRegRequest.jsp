<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>WPAY 표준 회원가입 요청</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body { background-color: #efefef;}
		body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
		table, img {border:none}
	</style>
</head>


<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 >
<form id="SendMemregForm_id" name="SendMemregForm_id" method="POST" >

    <input  type="hidden" name=custCd id="custCd" value="${custCd}" >
    <input  type="hidden" name=cardGb id="cardGb" value="${cardGb}" >
    <input  type="hidden" name=amountTi id="amountTi" value="${amountTi}" >

    <input  type="hidden" name=brchCd id="brchCd" value="${brchCd}" >
    <input  type="hidden" name=g_HASHKEY id="g_HASHKEY" value="${g_HASHKEY}" >
    <input  type="hidden" name=g_SEEDKEY id="g_SEEDKEY" value="${g_SEEDKEY}" >
    <input  type="hidden" name=g_SEEDIV id="g_SEEDIV" value="${g_SEEDIV}" >
	<!-- <br/><b>mid</b> : 가맹점 ID -->
	<input  type="hidden" name="mid" id="mid" value="${param_mid}"  />	

	<!-- <br/><b>userId</b> : 가맹점 고객 ID -->
	<input  type="hidden" name="userId" id="userId" value="${param_userId}" />

	<!-- <br/><b>returnUrl</b> : 회원가입 결과전달 URL -->
	<input  type="hidden" name="returnUrl" id="returnUrl" value="${param_returnUrl}"  />


	<!-- <br/><b>ci</b> : 고객의 CI -->
	<input  type="hidden" name="ci" id="ci"  value="${param_ci}" >

	<!-- <br/><b>userNm</b> : 고객실명 -->
	<input  type="hidden" name="userNm" id="userNm" value="${param_userNm}" >

	<!-- <br/><b>hNum</b> : 고객 휴대폰번호 -->
	<input  type="hidden" name="hNum" id="hNum" value="${param_hNum}" >

	<!-- <br/><b>hCorp</b> : 휴대폰 통신사('SKT', 'KTF', 'LGT', 'SKR':SKT알뜰폰, 'LGR':LGT알뜰폰, 'KTR':KT알뜰폰) -->
	<input  type="hidden" name="hCorp" id="hCorp" value="${param_hCorp}" >

	<!-- <br/><b>birthDay</b> : 고객 생년월일(yyyymmdd) -->
	<input  type="hidden" name="birthDay" id="birthDay" value="${param_birthDay}" >

	<!-- <br/><b>socialNo2</b> : 주민번호 뒤 첫자리 -->
	<input  type="hidden" name="socialNo2" id="socialNo2" value="${param_socialNo2}" >

	<!-- <br/><b>frnrYn</b> : 외국인여부(Y:외국인,N:내국인) -->
	<input  type="hidden" name="frnrYn" id="frnrYn" value="${param_frnrYn}" >

	<!-- <br/><b>signature</b> : HashValue -->
	<input  type="hidden" name="signature" id="signature" value="${param_signature}" >
	
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
		var sendfrm = document.getElementById("SendMemregForm_id");
	
		sendfrm.action = "${requestURL}"; 
		sendfrm.submit();
	}
-->	
</script>
