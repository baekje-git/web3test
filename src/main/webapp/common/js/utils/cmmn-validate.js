/***************************************************************************************
 * Description  : 유효성 여부를 체크하여 메시지로 반환한다.
 * Argument     : arg
 * Return       : string
 * Sample       : 
 ***************************************************************************************/
var CmmnValid = {
    run : function(op){
        this.op = op || {};
        this.op.scope = op.scope || "";
        this.op.rules = op.rules || "";
        
        var target;
        var title = "title";
        
        if(this.op.scope){
            target = $(this.op.scope);
        }else{
            target = $(document);
        }
        
        for(key in this.op.rules){
            var element = $(target).find("[name=" + key + "]");
            
            var rule = this.op.rules[key];
            for(r in rule){
                if(r == title){continue; }
                
                var nodeName = element[0].nodeName.toUpperCase();
                var type = element[0].type.toUpperCase();
                var tag = "";
                if(nodeName == "INPUT"){
                    tag = type;
                }else{
                    tag = nodeName;
                }
                
                if(r == "isRequired"){
                    if(!this.rules[r][tag](element, rule[r])){
                        alert(this.message[r][tag](this.op.rules[key][title], rule[r]));
                        
                        if(nodeName == "SELECT") {
                            $(element).attr('style','border:1px solid red;');
                        }
                        element.focus();
                        return false;
                    } else {
                        if(nodeName == "SELECT") {
                            $(element).attr('style','');
                        }
                    }
                }else{
                    if(!this.rules[r](element, rule[r])){
                        alert(this.message[r](this.op.rules[key][title], rule[r]));
                        element.focus();
                        return false;
                    }
                }
            }
        }
        return true;
    },
    
    rules : {
        isRequired : {
            "TEXT" : function(e, v){
                if(!v){return true; }
                if($(e).val() == null || $(e).val().trim() == ""){
                    return false;
                }else{
                    return true;
                }
            },
            "RADIO" : function(e, v){
                if(!v){return true; }
                
                if($(e).filter(":checked").val() == null || $(e).filter(":checked").val() == ""){
                    return false;
                }else{
                    return true;
                }
            },
            "SELECT" : function(e, v){
                if(!v){return true; }
                
                if($(e).find("option:selected").val() == null || $(e).find("option:selected").val() == ""){
                    return false;
                }else{
                    return true;
                }
            },
            "CHECKBOX" : function(e, v){
                if(!v){return true; }
                
                if($(e).filter(":checked").length <= 0){
                    return false;
                }else{
                    return true;
                }
            },
            "TEXTAREA" : function(e, v){
                if(!v){return true; }
                if($(e).val() == null || $(e).val().trim() == ""){
                    return false;
                }else{
                    return true;
                }
            },
            "HIDDEN" : function(e, v){
                if(!v){return true; }
                if($(e).val() == null || $(e).val().trim() == ""){
                    return false;
                }else{
                    return true;
                }
            },
            "PASSWORD" : function(e, v){
                if(!v){return true; }
                if($(e).val() == null || $(e).val().trim() == ""){
                    return false;
                }else{
                    return true;
                }
            }
        },
        
        minlength : function(e, v){
            if($(e).val().length < v){ return false;
            }else{ return true; }
        },
        maxlength : function(e, v){
            if($(e).val().replace(/,/g,'').length > v){ return false;
            }else{ return true; }
        },
        minbyte : function(e, v){
            var bt = CmmnValid.getByteAsStr($(e).val());
            if(parseInt(bt) < parseInt(v)) return false;
            else return true;
        },
        maxbyte : function(e, v){
            var bt = CmmnValid.getByteAsStr($(e).val());
            if(parseInt(bt) > parseInt(v)) return false;
            else return true;
        },
        isNumeric : function(e, v){
            if(!v){return true; }
            if( $(e).val() == "" ) return true; // 없으면 통과
            if(isNaN($(e).val().replace(/,/g,''))){
                return false;
            }else{
                return true;
            }
        },
        isDate : function(e, v){
            if(!v){return true; }
            if( $(e).val() == "" ) return true; // 없으면 통과
            return (/^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/).test($(e).val());
        },
        isEmail : function(e, v){
            if(!v){return true; }
            if( $(e).val() == "" ) return true; // 없으면 통과
            return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($(e).val());
    
        },
        isTelNo  : function(e, v){
            if(!v){return true; }
            if( $(e).val() == "" ) return true; // 없으면 통과
            return /^\(?([0-9]{2,3})\)?[-. ]?([0-9]{3,4})[-. ]?([0-9]{4})$/.test($(e).val());   // XXX XXX XXXX 
        },
        isKor : function(e, v){
            if(!v){return true; }
            if( $(e).val() == "" ) return true; // 없으면 통과
            return /^[가-힣]{2,15}$/.test($(e).val());  // 한글만
        },
        isId : function(e, v){
            if(!v){return true; }
            if( $(e).val() == "" ) return true; // 없으면 통과
            return /^[a-zA-Z0-9]{3,15}$/.test($(e).val());  // 영문과 숫자 3~15자리까지
        },
        isPwd : function(e, v){
            if(!v){return true; }
            if( $(e).val() == "" ) return true; // 없으면 통과
            return /^(?=.*[0-9])(?=.*[!@#$%^&*_])[a-zA-Z0-9!@#$%^&*_]{8,30}$/.test($(e).val());  // 영문&숫자&특수문자(!@#$%^&*_) 3가지 혼합, 8~30자
        }
    }
    
    , message : {
        isRequired : {
            "TEXT" : function(t, v){
                return t + "은(는) 필수 입력 항목입니다.";
            },
            "RADIO" : function(t, v){
                return t + "은(는) 필수 선택 항목입니다.";
            },
            "SELECT" : function(t, v){
                return t + "은(는) 필수 선택 항목입니다.";
            },
            "CHECKBOX" : function(t, v){
                return t + "은(는) 필수 선택 항목입니다.";
            },
            "TEXTAREA" : function(t, v){
                return t + "은(는) 필수 입력 항목입니다.";
            },
            "HIDDEN" : function(t, v){
                return t + "은(는) 필수 입력 항목입니다.";
            },
            "PASSWORD" : function(t, v){
                return t + "은(는) 필수 입력 항목입니다.";
            }
        },
        
        minlength : function(t, v){
            return t + "은(는) 최소 " + v + "자 이상 입력 해야합니다.";
        },
        maxlength : function(t, v){
            return t + "은(는) 최대 " + v + "자까지 입력 가능합니다.";
        },
        minbyte : function(t, v){
            return t + "은(는) 최소 " + v + "Byte 이상 입력 해야합니다.";
        },
        maxbyte : function(t, v){
            return t + "은(는) 최대 " + v + "Byte까지 입력 가능합니다.";
        },
        isNumeric : function(t, v){
            return t + "은(는) 숫자만 입력 가능합니다.";
        },
        isDate : function(t, v){
            return t + "은(는) [YYYY-MM-DD]형식으로 입력 가능합니다.";
        },
        isEmail : function(t, v){
            return t + " 형식이 올바르지 않습니다.";
        },
        isTelNo : function(t, v){
            return t + " 형식이 올바르지 않습니다.\n예) xxx-xxxx-xxxx or xxx-xxx-xxxx ( '-' 생략가능 )";
        },
        isKor : function(t, v){
            return t + "은(는) 한글 2~15자까지 허용 됩니다.";
        },
        isId : function(t, v){
            return t + "은(는) 영문&숫자 3~15자까지 허용 됩니다.";
        },
        isPwd : function(t, v){
            return t + "은(는) 영문&숫자&특수문자(!@#$%^&*_) 3가지 혼합, 8~30자까지 허용 됩니다.";
        },
        isPwd : function(t, v){
            return t + "은(는) 영문&숫자&특수문자(!@#$%^&*_) 3가지 혼합, 8~30자까지 허용 됩니다.";
        },
        isChaNo : function(t, v){
            return t + "을(를) 정확히 입력하세요.";
        }
    },
    
    getByteAsStr : function (str){
        var result = 0;
        for(var i = 0; i < str.length; i++) {
            if(escape(str.charAt(i)).length >= 4)
                result += 2;
            else if(escape(str.charAt(i)) == "%A7")
                result += 2;
            else if(escape(str.charAt(i)) != "%0D")
                result++;
        }
        return result;
    }
}


/**
 * @파일명     common_val.js
 * @설명      인풋 검증 및 마스킹 공통 함수 모음 (KEYUP / BLUR)
 * @작성자     공철현
 * @작성일     2015-10-15
 * @변경사항 
 *  2015-10-15  김가빈  최초작성
*/


/**
 * 입력이 object 이면 object 에 value 를 리턴하고 그 외에는 해당 입력값을 리턴한다
 * @param {object or String} obj
 * @return {string} object value
 * @example var value = getObjValue(input);
 */
function getObjValue(obj) {
    
    if(obj == null) return "";
    
    if (typeof (obj) == "object") {
        if (obj.jquery == undefined) {
            return obj.value;
        }
        return obj.val();
    } else {
        return obj;
    }
}


function setFormJqueryValue(obj, value) {
    if (obj.jquery == undefined) {
        obj.value = value;
    } else {
        obj.val(value);
    }
}

function cbsOnSelect(p) {
    p.select();
}

function checkFromToDate(obj, gubun) {
    var fdate = $("#"+obj+"_from").val();
    fdate = fdate.replace(/\/|\-|\./g, ""); // 특수문자 제거
    if(fdate == "") return;

    var tdate = $("#"+obj+"_to").val();
    tdate = tdate.replace(/\/|\-|\./g, ""); // 특수문자 제거
    if(tdate == "") return;
    
    if (fdate > tdate) {
        if(gubun == "from"){
            $("#"+obj+"_from").val("");
        } else {
            $("#"+obj+"_to").val("");
        }
    }
}

function ibcal(id,format){
    event.preventDefault();
    var opt = {
            Date:$("#"+id).val(),
            Format:format,
            OnButtonClick:function(evt){
                alert(evt);
                if(evt==2){ //지우기
                    $("#"+id).val("");            
                }
                ibcal.Close();
            }
    };
    if(format=="yyyy-MM")opt.Buttons = 4;
    function calPickCallBack(v){
        $("#"+id).val(IBSheet.dateToString(parseInt(v), format) ); 
    }
    var ibcal = IBSheet.showCalendar(opt,{Tag:id},calPickCallBack);
}

function ibcalFromTo(id,format){
    if(event!=null){
        event.preventDefault();
    }
    var isFrom = !!(id.substring(id.length-5)=="_from");
    var oppaID = isFrom?id.substring(0,id.length-4)+"to":id.substring(0,id.length-2)+"from";
    var oppaValue = document.getElementById(oppaID).value; 
    var oppaValueTimeStamp = oppaValue!=""?IBSheet.stringToDate(oppaValue,format):null;
    var opt = {
            Format:format,
            RowsPrev:2,
            RowsNext:2,
            Buttons:6,
            Texts:{Ok:"닫기",Clear:"지우기"},
            OnCanEditDate:function(d){
              if(oppaValue!=""){
                  if(isFrom){
                      if(d>oppaValueTimeStamp) return false;     
                  }else{
                      if(d<oppaValueTimeStamp) return false;     
                  }
                 
              }  
            },
            OnButtonClick:function(evt){
                if(evt==2){ //지우기
                    $("#"+id).val("");            
                }
                fromtoCal.Close();
            }
    };
    function calPickCallBack(v){
        $("#"+id).val(IBSheet.dateToString(parseInt(v), format) );
        if(oppaValue==""){
            if(isFrom){
                ibcalFromTo(id.substring(0,id.length-4)+"to",format);
            }else{
                ibcalFromTo(id.substring(0,id.length-2)+"from",format);
            }
        }
    }
    var fromtoCal = IBSheet.showCalendar(opt,{Tag:id},calPickCallBack);

}


$(window).on('load', function () {
    var g_org_value  = "";
    /**  
    * (입력)숫자만 허용 / 공백을 포함하여 다른 값은 return  
    * @event    {keyup} cbsNumberFormat(this)
    **/
    $('[data-validate-num]').inputmask('numeric', {
        autoGroup: false      //천단위 그룹
        , integerDigits: 15        //자리수 설정
    });
    
    $('[data-validate-num]').on({
        focus : function(){
            this.select();
        }
        , input : function(e){
            if(e.target.value != ""){
                if(e.target.value.indexOf('.') == e.target.value.length-1 ) return;
            }
            e.target.value = e.target.value.replace(/[^0-9.]/g, '');
        } 
    });
    
    /**  
    * (입력)한글만 허용 / 공백을 포함하여 다른 값은 return  
    **/
    $('[data-validate-kr]').on({
         input : function(e){
             e.target.value = e.target.value.replace(/[^ㄱ-ㆎ가-힝]/g, '');
        } 
    });

    /**  
    * (입력)한글만 제외 / 공백을 포함하여 다른 값은 return  
    **/
    $('[data-validate-expkr]').on({
        input : function(e){
            e.target.value = e.target.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]/g, '');
       } 
    });
    
    /**  
     * (입력)년 월 일 
     **/
    if( $('[data-validate-ymd]').length > 0 ){
        $('[data-validate-ymd]').each(function(i,o){
            //$(this).addClass("inpDate");
            var id = o.id;
            $(this).inputmask({ alias: "datetime"
                                , inputFormat:"yyyy-mm-dd"
                                , showMaskOnHover:false  // 마우스를 가리킬 때 마스크를 표시 여부
                                , showMaskOnFocus:false  // 입력에 포커스가있을 때 마스크를 표시 여부
                                , placeholder:" "        // placeholder 표시 글자 default: inputFormat
                                , outputFormat:"yyyymmdd"
                             }); 
            $(this).after("<button class='button is-dark' onclick='ibcal(\""+id+"\",\"yyyy-MM-dd\")'>달력</button>");
            
            $(this).on('focusout', function(e){
                if( $(this).val().replaceAll(" ", "").length != 10 ) $(this).val("") 
            });
        });
    }
    
    $('[data-validate-ymd]').on({
        input : function(e){
            e.target.value = e.target.value.replace(/[^0-9]/g, '');
        } 
    });
    
    /**  
     * (입력)년 월 
     **/
    if( $('[data-validate-ym]').length > 0 ){
        $('[data-validate-ym]').each(function(i,o){
            //$(this).addClass("inpDate");
            var id = o.id;
            $(this).inputmask({ alias: "datetime", inputFormat:"yyyy-mm",showMaskOnHover:false,placeholder:" "}); 
            $(this).after("<button class='button is-dark' onclick='ibcal(\""+id+"\",\"yyyy-MM\")'>달력</button>");
            
            $(this).on('focusout', function(e){
                if( $(this).val().replaceAll(" ", "").length != 8 ) $(this).val("") 
            });
        });
    }
    
    $('[data-validate-ym]').on({
        input : function(e){
            e.target.value = e.target.value.replace(/[^0-9]/g, '');
       } 
    });
    
    /**  
     * (입력)년 월 일 - 년 월 일
     **/
    if( $('[data-validate-fromto]').length > 0 ){
        $('[data-validate-fromto]').each(function(i,o){
            var id = o.id;
            $(this).attr("id", id+"_from");
            $(this).attr("name", id+"_from");
            $(this).after("<button class='button is-dark' onclick='ibcalFromTo(\""+id+"_from\",\"yyyy-MM-dd\")' >달력</button>"
                    +" ~ <input class='input' name='"+id+"_to' id='"+id+"_to' /><button class='button is-dark' onclick='ibcalFromTo(\""+id+"_to\",\"yyyy-MM-dd\")'>달력</button>");
            
            $("#"+id+"_from").inputmask({ alias: "datetime", inputFormat:"yyyy-mm-dd",showMaskOnHover:false,placeholder:" "});
            $("#"+id+"_from").on('focusout', function(e){
                if( $(this).val().replaceAll(" ", "").length != 10 ){
                    $(this).val("") 
                } else {
                    checkFromToDate( id , "from");
                }
            });
            
            $("#"+id+"_to").inputmask({ alias: "datetime", inputFormat:"yyyy-mm-dd",showMaskOnHover:false,placeholder:" "});
            $("#"+id+"_to").on('focusout', function(e){
                if( $(this).val().replaceAll(" ", "").length != 10 ){
                    $(this).val("") 
                } else {
                    checkFromToDate( id , "to");
                }
            });
            $("#"+id+"_to").width($("#"+id+"_from").width()); //width를 동일하게 맞춤
        });
    }
    
    $('[data-validate-fromto]').on({
        input : function(e){
            e.target.value = e.target.value.replace(/[^0-9]/g, '');
       }
    });
    
    /**
     * (입력) int ( 999,999,999)
     */
    $('[data-validate-int]').inputmask('numeric', {
        groupSeparator: ","    //separator 설정
        , autoGroup: true      //천단위 그룹
        , integerDigits: 15        //자리수 설정
    });
    
    $('[data-validate-int]').on({
        focus : function(){
            this.select();
        }
        , input : function(e){
            e.target.value = e.target.value.replace(/[^0-9]/g, '');
        } 
    });
    
    /**
     * (입력) float ( 999,999,999.00)
     */
    $('[data-validate-float]').inputmask('numeric', {
        groupSeparator: ","    //separator 설정
        , autoGroup: true      //천단위 그룹
        , max: 1000       //자리수 설정
        , digits: 2          // 소수점
    });
    
    $('[data-validate-float]').on({
        focus : function(){
            this.select();
        }
        , input : function(e){
            if(e.target.value != ""){
                if(e.target.value.indexOf('.') == e.target.value.length-1 ) return;
            }
            e.target.value = e.target.value.replace(/[^0-9.-]/g, '');
        } 
    });
    
    /**
     * (입력) float ( 999,999,999.00)
     */
    $('[data-validate-percent]').inputmask('numeric', {
        groupSeparator: ","    //separator 설정
        , autoGroup: true      //천단위 그룹
        , max: 100           //MAX 설정
        , digits: 2          // 소수점
    });
    
    $('[data-validate-percent]').on({
        focus : function(){
            this.select();
        }
        , input : function(e){
            if(e.target.value != ""){
                if(e.target.value.indexOf('.') == e.target.value.length-1 ) return;
            }
            e.target.value = e.target.value.replace(/[^0-9.]/g, '');
        } 
    });
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**  
    * 전화번호 포맷 (00-000-0000 등) 마스킹
    **/
    $('[data-validate-tel]').on({
        keyup : function(){
            cbsTelFormat(this); 
        }
    });
    
    /**  
    * 화폐포맷 마스킹   
    * @event    {focus}     cbsOnSelect(this)
    * @event    {keyup}     cbsMoneyFormat(this)
    * @event    {mouseup}   event return false
    **/

    $('[data-validate-money]').on({
        
        // 인풋 밸류 전체선택
        focus : function(){
            g_org_value = getOnlyNumber(getObjValue(this));
            
            cbsOnSelect(this);
        },
        
        // 화폐 포맷(000,000,000) 마스킹
        keyup : function(){
            cbsMoneyFormat(this);
        },
        
        focusout : function(){
            var new_value = getOnlyNumber(getObjValue(this));
            if(g_org_value != new_value){
                //if(new_value.length > 3){
                    $(this).change();
                //}
            } 
        },
        // 셀렉트 된 상태 유지하기 위해 마우스업 이벤트 false 처리
        mouseup : function(e) {
            e.preventDefault();
        }
    });

    
    
    
    /**  
    * 비율 형태 검증(소수점 허용) : 100,120 param 설정에 따라 범위를 벗어날 때 alert 출력
    * @option   공백(비율 검증) , 100(0 - 100 범위) , 120 (80 - 120 범위)
    * @event    {keyup}     cbsRateFormat(this,@option)
    **/
    $('[data-validate-rate]').on({
        // 인풋 밸류 전체선택
        focusout : function(){
            var val = this.value;
            var retArr = val.split(".");
            if( retArr.length == 2){
                if(retArr[1] == ""){
                    setFormJqueryValue( this , toInteger(val));
                }
            }
        },
        keyup : function(){
            cbsRateFormat(this, this.getAttribute('data-validate-rate'));
        }
    });
    
    $('[data-validate-trim]').on({
        // 인풋 밸류 전체선택
        focusout : function(){
            cbsTrimFormat(this);
        }
    });
    
    
    
    
    /**  
    * 소수점 자릿수 버림
    * @option   1, 2, 3, 4, 5 [모두 소수점 자릿수를 뜻함]
    * @event    {focusout}  toFixedRate(this,@option)
    **/
    $('[data-validate-fixRate]').on({
        focusout : function(){
            toFixedRate(this, this.getAttribute('data-validate-fixRate'));
        }
    });
    
    
    
    
    
    
    /**  
    * 주민번호 포맷 마스킹 (6-7)
    * @event    {keyup} cbsJuminFormat(this)
    **/
    $('[data-validate-format="jumin"]').on({
        focus : function(){
            this.setAttribute('maxlength' , 14);
        },
        keyup : function(){
            cbsJuminFormat(this);
        }
    });
    
    
    /**  
    * 주민번호 포맷 마스킹 (6-7) , 유효성 검증
    * @event    {keyup} cbsJuminFormat(this)
    **/
    $('[data-validate-format="juminchk"]').on({
        
        // 주민번호 포맷 마스킹
        keyup : function(){
            cbsJuminFormat(this);
        },
        
        // 주민번호 유효성 검증
        focusout : function() {
            cbsJuminNo(this); 
        }
    });
    
    
    
    /**  
    * 사업자등록번호 마스킹 (3-2-5)
    * @event    {keyup} cbsJuminFormat(this)
    **/
    $('[data-validate-format="biz"]').on({
        focus : function(){
            this.setAttribute('maxlength' , 12);
        },
        // 사업자등록번호 포맷 마스킹
        keyup : function(){
            cbsCompanyFormat(this);
        }
    });
    
    
    
    
    /**  
    * 밸류 length 에 따라 주민번호 or 사업자등록번호 마스킹
    * @event    {keyup}     cbsJuminFormat(this)
    * @event    {focusout}  cbsJuminCompanyFormat(this)
    **/
    $('[data-validate-format="juminbiz"]').on({
        
        // 기본적으로 keyup 에는 주민번호 포맷만 마스킹
        keyup : function(){
            cbsJuminFormat(this);
        },
        
        
        // blur 시에 length 체크하여 10자리일 경우 사업자등록번호 마스킹으로 변환
        focusout : function(){
            cbsJuminCompanyFormat(this);
        }
    });
    
    
    /**  
    * 주민번호 포맷 마스킹 (6-7) , 유효성 검증
    * @event    {keyup} cbsJuminFormat(this)
    **/
    $('[data-validate-format="bubinchk"]').on({
        
        // 주민번호 포맷 마스킹
        keyup : function(){
            cbsJuminFormat(this);
        },
        
        // 주민번호 유효성 검증
        focusout : function() {
            if( (this).value == "") return
            if( isBuBinNum(this) == false){
                comAlertMsg('COW0020');
                (this).focus();
            }; 
        }
    });
    
    
    /** 
    * 파일업로드  
    * @param {String}
    * @return { }
    */
    $('[data-fileupload]').on({
        click : function() {
            $(this).siblings('input[type="file"]').click()
            .on({
                change : function(){
                    var val = $(this).val();
                    $(this).siblings('input').val(val).attr('title',val);
                }
            });
            $(this).siblings('input[type="file"]').change();
        }
    });
    
    
});
