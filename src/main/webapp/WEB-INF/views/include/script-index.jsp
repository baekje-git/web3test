<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <script type="text/javascript">
    //<![CDATA[
        var contextPath      = "${contextPath}";
        var systemCurrDate   = "${systemDate}";
    //]]>
    </script>
    <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////
    [J-QUERY] -->
    <script type="text/javascript" src="<c:url value='/common/js/jquery-3.3.1.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/common/js/jquery-ui.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/common/js/jquery.form.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/common/js/jquery-cookie.js'/>"></script>
    
    <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////
    [CUSTOM]  -->
    <script type="text/javascript" src="<c:url value='/common/js/utils/cmmn-config.js?ver=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/common/js/utils/cmmn-prototype.js?ver=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/common/js/utils/cmmn-message.js?ver=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/common/js/utils/cmmn-function.js?ver=${ver}'/>"></script>
    <%-- <script type="text/javascript" src="<c:url value='/common/js/utils/cmmn-validate.js?ver=${ver}'/>"></script> --%>
    <script type="text/javascript" src="<c:url value='/common/js/utils/cmmn-date.js?ver=${ver}'/>"></script>
    
    <script type="text/javascript">
    $(document).ready(function() {
    	
    });
    </script>