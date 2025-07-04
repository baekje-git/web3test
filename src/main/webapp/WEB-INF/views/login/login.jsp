<%@page import="ever.cmmncode.domain.CacheCode"%>
<%@page import="ever.cmmncode.domain.CacheCodeGroup"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<title>ibsheet8 로그인화면 </title>
<head>
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/script-index.jsp" %>
    <link type="text/css" rel="stylesheet" href="<c:url value='/common/css/leftMenu-w3.css'/>"/>
	<!-- 샘플용 스타일 -->
	 <style>
	  body {
	/*     width: 1200px; */
	    width: 1400px; /* 이후 주석처리해야함 */
	    text-align: center;
	  }
	  
	  #search-wrap{
	    padding: 17px 0 0 20px;
	    text-align: left;
	    float: left;
	  }
	  #search-wrap input{
	    height: auto;
	    line-height: normal;
	    padding: .8em .5em;
	  }
	  
	  .btnCls{
	    text-align: right;
	    padding-top: 20px;
	  }
	  
	  #sheetDiv{
	    text-align: center;
	  }
	  
	 .ibsheet-wrap {
	    padding: 20px;
	    background-color: #fff;
	    border-radius: 7px;
	    box-shadow: 0px 4px 10px 0px rgba(105, 105, 188, 0.16);
	    width: 100%;
	    margin-bottom: 16px;
	 }
	</style>
	
	<script type="text/javascript">
	$(document).ready(function() {	    
	    $('#btn_login').focus();
	});
	
	
	</script>    
</head>

<body>

    <!-- 왼쪽 영역  -->
    <div style="float:left; width:80%; margin-left:250px; margin-top:100px; border: 1px solid #c5c5c5; background-color: #e5f0f2;">
        <div style="text-align:center; padding-left:10px; border-bottom:1px solid #c5c5c5;">
            <h3>로그인 샘플 페이지 </h3>            
        </div>
            
        <!-- 검색 영역  -->
        <div style="padding:10px;text-align:left">
        <form action="/login.do" method="post">
            <table>            
	           <tr>          
                    <td style="width:30%">아이디 :  <input type="text" name="id" value="admin"></td>
                    <td style="width:30%">패스워드 : <input type="password" name="pw" value="test123"> </td>
                    <td style="width:40%">
                        <button style="width:50%" id="btn_login"> 로그인 (바로클릭하세요)</button>                    
                    </td>                     
               </tr>
            </table>	
        </form>		        
        </div>
   </div>
 
</body>

</html>
