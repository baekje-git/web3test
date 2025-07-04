<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script>
		$(document).ready(function() {
			fn_currPageClassOn();
		});
		
		function fn_currPageClassOn(){
			var menu_list = document.querySelectorAll('nav')[0].querySelectorAll('a');
			var currPathname = location.pathname+location.search;
			var isMenu = false;
			
			for(var i=0; i<menu_list.length; i++){
				if(menu_list[i].pathname == currPathname){
					$("#"+menu_list[i].id).removeClass("w3-padding-menu");
					$("#"+menu_list[i].id).addClass("w3-red w3-padding");
					$("#"+menu_list[i].id).append("<span class='w3-tag w3-red w3-round w3-right'>&#8730;</span>");
					isMenu = true;
					return;
				}
			}
			
			if(!isMenu){
                $("#menu_01").addClass("w3-red w3-padding");
                $("#menu_01").append("<span class='w3-tag w3-red w3-round w3-right'>&#8730;</span>");			    
			}
		}
		
	</script>
	
	<nav class="w3-sidenav w3-light-grey" style="width:230px;border:1px solid #dcdcdc;">
		<div class="w3-container w3-dark-grey" style="height:100px">
			<div style="float:left;color:#f04145;font-size:30px;font-weight:600;padding-top:32px;padding-right:10px;padding-left:16px;">IBSheet</div>
			<div style="float:left;color:#8ec2e2;font-size:48px;font-family:fantasy;padding-top:19px;">8</div>
		</div>
	
		<div style="background-color:#39c4d7;height:50px;width:100%;border:0px;">
			<div style="padding-top: 10px;text-align: center;color: #fff;font-size: 20px;">Template</div>
		</div>
<%-- 		<a class="w3-red w3-padding" href='<c:url value="/ibsheetSampleCRUD.do"/>'>Home</a> --%>
		<a id="menu_01" class="w3-padding-menu" href='<c:url value="/sample/sample.do"/>'>기본 조회/저장 예제</a>
		<a id="menu_02" class="w3-padding-menu" href='<c:url value="/sample/dataType.do"/>'>데이터 타입 예제</a>
		<a id="menu_03" class="w3-padding-menu" href='<c:url value="/sample/sumType.do"/>'>합계/소계 예제</a>
		<a id="menu_04" class="w3-padding-menu" href='<c:url value="/sample/tree.do"/>'>트리 데이터 예제</a>
		<a id="menu_05" class="w3-padding-menu" href='<c:url value="/sample/serverPaging.do"/>'>서버페이징 조회 예제</a>
		<a id="menu_06" class="w3-padding-menu" href='<c:url value="/sample/excelUpDown.do"/>'>엑셀 업/다운로드 예제</a>
<%-- 		<a id="menu_07" class="w3-padding-menu" href='<c:url value="/sample/excelUpDown2.do"/>'>엑셀 업/다운로드(Direct)) 예제</a> --%>
		<a id="menu_08" class="w3-padding-menu" href='<c:url value="/sample/solid.do"/>'>솔리드 기능 예제</a>
		<a id="menu_09" class="w3-padding-menu" href='<c:url value="/sample/multiselect.do"/>'>select 기능 예제</a>
		<a id="menu_10" class="w3-padding-menu" href='<c:url value="/sample/ibTab.do"/>'>IBTab 예제</a>
		<a id="menu_11" class="w3-padding-menu" href='<c:url value="/sample/calendar.do"/>'>달력 예제</a>
	
	    
        <a id="menu_12" class="w3-padding-menu" href='<c:url value="/cmmnCode/codeManagement.do"/>'>공통코드 관리 예제</a>
        <a id="menu_13" class="w3-padding-menu" href='<c:url value="/cmmnCode/cacheList.do"/>'>캐쉬코드 조회 예제</a>
        <a id="menu_14" class="w3-padding-menu" href='<c:url value="/dynamicDbConnection/dbConnectionList.do"/>'>DB 동적연결정보 설정 예제</a>
        <a id="menu_15" class="w3-padding-menu" href='<c:url value="/dbsession/connectionSessionList.do"/>'>DB 세션 연결 목록 조회  예제</a>
        
        <a id="menu_15" class="w3-padding-menu" href='<c:url value="/sample/webEditor.do"/>'>웹에디터샘플</a>
        
        <a id="menu_16" class="w3-padding-menu" href='<c:url value="/logout.do"/>'>[로그아웃]</a>
		
	
<!-- 		<div class="w3-container w3-section" style="position:absolute;bottom:5px;"> -->
<!-- 			<div class="w3-border w3-round w3-padding w3-blue-grey"> -->
<!-- 				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&amp;times</span> -->
<!-- 				<p>Copyright 2020.<br/>IB Leaders All rights reserved.</p> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</nav>