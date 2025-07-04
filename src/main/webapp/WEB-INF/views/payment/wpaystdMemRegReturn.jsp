<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>WPAY 표준 회원가입 결과</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body { background-color: #ffffff;}
		body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#ffffff; line-height:19px;}
		table, img {border:none}
	</style>
</head>

<body bgcolor="#FFFFFF" text="#ffffff" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 >
<form id="SendPayregCardForm" name="SendPayregCardForm" method="POST" >
	<table width="450" border="0" cellspacing="0" cellpadding="0" style="padding:10px;" align="center">
		<tr>
			<td bgcolor="ffffff" align="center" style="padding:10px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="padding:20px">

					<tr>
						<td >
							<!-- span style="font-size:20px"><b>WPAY 표준 회원가입 결과 파라미터 정보</b></span><br/> -->
						</td>
					</tr>
					<tr >
						<td >
							<table>
								<tr>
									<td style="text-align:left;">
										
											
											<div style="border:2px #ffffff double;padding:10px;background-color:#ffffff;display:none;">
												
												<br/><b>resultCode</b>
												<br/><input  style="width:100%;" name="resultCode" value="${param_resultCode}" >

												<br/><b>resultMsg</b>
												<br/><input  style="width:100%;" name="resultMsg" value="${param_resultMsg}" >

												<br/><b>mid</b>
												<br/><input  style="width:100%;" name="mid" value="${param_mid}" >

												<br/><b>wtid</b>
												<br/><input  style="width:100%;" name="wtid" value="${param_wtid}" >

												<br/><b>userId</b>
												<br/><input  style="width:100%;" name="userId" value="${param_userId}" >

												<br/><b>wpayUserKey</b>
												<br/><input  style="width:100%;" name="wpayUserKey" value="${param_wpayUserKey}" >
												
												<br/><b>ci</b>
												<br/><input  style="width:100%;" name="ci" value="${param_ci}" >

												<br/><b>signature</b>
												<br/><input  style="width:100%;" name="signature" value="${param_signature}" >

                                                <br/><b>custCd</b>
                                                <br/><input  style="width:100%;" name="custCd" value="${custCd}" >

                                                <br/><b>cardGb</b>
                                                <br/><input  style="width:100%;" name="cardGb" value="${cardGb}" >

                                                <br/><b>amountTi</b>
                                                <br/><input  style="width:100%;" name="amountTi" value="${amountTi}" >

											</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>


<script language="javascript">
<!--
    gopage();
    function gopage() {

    	if ("${param_resultCode}" == "0000") {
    		goSkip();
    	} else {
    		var msg ="${param_resultCode}";
    		msg += "\n 관리자에게 문의하여 주십시오.";
    		close();
    		return;
    	}  	
    }
    
    function goSkip()
    {
    	var sendfrm = document.getElementById("SendPayregCardForm");
        sendfrm.action = "/payment/wpaystdCardRegRequest";
        sendfrm.submit();
    }
    
 
--> 
</script>