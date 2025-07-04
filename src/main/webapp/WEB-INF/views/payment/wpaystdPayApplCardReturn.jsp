<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!-- title>WPAY 표준 결제인증(신용카드) 결과</title-->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body { background-color: #ffffff;}
		body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#ffffff; line-height:19px;}
		table, img {border:none}
	</style>
</head>

<body bgcolor="#FFFFFF" text="#ffffff" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 >
	<div style="background-color:#ffffff;width:100%;font-size:13px;color: #ffffff;background-color: #ffffff;text-align: center;display:none;">
		WPAY 표준 결제인증(신용카드) 결과
	</div>
<form id="SendPayApplCardForm" name="SendPayApplCardForm" method="POST" >
	<table width="450" border="0" cellspacing="0" cellpadding="0" style="padding:10px;" align="center">
		<tr>
			<td bgcolor="ffffff" align="center" style="padding:10px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="padding:20px">

					<tr>
						<td >
							<!-- span style="font-size:20px"><b>WPAY 표준 결제인증(신용카드) 결과 파라미터 정보</b></span><br/-->
						</td>
					</tr>
					<tr >
						<td >
							<table>
								<tr>
									<td style="text-align:left;">
										
											<!-- br/><b>************** 결과 파라미터 **************</b-->
											<div style="border:2px #ffffff double;padding:10px;background-color:#ffffff;diplay:none;">
												
												<br/><b>resultCode</b>
												<br/><input  style="width:100%;" name="resultCode" value="${param_resultCode}" >

												<br/><b>resultMsg</b>
												<br/><input  style="width:100%;" name="resultMsg" value="${param_resultMsg}" >

												<br/><b>mid</b>
												<br/><input  style="width:100%;" name="mid" value="${param_mid}" >
												
												<br/><b>wtid</b>
												<br/><input  style="width:100%;" name="wtid" value="${param_wtid}" >

												<br/><b>oid</b>
												<br/><input  style="width:100%;" name="oid" value="${param_oid}" >
												
												<br/><b>wpayUserKey</b>
												<br/><input  style="width:100%;" name="wpayUserKey" value="${param_wpayUserKey}" >

												<br/><b>wpayToken</b>
												<br/><input  style="width:100%;" name="wpayToken" value="${param_wpayToken}" >

												<br/><b>wpayDomain</b>
												<br/><input  style="width:100%;" name="wpayDomain" value="${param_wpayDomain}" >

												<br/><b>signature</b>
												<br/><input  style="width:100%;" name="signature" value="${param_signature}" >

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
        //alert('${param_resultCode}');
        //alert('${param_resultMsg}');
        if ("${param_resultCode}" == "0000") {
            //alert('${param_resultMsg}');
            //alert("결제가 완료되었습니다.");
            
            
        } else {
            var msg ="${param_resultCode}";
            msg += "\n 관리자에게 문의하여 주십시오.";
        }
        
        //close();
        //return;
        
    }
  
--> 
</script>