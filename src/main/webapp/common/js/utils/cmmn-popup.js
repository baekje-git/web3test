$(function(){
    var PopUtil = {
        init: function(){
            return this;
        },
        
        /*****************************************************************************************
         * 공통업무팝업
         * PopUtil.cmmnOpenPopup(popupType, sendParam, returnFun);
         * return : object
         ****************************************************************************************/
        cmmnOpenPopup: function(popupType, sendParam ,returnFun) {
            console.log('@ POPUTIL.CMMN OPEN POPUP');
            console.log('popupType : ' , popupType);
            console.log('sendParam : ' , sendParam);
            console.log('returnFun : ' , returnFun);
            if( CmmnUtil.isEmpty(popupType)) return false;
            if(!CmmnUtil.isEmpty(sendParam) && !$.isPlainObject(sendParam)) return false;
            if(CmmnUtil.isEmpty(returnFun)) {
                returnFun = {POP_CALLBACK:""};
            }
            
            var popInfo = new Object();
            switch (popupType) {
                // -----------------------------------------------
                // 거래처
                // -----------------------------------------------
            case "CUST_POPUP"      : 
                popInfo.url    = "/bindURL/popup/popCustInfo.do";
                popInfo.w      = 700;
                popInfo.h      = 550;
                popInfo.name   = popupType;
                popInfo.params = $.extend(sendParam,returnFun);
                break;
                // -----------------------------------------------
                // 제조사
                // -----------------------------------------------
            case "MAKER_POPUP"     : 
                popInfo.url    = "/bindURL/popup/popMakerInfo.do";
                popInfo.w      = 700;
                popInfo.h      = 550;
                popInfo.name   = popupType;
                popInfo.params = $.extend(sendParam,returnFun);
                
                break;
                // -----------------------------------------------
                // 카테고리
                // -----------------------------------------------
            case "CATE_POPUP"     : 
                popInfo.url    = "/bindURL/popup/popCateInfo.do";
                popInfo.w      = 750;
                popInfo.h      = 550;
                popInfo.name   = popupType;
                popInfo.params = $.extend(sendParam,returnFun);
                
                break;
                
                
            default :
                alert("설정하신 팝업타입이 존재하지않습니다.");
                return null;
                break;
            }
            
            return PopUtil.openPopup(popInfo.url, popInfo.w, popInfo.h, popInfo.name, popInfo.params);
        },
        
        
        /*****************************************************************************************
         * 공통팝업
         * PopUtil.openPopup(url, w, h, name, option);
         * return : void
         ****************************************************************************************/
        openPopup: function(url, w, h, name, params) {
            console.log('@ POPUTIL.OPEN POPUP');
            
            // OPTION
            // ----------------------------------------------------------------------------------
            var pozX, pozY;
            var sw     = w / 2;
            var sh     = h / 2;
            var scroll = 0;
            pozX = (window.screen.width  / 2) - (w / 2);
            pozY = (window.screen.height / 2) - (h / 2);
            
            var opt  = "";
                opt += "width=" + w + ",height=" + h + ",left=" + pozX + ",top=" + pozY;
                opt += "toolbar=no,location=no,status=no,menubar=no,resizable=no,scrollbars=yes,modal=no,channelmode=no";
                
            // TARGET OPEN
            // ----------------------------------------------------------------------------------
            var gvPopId      = window.top.gvPopId;
            var gvPopIdArray = window.top.gvPopIdArray;
            
            gvPopId = window.open("", name, opt);
            
             //window.top.gvPopId.closed ? window.top.gvPopId = "" : window.top.gvPopId.focus();
             //window.top.gvPopIdArray.push({popId: name , target: window.top.gvPopId});
            
            console.log('gvPopId      : ' ,  gvPopId);
            console.log('gvPopIdArray : ' ,  gvPopIdArray);
            
            // PARAMS
            // ----------------------------------------------------------------------------------
            var $tempFrm = $('<form/></form>',{id:'tempFrm', name:'tempFrm', action:'', target: '',method:'post'}).appendTo($(document.body));
            console.log("params : " , params);
            CmmnUtil.addParamToHiddenInput($(document.tempFrm), params);
            
            // SUBMIT
            // ----------------------------------------------------------------------------------
            document.tempFrm.action = url;
            document.tempFrm.target = name;
            document.tempFrm.submit();
            
            // RESET
            if($("#tempFrm").length > 0) $("#tempFrm").remove();
            if(!gvPopId.closed ) gvPopId.focus();
            return gvPopId;
        },
        
        /*****************************************************************************************
         * 
         * PopUtil.setData(rowData);
         * return : boolean
         ****************************************************************************************/
        //callFunc(CmmnUtil.isEmpty(params) ? iptObj : params);
        setData: function(rowData,callback) {
            var callback = window.opener[callback];
            try {
                //if(!$.isFunction(callback)) return false;
                callback(rowData);
                window.close();
            } catch (e) {
                alert(e);
            }
        },
        
        /*****************************************************************************************
         * 
         * PopUtil.onTabEvent(iptObj, event);
         * return : boolean
         ****************************************************************************************/
        onTabEvent : function(iptObj, event){
            var keyCode  = event.which ? event.which : event.keyCode;
            if(keyCode == 13) event.preventDefault(); return false;
            if(keyCode == 9 ){
                //console.log('@ onTabEvent - (keyCode : ',keyCode,')');
                //event.keyCode = 13;
                //$(iptObj).trigger($.Event("keyup", {keyCode:13}));
            }
            return false;
        },
        
        /*****************************************************************************************
         * 
         * PopUtil.onSearchPopEvent(iptObj, event);
         * return : boolean
         ****************************************************************************************/
        onSearchPopEvent : function (iptObj, event, paramObj){
            var keyCode  = event.which ? event.which : event.keyCode;
            if(keyCode == 13){
                //console.log('@ onSearchPopEvent - (keyCode : ',keyCode,')');
                var iptCd  = $(iptObj).val();
                var iptNm  = $(iptObj).closest("span").next().find("input").val();
                var objId  = iptObj.getAttribute('id');
                if(objId  == "CUST_CD" && !CmmnUtil.isEmpty(iptCd)) { 
                    var regExp = /^[0-9]*$/;
                    if(regExp.test(iptCd)) {
                        $(iptObj).val(iptCd.lpad(6,"0"));
                    }
                }
                
                if(!CmmnUtil.isEmpty(iptNm)) {
                    return false;
                } else {
                    
                    var $fromObj = $(iptObj).closest("form");
                    var vCodeVal = $(iptObj).val();
                    var pType    = paramObj.pType;
                    
                    if(pType == "CUST" && vCodeVal.length == 6) {
                        this.onblurEventByName(iptObj, event, paramObj);
                    } else {
                        var callfunc = paramObj.callFunc;
                        var params   = paramObj.params;
                        this.onKeyupEvent(iptObj, event, callfunc, params);
                    }
                    
                }
                
            } else if(event.keyCode == 8) {
                $(iptObj).trigger($.Event("change", {keyCode:8}));
            }
            return false;
        },
        
        /*****************************************************************************************
         * 
         * PopUtil.onKeyupEvent(iptObj, event, funcNm, method, params);
         * return : boolean
         ****************************************************************************************/
        onKeyupEvent : function (iptObj, event, callfunc, params){
            var keyCode  = event.which ? event.which : event.keyCode;
            var callFunc = window[callfunc];
            if(!$.isFunction(callFunc)) return false;
            
            if(keyCode == 13){
                //console.log('@ onKeyupEvent - (event.keyCode : ',event.keyCode,')');
                //console.log('params : ' , params);
                callFunc(CmmnUtil.isEmpty(params) ? iptObj : params);
            }
        },
        
        /*****************************************************************************************
         * 
         * PopUtil.onblurEventByName(iptObj, event, paramObj);
         * return : boolean
         ****************************************************************************************/
        onblurEventByName : function(iptObj, event, paramObj){
            var keyCode  = event.which ? event.which : event.keyCode;
            //console.log('@ onblurEventByName - (keyCode : ',keyCode,')');
            //console.log('paramObj : ' , paramObj);
            
            var pType    = paramObj.pType; 
            var $fromObj = $(iptObj).closest("form");
            var vCodeVal = $(iptObj).val();
            
            // 기타값 배열 설정
            var vArrTagetId = new Array();
            if(!CmmnUtil.isEmpty(paramObj.etcInput)){
                if(paramObj.etcInput.indexOf("@") == -1){
                    vArrTagetId.push(paramObj.etcInput);
                } else {
                    $.each($.unique(paramObj.etcInput.split("@")), function(idx, val){
                        vArrTagetId.push(val);
                    });
                }
            }
            
            
            switch(CmmnUtil.getDefaultString(pType)){
            case "CUST" : 
                // 거래처 
                // --------------------------------------------------------------------------------------------
                var params = {
                    TXT_CUST_CD : vCodeVal
                };
                CmmnUtil.ajax.getNotLoad("/bindSELECT/popup/selectPopCustInfoList.ajax", params, function(data){
                    if(data == null || data.length == 0) {
                        //console.log('검색된 거래처가 없습니다.');
                        var callfunc = paramObj.callFunc;
                        var params   = paramObj.params;
                        PopUtil.onKeyupEvent(iptObj, event, callfunc, params);
                        return;
                    }
                    
                    //setTimeout(function(){},0);
                    var dataMap = data[0];
                    $(iptObj).blur().val(dataMap.CUST_CD).focus();
                    $("#"+paramObj.pName , $fromObj).val(dataMap.CUST_NM);
                    
                    // 기타 명칭 입력
                    $(vArrTagetId).each(function(idx, val){
                        $.each(dataMap, function(mapIdx, mapVal){
                            if(val.indexOf(mapIdx) > -1){
                                $("#" + val, $fromObj).val(mapVal);
                            }
                        });
                    });
                    
                    var pRetnFunc = paramObj.retnFunc;
                    if(!CmmnUtil.isEmpty(pRetnFunc)) {
                        var retnFunc  = window[pRetnFunc];
                        var dataMap  = data[0];
                        retnFunc(dataMap);
                    }
                    //try {} catch (e) {alert(e);}
                });
                break;
            }
            
        },
        /*****************************************************************************************
         * 
         * PopUtil.onChangeClearEvent(iptObj, event, targetId);
         * return : boolean
         ****************************************************************************************/
        onChangeClearEvent : function (iptObj, event, targetId){
            var keyCode  = event.which ? event.which : event.keyCode;
            console.log('@ onChangeClearEvent - (keyCode : ',keyCode,')');
            var $fromObj = $(iptObj).closest("form");
            
            //if(keyCode != 8) return false;
            //var targetId = paramObj.etcInput;
            var arrayTargetId = $.unique(targetId.split("@"));
            console.log("arrayTargetId : " , arrayTargetId);
            $(arrayTargetId).each(function(i,v){
                //if(i==0 && !CmmnUtil.isEmpty(v)) return false;
                $fromObj.find("#"+this).val("");
            });
        },
        
        /*****************************************************************************************
         * 
         * PopUtil.onClickEvent(iptObj, callFunc, params);
         * return : boolean
         ****************************************************************************************/
        onClickEvent : function (iptObj, callFunc, params){
            //console.log('@ onClickEvent');
            var callFunc = window[callFunc];
            if(!$.isFunction(callFunc)) return false;
            //console.log('params   : ' , params);
            callFunc(CmmnUtil.isEmpty(params) ? iptObj : params);
        },
        
        /*****************************************************************************************
         * 우편번호검색
         * PopUtil.postCodePopup(callback);
         * return : void
         ****************************************************************************************/
        postCodePopup : function(callback) {
            var width  = 500; //팝업의 너비
            var height = 600; //팝업의 높이
            new daum.Postcode({
                oncomplete: function(data) {
                    console.log('--- oncomplete  ---');
                    
                    // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var fullRoadAddr  = data.roadAddress;   // 도로명 주소 변수
                    var extraRoadAddr = '';                 // 도로명 조합형 주소 변수
                    var expRoadAddr   = '';                 // 예상 도로명주소
                    var expJibunAddr  = '';                 // 예상 지번주소

                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraRoadAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                       extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraRoadAddr !== ''){
                        extraRoadAddr = ' (' + extraRoadAddr + ')';
                    }
                    // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
                    if(fullRoadAddr !== ''){
                        fullRoadAddr += extraRoadAddr;
                    }
                    
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
                    // 예제를 참고하여 다양한 활용법을 확인해 보세요.
                    if($.isFunction(callback)) {
                        callback(data);
                    }
                    
                }, 
                onclose: function(state) { 
                    //console.log('----- onclose  ----');
                    //console.log('>> state : ' , state);
                    
                },
                onresize: function(size) {
                    //console.log('----- onresize ----');
                    //console.log('>> size  : '  , size);
                },
                theme: {
                    searchBgColor : "#2A96E6", //검색창 배경색
                    queryTextColor: "#FFFFFF"  //검색창 글자색
                }
            }).open({
                popupName : 'postCodePopup',
                autoClose : true,
                left : (window.screen.width  / 2) - (width  / 2),
                top  : (window.screen.height / 2) - (height / 2)
                });
        },
        
        
        
        /*****************************************************************************************
         * 
         * PopUtil.openHelpLink(time);
         * return : 
         ****************************************************************************************/
        openHelpLink : function(menuId){
            console.log('openHelpLink : ' , menuId);
            alert("@ 메뉴코드 : " + menuId);
        },
        
        /*****************************************************************************************
         * 
         * PopUtil.cmmnLayerPopup(sendURL);
         * return : 
         ****************************************************************************************/
        cmmnLayerPopup : function(sendURL) {
            console.log('cmmnLayerPopup');
            
            var $layer = $("#layer-default",top.document);
            var isDim  = $layer.prev().hasClass('dimBg');
            $("#layer-iframe",top.document).prop("src",sendURL);
            $("#layer-iframe",top.document).on('load',function() {
                
                isDim ? $('.dim-layer',top.document).show() : $layer.show();
                
                var $elWidth  = $layer.outerWidth();
                var $elHeight = $layer.outerHeight();
                var docWidth  = $(document).width();
                var docHeight = $(document).height();
                
                if ($elHeight < docHeight || $elWidth < docWidth) {
                    $layer.css({marginTop  : -$elHeight/2,marginLeft : -$elWidth /2});
                } else {
                    $layer.css({top: 0, left: 0});
                }
            });
            
            // 닫기
            $layer.find('a.btn-layerClose',top.document).on("click",function(){
                //console.log('-- a.btn-layerClose --');
                $("#iframe-layer",top.document).prop("src","");
                isDim ? $('.dim-layer',top.document).fadeOut() : $layer.fadeOut();
                return false;
            });

            $('.layer .dimBg',top.document).on("click",function(){
                //console.log('-- .layer .dimBg--');
                $("#layer-iframe",top.document).prop("src","");
                $('.dim-layer',top.document).fadeOut();
                return false;
            });
        }
    };
    window.PopUtil = PopUtil.init();
});
