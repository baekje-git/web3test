<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<title>sample page</title>
<head>
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/script-index.jsp" %>
    
    <link type="text/css" rel="stylesheet" href="<c:url value='/common/css/leftMenu-w3.css'/>"/>
</head>

<body>
<%@ include file="/WEB-INF/views/include/leftMenu.jsp" %>
    <div style="margin-left:235px;margin-right:5px;">
        <div id = "search-wrap">
           <form id = "searchFrm">
              <input type="text" name="searchTitle" placeholder="타이틀 검색"/>
           </form>
        </div>
        <div class="btnCls">
           <button type="button" class="ui-button" id="btnSearchMenu">메뉴데이터조회</button>
           <button type="button" class="ui-button" id="btnSearchSDK_SMS_REPORT">문자발송결과조회</button>
           <button type="button" class="ui-button" id="btnSaveA">저장(공통 Controller)</button>
           <button type="button" class="ui-button" id="btnSaveB">저장(개별 Controller)</button>
           <button type="button" class="ui-button" id="btnAdd">추가</button>
           <button type="button" class="ui-button" id="btnDel">삭제</button>
           <button type="button" class="ui-button" id="btnException">오류</button>
        </div>
        <hr>
        
    </div>
</body>


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
	fnInitPageLoad();
    fnInitPageEvnt();
});

function fnInitPageLoad() {
    
}

function fnInitPageEvnt() {
    
	$('#btnSearchMenu').on('click',function(){
		var vReqUrl  = "<c:url value='/bindSELECT/sample/selectMenu.ajax'/>";
	    var params = $('#searchFrm').serializeObject();
	    
	    CmmnUtil.ajax.post(vReqUrl, params, function(rtnData) {
	        console.log("메뉴조회",rtnData);	        
	    });
	});
    
	$('#btnSearchSDK_SMS_REPORT').on('click',function(){
        var vReqUrl  = "<c:url value='/bindSmsSELECT/sample2/selectSDK_SMS_REPORT.ajax'/>";
        var params = $('#searchFrm').serializeObject();
        
        CmmnUtil.ajax.post(vReqUrl, params, function(rtnData) {
            console.log("문자발송결과조회",rtnData);            
        });
    });
}

//샘플 데이터 조회(공통 서비스 호출)
function fnSearchListCommon(){
    var vReqUrl  = "<c:url value='/bindSELECT/sample/selectSampleList.ajax'/>";
    var params = $('#searchFrm').serializeObject();
    
    CmmnUtil.ajax.post(vReqUrl, params, function(rtnData) {
        console.log(JSON.stringify(rtnData));
        sheetA.loadSearchData(rtnData);
    });
}

//샘플 데이터 조회(별도 서비스 생성)
function fnSearchListController(){
    var vReqUrl  = "<c:url value='/sample/selectSampleList.ajax'/>";
    var params = $('#searchFrm').serializeObject();
    
    CmmnUtil.ajax.post(vReqUrl, params, function(rtnData) {
        console.log(JSON.stringify(rtnData));
        sheetA.loadSearchData(rtnData);
    });
}

//샘플 데이터 저장(공통 서비스 호출)
function fnSaveListCommon(){
  var gridData = CmmnUtil.getJsonObj(sheetA);
  if(gridData.data.length == 0){
      alert("저장할 내용이 없습니다.");
      return;
  }
  
  var vParams = {
          gridData : gridData.data
  }
  
  CmmnUtil.ajax.post("/bindSAVE/sample/saveSample/deleteSample.ajax", 'paramData='+JSON.stringifyEnc(vParams), function(data) {
      var CODE    = data.RESULT.CODE;
      var MESSAGE = data.RESULT.MESSAGE;
      alert(MESSAGE);
      if(CmmnUtil.isEquals(CODE,"SUCCESS")) {
          fnSearchListCommon();
      }
  });
  
}

//샘플 데이터 저장(별도 서비스 생성)
function fnSaveListController(){
    var gridData = CmmnUtil.getJsonObj(sheetA);
    if(gridData.data.length == 0){
        alert("저장할 내용이 없습니다.");
        return;
    }
    
    var vParams = {
            gridData : gridData.data
    }
    
    CmmnUtil.ajax.post("/sample/saveSample.ajax", 'paramData='+JSON.stringifyEnc(vParams), function(data) {
        var CODE    = data.RESULT.CODE;
        var MESSAGE = data.RESULT.MESSAGE;
        alert(MESSAGE);
        if(CmmnUtil.isEquals(CODE,"SUCCESS")) {
            fnSearchListController();
        }
    });
}

//샘플 데이터 저장(공통 서비스 호출)
function fnException(){
    var vParams;
    
CmmnUtil.ajax.post("/sample/ajaxException.ajax", 'paramData='+JSON.stringifyEnc(vParams), function(data) {
    alert(data);
    var CODE    = data.RESULT.CODE;
    var MESSAGE = data.RESULT.MESSAGE;
    if(CmmnUtil.isEquals(CODE,"SUCCESS")) {
    }
});

}

</script>
</html>
