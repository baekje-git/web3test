$(function(){
    var GridUtil = {
        init: function(){
            return this;
        },
        // 해당시트에 바인드된 click 이벤트 감지
        orgClickEvent :"",
        
        /*****************************************************************************************
         * 시트 생성
         ****************************************************************************************/
        create: function(QCELLProp){
            // console.log('--- GridUtil.create ---');
            // ----------------------------------------------------------------------
            // 기본 옵션 설정
            // ----------------------------------------------------------------------
            // QCELLProp.original = true;
            // 그리드영역
            if(CmmnUtil.isEmpty(QCELLProp.parentid)) {
                QCELLProp.parentid = QCELL.PARENTID;
            }
            // 그리드아이디
            if(CmmnUtil.isEmpty(QCELLProp.id)) {
                QCELLProp.id = _QCELL.ID;
            }
            // 빈데이터 메세지
            if(CmmnUtil.isEmpty(QCELLProp.emptymessage)) {
                QCELLProp.emptymessage = MSG_CODE.SYS06;
            }
            // 헤더옵션
            if(CmmnUtil.isEmpty(QCELLProp.rowheaders  )) {
                QCELLProp.rowheaders   = [_QCELL.SEQ , _QCELL.STATE];
            }
            // 헤더옵션을 사용하지 않음 (_QCELL.NONE)
            // QCELLProp.rowheaders = [_QCELL.NONE];
            if(!CmmnUtil.isEmpty(QCELLProp.rowheaders) && ($.isArray(QCELLProp.rowheaders) && QCELLProp.rowheaders.length == 1)) {
                if(CmmnUtil.isEquals(_QCELL.NONE,QCELLProp.rowheaders[0])){
                    QCELLProp.rowheaders   = [];
                }
            }
            // 선택모드
            if(CmmnUtil.isEmpty(QCELLProp.selectmode  )) {
                QCELLProp.selectmode   = _QCELL.ROW;
            }
            // DEL키모드(2020.04.06 QCELL_v2.1.91패치 추가)
            if(CmmnUtil.isEmpty(QCELLProp.delkeymode  )) {
                QCELLProp.delkeymode   = _QCELL.FOCUS;
            }
            // 시트높이
            if(CmmnUtil.isEmpty(QCELLProp.rowheight   )) {
                QCELLProp.rowheight    = {header: 30, data: 25};
            }
            // 에디터활성화방식
            if(CmmnUtil.isEmpty(QCELLProp.oneclickedit)) {
                QCELLProp.oneclickedit = false;
            }
            // 복사사용여부
            if(CmmnUtil.isEmpty(QCELLProp.copy)) {
                QCELLProp.copy   = true;
            }
            // 붙여넣기사용여부
            if(CmmnUtil.isEmpty(QCELLProp.paste)) {
                QCELLProp.paste  = true;
            }
            // 데이터백업사용여부
            if(CmmnUtil.isEmpty(QCELLProp.original)) {
                QCELLProp.original = true;
            }
            // 반복행기본설정
            if(CmmnUtil.isEmpty(QCELLProp.highlightrepeat)) {
                QCELLProp.highlightrepeat = 2;
            }
            // 넓이
            if(CmmnUtil.isEmpty(QCELLProp.width)) {
                QCELLProp.width  = "100%";
            }
            // 높이
            if(CmmnUtil.isEmpty(QCELLProp.height)) {
                QCELLProp.height = "100%";
            }
            // 하단고정(bottom)높이
            if(CmmnUtil.isEmpty(QCELLProp.bottompanelheight)) {
                QCELLProp.bottompanelheight = 25;
            }
            
            if(QCELLProp.bottompanel && QCELLProp.bottompanelstyle != "custom") {
                QCELLProp.bottompanelstyle    ='custom';
                QCELLProp.bottompanelcallback =  function(row, col, data){ return ' [' + (row - this.getRows('header') +1 ) +'/'+(this.getRows()-this.getRows('header')) + ']'};
            }
            
            var columnList = QCELLProp.columns;
            $.each(columnList,function(idx,obj){
                var alignD         = obj.hasOwnProperty("alignD");
                var alignH         = obj.hasOwnProperty("alignH");
                var styleclassname = obj.hasOwnProperty("styleclassname");
                //console.log("alignD : " , alignD , " , alignH : " , alignH);
                //console.log("styleclassname : " , styleclassname);
                // 데어터 스타일
                if(alignD) {
                    if(styleclassname) {
                        obj.styleclassname.data = "align-".concat(obj.alignD);
                    } else {
                        obj["styleclassname"] = {data: "align-".concat(obj.alignD)};
                    }
                }
                
                // 헤더 스타일
                if(alignH) {
                    if(styleclassname) {
                        obj.styleclassname.header = "align-".concat(obj.alignH);
                    } else {
                        obj["styleclassname"] = {header: "align-".concat(obj.alignH)};
                    }
                }
                
                var required  = obj.hasOwnProperty(_QCELL.OPTIONS)?(obj.options.hasOwnProperty(_QCELL.REQUIRED)?obj.options.required:false):false;
                var title     = required?(obj.hasOwnProperty(_QCELL.TITLE)?((obj.title[0].trim()).replace(/\*/g,'').trim()).concat("*"):o.title[0]):obj.title[0];
                //if(required) console.log('=> target : ' , QCELLProp.id  ,'의 (' , idx ,')번째 필수값여부 : ' , required ,' \n변경전 : [' , obj.title[0] , '] \n변경후 : [' , title , ']');
                columnList[idx].title[0] = title;
            });
            
            
            
            QCELL.create(QCELLProp);
            
            //var qcellId = QCELLProp.id;
            //if(!CmmnUtil.isEmpty(QCELLProp.id)) {GridUtil.getRequired(QCELL.getInstance(QCELLProp.id));}
            //setTimeout(function(){},100);
            
        },
        
        
        /*****************************************************************************************
         * GridUtil.createPop(QCELLProp);
         * return : void
         ****************************************************************************************/
        createPop: function(QCELLProp){
            
            GridUtil.create(QCELLProp);
            
            var qcellId = QCELL.getInstance(QCELLProp.id);
            
            // 더블클릭시
            qcellId.bind("dblclick",function(e){
                console.log('@ POPUP QCELL DBLCLICK');
                var target   = e.data.target;
                var col      = Number(target.getIdx("col"));
                var row      = Number(target.getIdx("row"));
                var callback = $("#POP_CALLBACK").length>0?$("#POP_CALLBACK").val():"";
                PopUtil.setData(qcellId.getRowData(row),callback);
                //if(!CmmnUtil.isEmpty(callback)){}
                
            });
            
            // 키다운시
            qcellId.bind("keydown" ,function(e){
                console.log('@ POPUP QCELL KEYDOWN');
                var target   = e.data.target;
                var col      = Number(target.getIdx("col"));
                var row      = Number(target.getIdx("row"));
                var callback = $("#POP_CALLBACK").length>0?$("#POP_CALLBACK").val():"";
                var keyCode  = e.which ? e.which : e.keyCode;
                if(CmmnUtil.isEquals(keyCode,13)||CmmnUtil.isEquals(keyCode,32)){
                    PopUtil.setData(qcellId.getRowData(row),callback);
                    //if(!CmmnUtil.isEmpty(callback)){}
                } else if(CmmnUtil.isEquals(keyCode,27)){
                    window.close();
                }
            });
        },
        
        
        /*****************************************************************************************
         * GridUtil.getRequired();
         * return : void
         ****************************************************************************************/
        getRequired : function() {
            //console.log('--- getRequired ---');
            $(".rt-qc-colheader-area span").each(function(i,v){
                var title = $(v).text();
                if(title.indexOf("*")>-1){
                    $(v).text(title.replace("*",""));
                    $(this).after('<b style="color:red;">*</b>');
                }
            });
        },
        
        /*****************************************************************************************
         * 큐셀 컬럼에 대한 데이트포맷을 설정한다.(컬럼타입- type : "static" or "input" or "datepicker") 
         * GridUtil.getDateFormat(); => TO_CHAR(SYSDATE,'YYYYMMDD')
         * return : object
         ****************************************************************************************/
        getDateFormat : function(){
            return {type: "date", origin: "YYYYMMDD", rule: "YYYY-MM-DD"};
        },
        
        /*****************************************************************************************
         * 큐셀 컬럼에 대한 데이트포맷을 설정한다.(컬럼타입- type : "static" or "input" or "datepicker") 
         * GridUtil.getDateTimeFormat(); => TO_CHAR(SYSDATE,'YYYYMMDD HH24MISS')
         * return : object
         ****************************************************************************************/
        getDateTimeFormat : function(){
            return {type: "date", origin: "YYYYMMDD hhmmss", rule: "YYYY-MM-DD a hh:mm:ss"};
        },
        
        /*****************************************************************************************
         * 큐셀 컬럼에 대한 숫자포맷을 설정한다.(컬럼타입- type : "static" or "input") 
         * GridUtil.getNumberFormat();
         * return : object
         ****************************************************************************************/
        getNumberFormat : function(){
            return {type: "number", rule:"#,##0"};
        },
        
        /*****************************************************************************************
         * 큐셀 컬럼에 대한 금액포맷을 설정한다.(컬럼타입- type : "static" or "input") 
         * GridUtil.getNumberFormat();
         * return : object
         ****************************************************************************************/
        getPriceFormat : function(){
            return {type: "number", rule:"￦ #,##0"};
        },
        
        /*****************************************************************************************
         * 큐셀 컬럼에 대한 데이트포맷을 설정한다.(컬럼타입- type : "static" or "input" or "datepicker") 
         * GridUtil.getPhoneFormat();
         * return : object
         ****************************************************************************************/
        getPhoneFormat  : function(){
            return {type: "custom", callback:function(data){return data.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");}}
        },
        
        /*****************************************************************************************
         * 포커스 사용하는 데이터 조회
         * GridUtil.searchFocusUse(qcellId,data,callback);
         * return : void
         ****************************************************************************************/
        searchFocusUse  : function(qcellId,data,callback){
            this.search(qcellId,data,callback,true);
        },
        
        /*****************************************************************************************
         * 포커스 미사용하는 데이터 조회
         * GridUtil.searchFocusNot(qcellId,data,callback);
         * return : void
         ****************************************************************************************/
        searchFocusNot  : function(qcellId,data,callback){
            this.search(qcellId,data,callback,false);
        },
        
        /*****************************************************************************************
         * 데이터 조회(default:포커스사용)
         * GridUtil.search(qcellId,data,callback,focus);
         * return : void
         ****************************************************************************************/
        search: function(qcellId,data,callback,focus){
            try {
                
                CmmnUtil.loadingStart();
                
                qcellId.showProgress();
                qcellId.setData(data);
                qcellId.hideProgress();
                
                var qcells = QCELL.getInstanceIDs().sort();
                if(qcells.length>0 && CmmnUtil.isEquals(qcells[0],qcellId.getID()) && data.length==0) {
                    CmmnUtil.setDefIptFocus();
                }
                
                if(!CmmnUtil.isEmpty(callback) && $.isFunction(callback)){
                    callback(data);
                }
                qcellId.clearStates();
                
                // 포커스 처리
                focus = CmmnUtil.isEmpty(focus) ? true : focus;
                if(focus && data.length > 0){
                    qcellId.scrollTop(qcellId.getRows(_QCELL.HEADER));
                    qcellId.scrollLeft(qcellId.getCols(_QCELL.HEADER));
                    setTimeout(function(){
                    qcellId.focusCell(1,qcellId.getCols(_QCELL.HEADER));
                    qcellId.clickCell(1,qcellId.getCols(_QCELL.HEADER));
                    GridUtil.getRequired(QCELL.getInstance(qcellId.getID()));
                    },100);
                } else {
                    GridUtil.getRequired(QCELL.getInstance(qcellId.getID()));
                }
                
            } catch(error) {
                CmmnUtil.progressBar(1000);
                console.log('error : ' , error);
                alert(error);
            } finally {
                CmmnUtil.loadingEnd();
                GridUtil.beforeClickRow(qcellId);
            }
            
        },
        /*****************************************************************************************
         * 데이터 삭제
         * GridUtil.removeAll(qcellId);
         * return : void
         ****************************************************************************************/
        removeAll: function(qcellId){
            qcellId.setData([]);
        },
        
        /*****************************************************************************************
         * 엑셀 다운로드
         * GridUtil.excelDownload(qcellId,propInfo);
         * return : void
         ****************************************************************************************/
        excelDownload: function(qcellId,propInfo){
            CmmnUtil.loadingStart();
            if(CmmnUtil.isEmpty(propInfo)) {
                propInfo = {};
                propInfo.filename = "";
            }
            
            if(CmmnUtil.isEmpty(propInfo.filename)) {
                propInfo.filename = "excel_".concat(DateUtil.getTimeStamp());
            }
            
            var properties = {
                filename:       propInfo.filename,
                border:         true,
                headershow:     true,
                colwidth:       true
            }
            
            if(!CmmnUtil.isEmpty(propInfo.addColumns)) {
                properties.addColumns = propInfo.addColumns;
            }
            
            if(!CmmnUtil.isEmpty(propInfo.delColumns)) {
                properties.delColumns = propInfo.delColumns;
            }
            
            if(!CmmnUtil.isEmpty(propInfo.orderColumns)) {
                properties.orderColumns = propInfo.orderColumns;
            }
            
            qcellId.excelDownload(properties);
            CmmnUtil.progressBar(1000);
        },
        
        /****************************************************************************************
         * 데이터 출력
         * GridUtil.print();
         * return : void
         ****************************************************************************************/
        print: function(){
            window.print();
        },
        
        /*****************************************************************************************
         * 데이터 초기화
         * GridUtil.resetData(qcellId);
         * return : void
         ****************************************************************************************/
        resetData: function(qcellId,callback){
            //console.log('GridUtil.resetData()');
            try {
                qcellId.resetData();
                var dataCnt = qcellId.getRows(_QCELL.DATA);
                if(dataCnt > 0){
                    qcellId.clickCell(1,qcellId.getCols(_QCELL.HEADER));
                }
                if(!CmmnUtil.isEmpty(callback) && $.isFunction(callback)){
                    callback();
                }
            } catch(error) {
                console.log('error : ' , error);
            } finally {
                GridUtil.getRequired(QCELL.getInstance(qcellId.getID()));
            }
        },
        
        /*****************************************************************************************
         * 열 인덱스 반환
         * GridUtil.getColsOfKey(qcellId, string);
         * return : number
         ****************************************************************************************/
        getColsOfKey: function(qcellId,colId){
            return qcellId.getColsOfKey(colId)[0];
        },
        
        /*****************************************************************************************
         * 헤더키값 반환
         * GridUtil.getKeyOfCol(qcellId, number);
         * return : string
         ****************************************************************************************/
        getKeyOfCol: function(qcellId,colIndex){
            return qcellId.getKeyOfCol(Number(colIndex));
        },
        
        /*****************************************************************************************
         * 변경상태기준 변경된 데이터 반환
         * GridUtil.getStates(qcellId);
         * return : array -> {d:[],i:[],u:[]}
         ****************************************************************************************/
        getStates: function(qcellId){
            return qcellId.getStates();
        },
        
        /*****************************************************************************************
         * 행과변경상태를 포함하는 변경된 데이터 반환
         * GridUtil.getStatesEx(qcellId);
         * return : array -> [{row:1,data:{},status:'u'},{row:2,data:{},status:'i'},{row:3,data:{},status:'d'}]
         ****************************************************************************************/
        getStatesEx: function(qcellId){
            return qcellId.getStatesEx();
        },
        
        /*****************************************************************************************
         * 변경된 데이터의 상태값과 행정보를 가공처리하여 저장 데이터 반환
         * GridUtil.getSaveData(qcellId);
         * return : array -> [{ROW:1,STATUS:"U"},{ROW:2,STATUS:"I"},{ROW:3,STATUS:"D"}]
         ****************************************************************************************/
        getSaveData: function(qcellId){
            var saveList = new Array();
            var exList   = qcellId.getStatesEx();
            $.each(exList,function(i,v){
                v.data.ROW    = (v.row).toString();
                v.data.STATUS = (v.status).toUpperCase();
                saveList.push(v.data);
            });
            return saveList;
        },
        
        /*****************************************************************************************
         * 행추가 행번호반환(마지막에 행추가)
         * GridUtil.addRow(qcellId,data,callback);
         * return : number
         ****************************************************************************************/
        addRow: function(qcellId,data,callback){
            GridUtil.orgClickEvent = qcellId.getUserEventInfo().click;
            if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                qcellId.unbind("click");
            }
            //if(qcellId.isScroll(_QCELL.VERTICAL)){}
            qcellId.addRow(CmmnUtil.isEmpty(data) ? {} : data);
            qcellId.scrollTop(qcellId.getRows(_QCELL.DATA));
            setTimeout(function(){
                qcellId.focusCell(qcellId.getRows(_QCELL.DATA),qcellId.getCols(_QCELL.HEADER));
                qcellId.clickCell(qcellId.getRows(_QCELL.DATA),qcellId.getCols(_QCELL.HEADER));
                if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                    qcellId.bind('click' , GridUtil.orgClickEvent);
                }
                // SET BEFORE ROW 2020.03.26
                GridUtil.getCheckRowChange(qcellId,Number(qcellId.getRows(_QCELL.DATA)));
                if(!CmmnUtil.isEmpty(callback) && $.isFunction(callback)){
                    return callback(qcellId.getRows(_QCELL.DATA));
                } else {
                    return qcellId.getRows(_QCELL.DATA);
                }
            },100);
        },
        
        /*****************************************************************************************
         * 행추가 행번호반환(선택한위치에 행추가)
         * GridUtil.insertRow(qcellId,insertData);
         * return : array
         ****************************************************************************************/
        insertRow: function(qcellId,data,callback){
            GridUtil.orgClickEvent = qcellId.getUserEventInfo().click;
            if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                qcellId.unbind("click");
            }
            
            var dataSize  = qcellId.getRows(_QCELL.DATA);
            var selectRow = qcellId.getSelectedRow();
            if(selectRow == -1 && dataSize > 0) {
                qcellId.bind('click' , GridUtil.orgClickEvent);
                alert(MsgUtil.getMsg("SYS07"));
                return selectRow;
            } else 
            if(selectRow == -1 && dataSize == 0) {
                selectRow = 1;
            }
            
            qcellId.insertRow(selectRow,CmmnUtil.isEmpty(data) ? {} : data);
            if(qcellId.isScroll(_QCELL.VERTICAL)){qcellId.scrollTop(selectRow-1);}
            setTimeout(function(){
                qcellId.focusCell(selectRow,qcellId.getCols(_QCELL.HEADER));
                qcellId.clickCell(selectRow,qcellId.getCols(_QCELL.HEADER));
                if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                    qcellId.bind('click' , GridUtil.orgClickEvent);
                }
                // SET BEFORE ROW 2020.03.26 
                GridUtil.getCheckRowChange(qcellId,Number(selectRow));
                if(CmmnUtil.isEmpty(callback)){
                    return selectRow;
                } else {
                    return callback(selectRow);
                }
            },100);
        },
        
        /*****************************************************************************************
         * 행추가 행번호반환(선택한위치에 아래로행추가)
         * GridUtil.insertRowDrop(qcellId,insertData);
         * return : array
         ****************************************************************************************/
        insertRowDrop: function(qcellId,data,callback){
            GridUtil.orgClickEvent = qcellId.getUserEventInfo().click;
            if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                qcellId.unbind("click");
            }
            
            var dataSize  = qcellId.getRows(_QCELL.DATA);
            var selectRow = qcellId.getSelectedRow();
            if(selectRow == -1 && dataSize > 0) {
                qcellId.bind('click' , GridUtil.orgClickEvent);
                alert(MsgUtil.getMsg("SYS07"));
                return selectRow;
            } else 
            if(selectRow == -1 && dataSize == 0){
                selectRow = 1;
            } else {
                selectRow = selectRow+1;
            }
            
            qcellId.insertRow(selectRow,CmmnUtil.isEmpty(data) ? {} : data);
            if(qcellId.isScroll(_QCELL.VERTICAL)){qcellId.scrollTop(selectRow-1);}
            setTimeout(function(){
                qcellId.focusCell(selectRow,qcellId.getCols(_QCELL.HEADER));
                qcellId.clickCell(selectRow,qcellId.getCols(_QCELL.HEADER));
                if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                    qcellId.bind('click' , GridUtil.orgClickEvent);
                }
                // SET BEFORE ROW 2020.03.26 
                GridUtil.getCheckRowChange(qcellId,Number(selectRow));
                if(CmmnUtil.isEmpty(callback)){
                    return selectRow;
                } else {
                    return callback(selectRow);
                }
            },100);
        },
        
        /*****************************************************************************************
         * 행삭제 
         * GridUtil.deleteRow(qcellId,realFlag);
         * return : boolean
         ****************************************************************************************/
        deleteRow: function(qcellId,realFlag){
            //console.log('GridUtil.deleteRow()');
            var maxiumRow = qcellId.getRows(_QCELL.DATA);
            var selectRow = qcellId.getSelectedRow();
            if (selectRow == -1) {
                alert(MsgUtil.getMsg("SYS07"));
                return false;
            }
            
            realFlag = CmmnUtil.isEmpty(realFlag) ? false: (typeof realFlag == "boolean" ? realFlag : (typeof realFlag == "string" && (realFlag == "true"||realFlag == "false") ? Boolean(realFlag) : false));
            
            GridUtil.orgClickEvent = qcellId.getUserEventInfo().click;
            if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                qcellId.unbind("click");
            }
            
            if(CmmnUtil.isEquals(GridUtil.getFocusRowState(qcellId),_QCELL.I)){
                GridUtil.getRequired();
                qcellId.deleteRow(selectRow);
                var cRow = CmmnUtil.isEquals(selectRow,maxiumRow)?selectRow-1:selectRow;
                if(qcellId.isScroll(_QCELL.VERTICAL)){
                    qcellId.scrollTop(cRow-1);
                    setTimeout(function(){
                        if(maxiumRow != 1) {
                            qcellId.focusCell(cRow,qcellId.getCols(_QCELL.HEADER));
                            qcellId.clickCell(cRow,qcellId.getCols(_QCELL.HEADER));
                        }
                        // SET BEFORE ROW 2020.03.26 
                        GridUtil.getCheckRowChange(qcellId,Number(cRow));
                        if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                            return qcellId.bind('click' , GridUtil.orgClickEvent);
                        }
                    },100);
                } else {
                    setTimeout(function(){
                        if(maxiumRow != 1) {
                            qcellId.focusCell(cRow,qcellId.getCols(_QCELL.HEADER));
                            qcellId.clickCell(cRow,qcellId.getCols(_QCELL.HEADER));
                        }
                        // SET BEFORE ROW 2020.03.26 
                        GridUtil.getCheckRowChange(qcellId,Number(cRow));
                        if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                            return qcellId.bind('click' , GridUtil.orgClickEvent);
                        }
                    },100);
                }
                
            } else {
                if(realFlag) {
                    if(!confirm(MsgUtil.getMsg("DDD01"))) return qcellId.bind('click' , GridUtil.orgClickEvent);
                    GridUtil.getRequired();
                    qcellId.deleteRow(selectRow);
                    var cRow = CmmnUtil.isEquals(selectRow,maxiumRow)?selectRow-1:selectRow;
                    if(qcellId.isScroll(_QCELL.VERTICAL)){
                        qcellId.scrollTop(cRow-1);
                        setTimeout(function(){
                            if(maxiumRow != 1) {
                                qcellId.focusCell(cRow,qcellId.getCols(_QCELL.HEADER));
                                qcellId.clickCell(cRow,qcellId.getCols(_QCELL.HEADER));
                            }
                            // SET BEFORE ROW 2020.03.26 
                            GridUtil.getCheckRowChange(qcellId,Number(cRow));
                            if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                                return qcellId.bind('click' , GridUtil.orgClickEvent);
                            }
                        },100);
                    } else {
                        setTimeout(function(){
                            if(maxiumRow != 1) {
                                qcellId.focusCell(cRow,qcellId.getCols(_QCELL.HEADER));
                                qcellId.clickCell(cRow,qcellId.getCols(_QCELL.HEADER));
                            }
                            // SET BEFORE ROW 2020.03.26 
                            GridUtil.getCheckRowChange(qcellId,Number(cRow));
                            if(!CmmnUtil.isEmpty(GridUtil.orgClickEvent)) {
                                return qcellId.bind('click' , GridUtil.orgClickEvent);
                            }
                        },100);
                    }
                }
            }
            
            return qcellId.bind('click' , GridUtil.orgClickEvent);
        },
        /*****************************************************************************************
         * 선택된 행의 상태값을 반환
         * GridUtil.getFocusRowState(qcellId);
         * return : string
         ****************************************************************************************/
        getFocusRowState: function(qcellId){
            if(!CmmnUtil.isEmpty(qcellId.getFocusRowState())){
                return qcellId.getFocusRowState()[0];
            } else {
                return "";
            }
        },
        
        /*****************************************************************************************
         * 행 데이터 반환
         * GridUtil.getRowData(qcellId, row);
         * return : object
         ****************************************************************************************/
        getRowData: function(qcellId,row){
            if(CmmnUtil.isEmpty(row)){
                return qcellId.getRowData(qcellId.getSelectedRow());
            } else {
                return qcellId.getRowData(row);
            }
        },
        
        /*****************************************************************************************
         * 선택된 행번호 반환
         * GridUtil.getSelectedRow(qcellId);
         * return : number
         ****************************************************************************************/
        getSelectedRow: function(qcellId){
            return qcellId.getSelectedRow();
        },
        
        /*****************************************************************************************
         * 선택된 행의 셀값 반환
         * GridUtil.getCellData(qcellId,col);
         * return : string
         ****************************************************************************************/
        getCellData: function(qcellId,col){
            return qcellId.getCellData(qcellId.getSelectedRow(), col);
        },
        
        /*****************************************************************************************
         * 시트의 필수값 체크
         * GridUtil.checkRequiredValue(qcellId);
         * return : boolean
         ****************************************************************************************/
        checkRequiredValue : function(qcellId) {
            if(CmmnUtil.isEmpty(qcellId)) return false;
            var checkedFields   = qcellId.checkRequired();
            var uninputtedField = "";
            if(checkedFields.length > 0) {
                for(i=0; i <checkedFields.length; i++ ) {
                    var row = checkedFields[i].row;
                    var col = checkedFields[i].col;
                    //var label = qcell.getCellLabel(row,col);
                    alert(MsgUtil.getMsg("SYS10","행:" + row + "," + "열:" + col));
                    qcellId.scrollTop(row);
                    qcellId.focusCell(row, col);
                    qcellId.clickCell(row, col);
                    //qcellId.dblClickCell(row, col);
                    return false;
                }
            }
            return true;
        },
        
        /*****************************************************************************************
         * 시트의 중복값 체크
         * GridUtil.checkDupCellsValue(qcellId,colId);
         * return : boolean
         ****************************************************************************************/
        checkDupCellsValue : function(qcellId,colId) {
            if(CmmnUtil.isEmpty(qcellId)||CmmnUtil.isEmpty(colId)) return false;
            
            var cols = new Array();
            if($.isArray(colId)) {
                cols = colId;
            } else {
                cols.push(colId);
            }
            
            console.log(cols);
            var chkFlag   = true;
            $.each(cols,function(i,v){
                // console.log(">> i : " , i , "v : " , v);
                
                var dupData   = qcellId.getColData(GridUtil.getColsOfKey(qcellId, v));
                var result    = [];
                var dupRow    = -1;
                var dupValue  = "";

                $.each(dupData, function(index, element) {
                    //console.log(index , '>> ' , element);
                    if ($.inArray(element, result) == -1) {
                        result.push(element);
                        chkFlag = true;
                        return true;
                    } else {
                        dupRow   = index+1;
                        dupValue = element;
                        chkFlag = false;
                        return false;
                    }
                });
                
                if(!chkFlag){
                    // console.log('>> 중본된 행 : ' , dupRow , ">> 중복된 값 : " , dupValue);
                    var label = qcellId.getCellLabel(0,GridUtil.getColsOfKey(qcellId, v)).replace("*","");
                    alert(MsgUtil.getMsg("SYS11", label, dupRow, dupValue));
                    qcellId.focusCell(dupRow,GridUtil.getColsOfKey(qcellId, v));
                    qcellId.clickCell(dupRow,GridUtil.getColsOfKey(qcellId, v));
                    return chkFlag;
                }
                return chkFlag;
            });
            
            return chkFlag;
        },
        
        /*****************************************************************************************
         * 생성된 시트객체에 이전행정보 생성옵션추가
         * GridUtil.beforeClickRow(qcellId,newRow);
         * return : boolean
         ****************************************************************************************/
        // 해당시트에 클릭된 행정보 감지(이전행,현재행비교)
        beforeClickRow : function(qcellId){
            return qcellId.beforeClickRow = new Array();
        },
        
        /*****************************************************************************************
         * 시트의 행선택 변경상태를 체크한다.
         * GridUtil.getCheckRowChange(qcellId,newRow);
         * return : boolean
         ****************************************************************************************/
        getCheckRowChange : function(qcellId,newRow) {
            var changeRow = true;
            var newRow    = Number(newRow);
            try {
                if(CmmnUtil.isEmpty(qcellId)||CmmnUtil.isEmpty(newRow)) throw "파라미터 : qcellId 또는 newRow 를 설정하세요.";
                //beforeClickRow 없어서.. 오류 발생 임시 ==> 공통에서 처리해주세요.
                if(CmmnUtil.isEmpty(qcellId.beforeClickRow)){
                    qcellId.beforeClickRow = [];
                }
                if(qcellId.beforeClickRow.length == 0) {
                    qcellId.beforeClickRow.push(newRow);
                    changeRow = false;
                    return false;
                }
                changeRow = $.inArray(newRow,qcellId.beforeClickRow)>-1?false:true;
                if(!changeRow) { 
                    return true;
                } else {
                    qcellId.beforeClickRow.push(newRow);
                    qcellId.beforeClickRow.shift();
                    return false;
                }
                
            } catch (e) {
                err_message = "호출함수 : GridUtil.getCheckRowChange(qcellId,newRow);\n" + e;
                alert(err_message);
            } finally {
                //console.log('이전행 : ' , qcellId.beforeClickRow[0], '/ 현재행 : ' , newRow , '변경상태 : ' , changeRow);
            }
        }
        
    };
    window.GridUtil = GridUtil.init();
});