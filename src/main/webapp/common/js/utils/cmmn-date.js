$(function(){
    
    var DateUtil = {
        init: function(){
            return this;
        },
        
        /*****************************************************************************************
         * 년월일 달력 초기설정
         * DateUtil.initDefaultDatepicker();
         * return : void
         ****************************************************************************************/
        initDefaultDatepicker: function(){
            $.datepicker.setDefaults({
                dateFormat         : 'yy-mm-dd'
              , prevText           : '이전 달'
              , nextText           : '다음 달'
              , monthNames         : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
              , monthNamesShort    : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
              , dayNames           : ['일', '월', '화', '수', '목', '금', '토']
              , dayNamesShort      : ['일', '월', '화', '수', '목', '금', '토']
              , dayNamesMin        : ['일', '월', '화', '수', '목', '금', '토']
              , showMonthAfterYear : true
              , changeMonth        : true
              , changeYear         : false
              , yearSuffix         : '년'
              , showOtherMonths    : true
              , defaultDate        : new Date()
            });
            
            $.each($(".board_data_wrap.date input[type=text]"),function(i,v){
                if(!CmmnUtil.isEmpty(v.id)) {
                    if(CmmnUtil.isEquals($("#".concat(v.id)).data("minData"),0)) {
                        $("#".concat(v.id)).datepicker({minDate: 0});
                    } else {
                        $("#".concat(v.id)).datepicker();
                    }
                    //$("#".concat(v.id)).datepicker('setDate', new Date());
                }
            });
        },
        
        /*****************************************************************************************
         * 년월 달력 초기설정
         * DateUtil.initDefaultMonthpicker();
         * return : void
         ****************************************************************************************/
        initDefaultMonthpicker: function(){
            
            $.each($(".board_data_wrap.month input[type=text]"),function(i,v){
                if(!CmmnUtil.isEmpty(v.id)) {
                    
                    $("#".concat(v.id)).datepicker({ 
                        dateFormat: 'yy-mm'
                      , changeMonth: true 
                      , changeYear: true 
                      , showButtonPanel: true 
                      , currentText : "현재월"
                      , closeText   : "선택"
                      , onChangeMonthYear: function(year, month, inst) {}
                      , onClose: function(dateText, inst) { 
                          var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
                          var year  = $("#ui-datepicker-div .ui-datepicker-year  :selected").val(); 
                          $(this).datepicker('setDate', new Date(year, month, 1)); 
                          $(".ui-datepicker-calendar").css("display","none"); 
                          $(".ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year").css("width" ,"40%");
                          $(".ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year").css("margin-right","3px");
                          $(".ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year").css("margin-left" ,"3px");
                      }
                    });
                    
                    $("#".concat(v.id)).focus(function() {
                        $(".ui-datepicker-calendar").css("display","none"); 
                        $(".ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year").css("width" ,"40%");
                        $(".ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year").css("margin-right","3px");
                        $(".ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year").css("margin-left" ,"3px");
                        //$("#ui-datepicker-div").position({ my: "center top", at: "center bottom", of: $(this)}); 
                    });
                }
            });
            
        },
        
        /*****************************************************************************************
         * 년도 달력 초기설정
         * DateUtil.initDefaultYearpicker();
         * return : void
         ****************************************************************************************/
        initDefaultYearpicker: function(){
            
            $.each($(".board_data_wrap.year input[type=text]"),function(i,v){
                if(!CmmnUtil.isEmpty(v.id)) {
                    
                    $("#".concat(v.id)).datepicker({ 
                        dateFormat: 'yy'
                      , changeMonth: false 
                      , changeYear: true 
                      , showButtonPanel: true
                      , yearRange : 'c-100:c+100'
                      , currentText : "현재년도"
                      , closeText   : "선택"
                      , onChangeMonthYear: function(year, month, inst) {}
                      , beforeShow: function(input, inst) {}
                      , onClose: function(dateText, inst) {
                          var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                          $(this).datepicker('setDate', new Date(year, 0, 1));
                          $(".ui-datepicker-calendar").css("display","none"); 
                          $(".ui-datepicker-month"   ).css("display","none");
                          $(".ui-datepicker-prev.ui-corner-all"   ).css("display","none");
                          $(".ui-datepicker-next.ui-corner-all"   ).css("display","none");
                          $(".ui-datepicker select.ui-datepicker-year").css("margin-right","3px");
                          $(".ui-datepicker select.ui-datepicker-year").css("margin-left" ,"3px");
                      }
                    });
                    
                    $("#".concat(v.id)).focus(function() {
                        $(".ui-datepicker-calendar").css("display","none"); 
                        $(".ui-datepicker-month"   ).css("display","none"); 
                        $(".ui-datepicker-prev.ui-corner-all"   ).css("display","none");
                        $(".ui-datepicker-next.ui-corner-all"   ).css("display","none");
                        $(".ui-datepicker select.ui-datepicker-year").css("margin-right","3px");
                        $(".ui-datepicker select.ui-datepicker-year").css("margin-left" ,"3px");
                    });
                }
            });
        },
        
        /***************************************************************************************
         * Description  : 달력(DATEPICKER) 시작일 종료일을 체크한다
         * DateUtil.setTermCalendar(s,e);
         * Return       : void
         ***************************************************************************************/
        setTermCalendar : function(bgnId, endId){
            $(bgnId).datepicker("option", "maxDate", $(endId).val());
            $(endId).datepicker("option", "minDate", $(bgnId).val());
            $(bgnId).datepicker("option", "onClose", function(selectedDate){
                $(endId).datepicker("option", "minDate", selectedDate);
            });
            $(endId).datepicker("option", "onClose", function(selectedDate){
                $(bgnId).datepicker("option", "maxDate", selectedDate);
            });
        },
        
        /***************************************************************************************
         * Description  : 달력(DATEPICKER) 시작일 종료일을 체크한다
         * DateUtil.getNewDate(diff[없으면생략],dash);
         * Return       : rtnVal  날짜문자열을 반환한다.
         * Example :  getDate() : 금일, getDate(5) : 금일기준 5일이후, getDate(-5) : 금일기준 5일이전
         ***************************************************************************************/
        getNewDate : function(diff,dash) {
            var today = "";
            var date  = new Date();
            // 차이값이 존재할경우
            if(diff != undefined) {
                // 스트링 형태이면 넘버로 치환
                if(diff != "F"){
                    if(typeof diff == 'string'){
                        diff = Number(diff);
                        date.setDate(date.getDate() + diff);
                    } else
                    if(typeof diff == 'number'){
                        date.setDate(date.getDate() + diff);
                    } else
                    if(typeof diff == 'boolean'){
                        dash = diff;
                    }
                }
            }
            
            // dash 값이 존재하면
            if(dash != undefined) {
                if(typeof dash == 'boolean'){
                    dash = dash;
                } else {
                    dash = false;
                }
            }
            
            var year  = date.getFullYear(); 
            var month = new String(date.getMonth()+1); 
            var day   = new String(date.getDate()); 
            
            if(diff == "F"){
                day = "1";
            }
            
            if(month.length == 1){
                month = "0" + month; 
            } 
            
            if(day.length == 1){
                day = "0" + day; 
            } 
            
            if(dash){
                today = year + "-" + month + "-" + day;
            } else {
                today = year + ""  + month + ""  + day;
            }
            return today;
        },
        
        /***************************************************************************************
         * Description  : 해당월의 1일을 문자열로 반환한다.
         * DateUtil.getOneDate(dash);
         * Return       : rtnVal  날짜문자열을 반환한다.
         ***************************************************************************************/
        getOneDate : function(dash){
            return this.getNewDate('F',dash);
        },

        /***************************************************************************************
         * Description  : 오늘날짜기준으로 해당월을 문자열로 반환한다. (YYYYMMDD)
         * DateUtil.getAddMonth(diff,dash);
         * Return       : rtnVal  날짜문자열을 반환한다.
         * Example : getAddMonth() : 금일, getAddMonth(5) : 금일기준 5달이후, getAddMonth(-5) : 금일기준 5달이전
         ***************************************************************************************/
        getAddMonth : function(diff,dash) {
            var today = "";
            var date  = new Date();
            var day   = new String(date.getDate()); 
            if(day.length == 1){
                day = "0" + day; 
            } 
            
            if(typeof diff == 'boolean'){
                dash = diff;
                if(dash){
                    today = getYearMonth(0,dash) + "-" + day;
                } else {
                    today = getYearMonth(diff,dash) + "" + day;
                }
            } else {
                if(dash){
                    today = getYearMonth(diff,dash) + "-" + day;
                } else {
                    today = getYearMonth(diff,dash) + "" + day;
                }
            }
            return today;
        },

        /***************************************************************************************
         * Description  : 년월을 문자열로 반환한다. (YYYYMM)
         * DateUtil.getYearMonth(diff,dash);
         * Return       : rtnVal  날짜문자열을 반환한다.
         * Example : getYearMonth() : 금일, getYearMonth(5) : 금일기준 5달이후, getYearMonth(-5) : 금일기준 5일이전
         ***************************************************************************************/
        getYearMonth : function(diff,dash) {
            var ym   = "";
            var date = new Date();
            if(diff != undefined) {
                if(typeof diff != 'string' && typeof diff != 'boolean'){
                    date.setMonth(date.getMonth() + diff);
                } else {
                    if(typeof diff == 'boolean'){
                        dash = diff;
                    }
                }
            };
            var year  = date.getFullYear();
            var month = new String(date.getMonth() + 1); 
            
            // 한자리수일 경우 0을 채워준다. 
            if(month.length == 1){
                month = "0" + month; 
            } 
            
            if(dash){
                ym = year + "-" + month;
            } else {
                ym = year + "" + month;
            }
            return ym;
        },
        
        
        /***************************************************************************************
         * Description  : 해날날짜의 요일을 코드로 반환
         * DateUtil.getWeekCode(pDt);
         * Return       : number
         ***************************************************************************************/
        getWeekCode: function(pDt){
            var weekCd = [0, 1, 2, 3, 4, 5, 6];
            var dayOfWeek = weekCd[new Date(pDt).getDay()];
            return dayOfWeek;
        },
        
        /***************************************************************************************
         * Description  : 해날날짜의 요일을 이름으로 반환
         * DateUtil.getWeekCode(pDt);
         * Return       : string
         ***************************************************************************************/
        getWeekName: function(pDt){
            var weekNm = ['일', '월', '화', '수', '목', '금', '토'];
            var dayOfWeek = weekNm[new Date(pDt).getDay()];
            return dayOfWeek;
        },
        
        /***************************************************************************************
         * Description  : 년을 문자열로 반환한다. (YYYY)
         * DateUtil.getYear(diff);
         * Return       : rtnVal  날짜문자열을 반환한다.
         ***************************************************************************************/
        getYear: function(diff){
            var yyyy    = "";
            var date  = this.newDate(); //new Date();
            
            if(diff != undefined) {
                if(typeof diff == 'string'){
                    diff = Number(diff);
                    if(!isNaN(diff)) {
                        date.setFullYear(date.getFullYear() + diff);
                    }
                }
            };
            
            var year  = date.getFullYear(); 
            yyyy = year;
            return yyyy;
        },
        
        /***************************************************************************************
         * Description  : 입력받은 문자열의 마지막일을 반환한다 반환한다. (YYYYMMDD)
         * DateUtil.getLastDay(diff,dash);
         * Return       : rtnVal  날짜문자열을 반환한다.
         ***************************************************************************************/
        getLastDay : function(diff,dash){
            var date  = new Date();
            var year  = "";
            var month = "";
            var day   = "";
            var rtn   = "";
            if(diff != undefined) {
                
                if(typeof diff == 'boolean'){
                    dash = diff;
                    year  = date.getFullYear();
                    month = new String(date.getMonth()+1);
                    day   = new String(date.getDate());
                } else {
                    var datePattern = /^(19|20)\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])$/;
                    if(datePattern.test(diff)){
                        if(typeof diff == 'string'){
                            year  = diff.substr(0, 4);
                            month = diff.substr(4, 2);
                            day   = diff.substr(6);
                        } else
                        if(typeof diff == 'number'){
                            diff = new String(diff);
                            year  = diff.substr(0, 4);
                            month = diff.substr(4, 2);
                            day   = diff.substr(6);
                        }
                    } else {
                        alert("비교하려는 날짜 형식이 유효하지 않습니다.");
                        return "";
                    }
                }
            } else {
                year  = date.getFullYear();
                month = new String(date.getMonth()+1);
                day   = new String(date.getDate());
            }
            
            date  = new Date(year, month, 0);
            year  = date.getFullYear();
            month = new String(date.getMonth()+1);
            day   = new String(date.getDate());
            
            // 한자리수일 경우 0을 채워준다. 
            if(month.length == 1){
                month = "0" + month; 
            }
            if(day.length == 1){
                day = "0" + day; 
            }
            
            // dash 값이 존재하면
            if(dash != undefined) {
                if(typeof diff == 'boolean'){
                    dash = dash;
                }
            } else {
                dash = false;
            }
            
            if(dash){
                rtn = year + "-" + month + "-" + day;
            } else {
                rtn = year + month + day;
            }
            
            return rtn;
        },
        
        /*****************************************************************************************
         * 현재날짜 (년월일시분초)
         * DateUtil.getTimeStamp();
         * return : string
         ****************************************************************************************/
        getTimeStamp: function(mask){
            var tmpYear  = new Date().getFullYear().toString();
            var tmpMonth = CmmnUtil.addZero(new Date().getMonth()+1, 2);
            var tmpDay   = CmmnUtil.addZero(new Date().getDate()   , 2);
            var tmpHourr = CmmnUtil.addZero(new Date().getHours()  , 2);
            var tmpMin   = CmmnUtil.addZero(new Date().getMinutes(), 2);
            var tmpSec   = CmmnUtil.addZero(new Date().getSeconds(), 2);
            var tmpMs    = CmmnUtil.addZero(new Date().getMilliseconds(), 3);
            
            var nowTimeStamp = mask ? tmpYear.concat("-",tmpMonth,"-",tmpDay," ",tmpHourr,":",tmpMin,":",tmpSec," ",tmpMs) 
                                    : tmpYear+tmpMonth+tmpDay+tmpHourr+tmpMin+tmpSec+tmpMs; 
            return nowTimeStamp;
        },
        
        addZero: function(x, n) {
            while (x.toString().length < n) {
              x = "0" + x;
            }
            return x;
        },
        
        /*****************************************************************************************
         * 
         * DateUtil.newDate()
         * return : string
         ****************************************************************************************/
        newDate : function(year, month, day, hour, minutes, seconds, milliseconds){
            var dateObj = null;
            
            if(year != undefined || month != undefined || day != undefined || hour != undefined || minutes != undefined || seconds != undefined || milliseconds){
                dateObj = new Date(year != undefined ? year : null
                        , month != undefined ? month : null
                        , day != undefined ? day : null
                        , hour != undefined ? hour : null
                        , minutes != undefined ? minutes : null
                        , seconds != undefined ? seconds : null
                        , milliseconds != undefined ? milliseconds : null);
            } else {
                var currYear = systemCurrDate.substr(0, 4);
                var currMonth = (Number(systemCurrDate.substr(5, 2)) - 1) + "";
                var currDay = systemCurrDate.substr(8, 2);
                var currHour = systemCurrDate.substr(11, 2);
                var currMinutes = systemCurrDate.substr(14, 2);
                var currSeconds = systemCurrDate.substr(17, 2);
                dateObj = new Date(currYear, currMonth, currDay, currHour, currMinutes, currSeconds);
            }
            return dateObj;
        },
        
        /*****************************************************************************************
         * 
         * DateUtil.getMonthContrl(id,a,b,c,fclr)
         * return : string
         ****************************************************************************************/
        getMonthContrl: function(id,a,b,c,fclr){
            this.getDateContrl(id,a,b,c,fclr,"MONTH");
        },
        
        /*****************************************************************************************
         * 
         * DateUtil.getYearContrl(id,a,b,c,fclr)
         * return : string
         ****************************************************************************************/
        getYearContrl: function(id,a,b,c,fclr){
            this.getDateContrl(id,a,b,c,fclr,"YEAR");
        },
        
        /*****************************************************************************************
         * 
         * DateUtil.getDateControl(id,a,b,c,fclr,valType)
         * return : string
         ****************************************************************************************/
        getDateContrl: function(id,a,b,c,fclr,valueType){
            if($("#"+id).length == 0) return false;
            var rtnDt;
            var nDate  = this.newDate();
            var year;
            var month;
            var day;
            var dw = nDate.getDay();
            
            // -------------------------------------------------------------
            var frDt = $("#"+id).val();
            if(fclr!=''){
                var frm = $("#"+id).closest("form");
                $("a[name='"+id+"_BTCLR_N']" , frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_D1']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_W1']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_M1']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_M3']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_M6']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_Y1']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_Y2']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                $("a[name='"+id+"_BTCLR_Y3']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                if($("a[name='"+id+"_BTCLR_Y4']", frm).length>0){
                    $("a[name='"+id+"_BTCLR_Y4']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                }
                if($("a[name='"+id+"_BTCLR_Y5']", frm).length>0){
                    $("a[name='"+id+"_BTCLR_Y5']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                }
                if($("a[name='"+id+"_BTCLR_Y6']", frm).length>0){
                    $("a[name='"+id+"_BTCLR_Y6']", frm).css("border","1px #b8b8b8 solid").css("color","#555");
                }
                $("a[name='"+fclr+"']", frm).css("border","1px #4b97ff solid").css("color" ,"color:#4b97ff");
            }
            
            if(a == "d"){   // 현재기준 1일전
                nDate.setHours(0, 0, 0, 0); 
                var vDate = Number(b) * 1;
                nDate.setDate(nDate.getDate() - vDate);
                dw = nDate.getDay();
                if(dw == 0){//일요일이면.
                    nDate.setDate(nDate.getDate() - 2);
                } else if(dw == 6) {  // 토요일이면
                    nDate.setDate(nDate.getDate() - 1);
                }
            } else if(a == "w") {   // 현재기준 1주일전
                var vDate = Number(b) * 7;
                nDate.setDate(nDate.getDate() - vDate);
                dw = nDate.getDay();
                if(dw == 0){//일요일이면.
                    nDate.setDate(nDate.getDate() + 1);
                } else if(dw == 6) {  // 토요일이면
                    nDate.setDate(nDate.getDate() + 2);
                } else if(dw > 1){
                    nDate.setDate(nDate.getDate() - (dw - 1));
                }
            } else if(a == "m") {   // 현재기준 n개월전
                nDate.setMonth(nDate.getMonth() - Number(b));
                if(c != "" && c != undefined){
                    nDate.setDate(Number(c));
                }
            } else if(a == "y") {   // 현재기준 n년전
                
            } else {
                
            }
            
            year  = nDate.getFullYear();
            month = new String(nDate.getMonth()+1); 
            day   = new String(nDate.getDate());
            
            if(month.length == 1){month = "0" + month;} 
            if(day.length   == 1){day   = "0" + day; }
            
            if(CmmnUtil.isEmpty(valueType)){
                // YYYY-MM-DD
                rtnDt  = year + "-" + month + "-" + day;
            } else {
                if(CmmnUtil.isEquals(valueType, "YEAR")){
                    // YYYY
                    rtnDt  = year;
                } else 
                if(CmmnUtil.isEquals(valueType,"MONTH")){
                    // YYYY-MM
                    rtnDt  = year + "-" + month;
                }
            }
            
            $("#"+id).val(rtnDt);
        }
    };
    window.DateUtil = DateUtil.init();
    DateUtil.initDefaultDatepicker();
    DateUtil.initDefaultMonthpicker();
    DateUtil.initDefaultYearpicker();
});
