$(function(){
    
    $(document).ajaxStart(function(){
        CmmnUtil.loadingStart();
    }).ajaxComplete(function() {
    }).ajaxStop(function(){
         CmmnUtil.loadingEnd();
    }).ajaxError(function() {
        CmmnUtil.progressBar(1000);
    }); 
    
    var CmmnUtil = {
            
        init: function(){
            return this;
        },
        /***************************************************************************************
         * Description  : AJAX 비동기 통신
         * Argument     : 
         * Return       : 
         * Sample       : 
         ***************************************************************************************/
        ajax : {
            call : function(method, action, params, callback, ploadYn){
                //console.log('>>> ajax.call <<<');
                var resultData;
                
                var ploadYn  = CmmnUtil.isEmpty(ploadYn)  ? true  : false;
                var async    = CmmnUtil.isEmpty(callback) ? false : true;  //동기-비동기
                var params   = CmmnUtil.isEmpty(params)   ? null  : params;
                $.ajax({
                    type     : method,
                    url      : action,
                    data     : params,
                    async    : async,
                    cache    : false,
                    context  : this,
                    global   : ploadYn,
                    // -----------------------------------------------
                    // beforeSend
                    // -----------------------------------------------
                    beforeSend : function(jqXHR, opts) {
                        //console.log(">>> befSend <<<");
                        //console.log("jqXHR : " , jqXHR);
                    },
                    // -----------------------------------------------
                    // success
                    // -----------------------------------------------
                    success  : function(response, textStatus, jqXHR) {
                       //console.log(">>> success <<<");
                       //console.log("response : " , response);
                       if(!CmmnUtil.isEmpty(callback)){
                           callback(response);
                       } else {
                           resultData = response;
                       }
                       //jqXHR.done(function(response, textStatus, jqXHR ) {console.log("jqXHR.done : " + response);});
                       //jqXHR.fail(function( jqXHR, textStatus, errorThrown ) {console.log("jqXHR.fail : " + errorThrown);});
                       

                       
                    },
                    // -----------------------------------------------
                    // error
                    // -----------------------------------------------
                    error    : function(jqXHR, textStatus, errorThrown) {
                        CmmnUtil.progressBar(1000);
//                        console.log(">>> error   <<<");
//                        console.log("jqXHR.status     : " , jqXHR.status);
//                        console.log("jqXHR.statusText : " , jqXHR.statusText);
//                        console.log("errorThrown      : " , errorThrown);
                        if(jqXHR.status == CmmnConfig.HTTP_CODE.R401) {
                            // 로그인시간이 만료되었습니다
                            alert(MsgUtil.getMsg("SYS03"));
                            top.window.location.replace(CmmnConfig.PAGE.LOGIN);
                        } else if(jqXHR.status == 0){
                        } else if(jqXHR.status == CmmnConfig.HTTP_CODE.R500){
                            alert(jqXHR.responseJSON.RESULT.MESSAGE);
                        } else {
                            // 처리중 오류가 발생하셨습니다
                            alert(MsgUtil.getMsg("SYS02"));
                        }
                    }
                });
                //console.log('>> resultData : ' , resultData);
                return resultData;
            },
            
            multiCall : function(method, multipart, action, params, callback, ploadYn){
                
                $.ajax({
                    type        : method,
                    url         : action,
                    data        : params,
                    enctype     : multipart == true ? 'multipart/form-data' : '',
                    cache       : false,
                    processData : false,
                    contentType : false,
                    success     : function(response, textStatus, jqXHR) {
                        //console.log('response : ' ,response);
                        if(!CmmnUtil.isEmpty(callback)){
                            callback(response);
                        }
                        
                    },
                    error:function(jqXHR, textStatus, errorThrown){
                        CmmnUtil.progressBar(1000);
//                        console.log(">>> error   <<<");
//                        console.log("jqXHR.status     : " , jqXHR.status);
//                        console.log("jqXHR.statusText : " , jqXHR.statusText);
//                        console.log("errorThrown      : " , errorThrown);
                        if(jqXHR.status == CmmnConfig.HTTP_CODE.R401) {
                            // 로그인시간이 만료되었습니다
                            alert(MsgUtil.getMsg("SYS03"));
                            top.window.location.replace(CmmnConfig.PAGE.LOGIN);
                        } else if(jqXHR.status == 0){
                            
                        } else {
                            // 처리중 오류가 발생하셨습니다
                            alert(MsgUtil.getMsg("SYS02"));
                        }
                    }
                });
            },
            
            // AJAX GET  로딩바 사용
            /***************************************************************************************
             * Description  : 공통 AJAX GET 로딩바 사용
             * CmmnUtil.ajax.get(action, params, callback);
             ***************************************************************************************/
            get  : function(action, params, callback){
                return CmmnUtil.ajax.call(CmmnConfig.AJAX.TYPE.GET , action, params, callback);
            },
            // AJAX POST 로딩바 사용
            /***************************************************************************************
             * Description  : 공통 AJAX POST 로딩바 사용
             * CmmnUtil.ajax.get(action, params, callback);
             ***************************************************************************************/
            post : function(action, params, callback){
                return CmmnUtil.ajax.call(CmmnConfig.AJAX.TYPE.POST, action, params, callback);
            },
            
            // AJAX POST 로딩바 미사용
            /***************************************************************************************
             * Description  : AJAX POST 로딩바 미사용
             * CmmnUtil.ajax.postNotLoad(action, params, callback);
             ***************************************************************************************/
            postNotLoad : function(action, params, callback){
                return CmmnUtil.ajax.call(CmmnConfig.AJAX.TYPE.POST, action, params, callback, false);
            },
            /***************************************************************************************
             * Description  : AJAX GET 로딩바 미사용
             * CmmnUtil.ajax.getNotLoad(action, params, callback);
             ***************************************************************************************/            
            getNotLoad : function(action, params, callback){
                return CmmnUtil.ajax.call(CmmnConfig.AJAX.TYPE.GET, action, params, callback, false);
            },
            //AJAX (new FormData)
            /***************************************************************************************
             * Description  : AJAX FORMDATA 전송
             * CmmnUtil.ajax.form(action, params, callback);
             ***************************************************************************************/
            form : function(action, params, callback){
                return CmmnUtil.ajax.multiCall(CmmnConfig.AJAX.TYPE.POST, false, action, params, callback);
            },
            //AJAX (new FormData)
            /***************************************************************************************
             * Description  : AJAX FORMDATA 전송
             * CmmnUtil.ajax.form(action, params, callback);
             ***************************************************************************************/
            multi : function(action, params, callback){
                return CmmnUtil.ajax.multiCall(CmmnConfig.AJAX.TYPE.POST, true, action, params, callback);
            }
        },
        /***************************************************************************************
         * Description  : 로딩바 텝용
         * CmmnUtil.progressBar();
         * Return       : 
         * Sample       : 
         ***************************************************************************************/
        progressBar : function(ms){
            var agent = navigator.userAgent.toLowerCase();
            var name  = navigator.appName;
            //if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1) return false;
            $(".box_loading",top.document).css("display","block");
            if($.isNumeric(ms)){
                setTimeout(function(){
                     $(".box_loading",top.document).css("display","none");
                },ms);
            }
        },
        /***************************************************************************************
         * Description  : 로딩바 시작
         * CmmnUtil.loadingStart();
         * Return       : 
         * Sample       : 
         ***************************************************************************************/
        loadingStart : function(ms){
            var agent = navigator.userAgent.toLowerCase();
            var name  = navigator.appName;
            //if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1) return false;
            $(".box_loading",top.document).css("display","block");
            if($.isNumeric(ms)){
                setTimeout(function(){
                    CmmnUtil.loadingEnd();
                },ms);
            }
        },
        /***************************************************************************************
         * Description  : 로딩바 종료
         * CmmnUtil.loadingEnd();
         * Return       : 
         * Sample       : 
         ***************************************************************************************/
        loadingEnd : function(){
            var agent = navigator.userAgent.toLowerCase();
            var name  = navigator.appName;
            //if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1) return false;
            $(".box_loading",top.document).css("display","none");
        },
        
        /*****************************************************************************************
         * 공통코드를 배열로 반환
         * CmmnUtil.getCmmnCode(vObj);
         * return : array
         ****************************************************************************************/
        getCmmnCode : function(vObj,title){
            title = CmmnUtil.isEmpty(title) ? false : title; 
            var codeList = new Array();
            var codeData = CmmnUtil.ajax.post("/cmmn/selectCmmnCodeList.ajax",vObj);
            codeList.push({label: "선택" , value: ""});
            if(!CmmnUtil.isEmpty(codeData)) {
                $.each(codeData,function(i,v){
                    var codeId    = v.CODE_ID;
                    var codeNm    = v.CODE_NM;
                    codeList.push({label: title ? codeNm.concat('['+codeId+']'): codeNm , value: codeId});
                });
            }
            // console.log('>>> codeList : ' , codeList);
            return codeList;
        },
        
        /*****************************************************************************************
         * 임의의 코드를 배열로 반환
         * CmmnUtil.getCustomCode(vUrl,vObj);
         * return : array
         ****************************************************************************************/
        getCustomCode : function(vUrl,vObj){
            var codeList = new Array();
            var codeData = CmmnUtil.ajax.post(vUrl,vObj);
            codeList.push({label: "선택" , value: ""});
            if(!CmmnUtil.isEmpty(codeData)) {
                $.each(codeData,function(i,v){
                    var codeId    = v.CODE_ID;
                    var codeNm    = v.CODE_NM;
                    codeList.push({label: codeNm , value: codeId});
                });
            }
            return codeList;
        },
        
        /*****************************************************************************************
         * 전체 카테고리 코드 사용자태크 <tag:gateAll/>
         * CmmnUtil.getCmmnCateTagLib(url,elem,params);
         * return : array
         ****************************************************************************************/
        getCmmnCateTagLib : function(url,elem,params){
            //console.log('params : ' + params);
            var select     = $(elem);
            var selectNext = $(elem).next();
            var params = params;
            var url = url;
            var vParams = params||{};
            CmmnUtil.ajax.postNotLoad(url, params, function(data) {
                select.empty();
                select.append('<option value="" selected="selected">선택</option>');
                if(selectNext.length>0){
                    selectNext.empty();
                    selectNext.append('<option value="" selected="selected">선택</option>');
                }
                if(data.length==0) return false;
                $.each(data,function(i,v){
                    var codeId =  v.CODE_ID;
                    var codeNm =  "("+codeId+")"+v.CODE_NM;
                    select.append('<option value="'+ v.CODE_ID +'">' + codeNm + '</option>');
                });
            });
        },
        
        /*****************************************************************************************
         * 카테고리 코드
         * CmmnUtil.getCmmnCate(url,elem,params);
         * return : array
         ****************************************************************************************/
        getCmmnCate : function(url,elem,params){
            var select = $(elem);
            var params = params;
            var url = url;
            var vParams = params||{};
            CmmnUtil.ajax.postNotLoad(url, params, function(data) {
                select.empty();
                select.append('<option value="" selected="selected">선택</option>');
                if(data.length==0) return false;
                $.each(data,function(i,v){
                    select.append('<option value="'+ v.CODE_ID +'">' + v.CODE_NM + '</option>');
                });
            });
            
            select.off('change').on('change',function(e){
                var v = $(this).val();
                var selects = $(this).parent().find('select');
                var idx = selects.index(this);
                var len = selects.length;
                $.each(selects,function(i,v){
                    if(i>idx){
                        $(v).empty();
                        $(v).append('<option value="" selected="selected">선택</option>');
                    }
                });
                if(idx<len-1){
                    if(CmmnUtil.isEmpty($(selects[idx]).val())) return;
                    CmmnUtil.getCmmnCate(url,'#'.concat(selects[idx+1].id),{UPPERKIND:v});
                } 
            });
        },
        
        /***************************************************************************************
         * Description  : Context Path
         * CmmnUtil.getContextPath();
         * return : string
         ***************************************************************************************/
        getContextPath : function(){
            var hostIndex = location.href.indexOf( location.host ) + location.host.length;
            return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
        },
        
        /***************************************************************************************
         * Description  : actionEventKey
         * CmmnUtil.actionEventKey();
         ***************************************************************************************/
        actionEventKey : function(){
            $('input[type=text]:not(:disabled):not([readonly]):not(.hasDatepicker)').closest('span:not(.popup)').on({
                // ----------------------------------------------------------------------------
                // KEYDOWN
                // ----------------------------------------------------------------------------
                "keydown"  : function(e){
                },
                // ----------------------------------------------------------------------------
                // KEYPRESS
                // ----------------------------------------------------------------------------
                "keypress" : function(e){
                },
                // ----------------------------------------------------------------------------
                // KEYUP
                // ----------------------------------------------------------------------------
                "keyup"    : function(e){
                    CmmnUtil.actionKeyUpEvent(e);
                },
                // ----------------------------------------------------------------------------
                // FOCUS
                // ----------------------------------------------------------------------------
                "focus"    : function(e){
                },
                // ----------------------------------------------------------------------------
                // BLUR
                // ----------------------------------------------------------------------------
                "blur"     : function(e){
                }
            });
        },
        
        /*****************************************************************************************
         * keyup 시 input data 이벤트키 처리 
         * CmmnUtil.actionKeyUpEvent(e);
         * return : event
         ****************************************************************************************/
        actionKeyUpEvent : function(e){
            // console.log('키업 데이터키 액션이벤트');
            var keyCode  = e.which ? e.which : e.keyCode;
            var dataCode = e.target.getAttribute('data-key');
            
            if(!CmmnUtil.isEmpty(dataCode)){
                var dataArray = dataCode.split(',');
                $.each(dataArray,function(i,v){
                    switch (v) {
                    case "search" : // 엔터키조회
                        if(CmmnUtil.isEquals(keyCode,13)) $("#BTN_SEARCH").trigger("click");
                        break;
                    case "esc"    : // 팝업닫기
                        if(CmmnUtil.isEquals(keyCode,27)) {
                            if($(e.target).closest("div.popwrap").length > 0) {
                                window.close();
                            }
                        }
                        break;
                    default :
                        break;
                    } // end switch
                });
            }
        },
        
        /*****************************************************************************************
         * 빈값체크 
         * CmmnUtil.isEmpty(arg);
         * return : boolean
         ****************************************************************************************/
        isEmpty : function(arg){ 
            //if(typeof arg == "string" ) {arg = arg.trim();}
            if(typeof arg == "number" ) {arg = arg == 0 ? String(arg) : arg;}
            if(typeof arg == "boolean") {arg = String(arg);}
            if( arg == "" || arg == null || arg == undefined || arg == 'undefined' || (typeof arg == "object" && (!Object.keys(arg).length || arg.length == 0) ) ){
                return true;
            } else{
                return false; 
            } 
        },

        /*****************************************************************************************
         * 값은값체크 
         * CmmnUtil.isEquals(a,b);
         * return : boolean
         ****************************************************************************************/
        isEquals : function(a,b){
            if(a == b) {
                return true;
            } else {
                return false;
            }
        },
        
        /*****************************************************************************************
         * Null 체크후 default value 리턴
         * CmmnUtil.getDefaultString(a,b);
         * return : arg1
         ****************************************************************************************/
        getDefaultString: function (arg1, arg2){
            if(CmmnUtil.isEmpty(arg1)){
                if(!CmmnUtil.isEmpty(arg2)){
                    return arg2;
                }
                return "";
            }
            
            //int - boolean 형변환
            if(arg2 == "" || arg2 == null || arg2 == undefined){
                if($.isNumeric(arg2)){
                    return parseInt($.trim(arg1).replace(/[^0-9]/g, ''));
                }else if(typeof arg2 === "boolean"){
                    return (arg1 === true);
                }
            }
            return arg1;
        },
        
        /*****************************************************************************************
         *  자리숫앞에 0 채우기
         * CmmnUtil.addZero(x,n);
         * return : string
         ****************************************************************************************/
        addZero : function(x, n){
            while (x.toString().length < n) {
              x = "0" + x;
            }
            return x;
        },
        
        /*****************************************************************************************
         *  화면로딩후 입력박스에 포커스처리
         * CmmnUtil.setDefIptFocus();
         * return : void
         ****************************************************************************************/
        setDefIptFocus : function(){
            var firstInput = $(".search_area input[type=text]:not(:disabled):not([readonly]):not(.hasDatepicker):first");
            if(firstInput.length>0){return firstInput.focus();}
        },
        
        /*****************************************************************************************
         *  파일다운로드
         * CmmnUtil.attachFileDownload(fileNo);
         * return : void
         ****************************************************************************************/
        attachFileDownload : function(fileNo){
            if(CmmnUtil.isEmpty(fileNo)) return false;
            if(confirm(MsgUtil.getMsg("CFM15"))) {
                location.href = "/cmmnFile/attachFileDownload.do?FILE_NO="+fileNo;
            }
        },
        
        
        /*****************************************************************************************
         * Json Object 를 form binding 처리
         * CmmnUtil.setJsonToForm(pFrm, pJsonObj);
         * return : object
         ****************************************************************************************/
        setJsonToForm : function (pFrm, pJsonObj){
            var fvRtnData = {};
            $(pFrm)[0].reset();
            $.each(pJsonObj, function(id, value){
                if($(pFrm).find('input[name="'+id+'"], select[name="'+id+'"], textarea[name="'+id+'"], #'+id).length > 0){
                    var vObj = $(pFrm).find('input[name="'+id+'"], select[name="'+id+'"], textarea[name="'+id+'"], #'+id);
                    //checkbox 처리
                    if($(vObj).is(':checkbox')){
                        $(vObj).each(function(i){
                            //checkbox value 확인후 체크입력 처리.
                            if($(this).prop('defaultValue') == value){
                                $(this).prop('checked', true);
                                fvRtnData[$(this).attr('name')+''] = value;
                            }else{
                                if(value == "Y" || value == "1" || value == "M"){
                                    $(vObj).prop("checked",true);
                                } else {
                                    $(vObj).prop("checked",false);
                                }
                            }
                        });
                        //$(vObj).trigger("change"); 
                    }
                    //radio 처리
                    else if($(vObj).is(':radio')){
                        $(vObj).each(function(i){
                            //checkbox value 확인후 체크입력 처리.
                            if($(this).prop('defaultValue') == value){
                                $(this).prop('checked', true);
                                fvRtnData[$(this).attr('name')+''] = value;
                            }else{
                                $(this).prop('checked', false);
                            }
                        });
                        //$(vObj).trigger("change");  
                    }
                    //checkbox 외 form 처리.
                    else{
                        /*
                        if($(vObj).is('.hasDatepicker')){
                            if($(vObj.closest('span')).hasClass('date')) {
                                $(vObj).datepicker("setDate", new Date(value.toYmdFormat()));
                            } else if($(vObj.closest('span')).hasClass('year')){
                            } else if($(vObj.closest('span')).hasClass('month')){
                            }
                        } else {
                            $(vObj).val(value);
                            fvRtnData[$(this).attr('name')+''] = value;
                        }
                        */
                        $(vObj).val(value);
                        fvRtnData[$(this).attr('name')+''] = value;
                    }
                }
            });
            
            return fvRtnData;
        },
        
        /*****************************************************************************************
         * selLoadRecord  qcellId 정보 form 에 읽어오기 (권장)
         * CmmnUtil.selLoadRecord(newRow,formId,qcellId)
         * return : void
         ****************************************************************************************/
        selLoadRecord : function(newRow,formId,qcellId){
            console.log('--- CmmnUtil.selLoadRecord ---');
            if(newRow ==  0 || newRow == -1) return false;
            if(qcellId == "undefined" || qcellId == "" || qcellId == undefined) return false;
            if(formId  == "undefined" || formId  == "" || formId  == undefined) return false;
            if(!$("form[id="+formId+"]")[0])  return false;
            
            $("form[id="+formId+"]")[0].reset();
            var obj = qcellId.getRowData(newRow);
            console.log('>> obj : ' , obj);
            for (elem in obj) {
                var elemStr = "";
                var tempArr = elem.toLowerCase().split('_');
                for(var i=0; i< tempArr.length; i++){
                    if(i > 0){
                        elemStr += tempArr[i].substring(0,1).toUpperCase()+ tempArr[i].substring(1,tempArr[i].length);
                    } else {
                        elemStr += tempArr[i];
                    }
                }
                var inputNm = typeof $("#" + elem)[0] == "undefined" ? elemStr : elem;

                if ($("#" + inputNm)[0]) {
                    if($("#" + inputNm)[0].type == "checkbox"){
                        $("#" + inputNm).val(obj[elem]);
                        if(obj[elem] == "Y" || obj[elem] == "1" || obj[elem] == "M"){
                            $("#" + inputNm).prop("checked",true);
                        } else {
                            $("#" + inputNm).prop("checked",false);
                        }
                    } else if($("#" + inputNm)[0].type == "select"){
                        $(":input " + "#" + inputNm).val(obj[elem]);
                    } else {
                        if($("#" + inputNm).prop('class').indexOf("hasDatepicker")>0){
                            if(obj[elem] != null && obj[elem] != "" && obj[elem] != undefined){
                                obj[elem] = obj[elem].replace(/-/g,'');
                                obj[elem] = obj[elem].substr(0,4)+"-"+obj[elem].substr(4,2)+"-"+obj[elem].substr(6,2);
                                $("#" + inputNm).val(obj[elem]);
                            }
                        } else {
                            $("#" + inputNm).val(obj[elem]);
                        }
                    }
                } else if($('input:radio[name='+ inputNm +']')[0]){
                    if(obj[elem] != ""){
                        $('input:radio[name='+ inputNm +']').filter('[value='+obj[elem]+']').prop('checked', true);
                    }
                }
            }// for end
        },
        
        /*****************************************************************************************
         * form 에 parameter 정보를 hidden input 으로 추가.
         * CmmnUtil.addParamToHiddenInput(pFrm, pParamObj);
         * return : object
         ****************************************************************************************/
        addParamToHiddenInput : function(pFrm, pParamObj){
            if(!CmmnUtil.isEmpty(pFrm) && !CmmnUtil.isEmpty(pParamObj)){
                $.each(pParamObj, function(key,val){
                    if(!CmmnUtil.isEmpty($(pFrm).find('#'+key)) || !CmmnUtil.isEmpty($(pFrm).find('[name='+key+']'))){
                        var vObj = CmmnUtil.isEmpty($(pFrm).find('#'+key))? $(pFrm).find('[name='+key+']') : $(pFrm).find('#'+key);
                        if($(vObj).is(':checkbox')){
                            // 체크박스 일경우 처리
                            $(vObj).each(function(){
                                if($(this).prop('value') == val){
                                    $(this).prop('checked', true);
                                } else {
                                    $(this).prop('checked', false);
                                }
                            });
                        } else {
                            // 체크박스 일경우 아닐경우
                            $(vObj).val(val);
                        }
                    } else {
                        var $fvHidObj = $('<input/>',{type:'hidden', id:key, name:key, value:val , readonly : true}); 
                        $fvHidObj.appendTo($(pFrm));
                    }
                }); // end each
            } // end if
        },
        /*****************************************************************************************
         * form 에 parameter 정보를 hidden input 으로 추가.
         * CmmnUtil.timeFormat(time);
         * return : 
         ****************************************************************************************/
        inputTimeColon : function(time){
            // replace 함수를 사용하여 콜론( : )을 공백으로 치환한다.
            var replaceTime = time.value.replace(/\:/g, "");
            // 텍스트박스의 입력값이 4~5글자 사이가 되는 경우에만 실행한다.
            if(replaceTime.length >= 4 && replaceTime.length < 5) {
                var hours = replaceTime.substring(0, 2); 
                var minute = replaceTime.substring(2, 4);
                // isFinite함수를 사용하여 문자가 선언되었는지 확인한다.
                if(isFinite(hours + minute) == false) {
                    alert(MsgUtil.getMsg(WARN03));
                    time.value = "00:00";
                    return false;
                }
                if(hours + minute > 2400) {
                    alert("시간은 24시를 넘길 수 없습니다.");
                    time.value = "24:00";
                    return false;
                }
                if(minute > 60) {
                    alert("분은 60분을 넘길 수 없습니다.");
                    time.value = hours + ":00";
                    return false;
                }
                time.value = hours + ":" + minute;
            }
        },
        
        /*****************************************************************************************
         * 메뉴이동
         * CmmnUtil.moveToMenu(menuId);
         * return : 
         ****************************************************************************************/
        moveToMenu : function(menuId) {
            if(CmmnUtil.isEmpty(menuId) || $("#"+menuId,top.document).length==0) return false;
            $("#"+menuId,top.document).trigger("click");
        },
        
        /*****************************************************************************************
         * loacation.href 이동시 메뉴정보파라미터 셋팅(특이케이스)
         * CmmnUtil.getMenuInfo(menuAuth);
         * return : object
         ****************************************************************************************/
        getMenuInfo : function(menuAuth) {
            var menuInfo = {
                    MENU_ID    : $("#top_nav_area").data("page-menu-id"),
                    PGM_ID     : $("#top_nav_area").data("page-pgm-id"),
                    VIEW_PKID  : $("#top_nav_area").data("menu-pk-id"),
                    MENU_NAVI  : $("#top_nav_area").data("menu-nav"),
                    MENU_AUTH  : CmmnUtil.isEmpty(menuAuth)?"000000000000":menuAuth
                };
            return menuInfo;
        },
        
        /*****************************************************************************************
         * loacation.href 이동시 메뉴정보파라미터 셋팅(특이케이스)
         * CmmnUtil.getMenuInfoEnc(menuAuth);
         * return : string 
         ****************************************************************************************/
        getMenuInfoEnc : function(menuAuth) {
            menuInfo = "MENU_INFO=" + JSON.stringifyEnc(CmmnUtil.getMenuInfo(menuAuth));
            return menuInfo;
        },
        /*****************************************************************************************
         * form 형태의화면 저장시 필수값체크
         * CmmnUtil.getFormValid(arr);
         * return : string
         ****************************************************************************************/
        getFormValid : function(arr) {
            var target = arr;
            var rtn = true;
            var check = function(arr){
                $.each(target,function(i,v){
                    var elem = $('#'+v);
                    var val = elem.val();
                    if(CmmnUtil.isEmpty(val)){
                        var th = elem.closest("tr")[0].innerText.trim();
                        MsgUtil.alert(MsgUtil.getMsg("SYS10",th),elem);
                        rtn = false;
                        return false;
                    }
                });
                return rtn;
            }
            return !check(target);
        },
        /***********************************************************************************************
            name        : 저장할 데이터만 JSON Array 형태로 가져오는 함수
         ************************************************************************************************
            syntax      :
            parameter   :
            return      :
         ************************************************************************************************/
        getJsonObj :function (sheetObj){
            var obj = (sheetObj && sheetObj.getSaveJson({
              }));
              if(obj.Message == "RequiredError") delete obj.row;
            return obj;
        },
    };
    window.CmmnUtil = CmmnUtil.init();
});