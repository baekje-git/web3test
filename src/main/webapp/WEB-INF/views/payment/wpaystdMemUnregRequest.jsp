<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>WPAY 표준 회원서비스 해지 요청</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		body { background-color: #efefef;}
		body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
		table, img {border:none}
	</style>
</head>

<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 >
	<div style="background-color:#f3f3f3;width:100%;font-size:13px;color: #ffffff;background-color: #000000;text-align: center">
		WPAY 표준 회원서비스 해지 결과
	</div>
<table width="450" border="0" cellspacing="0" cellpadding="0" style="padding:10px;" align="center">
		<tr>
			<td bgcolor="6095BC" align="center" style="padding:10px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="padding:20px">

					<tr>
						<td >
							<span style="font-size:20px"><b>WPAY 표준 회원서비스 해지 결과 파라미터 정보</b></span><br/>
						</td>
					</tr>
					<tr >
						<td >
							<table>
								<tr>
									<td style="text-align:left;">
										<br/><b>************** 결과 파라미터 **************</b>
										<div style="border:2px #dddddd double;padding:10px;background-color:#f3f3f3;">
												
											<br/><b>resultCode</b>
											<br/><input  style="width:100%;" name="resultCode" value="${resultCode}" >

											<br/><b>resultMsg</b>
											<br/><input  style="width:100%;" name="resultMsg" value="${resultMsg}" >

											<br/><b>wtid</b>
											<br/><input  style="width:100%;" name="wtid" value="${wtid}" >

											<br/><b>userId</b>
											<br/><input  style="width:100%;" name="userId" value="${userId}" >

											<br/><b>wpayUserKey</b>
											<br/><input  style="width:100%;" name="wpayUserKey" value="${wpayUserKey}" >
											
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
</body>
</html>
