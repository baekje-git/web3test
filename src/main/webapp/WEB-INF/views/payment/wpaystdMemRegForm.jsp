<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Arrays" %>

<%
    /****************************************************************************************************
    * WPAY 표준 회원가입 페이지
    *****************************************************************************************************/
    /*
    HttpSession session1 = request.getSession();
	session1.setAttribute("P_CUST_CD", request.getParameter("custCd"));
	session1.setAttribute("P_BRCH_CD", request.getParameter("brchCd"));
 	Map<String, String[]> arr = request.getParameterMap();

    for (String a : arr.keySet()) {
        
        System.out.println(Arrays.toString(arr.get(a)));
    } */
%>

<!DOCTYPE html>
<html>
<head>
    <title>WPAY 표준 회원가입 정보입력</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
        body { background-color: #ffffff;}
        body, tr, td {font-size:9pt; font-family:굴림,verdana; color:#ffffff; line-height:0px;}
        table, img {border:none}
    </style>
</head>
<script language="javascript">
<!--
    function goNext(frm)
    {
        var formParam = document.getElementById("SendMemregForm");
        var url = "/payment/wpaystdMemRegRequest";        
        //MakeNewWindow(frm, url);
        MakeNewWindow(formParam, url);
    }
        
    function MakeNewWindow(frm, url)
    {
        var IFWin;
        var OpenOption = 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=450,height=650,top=100,left=400,';
        IFWin=window.open('', 'IfWindow' ,OpenOption);
    
        frm.action = url;
        frm.target = "IfWindow";
        frm.method = "POST";
        frm.submit();
    
        IFWin.focus();
    }
    -->
</script>

<body bgcolor="#FFFFFF" text="#FFFFFF" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0>
<form id="SendMemregForm" name="SendMemregForm" method="POST" >
    <input  type="hidden" name=CARD_GB id="CARD_GB" value="${CARD_GB}" >

    <input  type="hidden" name=custCd id="custCd" value="${custCd}" >
    <input  type="hidden" name=cardGb id="cardGb" value="${cardGb}" >
    <input  type="hidden" name=amountTi id="amountTi" value="${amountTi}" >

    <input  type="hidden" name=brchCd id="brchCd" value="${brchCd}" >
    <input  type="hidden" name=requestDomain id="requestDomain" value="${requestDomain}" >
    <input  type="hidden" name=g_HASHKEY id="g_HASHKEY" value="${g_HASHKEY}" >
    <input  type="hidden" name=g_SEEDKEY id="g_SEEDKEY" value="${g_SEEDKEY}" >
    <input  type="hidden" name=g_SEEDIV id="g_SEEDIV" value="${g_SEEDIV}" >
   
    <table width="0" border="0" cellspacing="0" cellpadding="0" style="padding:10px;" align="center">
        <tr>
            <td align="center" style="padding:10px">
                <table width="0" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="padding:20px">

                    <tr>
                        <td>
                            <table >
                                <tr>
                                    <td style="text-align:left;">
                                        
                                        <br/>
                                        <!-- b>***** 필 수 *****</b> -->
                                        <div style="border:0px #ffffff double;padding:10px;background-color:#ffffff;display:none">
                                            <br /><b>mid</b> : 가맹점 ID
                                            <br /><input  class="input" style="width:100%;color:gray;" name="mid" id="mid" value="${g_MID}"  readOnly>
                                                
                                            <br /><b>userId</b> : 가맹점 고객 ID
                                            <br /><input  class="input" style="width:100%;" name="userId" id="userId" value="${param_userId}" >
                                            
                                            <br /><b>returnUrl</b> : 회원가입 결과전달 URL
                                            <br /><input  class="input" style="width:100%;color:gray;" name="returnUrl" id="returnUrl" value="${strCurrentDomain}/wpaystdMemRegReturn?custCd=${custCd}&cardGb=${cardGb}&amountTi=${amountTi}"  readOnly>
                                        </div>
                                        <br /><br />
                                        
                                        <!-- <b>***** 옵션 *****</b> -->
                                        <div style="border:0px #ffffff double;padding:10px;background-color:#ffffff;display:none;">
                                        
                                            <br /><b>ci</b> : 고객의 CI
                                            <br /><input  class="input" style="width:100%;" name="ci" id="ci"  value="" >
                                            
                                            <br /><b>userNm</b> : 고객실명 (CI 입력시 필수값)
                                            <br /><input  class="input" style="width:100%;" name="userNm" id="userNm" value="" >
                                            
                                            <br /><b>hNum</b> : 고객 휴대폰번호 (CI 입력시 필수값)
                                            <br /><input  class="input" style="width:100%;" name="hNum" id="hNum" value="" >
                                            
                                            <br /><b>hCorp</b> : 휴대폰 통신사 (CI 입력시 필수값)
                                            <br />('SKT', 'KTF', 'LGT', 'SKR':SKT알뜰폰, 'LGR':LGT알뜰폰, 'KTR':KT알뜰폰)
                                            <br />
                                            <select  name="hCorp" id="hCorp" style="width:100%;" onchange="">
                                                <option value="">통신사</option>
                                                <option value="SKT">SKT</option>
                                                <option value="KTF">KT</option>
                                                <option value="LGT">LGT</option>
                                                <option value="SKR">SKT 알뜰폰</option>
                                                <option value="KTR">KT 알뜰폰</option>
                                                <option value="LGR">LGT 알뜰폰</option>
                                            </select>
                                            
                                            <br /><b>birthDay</b> : 고객 생년월일(yyyymmdd) (CI 입력시 필수값)
                                            <br /><input  class="input" style="width:100%;" name="birthDay" id="birthDay" value="" >
                                            
                                            <br /><b>socialNo2</b> : 주민번호 뒤 첫자리 (CI 입력시 필수값)
                                            <br /><input  class="input" style="width:100%;" name="socialNo2" id="socialNo2">
                                            
                                            <br /><b>frnrYn</b> : 외국인여부(Y:외국인,N:내국인) (CI 입력시 필수값)
                                            <br /><input  class="input" style="width:100%;" name="frnrYn" id="frnrYn" value="" >
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
	//gopage();
	function gopage() {
	    var msg  = "비회원으로 간편결제를 이용하시려면\n";
	    msg += "최초 1회 회원가입이 필요합니다.\n";
	    msg += "회원 가입하시겠습니까?\n";
	    if (!confirm(msg)) {
	        close();
	        return;
	    } else {
	        goSkip();
	    }
	    
	}
	goSkip();
	function goSkip()
	{
		var formParam = document.getElementById("SendMemregForm");
	    var url = "/payment/wpaystdMemRegRequest";
	    
	    //MakeNewWindow(frm, url);
	    MakeNewWindow(formParam, url);
	}
--> 
</script>
