<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////
    [VARIABLE] -->
    <jsp:useBean id="nowDate" class="java.util.Date" />
    <fmt:formatDate var="ver" value="${nowDate}" pattern="yyyyMMddHHmmss" />
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////
    [INCLUDE] -->
    
    <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////
    [WEB-SITE] -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/css/common.css?ver=${ver}'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/css/jquery-ui.css?ver=${ver}'/>" />
    
 