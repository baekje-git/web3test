$(function(){
    
    var IBUtil = {
    	
        init: function(){
            return this;
        },
        
        /***********************************************************************************************
        함수명   : insertSortColumn
        설명     : IBSheet 그리드내 입력버튼 클릭시 새로운 로우에 정렬순번을 지정하고 그 이후의 정렬순번
                   은 1씩 증가 하도록 처리함
        파라미터 : pSheetId (시트ID) , pSortColumn (정렬컬럼)
        예제     : IBUtil.insertSortColumn(Sheet1 , "SORT_NO"); 
        컬럼명을 SEQ로 대처가능  Name 을 SEQ로 작성 가능 
        ************************************************************************************************/
        insertSortColumn : function(pSheetId,pSortColumn) {
            sheetObj   = window[pSheetId] == undefined ? pSheetId : window[pSheetId];
            var focusRow = sheetObj.getFocusedRow();
            var index = sheetObj.getRowIndex(focusRow);
            sheetObj.setValue(focusRow, pSortColumn, sheetObj.getValue(sheetObj.getRowByIndex(index-1), pSortColumn)+1  );

            var Rows = sheetObj.getDataRows();
            for(var i=0; i<Rows.length; i++){
                if( i == 0) continue;
                if(sheetObj.getValue(Rows[i], pSortColumn) == sheetObj.getValue(Rows[i-1], pSortColumn)){
                    sheetObj.setValue(Rows[i], pSortColumn, sheetObj.getValue(Rows[i], pSortColumn) + 1);
                } else {
                    break;
                }
            }
            pSheetId.renderBody();
        },

        /***********************************************************************************************
        함수명   : markedUp
        설명     : IBSheet내의 현재로우 위치를 한칸 위로 올린다. 정렬컬럼의 경우 서로 맞바꾼다.
        파라미터 : pSheetId (시트ID) , pSortColumn (정렬컬럼)
        예제     : IBUtil.markedUp(Sheet1 , "SORT_NO");
        컬럼명을 SEQ로 대처가능  Name 을 SEQ로 작성 가능 , 현재 위치에서 한칸 위로 올린다
        ************************************************************************************************/
        markedUp : function(pSheetId , pSortColNm) {
            sheetObj   = window[pSheetId] == undefined ? pSheetId : window[pSheetId];
            //SheetId   = window[pSheetId];

            if(sheetObj.getShownRows()[0] == sheetObj.getFocusedRow() ) return;
            //if(SheetId.GetTopRow() == SheetId.GetSelectRow() ) return;
            
            var index = sheetObj.getRowIndex(sheetObj.getFocusedRow());
            sheetObj.moveRow({row:sheetObj.getFocusedRow() , next:sheetObj.getRowByIndex(index-1)});
        },

        /***********************************************************************************************
        함수명   : markedDown
        설명     : IBSheet내의 현재로우 위치를 한칸 아래로 내린다. 정렬컬럼의 경우 서로 맞바꾼다.
        파라미터 : pSheetId (시트ID) , pSortColumn (정렬컬럼)
        예제     : IBUtil.markedDown(Sheet1 , "SORT_NO");
        컬럼명을 SEQ로 대처가능  Name 을 SEQ로 작성 가능 , 현재 위치에서 한칸 아래로 내린다
        ************************************************************************************************/
        markedDown : function(pSheetId , pSortColNm) {
            sheetObj   = window[pSheetId] == undefined ? pSheetId : window[pSheetId];
            if(sheetObj.getLastRow() == sheetObj.getFocusedRow() ) return;
            var index = sheetObj.getRowIndex(sheetObj.getFocusedRow());
            sheetObj.moveRow({row:sheetObj.getFocusedRow() , next:sheetObj.getRowByIndex(index+1)});
        },
        /***********************************************************************************************
        함수명   : findSameValueRowArray
        설명     : IBSheet내에 비교문자열과 동일한 행 번호를 배열로 리턴한다.
        파라미터 : sheet(시트), colNm(컬럼명) , findVal(비교값)
        예제     : IBUtil.findSameValueRowArray(Sheet1 , "ITEM_CD" ,"0000210");
        ************************************************************************************************/
        findSameValueRowArray : function(sheet, colNm , findVal, pHidDelYn){
        	var vHidYn = CmmnUtil.getDefaultString(pHidDelYn,"Y");
            var rtn = [];
            var Rows = sheet.getDataRows();
            for(var i=0; i<Rows.length; i++){
                if(Rows[i][colNm] == findVal){
                    if(Rows[i].Deleted == true && vHidYn == "N") continue;
                    rtn.push(i);
                }
            }
            return rtn;
        },
        /***********************************************************************************************
        함수명   : findSameValueRowObjectArray
        설명     : IBSheet내에 비교문자열과 동일한 행의 데이터를 Object 배열로 리턴한다.  
        파라미터 : sheet(시트), colNm(컬럼명) , findVal(비교값)
        예제     : IBUtil.findSameValueRowObjectArray(Sheet1 , "ITEM_CD" ,"0000210");
         ************************************************************************************************/
        findSameValueRowObjectArray : function(sheet, colNm , findVal){
            var rtnObj = [];
            var Rows = sheet.getDataRows();
            for(var i=0; i<Rows.length; i++){
                if(Rows[i][colNm] == findVal){
                    if(Rows[i].Deleted == true && vHidYn == "N") continue;
                    rtnObj.push(Rows[i]);
                }
            }
            return rtnObj;
        },
        /***********************************************************************************************
        함수명   : sheetHideShowItem
        설명     : IBSheet내에 비교문자열과 동일한 행은 보여지고 나머지 행은 히든 처리 한다.
                   비교값에 'showAll'을 넘기면 모든 로우가 보여진다.
        파라미터 : Sheet(시트), colNm(컬럼명) , compareValue(비교값)
        예제     : IBUtil.sheetRowHideShowValue(Sheet1 , "ITEM_CD" ,"0000210");
        ************************************************************************************************/
        sheetRowHideShowValue : function (sheet , colNm ,compareValue){
            var Rows = sheet.getDataRows();
            for (var i = 0 ; i < Rows.length; i++){
                if(Rows[i].Deleted != true && (Rows[i][colNm] == compareValue || compareValue == 'showAll')){
                    Rows[i].Hidden = 1;
                }else{
                    Rows[i].Hidden = 0;
                }
            }
            sheet.renderBody();
        },

        /***********************************************************************************************
        함수명   : getInitData
        설명     : Sheet 에서 사용되는 data 선언정보를 JsonObject 로 반환한다.
        파라미터 : Sheet(시트)
        예제     : IBUtil.getInitData(Sheet1);
        ************************************************************************************************/
        getInitData : function (sheet) {
        	var dataSet = {};
        	var Cols = sheet.getCols();
    		for(var i=0; i< Cols.length; i++){
    		    if(sheet.getAttribute(null, Cols[i], "Type").toLowerCase() == "int"){
    		        dataSet[Cols[i]] = 0;
    		    } else if(sheet.getAttribute(null, Cols[i], "Type").toLowerCase() == "flate"){
    		        dataSet[Cols[i]] = 0.00;
    		    } else {
    		        dataSet[Cols[i]] = "";
    		    }
    		    if(!CmmnUtil.isEmpty(sheet.getAttribute(null, Cols[i], "DefaultValue")) ){
    		        dataSet[Cols[i].Name] = sheet.getAttribute(null, Cols[i], "DefaultValue");
    		    }
    		}
    		return dataSet;
        },

        /***********************************************************************************************
        함수명   : getInitRowData
        설명    : Sheet 사용 data 에 특정행의 특정column 정보만 data 로 반환.
        파라미터  : Sheet(시트), pRow(행), pSaveName[](입력값 사용 column)
        예제    : IBUtil.getInitData(Sheet1);
        사용 제외 - 행에 대한 정보는 getFocusedRow, getSelectedRows  등등 사용해서 정보를 추출한다
        ************************************************************************************************/
       /* getInitRowData : function (Sheet, pRow, pCols) {
        	var dataSet = IBUtil.getInitData(Sheet);
        	if(!CmmnUtil.isEmpty(pCols)){
        		$.each(pCols,function(idx, item){
        		    dataSet[item+""] = Sheet.getValue(pRow, item);
        		});
        	}
    		return dataSet;
        },
*/
        /***********************************************************************************************
        함수명   : setRowDataInit
        설명    : Sheet row data 초기화 상태변경
        파라미터  : Sheet(시트), pRow(행), pEvent(후처리이벤트여부), pExtObj(추가object)
        예제    : setRowDataInit(Sheet1, 1);
        사용 제외 - sheet7 에 SetRowData 함수 없음 무슨 기능인지 이해 안됨
        ************************************************************************************************/
       /* setRowDataInit : function (Sheet, pRow, pEvent, pExtObj){
        	var fvData = IBUtil.getInitData(Sheet);
        	var fvEvt = CmmnUtil.isEmpty(pEvent)? false:pEvent;
        	if(!CmmnUtil.isEmtpy(pExtObj)){
        		$.extend(fvData, pExtObj);
        	}
        	Sheet.SetRowData(pRow, fvData, {Event:fvEvt});
        },*/
        /***********************************************************************************************
        함수명   : setRowCellEditable
        설명     : IBSheet 특정 row cell 의 수정가능여부 변경처리
        파라미터 : Sheet(시트), pMode(0:전체변경, 1:해당cell만변경), pEditable(수정가능여부), pRow, pSaveNames(변경columns)
        예제     : setRowCellEditable(Sheet1,0,1,1,["DEL_CHK","BUY_ITEM_GB_CD","ITEM_CD","ITEM_QTY","DC_AMT","TOTAL_AMT"]);
        pRow 은 row object로 대처해서 사용 
        ************************************************************************************************/
        setRowCellEditable : function (sheet, pMode, pEditable, pRow, pSaveNames){
        	var vOpEditable = pEditable == 1 ? 0: 1;
        	var Cols = sheet.getCols();
            for(var i=0; i< Cols.length; i++){
                if($.grep(pSaveNames, function(item){return (item == Cols[i])}).length > 0){
                    sheet.setAttribute(pRow, Cols[i], "CanEdit", pEditable);
                }else if(pMode == 0){
                    sheet.setAttribute(pRow, Cols[i], "CanEdit", vOpEditable);
                }
            }
            sheet.renderBody();
        },
        /***********************************************************************************************
        함수명   : setRowCellBackColor
        설명     : IBSheet 특정 row cell 또는 row 의 색상설정
        파라미터 : Sheet(시트), pMode(0:전체변경, 1:해당cell만변경), pColor(색상구분값), pRow, pSaveNames(변경columns)
        예제     : setRowCellBackColor(Sheet1,0,"#FFFFFF",1,["DEL_CHK","BUY_ITEM_GB_CD","ITEM_CD","ITEM_QTY","DC_AMT","TOTAL_AMT"]);
        pRow 은 row object로 대처해서 사용
         ************************************************************************************************/
        setRowCellBackColor : function (sheet, pMode, pColor, pRow, pSaveNames){
            var vOpColor = (pColor == "" ? "": pColor);
            var Cols = sheet.getCols();
            for(var i=0; i< Cols.length; i++){
                if($.grep(pSaveNames, function(item){return (item == Cols[i])}).length > 0){
                    vOpColor = (vOpColor == "" ? (i % 2 == 0 ? "#FFFFFF" : "#EBFFFF") : vOpColor);
                    sheet.setAttribute(pRow, Cols[i], "Color", vOpColor);
                }else if(pMode == 0){
                     vOpColor = (vOpColor == "" ? (i % 2 == 0 ? "#FFFFFF" : "#EBFFFF") : vOpColor);
                     sheet.setAttribute(pRow, Cols[i], "Color", vOpColor);
                }
            }
            sheet.renderBody();
        },
        /***********************************************************************************************
        함수명   : setColumnEditable
        설명     : IBSheet 특정 column 의 수정가능여부 변경처리
        파라미터 : Sheet(시트), pMode(0:전체변경, 1:해당cell만변경), pEditable(수정가능여부), pSaveNames(변경columns)
        예제     : setColumnEditable(Sheet1,0,1,["DEL_CHK","BUY_ITEM_GB_CD","ITEM_CD","ITEM_QTY","DC_AMT","TOTAL_AMT"]);
        pRow 은 row object로 대처해서 사용
        ************************************************************************************************/
        setColumnEditable : function (sheet, pMode, pEditable, pSaveNames){
        	var vOpEditable = pEditable == 1 ? 0: 1;
            var Cols = sheet.getCols();
            for(var i=0; i< Cols.length; i++){
                if($.grep(pSaveNames, function(item){return (item == Cols[i])}).length > 0){
                    sheet.setAttribute(null, Cols[i], "CanEdit", pEditable);
                }else if(pMode == 0){
                     sheet.setAttribute(null, Cols[i], "CanEdit", vOpEditable);
                }
            }
            sheet.renderBody();
        },
        /***********************************************************************************************
        함수명   : getSumValueToJson
        설명     : IBSheet 특정 column 의 합계 값을 JsonObject 로 반환
        파라미터 : Sheet(시트), pSaveNames(변경columns), pCompare(특정Column값이 동일한경우만 Sum한다.)
        예제     : getSumValueToJsonObject(Sheet1 , ["TOTAL_AMT"], {"CHK" : "Y"});
        ************************************************************************************************/
        getSumValueToJsonObject : function (sheet, pSaveNames, pCompare){
        	var rJsonObj = {};
        	//초기화.
        	var Cols = sheet.getCols();
    		for(var i=0; i< Cols.length; i++){
    			if($.grep(pSaveNames, function(item){return (item == Cols[i])}).length > 0){
    				var fvType = sheet.getAttribute(null, Cols[i], "Type").toLowerCase() 
    				
    				if(fvType == "int"){
    					rJsonObj[Cols[i]] = 0;
    				}else if(fvType == "float"){
    					rJsonObj[Cols[i]] = 0.00;
    				}
    			}
    		}
    		//합계정보입력
    		var Rows = sheet.getDataRows();
    		for(var i=0; i< Rows.length; i++){
    			if(Rows[i].Deleted != true){
        			$.each(rJsonObj, function(key, value){
            			//추가비교조건 확인.
            			if(!CmmnUtil.isEmpty(pCompare)){
            				var fvFlag = true;
            				
            				$.each(pCompare, function(id,val){
            					if(sheet.getValue(Rows[i], id) != val){
            						fvFlag = false;
            						return false;        						
            					}
            				});
            				if(!fvFlag){
            					return true;
            				}
            			}
        				rJsonObj[key] = value + sheet.getValue(Rows[i], key);
        			});
    			}
    		}
    		return rJsonObj;
        },
        /***********************************************************************************************
        함수명   : sheetRowsDelete
        설명     : IBSheet 특정 row[] 에 따라 row 일괄삭제 처리
        파라미터 : Sheet(시트), pSaveName(비교대상 checkbox 이름), pHidDelYn(hidden 포함 삭제 여부), pCallBack(콜백)
        return : boolean (삭제 처리 결과)
        예제     : sheetRowsDelete(Sheet1 , ["1","2"], "N");
        
        ************************************************************************************************/
       /* sheetRowsDelete : function (Sheet, pRows, pHidDelYn, pCallBack, pSubSheet, pSyncKey){
        	var vRtn = false;
        	var vHidYn = CmmnUtil.getDefaultString(pHidDelYn,"Y");
        	if(CmmnUtil.isEmpty(pRows)){
        		return false;
        	}
        	
        	pRows = $.isArray(pRows)? pRows : [pRows];
        	
        	$(pRows.reverse()).each(function(i, row){
        		var fvData= {};
        		if(!CmmnUtil.isEmpty(fvData = Sheet.GetRowData(row))){
        			//row 상태가 삭제(D) 가 아닌경우
        			if(fvData.STATUS != 'D'){
        				//하위sheet 가 있는경우 하위sheet 삭제
        				if(!CmmnUtil.isEmpty(pSubSheet) && !CmmnUtil.isEmpty(pSyncKey)){
        	                //pSubSheet 의 pSyncKey 를 비교하고 해당 검색을 통해 DATA 를 삭제처리.
        					//배열인경우.
        					if($.isArray(pSyncKey)){
        						var fvSyncCols = {};
        						$.each(pSyncKey, function(idx, val){
        							fvSyncCols[val+""] = Sheet.GetCellValue(row, val);
        						});
        						var fvRows = IBUtil.getRowsFindValues(pSubSheet, fvSyncCols);
        						IBUtil.sheetRowsDelete(pSubSheet, fvRows, "Y");
        					}
        					//배열이 아닌경우.
        					else{
            	                var fvSyncCd = Sheet.GetCellValue(row, pSyncKey);
            	                if(IBUtil.isFindValue(pSubSheet, pSyncKey, fvSyncCd)){
            	                    IBUtil.sheetFindRowsDelete(pSubSheet, pSyncKey, fvSyncCd);
            	                }
        					}
        				}
        				
        				//Sheet row 의 상태가 숨김상태가 아닐때 삭제처리
        				if(!Sheet.GetRowHidden(row)){
        					//수정상태일 경우-data 조회 row 인경우
        					if(fvData.STATUS == 'I'){
        						//확인 메시지 없이 삭제 처리.
        						Sheet.RowDelete(row, 0);
        						vRtn = true;
        					}
        					//data 트랜잭션 조회 상태에서의 삭제-hidden 으로 처리.
        					else{
        						Sheet.SetCellValue(row, "STATUS", "D");
        						Sheet.SetRowHidden(row, 1);
        						vRtn = true;
        					}
        				}
        				//Sheet row 의 상태가 숨김상태이며 hidDelYn 이 Y 인경우 삭제
        				else if(vHidYn == "Y"){
        					//수정상태일 경우-data 조회 row 인경우
        					if(fvData.STATUS == 'I'){
        						//확인 메시지 없이 삭제 처리.
        						Sheet.RowDelete(row, 0);
        						vRtn = true;
        					}
        					//data 트랜잭션 조회 상태에서의 삭제-hidden 으로 처리.
        					else{
        						Sheet.SetCellValue(row, "STATUS", "D");
        						Sheet.SetRowHidden(row, 1);
        						vRtn = true;
        					}
        				}        				
        			}
        		}
        	});

        	if(!CmmnUtil.isEmpty(pCallBack) && $.isFunction(pCallBack)){
				pCallBack.call(this);
        	}
        	return vRtn;
        },*/
        /***********************************************************************************************
        함수명   : sheetChkRowsDelete
        설명     : IBSheet 특정 chekckbox column 의 check 여부에 따라 row 일괄삭제 처리
        파라미터 : Sheet(시트), pSaveName(비교대상 checkbox 이름), pHidDelYn(hidden 포함 삭제 여부-DEFAULT Y)
        return : boolean (삭제 처리 결과)
        예제     : sheetChkRowsDelete(Sheet1 , "CHK", delete_callback);
        ************************************************************************************************/
        sheetChkRowsDelete : function (sheet, pSaveName, pCallBack){
        	var vRows = sheet.getRowsByChecked(pSaveName);
        	var vRtn = false;
        	if(vRows.length == 0){
        	    alert(MsgUtil.getMsg("SYS08"));	//선택된 건이 없습니다.
        		return false;
        	}
        	sheet.deleteRows(vRows , 1);
        	
        	if(!CmmnUtil.isEmpty(pCallBack) && $.isFunction(pCallBack)){
                pCallBack.call(this);
            }
        },
        /***********************************************************************************************
        함수명   : sheetChkRowsConfirmDelete
        설명     : IBSheet 특정 chekckbox column 의 check 여부 확인후 기존 data 알림 confirm 후 삭제처리
        파라미터 : Sheet(시트), pSaveName(비교대상 checkbox 이름), fnCbRowDel_CallBack(콜백함수)
        예제     : sheetChkRowsConfirmDelete(Sheet1 , "CHK", fnCbRowDel_CallBack);
        이해 안됨
        ************************************************************************************************/
        /*sheetChkRowsConfirmDelete : function (Sheet, pSaveName, pSvCallBack, pFrCallBack, pSubSheet, pSyncKey){
        	var vRows = Sheet.FindCheckedRow(pSaveName);
        	var vRtn = false;
        	if(CmmnUtil.getDefaultString(vRows) == ""){
        	    alert(MsgUtil.getMsg("SYS08"));   //선택된 건이 없습니다.
        		return false;
        	}
        	//기존 저장 data 삭제 처리시 confirm
        	var fvStatus = false;
        	$.each(vRows.split('|'), function(idx, item){
        		if(Sheet.GetCellValue(item, "STATUS") == "U"){
        			fvStatus = true;
        			return false;
        		}
        	});
        	if(fvStatus){
        		CommonUtil.openDialogConfirm(MsgUtil.getMsg("DDD05"), function(rtn){
        			if(rtn){
        				//하위sheet 가 있는경우 하위sheet 삭제
        				if(!CmmnUtil.isEmpty(pSubSheet) && !CmmnUtil.isEmpty(pSyncKey)){
            	            $(vRows.split('|')).each(function(i, row){
            	                //pSubSheet 의 pSyncKey 를 비교하고 해당 검색을 통해 DATA 를 삭제처리.
            	                var fvSyncCd = Sheet.GetCellValue(row, pSyncKey);
            	                if(IBUtil.isFindValue(pSubSheet, pSyncKey, fvSyncCd)){
            	                    IBUtil.sheetFindRowsDelete(pSubSheet, pSyncKey, fvSyncCd);
            	                }
            	            });
        				}
        	            
        				if(IBUtil.sheetRowsDelete(Sheet, vRows.split('|'), "Y", pFrCallBack)){
        					pSvCallBack.call(this);
        				}
        			}
        		});
        	}else{
				//하위sheet 가 있는경우 하위sheet 삭제
				if(!CmmnUtil.isEmpty(pSubSheet) && !CmmnUtil.isEmpty(pSyncKey)){
    	            $(vRows.split('|')).each(function(i, row){
    	                //pSubSheet 의 pSyncKey 를 비교하고 해당 검색을 통해 DATA 를 삭제처리.
    	                var fvSyncCd = Sheet.GetCellValue(row, pSyncKey);
    	                if(IBUtil.isFindValue(pSubSheet, pSyncKey, fvSyncCd)){
    	                    IBUtil.sheetFindRowsDelete(pSubSheet, pSyncKey, fvSyncCd);
    	                }
    	            });
				}
        		IBUtil.sheetRowsDelete(Sheet, vRows.split('|'), "Y",pFrCallBack);
        	}        	
        },*/
        /***********************************************************************************************
        함수명   : getDataRowCount
        설명     : IBSheet getDataRowCount 를 반환한다.
        ************************************************************************************************/
        getDataRowCount : function (sheet, pStatus){
            var Rows = sheet.getRowsByStatus(pStatus);
            return Rows.length;
        	
        },
        /***********************************************************************************************
        함수명   : sheetFindRowsDelete
        설명     : IBSheet 특정 column 값 조회결과에 따라 row 일괄삭제 처리
        파라미터 : Sheet(시트), pSaveName(비교대상 checkbox 이름), pHidDelYn(hidden 포함 삭제 여부-DEFAULT Y)
        return : boolean (삭제 처리 결과)
        예제     : sheetFindRowsDelete(Sheet1 , "ITEM_QTY", "010101", "N");
        ************************************************************************************************/
        sheetFindRowsDelete : function (sheet, pSaveName, pFindValue){
        	var vRows = this.findSameValueRowArray(sheet,pSaveName, pFindValue);
        	var vRtn = false;
        	if(CmmnUtil.getDefaultString(vRows) == ""){
        		alert("없음");	//조회조건에 해당하는 자료가 없습니다.
        		return false;
        	}
        	for(i = 0 ; i < vRows.length; i++){
        	    sheet.deleteRow(sheet.getRowByIndex(vRows[i]) , 1);
        	}
        	
        },
        /***********************************************************************************************
        함수명   : sheetFindObjRowsDelete
        설명     : IBSheet 특정 column 값 조회결과에 따라 row 일괄삭제 처리
        파라미터 : Sheet(시트), pSaveName(비교대상 checkbox 이름), pHidDelYn(hidden 포함 삭제 여부-DEFAULT Y)
        return : boolean (삭제 처리 결과)
        예제     : sheetFindObjRowsDelete(Sheet1 , {ITEM_CD:010101}, "N");
        ************************************************************************************************/
        sheetFindObjRowsDelete : function (sheet, pJsonObj, pHidDelYn){
        	var vRows = this.getRowsFindValues(sheet,pJsonObj);
        	var vHidYn = CmmnUtil.getDefaultString(pHidDelYn,"Y");
        	/*
        	var vRtn = false;
        	if(CmmnUtil.getDefaultString(vRows) == ""){
        		CommonUtil.openDialogAlert(MsgUtil.getMsg("RRR03"));	//조회조건에 해당하는 자료가 없습니다.
        		return false;
        	}*/
        	sheet.deleteRows(vRows , 1);
        },
        /***********************************************************************************************
        함수명   : sheetFindRowsConfirmDelete
        설명     : IBSheet 특정 column 값 조회결과 확인후 기존 data 알림 confirm 후 삭제처리
        파라미터 : Sheet(시트), pSaveName(비교대상 checkbox 이름), fnCbRowDel_CallBack(콜백함수)
        예제     : sheetChkRowsConfirmDelete(Sheet1 , "CHK", fnCbRowDel_CallBack);
        이해 못함 ..  CommonUtil.openDialogConfirm 함수가 없어서 내용을 확인 할 수 없음
        ************************************************************************************************/
        /*sheetFindRowsConfirmDelete : function (Sheet, pSaveName,pFindValue, pSvCallBack, pFrCallBack){
        	var vRows = this.findSameValueRowArray(Sheet,pSaveName, pFindValue);
        	var vRtn = false;
        	if(CmmnUtil.getDefaultString(vRows) == ""){
        		CommonUtil.openDialogAlert(MsgUtil.getMsg("RRR03"));	//조회조건에 해당하는 자료가 없습니다.
        		return false;
        	}
        	//기존 저장 data 삭제 처리시 confirm
        	var fvStatus = false;
        	$.each(vRows.split('|'), function(idx, item){
        		if(Sheet.GetCellValue(item, "STATUS") == "U"){
        			fvStatus = true;
        			return false;
        		}
        	});
        	if(fvStatus){
        		CommonUtil.openDialogConfirm(MsgUtil.getMsg("DDD05"), function(rtn){
        			if(rtn){
        				if(IBUtil.sheetRowsDelete(Sheet, vRows.split('|'), "Y", pFrCallBack)){
        					pSvCallBack.call(this);
        				}
        			}
        		});
        	}else{
            	return this.sheetRowsDelete(Sheet, vRows, "Y", pFrCallBack);
        	}
        },*/
        /***********************************************************************************************
        함수명   : isFindValue
        설명     : IBSheet내에 비교문자열과 동일한 행 유무를 확인 한다.
        파라미터 : sheet(시트), pSaveName(컬럼명) , pFindVal(비교값), pSkipRow(비교 대상 제외 ROW Cnt)
        return	: boolean
        예제     : IBUtil.isFindValue(Sheet1 , "ITEM_CD" ,"0000210");
        ************************************************************************************************/
        isFindValue : function(sheet, pSaveName , pFindVal, pSkipRow){
            var vRtn = false;
            var Rows = sheet.getDataRows();
            for(var i=0 ; i < Rows.length ; i ++){
                if(Rows[i].Deleted == true) continue;
                if(!CmmnUtil.isEmpty(pSkipRow) && pSkipRow == sheet.getRowIndex(Rows[i])) continue;
                if(sheet.getValue(Rows[i],pSaveName) == pFindVal){
                    vRtn = true;
                    break;
                }
            }
            return vRtn;
        },
        /***********************************************************************************************
        함수명   : isFindValues
        설명     : IBSheet내에 비교문자열과 동일한 행 유무를 확인 한다.
        파라미터 : sheet(시트), pJsonVals(비교값)
        return	: boolean
        예제     : IBUtil.isFindValues(Sheet1 , {"ITEM_CD":"0000210"});
        ************************************************************************************************/
        isFindValues : function(Sheet, pJsonVals){
            // console.log('--- IBUtil.isFindValues ---');
            var vRtn = false;
            for(var i = Sheet.GetDataFirstRow() ; i <= Sheet.GetDataLastRow(); i++){
            	//삭제상태행은 제외처리.
            	if(Sheet.GetCellValue(i, "STATUS") == 'D') continue;

            	vRtn = false;
            	$.each(pJsonVals, function(key, value){
                    if(Sheet.GetCellValue(i, key) == value){
                        // console.log('key : ' , key , ' / ' , 'value : ' , value);
                    	vRtn = true;
                    }else{
                    	vRtn = false;
                    	return false;
                    }
            	});
            	if(vRtn){
            		break;
            	}
            }
            return vRtn;
        },
        /***********************************************************************************************
        함수명   : getRowsFindValues
        설명     : IBSheet내에 비교문자열과 동일한 행 을 배열로 반환한다.
        파라미터 : sheet(시트), pJsonVals(비교값)
        return	: array row object
        예제     : IBUtil.getRowsFindValues(Sheet1 , {"ITEM_CD":"0000210"});
        ************************************************************************************************/
        getRowsFindValues : function(sheet, pJsonVals){
            // console.log('--- IBUtil.getRowsFindValues ---');
            var vRtn = [];
            var Rows = sheet.getDataRows();
            for(var i=0; i < Rows.length; i++){
            	//삭제상태행은 제외처리.
                if(Rows[i].Deleted == true) continue;

                var vCompare = false;
                var vRowData = Rows[i];
            	$.each(pJsonVals, function(key, value){
            		if(Rows[i].hasOwnProperty(key)){
            		    // console.log('hasOwnProperty : ', vRowData.hasOwnProperty(key));
            		    //console.log('key : ' , key , ' / ' , 'value : ' , value, 'sheet' , sheet.getValue(Rows[i], key ));
                        
            		    if(sheet.getValue(Rows[i], key ) == value){
                        	vCompare = true;
                        }else{
                        	vCompare = false;
                        	return false;
                        }
            		}
            	});
            	if(vCompare){
            		vRtn.push(Rows[i]);
            	}
            }
            return vRtn;
        },
        /***********************************************************************************************
        함수명   : isDiffValue
        설명     : IBSheet내에 비교문자열과 다른 행 유무를 확인 한다. 행에 비교값이 있으면  true 없으면 false
    	파라미터 : sheet(시트), pSaveName(컬럼명) , pFindVal(비교값)
        return	: boolean
        예제     : IBUtil.isDiffValue(Sheet1 , "TEX_YN", "Y");
        ************************************************************************************************/
        isDiffValue : function (sheet, pSaveName , pFindVal){
            var vRtn = false;
            var Rows = sheet.getDataRows();
            for(var i=0; i < Rows.length;i++){
                if(Rows[i].Deleted == true) continue;
                if(!CmmnUtil.isEmpty(sheet.getValue(Rows[i], pSaveName)) && sheet.getValue(Rows[i], pSaveName) != pFindVal){
                    vRtn = true;
                    break;
                }
            }
        	return vRtn;
        },
        /***********************************************************************************************
        함수명   : getColNmByType
        설명     : IBSheet 특정 type 의 첫번째 column name 을 반환한다.
        파라미터 : Sheet(시트), pType(type)
        예제     : IBUtil.getColNmByType(Sheet1 , "Bool");
        ************************************************************************************************/
        getColNmByType : function (sheet, pType){
            sheet.getAttribute(null, pType)
        	var fvSvNm = "";
            var Cols = sheet.getCols();
    		for(var i=0; i< Cols.length; i++){
				if(sheet.getAttribute(null, Cols[i], "Type").toLowerCase() == pType.toLowerCase()){
					fvSvNm = Cols[i];
					break;
				}
    		}
    		return fvSvNm;
        },
        /***********************************************************************************************
        함수명   : getCellIndexByColNm
        설명     : IBSheet 특정 name 의 column index 을 반환한다.
        파라미터 : Sheet(시트), pColNm(컬럼명)
        예제     : IBUtil.getCellIndexByColNm(Sheet1 , "ITEM_NM");
        ************************************************************************************************/
        getCellIndexByColNm : function (sheet, pColNm){
            return sheet.getColIndex(pColNm);
        },

        /***********************************************************************************************
        함수명   : putColJsonObject
        설명    : Sheet pCols 에 jsonstring 값 입력.
        파라미터  : Sheet(시트), pRow(행), pCols(입력값 사용 column), pJsonObj, pReplace(빈값초기화여부 Y/N)
        예제    : IBUtil.putColJsonObject(Sheet,1,'CUSTDATA',{});
        이해 못함 . 한 컬럼에 그냥  JSON 데이타를 넣는 걸로 보이는데 사용여부 삭졔예정
        ************************************************************************************************/
/*        putColJsonObject : function (Sheet, pRow, pCols, pJsonObj, pReplace) {
        	var fvReplace = CmmnUtil.isEmpty(pReplace)? "Y":pReplace;
        	//수정인경우.
        	if(Sheet.GetCellValue(pRow,"STATUS")!='I' && !CmmnUtil.isEmpty(Sheet.GetCellValue(pRow, pCols))){
        		Sheet.SetCellValue(pRow, pCols, CommonUtil.mergeJsonString(JSON.parse(Sheet.GetCellValue(pRow, pCols)), pJsonObj, fvReplace)); 
        	}
        	//신규등록인 경우.
        	else{
        		Sheet.SetCellValue(pRow, pCols, JSON.stringify(pJsonObj)); 
        	}
        },*/
        /***********************************************************************************************
        함수명   : sheetRowEditableByCompare
        설명     : IBSheet내에 비교문자열과 동일하지 않은 행의 수정불가 및 BACKGROUNDCOLOR 변경처리.
        파라미터 : Sheet(시트), pCompareObj{}(비교컬럼:비교값)
        예제     : IBUtil.sheetRowEditableByCompare(Sheet1 , {"ITEM_CD":"0000210"});
        ************************************************************************************************/
        sheetRowEditableByCompare : function (sheet, pCompareObj){
        	var fvRow = 0;
        	var Rows = sheet.getDataRows();
            for (var i = 0 ; i < Rows.length; i++){
            	var fvComp = false;
                $.each(pCompareObj,function(id, val){
                	if(sheet.getValue(Rows[i] , id) == val ){
                		fvComp = true;
                		if(fvRow==0) fvRow = i;
                	}
                	else{
                		fvComp = false;
                		return false;
                	}
                });
                //비교 결과가 true 행은 수정가능처리.
                if(fvComp){
                    Rows[i].CanEdit = 1;
                }
                //비교결과 false 행은 수정불가처리.
                else{
                    Rows[i].CanEdit = 0;
                }
            }
            sheetA.renderBody();
        },
        /***********************************************************************************************
        함수명   : isCompareType
        설명     :pSheet 내에 cell type 을 비교하고 true/false 반환..
        파라미터 : pSheet(기준sheet), pRow(행 Object), pCol(컬럼), pCompareType(타입)
        예제     : IBUtil.isCompareType(Sheet1,Sheet1.getFocuesdRow(),"ITEM_QTY", "int");
        ************************************************************************************************/
        isCompareType : function (sheet, pRow, pCol, pCompareType){
    		var fvIs = false;
			if(sheet.getAttribute(pRow, pCol, "Type").toLowerCase() == pCompareType.toLowerCase() ){
				fvIs = true;
			}
    		return fvIs;
        },
        /***********************************************************************************************
        함수명   : sheetSyncCellValue
        설명     :pSheet1 내에 비교문자열과 동일 행의 pSheet2 cell value 값 동기화.
        파라미터 : pSheet1(기준sheet), pSheet2(대상sheet), pSyncCol(동기화column), pCompareObj{비교키:값}, pGroupExpObj{숫자형value 의 경우 제외될 대상조회용 키:값},pNoneAdd(none경우 추가여부 Y/N), pRow(대상행)
        예제     : IBUtil.sheetSyncCellValue(Sheet1,Sheet2,"ITEM_QTY", {"ITEM_CD":"0000210","EXP_DT":""},{"ITEM_CD":"0000210"}, 1);
        어떤 용도인지 확인 후 변경 필요 .. 사용여부 확인
        ************************************************************************************************/
        /*sheetSyncCellValue : function (pSheet1, pSheet2, pSyncCol, pCompareObj, pGroupExpObj, pNoneAdd, pRow, pCallBack){
        	
        	var fvRows = [];
        	if(!CmmnUtil.isEmpty(pRow)){
        		fvRows.push(pRow);
        	}else{
        		for (var i = pSheet1.GetDataFirstRow() ; i <= pSheet1.GetDataLastRow(); i++){
            		fvRows.push(i);
        		}
        	}
        	
        	var fvSheet1ColNm = "";
        	var fvSheet2ColNm = "";
        	var fvSyncColArray = [];
        	//SHEET1과 SHEET2 의 동기화 대상 column 명이 다른경우.
        	if((fvSyncColArray = pSyncCol.split('|')).length > 1){
        		fvSheet1ColNm = fvSyncColArray[0];
        		fvSheet2ColNm = fvSyncColArray[1];
        	}
        	//sheet 간 컬럼명이 같은경우.
        	else{
        		fvSheet1ColNm = fvSyncColArray[0];
        		fvSheet2ColNm = fvSyncColArray[0];
        	}
        	
        	//cell type 변수.
        	var fvColType = IBUtil.isCompareType(pSheet1, pRow, fvSheet1ColNm, "int")? 'int':'str';
        	var fvTotalSumValue = 0;
        	if(fvColType == 'int'){
        		fvTotalSumValue = getGroupExpValue();
        	}

        	//제외값을 구하는 내부함수.
        	function getGroupExpValue(ipRow){
            	var fvExpComp = false;
            	var fvExpValue = 0;
            	$.each(IBUtil.getRowsFindValues(pSheet2,pGroupExpObj), function(idx, row){
            		//현재행 과 같은 행은 제외.
            		if(!CmmnUtil.isEmpty(ipRow) && row == ipRow) return true;
            		
                    fvExpValue += parseInt(pSheet2.GetCellValue(row, fvSheet2ColNm));            		
            	});
            	
            	return fvExpValue;
        	}
        	
        	
        	//동기화 대상 정보 수정처리.
        	function setRowsValue(p1Row, p2Rows){
        		var fvMainValue = parseInt(pSheet1.GetCellValue(p1Row,fvSheet1ColNm));
            	//숫자형인 경우 pGroupObj 로 한번더 비교하여 제외될 값을 구한다.
				if(fvColType == 'int'){  
					var fvCalcValue = fvMainValue - fvTotalSumValue;
					//계산후 수량이 차감 되어야할 경우
					if(fvCalcValue < 0){
						$(IBUtil.getRowsFindValues(pSheet2,pGroupExpObj).reverse()).each(function(idx, row){
	    	            	var fvExpValue = parseInt(pSheet2.GetCellValue(row, fvSheet2ColNm));
    	            		fvCalcValue = fvCalcValue + fvExpValue;
	    	            	if(fvCalcValue < 0){
		                    	pSheet2.SetCellValue(row, fvSheet2ColNm, 0);
	    	            	}else{
		                    	pSheet2.SetCellValue(row, fvSheet2ColNm, fvCalcValue);
		                    	return false;
	    	            	}
						});	
					}
					//계산후 수량이 추가되어야할 경우
					else{
		        		$.each(p2Rows, function(idx, row){
		    	            	//(pSheet1 cell value - pGroupExpObj 대상 cell value 합) 입력 
		                    	pSheet2.SetCellValue(row, fvSheet2ColNm, fvMainValue - parseInt(getGroupExpValue(row)));
		        		});	
					}
				}
				//숫자형이 아닌경우 pSheet1 cellvalue 를 pSheet2 cellvalue 로 입력.
				else{
	        		$.each(p2Rows, function(idx, row){
	                	pSheet2.SetCellValue(row, fvSheet2ColNm, fvMainValue);
	        		});
				}
        	}
        	

            //for (var row of fvRows){
        	$(fvRows.reverse()).each(function(idx, row){
        	//$.each(fvRows, function(idx, row){
            	var fvComp = false;
            	var fvCompRows = [];
            	
            	for(var i = pSheet2.GetDataFirstRow(); i<=pSheet2.GetDataLastRow(); i++){
            		fvComp = false;
                    $.each(pCompareObj,function(id, val){
                    	if(CmmnUtil.getDefaultString(pSheet2.GetCellValue(i, id)) == CmmnUtil.getDefaultString(val) ){
                    		fvComp = true;
                    	}
                    	else{
                    		fvComp = false;
                    		return false;
                    	}
                    });
                    //비교 결과가 true 행은 수정가능처리.
                    if(fvComp){
                    	fvCompRows.push(i);
                    }
            	}
                //비교결과가 없는 경우 신규 row 생성
                if(fvCompRows.length == 0 && CmmnUtil.getDefaultString(pNoneAdd) == "Y"){
                	
                	if(fvColType=='int' && (parseInt(pSheet1.GetCellValue(row,fvSheet1ColNm)) - fvTotalSumValue) > 0){
    		            //신규 ROW 를 추가 
    		            var fvAddRow = pSheet2.DataInsert(-1);
    		            pSheet2.SetRowData(fvAddRow, pCompareObj);
    		            fvCompRows.push(fvAddRow);
                	}else if(fvColType=='str'){
    		            //신규 ROW 를 추가 
    		            var fvAddRow = pSheet2.DataInsert(-1);
    		            pSheet2.SetRowData(fvAddRow, pCompareObj);
    		            fvCompRows.push(fvAddRow);
                	}
                }
                //cell value 동기화 호출.
                setRowsValue(row, fvCompRows);
        	});
            
    		//콜백이 있는경우 콜백 호출.
    		if(!CmmnUtil.isEmpty(pCallBack) && $.isFunction(pCallBack)){
    			pCallBack();
    		}
        },*/

        /***********************************************************************************************
        함수명   : sheetSyncSumValue
        설명     :pSheet1 내에 비교문자열과 동일 행의 pSheet2 sum value 값 동기화.
        변환만 했음 .. 테스트 못함 
        ************************************************************************************************/
        sheetSyncSumValue : function (pSheet1, pSheet2, pFindObj, pSumValueNm){
        	
        	var fvMRows = IBUtil.getRowsFindValues(pSheet1, pFindObj);
        	var fvDRows = IBUtil.getRowsFindValues(pSheet2, pFindObj);       	
        	var fvSumValue = 0;
        	for(var i=0; i < fvMRows.length; i++){
        	    fvSumValue += (pSheet1.getValue(fvMRows[i], pSumValueNm) * 1);
        	}
        	for(var i=0; i < fvDRows.length; i++){
        	    pSheet2.setValue(fvDRows[i], pSumValueNm, fvSumValue);    
            }
        },
        /***********************************************************************************************
        함수명   : getSumValueToGrpObject
        설명     : IBSheet 특정 column 의 합계 값을 group 기준으로 반환
        파라미터 : Sheet(시트), pGrpNm(기준column명), pSumValueNm(sum column)
        예제     : getSumValueToGrpObject(Sheet1 , "ITEM_CD", "ITEM_QTY");
        ************************************************************************************************/
        getSumValueToGrpObject : function (sheet, pGrpNm, pSumValueNm, pCompare){
        	var rObj = {};
        	var Rows = sheet.getDataRows();

        	for(var i=0; i < Rows.length; i ++){
        	    if(Rows[i].Deleted != true){
        	        var fvSumValue = 0;
                    var fvGrpNm = "";
                  //추가비교조건 확인.
                    if(!CmmnUtil.isEmpty(pCompare)){
                        var fvFlag = true;
                        $.each(pCompare, function(id,val){
                            if(Rows[i].id != val){
                                fvFlag = false;
                                return false;                               
                            }
                        });
                        if(!fvFlag){
                            return true;
                        }
                    }
                    
                  //배열인 경우.
                    if($.isArray(pGrpNm)){
                        $.each(pGrpNm, function(id, val){
                            if(fvGrpNm == "") fvGrpNm = Rows[i][val+""];
                            else fvGrpNm = fvGrpNm+'|'+Rows[i][val+""];
                        });

                        if(CmmnUtil.isEmpty(rObj[fvGrpNm+""])){
                            rObj[fvGrpNm+""] = 0;
                        }

                        //sum value 생성
                        if($.isArray(pSumValueNm)){
                            $.each(pSumValueNm, function(id, val){
                                fvSumValue = Rows[i][val+""];
                            });                         
                        }else{
                            fvSumValue = Rows[i][pSumValueNm+""];
                        }
                        rObj[fvGrpNm] = rObj[fvGrpNm] + Rows[i][pSumValueNm+""];
                    }
                    //string 인경우.
                    else{
                        if(CmmnUtil.isEmpty(rObj[Rows[i][pGrpNm+""]])){
                            rObj[Rows[i][pGrpNm+""]] = 0;
                        }
                        rObj[Rows[i][pGrpNm+""]] = rObj[Rows[i][pGrpNm+""]] + Rows[i][pSumValueNm+""];
                    }
                }
        	}
    		return rObj;
        },
        /***********************************************************************************************
        함수명   : getSearchDataOption
        설명     : IBSheet 조회 option 반환(조회결과에 따른 focus 처리 callback 추가)
        파라미터 : pCallBack(callback function)
        예제     : getSearchDataOption(function(){/some process/});
        이해 못함 sheet는 어디에?  조회후 callback 이벤트 싫행시 사용하는 건지 확인 필요..필요시 신규 코딩 해야 함
        ************************************************************************************************/
        /*getSearchDataOption : function(pCallBack){
        	var fvFocusObj = $(':focus').attr('id');
        	
        	return {Sync:1, Wait:0, CallBack:function(sheet, code){  
            	if(!CmmnUtil.isEmpty(fvFocusObj) && sheet.GetFocusAfterProcess()) $('#'+fvFocusObj).blur();      		
        		IBUtil.searchOptionCtrl(sheet, fvFocusObj, pCallBack);       		
            }};
        },*/
        /***********************************************************************************************
        함수명   : getSearchDataWaitOption
        설명     : IBSheet 조회 option 반환(조회결과에 따른 focus 처리 callback 추가)
        파라미터 : pCallBack(callback function)
        예제     : getSearchDataWaitOption(function(){/some process/});
        이해 못함 sheet는 어디에?  조회후 callback 이벤트 싫행시 사용하는 건지 확인 필요..필요시 신규 코딩 해야 함
        ************************************************************************************************/
        /*getSearchDataWaitOption : function(pCallBack){
        	var fvFocusObj = $(':focus').attr('id');
        	
        	return {Sync:1, Wait:1, CallBack:function(sheet, code){  
            	if(!CmmnUtil.isEmpty(fvFocusObj) && sheet.GetFocusAfterProcess()) $('#'+fvFocusObj).blur();      		
        		IBUtil.searchOptionCtrl(sheet, fvFocusObj, pCallBack);       		
            }};
        },*/
        /***********************************************************************************************
        함수명   : getSearchAppendDataOption
        설명     : IBSheet 추가 조회 option 반환(조회결과에 따른 focus 처리 callback 추가)
        파라미터 : pCallBack(callback function)
        예제     : getSearchAppendDataOption(function(){/some process/});
        이해 못함 sheet는 어디에?  조회후 callback 이벤트 싫행시 사용하는 건지 확인 필요..필요시 신규 코딩 해야 함
        ************************************************************************************************/
       /* getSearchAppendDataOption : function(pCallBack){
        	var fvFocusObj = $(':focus').attr('id');
        	
        	return {Sync:1, Wait:0, Append:true, CallBack:function(sheet, code){
            	if(!CmmnUtil.isEmpty(fvFocusObj) && sheet.GetFocusAfterProcess()) $('#'+fvFocusObj).blur();
        		IBUtil.searchOptionCtrl(sheet, fvFocusObj, pCallBack);
            }};
        },*/
        /***********************************************************************************************
        함수명   : searchOptionCtrl
        설명     : IBSheet 조회 option 후처리
        파라미터 : pSheet, pCallBack(callback function)
        예제     : searchOptionCtrl(sheet1, function(){/some process/});
        DocIni 없음.  확인 후 추후 코딩
        ************************************************************************************************/
        /*searchOptionCtrl : function(pSheet, pFocusObj, pCallBack){
        	var fvSheetFocus = pSheet.GetFocusAfterProcess();
    		if(fvSheetFocus){
        		setTimeout(function(){
            		setTimeout(function(){
                		//시트 엔터키 사용 허용 상태 변경.
                		DocIni.putSheetEnterStatus(true);
            		}, 300); 
            		
	            	if(pSheet.SearchRows() > 0 && pSheet.GetFocusAfterProcess()){	            		
	            			pSheet.SetFocus();	
	            			//정렬정보 확인 및 자동정렬 처리.
	            			if(!CmmnUtil.isEmpty(DocIni.getSheetSortInfo(pSheet.id))
	            					&& DocIni.getSheetSortInfo(pSheet.id).length > 0){
	            				var aSortInfo = DocIni.getSheetSortInfo(pSheet.id);

	            				aSortInfo.forEach(function(sort) {
	            					pSheet.ColumnSort(sort.Col,sort.SortOrder);
	            	      	    });	            				
	            			}
	            	    	return false;
	            	}else{
	            		pSheet.SetBlur();
	            		if(!CmmnUtil.isEmpty(pFocusObj)){
	            			$('#'+pFocusObj).focus();
	            	    	return false;
	            		}
	            	}
        		}, 100);
    		}
    		//콜백이 있는경우 콜백 호출.
    		if(!CmmnUtil.isEmpty(pCallBack) && $.isFunction(pCallBack)){
    			pCallBack();
    			return false;
    		}
        },*/
        /***********************************************************************************************
        함수명   : getTemplateDownToExcel
        설명     : IBSheet excel template 다운로드
        파라미터 : pSheet(sheet), pFileName(엑셀파일명), pHiddenCol(숨김컬럼을 엑셀에서 숨기기 기능으로 다운 받을지 여부)
        예제     : IBUtil.getTemplateDownToExcel(sheet, '약학정보');
        ************************************************************************************************/
        getTemplateDownToExcel : function (pSheet, pFileName, pHiddenCol){
        	var fvRow = 1;
        	if(pSheet.getTotalRowCount() == 0){
        		fvRow = pSheet.addRow({next:pSheet.getLastRow()});
        	}
        	var fvNm = pSheet.id + '.xlsx';
        	if(!CmmnUtil.isEmpty(pFileName)){
        		fvNm = pFileName + '.xlsx';
        	}
        	
    	    var params = {
                "fileName": fvNm,
                "sheetName": "Sheet1",
                "sheetDesign": 1,
                "CheckBoxOnValue": "Y",
                "CheckBoxOffValue": "N",
                //"DownCols":fvTempCols.join('|'),
                "DownCombo": "CODE",
                "downHeader": 1,
                "downRows" :"0|"+fvRow,
                "hiddenColumn" : pHiddenCol != null ? pHiddenCol : true
            };

            $.when(pSheet.Down2Excel(params)).done(function(){
            	//if(fvRow > 0) pSheet.removeRow(pSheet.getColByIndex(fvRow));
            });
        },
        /***********************************************************************************************
        함수명   : setColsDefaultAttr
        설명     : IBSheet 컬럼 기본속성 정의
        파라미터 : pColsData(컬럼data object)
        예제     : setColsDefaultAttr({Header:"순번",SaveName:"SEQ"	,CmmAttr:"seq"	},{Header:"" ,SaveName:"CHK",ECmmAttr:"del"	});
        필요시  Extend 사용 IB_Preset 에서 정의 필요
        ************************************************************************************************/
       /* setColsDefaultAttr : function (pColsData){
			$.each(pColsData, function(key, val){
				//CmmAttrType 속성이 있는경우 속성 자동정의.
				if(!CmmnUtil.isEmpty(pColsData['CmmAttr']) || !CmmnUtil.isEmpty(pColsData['ECmmAttr'])){
					var fvAttr = !CmmnUtil.isEmpty(pColsData['ECmmAttr'])? "ECmmAttr":"CmmAttr";
					if(fvAttr == "ECmmAttr"){
	            		if(CmmnUtil.isEmpty(pColsData['Edit'])) pColsData.Edit = true;
					}else{
	            		if(CmmnUtil.isEmpty(pColsData['Edit'])) pColsData.Edit = false;
					}
					switch(pColsData[fvAttr+''].toLowerCase()){
		            	case "status": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width = 50;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth = 30;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align = "Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type = "Status";
		            		if(CmmnUtil.isEmpty(pColsData['Hidden'])) pColsData.Hidden = true;
		            		break;
		            	case "seq": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width =  50;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth = 50;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align = "Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type = "Seq";
		            		break;
		            	case "sn": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width =  100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth = 80;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align = "Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type = "Text";
		            		break;
		            	case "itemcd": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width = 100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth = 80;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align = "Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type = "Text";
		            		if(!CmmnUtil.isEmpty(pColsData['Header']) && pColsData['Header'].split('|').length <= 1){
		            			pColsData.Header = "품목코드";
		            		}
		            		
		            		break;
		            	case "itemnm": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 250;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth = 180;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align = "Left";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type = "Text";
		            		if(!CmmnUtil.isEmpty(pColsData['Header']) && pColsData['Header'].split('|').length <= 1){
		            			pColsData.Header = "품목명";
		            		}
		            		break;
		            	case "makercd": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(!CmmnUtil.isEmpty(pColsData['Header']) && pColsData['Header'].split('|').length <= 1){
		            			pColsData.Header = "메이커코드";
		            		}
		            		break;
		            	case "makernm": 
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 100;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Left";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(!CmmnUtil.isEmpty(pColsData['Header']) && pColsData['Header'].split('|').length <= 1){
		            			pColsData.Header = "메이커명";
		            		}
		            		break;
		            	case "custcd": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(!CmmnUtil.isEmpty(pColsData['Header']) && pColsData['Header'].split('|').length <= 1){
		            			pColsData.Header = "거래처코드";
		            		}
		            		break;
		            	case "custnm": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 120;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 100;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Left";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(!CmmnUtil.isEmpty(pColsData['Header']) && pColsData['Header'].split('|').length <= 1){
		            			pColsData.Header = "거래처명";
		            		}
		            		break;
		            	case "barcode": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 100;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Left";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "qty": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 60;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 50;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Int";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue= 0;
		            		if(CmmnUtil.isEmpty(pColsData['FalseValue'])) pColsData.FalseValue=0;
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format= 'Integer';
		            		break;
		            	case "amt": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 90;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 70;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Right";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Int";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue= 0;
		            		if(CmmnUtil.isEmpty(pColsData['EmptyToReplaceChar'])) pColsData.EmptyToReplaceChar= '0';
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format= 'Integer';
		            		break;
		            	case "decamt": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 90;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 70;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Right";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Float";
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format="#0,#00.#0";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue= 0;
		            		if(CmmnUtil.isEmpty(pColsData['EmptyToReplaceChar'])) pColsData.EmptyToReplaceChar= '0';
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format= 'Integer';
		            		break;
		            	case "rate": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 70;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 70;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Float";
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format="#0.#0";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue= 0;
		            		if(CmmnUtil.isEmpty(pColsData['EmptyToReplaceChar'])) pColsData.EmptyToReplaceChar= '0.0';
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format= 'Integer';
		            		break;
		            	case "cd": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "yn": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue="N";	
		            		break;
		            	case "ynnm": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue="무";
		            		break;
		            	case "combo": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Combo";
		            		if(!CmmnUtil.isEmpty(pColsData['ComboData'])){
		            			pColsData.ComboCode = pColsData['ComboData'].CODE;
		            			pColsData.ComboText = pColsData['ComboData'].CODE_NAME;
		            		}
		            		break;
		            	case "cdnm": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "expdt": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 80;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "prodno": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 100;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "serialno": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 100;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "date": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 100;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 80;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Date";
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format="yyyy-MM-dd";
		            		break;
		            	case "time": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 70;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 30;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format="HH:mm:ss";
		            		break;
		            	case "datetime": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 150;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 30;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format="yyyy-MM-dd HH:mm:ss";
		            		break;
		            	case "ym":
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 50;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 30;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		if(CmmnUtil.isEmpty(pColsData['Format'])) pColsData.Format="yyyy-MM";
		            		break;
		            	case "check": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 60;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="CheckBox";
		            		if(CmmnUtil.isEmpty(pColsData['TrueValue'])) pColsData.TrueValue="Y";
		            		if(CmmnUtil.isEmpty(pColsData['FalseValue'])) pColsData.FalseValue="N";
		            		break;
		            	case "scheck":
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 60;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Radio";
		            		if(CmmnUtil.isEmpty(pColsData['RadioIcon'])) pColsData.RadioIcon= false;
		            		if(CmmnUtil.isEmpty(pColsData['TrueValue'])) pColsData.TrueValue="Y";
		            		if(CmmnUtil.isEmpty(pColsData['FalseValue'])) pColsData.FalseValue="N";
		            		break;
		            	case "text":
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 100;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Left";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "aprvno":
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 70;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 70;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "memo":
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 250;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 100;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Left";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
		            	case "del": 
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 60;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 60;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Button";
		            		if(CmmnUtil.isEmpty(pColsData['HeaderHtml'])) pColsData.HeaderHtml= "";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue= "삭제";
		            		break;
		            	case "btn":
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 80;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Button";
		            		if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue= "확인";
		            		break;
		            	case "autosum":
		            	    if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            	    if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 50;
		            	    if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Right";
		            	    if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="AutoSum";
                            if(CmmnUtil.isEmpty(pColsData['DefaultValue'])) pColsData.DefaultValue= 0;
                            if(CmmnUtil.isEmpty(pColsData['EmptyToReplaceChar'])) pColsData.EmptyToReplaceChar= '0';
		            	    break;
		            	case "pass":
		            	    if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 100;
		            	    if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 50;
		            	    if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Left";
		            	    if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Pass";
		            	    break;
		            	default :
		            		if(CmmnUtil.isEmpty(pColsData['Width'])) pColsData.Width= 80;
		            		if(CmmnUtil.isEmpty(pColsData['MinWidth'])) pColsData.MinWidth= 80;
		            		if(CmmnUtil.isEmpty(pColsData['Align'])) pColsData.Align="Center";
		            		if(CmmnUtil.isEmpty(pColsData['Type'])) pColsData.Type="Text";
		            		break;
					};
					
					//column width 자동계산.
					var fvTempChar = '';
					var fvHeaderLength =  0;
					if(!CmmnUtil.isEmpty(pColsData['Width'])){
						//header/value/format 의 문자열로 width 정의.
						if(!CmmnUtil.isEmpty((fvTempChar = pColsData['Header'])) 
								|| !CmmnUtil.isEmpty((fvTempChar = pColsData['DefaultValue'])) 
								|| !CmmnUtil.isEmpty((fvTempChar = pColsData['Format']))){
							if(( (fvHeaderLength = fvTempChar.substr('\n').length) * 20 ) > 0 && fvHeaderLength > pColsData['Width'] ){								
								pColsData.Width= fvHeaderLength;
								pColsData.MinWidth= fvHeaderLength - 20;
							}
						}
					}
				}
			});
			return pColsData;
        },*/

        /***********************************************************************************************
        함수명   : genColRowDeleteButtonEvent
        설명     : IBSheet row 삭제 버튼 클릭 이벤트 함수 생성
        ************************************************************************************************/
     /*   genColRowDeleteButtonEvent: function(pSheet, pColData){
    		//기존 button click function 존재여부 확인.
    		if(!$.isFunction(window[pSheet.id + "_OnButtonClick"])){
	    		//삭제 button 속성이 있는경우 이벤트 생성.
	        	if(CmmnUtil.getDefaultString(pColData['CmmAttr']).toLowerCase() == "del"
	        		|| CmmnUtil.getDefaultString(pColData['ECmmAttr']).toLowerCase() == "del"){
	        		
	        		//DelOption Object 변수입력.
	        		var fvOptionData = CmmnUtil.isEmpty(pColData["DelOption"])? {}: pColData["DelOption"];
	        		
        		    window[pSheet.id + "_OnButtonClick"] = function(row, col)  {
        		        var fvColNm = pSheet.ColSaveName(col);
        		        //custom 이벤트 함수 가 있는경우.
        		        if(!CmmnUtil.isEmpty(fvOptionData['EventFunction']) ){
        	        		if($.isFunction(fvOptionData['EventFunction'])){
        	        			fvOptionData['EventFunction'].call(this, row, col);
        	        		}else{
        	        			window[fvOptionData['EventFunction']+''].call(this, row, col);
        	        		}
        		        }
    	        		//custom 이벤트 함수 가 없는경우
    	        		//IBUtil 의 row 삭제 함수 호출
    	        		else{
    	        			var fvBefore, fvCallBack, fvSyncSheet;
    	        			var fvSyncNm = (!CmmnUtil.isEmpty(fvOptionData['SyncNm'])) ? fvOptionData['SyncNm']: null;

    	        			if(!CmmnUtil.isEmpty(fvOptionData['BeforeFunction'])){
    	        				
    	        				if($.isFunction(fvOptionData['BeforeFunction'])){
    	        					fvBefore = fvOptionData['BeforeFunction'].call(this, row, col);
    	        				}else{
    	        					fvBefore = window[fvOptionData['BeforeFunction']+''].call(this, row, col);
    	        				}
    	        			}
    	        			
    	        			if(!CmmnUtil.isEmpty(fvOptionData['CallBack'])){
    	        				if($.isFunction(fvOptionData['CallBack'])){
    	        					fvCallBack = fvOptionData['CallBack'];
    	        				}else{
    	        					fvCallBack = window[fvOptionData['CallBack']+''];
    	        				}
    	        			}
    	        			if(!CmmnUtil.isEmpty(fvOptionData['SyncSheet'])){
    	        				if(typeof fvOptionData['SyncSheet'] == "string"){
    	        					fvSyncSheet = window[fvOptionData['SyncSheet']+''];
    	        				}else{
    	        					fvSyncSheet = fvOptionData['SyncSheet'];
    	        				}
    	        			}
    	        			
    	        			//before function 이 있는경우 선처리후 row 삭제.
    	        			if(!CmmnUtil.isEmpty(fvBefore) && $.isFunction(fvBefore)){
    	        				$.when(fvBefore.call(this)).done(function(){
    	    	        			IBUtil.sheetRowsDelete(pSheet, row, "Y", fvCallBack, fvSyncSheet, fvSyncNm);
    	        				})
    	        			}else{
        	        			IBUtil.sheetRowsDelete(pSheet, row, "Y", fvCallBack, fvSyncSheet, fvSyncNm);
    	        			}
    	        		}
        		    };
	    		    
	        	}
    		}
        },*/
        /***********************************************************************************************
        함수명   : removeAllSheet
        설명     : 전체 Sheet 의 목록을 삭제한다.
        예제 : IBUtil.removeAllSheet() or IBUtil.removeAllSheet('sheet1|sheet2')
        ************************************************************************************************/
        removeAllSheet : function(pSheet){
        	if(!CmmnUtil.isEmpty(pSheet)){
        		$.each(pSheet.split("|"), function(idx, sheet){
        			if(!CmmnUtil.isEmpty(window[sheet])){
        				window[sheet].removeAll();
//						CommonLogger.log('sheet', sheet);
        			}
        		});
        	}else{
        	    for(var i =0 ; i < IBSheet.length ; i ++){
        	        window[IBSheet[i].id].removeAll();
        	    }
        	}
        },

        /***********************************************************************************************
        함수명   : getFindCount
        설명     : IBSheet내의 cell 의 value 행건수를 반환 한다.
        파라미터 : pSheetId (시트ID) , pKey, pValue
        예제     : getFindCount(Sheet1 , "ITEM_CD", "000001");
        ************************************************************************************************/
        getFindCount : function(pSheet , pKey, pValue, pExcSel) {
        	var findRows = 0;
        	var row = pSheet.getFirstRow();
        	do {
        	    row = pSheet.findText(pKey, pValue, row);
        	    
        	    if(row == null) break;
        	    
        	    if(row.Deleted != true){
        	        findRows++;
        	    }
        	    row = pSheet.getNextRow(row);
        	} while(row != null)
        	return findRows;
        },

        /***********************************************************************************************
        함수명   : getSearchNameByType
        설명     : IBSheet내의 특정 Cell의 Code값에 대한 Name값을 조회 한다.
        파라미터 : pSheetId (시트ID) , pRow (Row Index), pCol (Code에 해당하는 SaveName), pType(거래처/품목/메이커 구분 코드), pUrl, pParamObj, pCallBack
        예제     : getFindNameByType(Sheet1 , row, "CUST_CD", "/cmm/selectPopCustList", "CUST", {"BRCH_CD":"01"}, fnCallback);
        공통 함수 새로 작업 필요 
        ************************************************************************************************/
       /* getFindNameByType : function(pSheet, pRow, pCol, pType, pUrl, pParamObj, pCallBack){
            if(typeof pUrl != "string"){
                alert("getFindNameByType Warning   \n" + "URL 매개변수를 확인하세요.");
                return;
            }
            
            
        	var vBrchCd = parent.gfvGSS.BRCH_CD;			// 지점코드(검색) 없으면 지점코드(세션)
        	var vCodeVal = pSheet.GetCellValue(pRow, pSheet.ColSaveName(pCol));
        	var vParam = {};
        	
        	switch(CmmnUtil.getDefaultString(pType)){
        		case "CUST" :
        			// 거래처 코드가 5/7 자리인 경우만 조회한다.
        			if(vCodeVal.length != 5 && vCodeVal.length != 7){
        				return;
        			}

        			// 거래처코드가 5자리인 경우는 지점코드와 같이 검색한다.
        			if(vCodeVal.length == 5){
        				vCodeVal = parent.gfvGSS.P_BRCH_CD + vCodeVal;
        			}

        			vParam = {
                			BRCH_CD : vBrchCd,						// 지점코드
                			DEPT_CD : parent.gfvGSS.DEPT_CD,		// 부서코드(세션)
                			CUST_CD : vCodeVal						// 거래처코드(검색)
                	};
        			
        			break;
        		case "ITEM" :
        			// 품목 코드가 6 자리 이상 인 경우만 조회한다.
        			if(vCodeVal.length < 6){
        				return;
        			}

        			vParam = {
                			BRCH_CD : vBrchCd,				// 지점코드
                			ITEM_CD : vCodeVal				// 품목코드(검색)
                	};

        			break;
        		case "MAKER" :
        			// 메이커 코드가 3 자리인 경우만 조회한다.
        			if(vCodeVal.length != 3){
        				return;
        			}

        			vParam = {
                			MAKER_CD : vCodeVal				// 메이커코드(검색)
                	};
        			
        			break;
        	}

        	if(!CmmnUtil.isEmpty(pParamObj)){
        		$.extend(pParamObj, vParam);
        		vParam = pParamObj;
        	}
        	
            CommonUtil.doAjaxCallNotLoad(pUrl, vParam, function(pRowData){
            	if(pRowData.Data == null || pRowData.Data.length == 0) {
            		// 검색 결과가 없으면 해당 Cell의 팝업 이벤트를 띄운다.
            		var callFunc = window[pSheet.id + "_OnPopupClick"];
            		if($.isFunction(callFunc)){
            			callFunc(pRow, pCol);
            		}
            		return;
            	}

            	var dataMap = pRowData.Data[0];
                if(pCallBack != undefined && pCallBack != "" && pCallBack != null){
                	pCallBack(dataMap);
                }
            });
        },*/
        
        /***********************************************************************************************
        함수명   : getArrayModifiedColSaveName
        설명     : IBSheet의 특정 Row내 변경된 Col의 SaveName을 배열로 리턴한다.
        파라미터 : sheetObj (시트Object) , chkRow (조회로우) , exceptColSaveNameArry (체크제외 SaveName Array)
        반환값   : Array = ["IP_AMT" ,"MODI_COL1" .....] 
        예제     : IBUtil.getArrayModifiedColSaveName(Sheet1 , Sheet1.get... , ["STATUS" , "CHK"]);
        ************************************************************************************************/
        getArrayModifiedColSaveName : function (sheetObj, chkRow , exceptColSaveNameArry){
            var fvRtnModifiedSaveNameArry = [];
            var json = sheetObj.getChangedData(chkRow);
            var cols = sheetObj.getCols();
            var bExceptCol = false;
            for( var i=0; i< cols.length ; i++){
                for(var index in exceptColSaveNameArry){
                    if(cols[i] == exceptColSaveNameArry[index] ){
                        bExceptCol  = true;
                        break;
                    }
                } 
                if(bExceptCol) continue;
                
                if(json.indexOf("\""+cols[i]+"\"") > 0 ) fvRtnModifiedSaveNameArry.push(cols[i]);
                bExceptCol = false;
            }
            return fvRtnModifiedSaveNameArry;
        },
        /***********************************************************************************************
        함수명   : genDown2Excel
        설명     : excel/csv 다운로드.
        이해 필요 .. 팝업이 있고 ajax 를 호출해서 무엇인가 하는것 같음
        ************************************************************************************************/
       /* genDown2Excel : function (pSheet, pObj){
            CommonPopup.dialogPopupOpen('DownExcel',"/bjpUrl/com/popDown2Excel.act","엑셀",'200','200', function(rData){
                // console.log('rData : ' , rData);
                // ----------------------------------------------------
                // 고정행이 있는 경우 엑셀다운로드시 
                // 리스트에 행을 포함하지 않으므로 기본값으로 설정한다.
                // ibsheet.js Version 7.0.13.72 에서 임시사용함. 
                // 그런데 헤더가 머지된 시트에서는 동작안함...
                // ibsheet 문의하여 배포받은 버젼으로 적용후 주석처리예정
                // By 강민석 2018-03-22
                // ----------------------------------------------------
                
                var frozen = pSheet.GetFrozenRows();
                // console.log('frozen : ' , frozen);
                if(frozen > 0){
                    pSheet.SetFrozenRows();
                }
                
                // ----------------------------------------------------
                
                
                var fvTitle = "";
                if(!CmmnUtil.isEmpty(pObj.TitleText)){
                    
                }else if(!CmmnUtil.isEmpty(pObj.FileName)){
                    fvTitle = pObj.FileName.split('.')[0];
                     pObj.TitleText  = "▶ "+fvTitle+"\r\n";
                     pObj.TitleAlign = "left";
                     
                     // 시트컬럼 카운트(전체) - INDEX ----------------
                     var colTotalCnt = pSheet.LastCol()+1;
                     // 히든컬럼 카운트(찾기) ------------------------
                     var colHideCnt  = 0;
                     for(var i = 0; i <= pSheet.LastCol(); i++){
                         if(pSheet.GetColHidden(i)){
                             console.log(pSheet.GetColHidden(i) , '→ ' , pSheet.ColSaveName(i));
                             colHideCnt ++;
                         }
                         
                     }
                     
                     for(var i = 0; i <= pSheet.LastCol(); i++){
                         if(pSheet.ColSaveName(i) == "STATUS" || pSheet.ColSaveName(i) == "DEL_CHECK"){
                             console.log(pSheet.ColSaveName(i));
                             colHideCnt ++;
                         }
                     }
                     
                     // console.log("colTotalCnt : " , colTotalCnt);
                     // console.log("colHideCnt  : " , colHideCnt);
                     // var colMergeCnt = (colTotalCnt - colHideCnt);
                     var colMergeCnt = colTotalCnt;
                     // 제목 머지설정하기 ------------------------------
                     pObj.UserMerge  = "0,0,2," + colMergeCnt.toString();
                } // ELSE IF END
                
                // console.log(' fvTitle : ' , fvTitle);
                // ===========================================================================
                // Mode 옵션
                // -1 : Status, DelCheck, Result 타입 및 Hidden 컬럼을 제외하고 다운로드
                //  0 : 모든 컬럼을 다운로드
                //  1 : Status, DelCheck 타입을 제외하고 다운로드
                //  2 : Hidden 컬럼을 제외하고 다운로드
                 // ===========================================================================
                pObj.Mode              = -1;
                pObj.SheetDesign       =  1;
                pObj.HiddenColumn      =  1;
                pObj.ExcelFontSize     =  9;
                pObj.Merge             =  1;
                pObj.NumberFormatMode  =  1;
             // pObj.AutoSizeColumn =  1;
                if(rData.DOWNTYPE == "EXCEL"){
                    if (pObj.DIRECT_URL != "" && pObj.DIRECT_URL != undefined){
                        var data =  CommonUtil.doAjaxCall(pObj.DIRECT_URL, $.param(pObj.DIRECT_PARAM));
                        pObj.SHEETDATA = data;
                        pSheet.DirectDown2Excel(pObj); 
                    } else { 
                         pSheet.Down2Excel(pObj);
                    }
                    
                }else if(rData.DOWNTYPE == "CSV"){
                    if(!CmmnUtil.isEmpty(pObj.FileName)){
                        pObj.FileName = pObj.FileName.replace("xlsx", "csv").replace("xls", "csv");
                    }
                    if(CmmnUtil.isEmpty(pObj.ColDelim)){
                        pObj.ColDelim = ",";
                    }
                    pSheet.ExportMode = 1;
                    pSheet.Down2Text(pObj);
                    pSheet.ExportMode = 3;
                }
            });
            return false;
        },*/
        /***********************************************************************************************
        함수명   : blurAllSheet
        설명     : 전체 Sheet 를 blur 한다.
        ************************************************************************************************/
        blurAllSheet : function(pSheet){
        	if(!CmmnUtil.isEmpty(pSheet)){
        		$.each(pSheet.split("|"), function(idx, sheet){
        			if(!CmmnUtil.isEmpty(window[sheet])){
        				window[sheet].blur();
//						CommonLogger.log('sheet', sheet);
        			}
        		});
        	}else{
                for(var i =0 ; i < IBSheet.length ; i ++){
                    window[IBSheet[i].id].blur();
                }
        	}
        	return true;
        },
        /***********************************************************************************************
        함수명   : clearUnloadAllSheet
        설명     : 전체 Sheet 를 메모리 삭제 한다.
        ************************************************************************************************/
        clearUnloadAllSheet : function(){
            for(var i =0 ; i < IBSheet.length ; i ++){
                if(!$.isEmptyObject(window[IBSheet[i].id])) window[IBSheet[i].id].clear();
            }
        	return true;
        },
        /***********************************************************************************************
        함수명   : getJsonObj
        설명     : 저장할 데이터만 JSON Array 형태로 가져오는 함수
        ************************************************************************************************/        
        getJsonObj :function (sheetObj){
            var obj = (sheetObj && sheetObj.getSaveJson({showAlert:1}));
            if(obj.Message == "RequiredError") delete obj.row;
            return obj;
        },
        /***********************************************************************************************
        함수명   : isCheckData
        설명     : Sheet데이터의 validate, 변경된 Row수 체크
        ************************************************************************************************/
        isCheckData:function(sheetDataAry){
            var rtn = true;
            var cnt = sheetDataAry.length;
            
            for(var i=0;i < cnt; i++){
                if(sheetDataAry[i].Message === "RequiredError"){
                    return false;
                }
            }
            
            var zeroCnt = 0;
            for(var i=0;i < cnt; i++){
                if(sheetDataAry[i].data.length === 0){
                    zeroCnt++;
                }
            }
            
            if(zeroCnt === cnt){
                alert(MSG_CODE.SYS09);
                return false;
            }
            
            return rtn;
        },
        
        /***********************************************************************************************
         * 함수명   : getEnumData
         * 설명     : Enum타입의 컬럼에 select 안에 들어갈 데이터를 배열데이터에서 추출
         * parameter : 배열
         * return : json object {ENUM_String:"",ENUM_KEYS:""}         
         ************************************************************************************************/
        getEnumData: function(vObj){
            var rtnObj = {ENUM_String:"",ENUM_KEYS:""};
            
            if(!CmmnUtil.isEmpty(vObj)) {
                $.each(vObj, function(i,v){
                    rtnObj.ENUM_KEYS += "|" + v.value;
                    rtnObj.ENUM_String += "|" + v.label;
                });
            }
            
            return rtnObj;
        },
        
        /***********************************************************************************************
         * 함수명   : deleteRow
         * 설명     : 선택한 sheet의 focus된 row를 삭제
        ************************************************************************************************/
        deleteRow:function(sheet){
            var frow = sheet.getFocusedRow();
            //신규행은 즉시 삭제하고, 그외에는 상태만 삭제로 바꾼다.
            if(frow.Added){
                sheet.removeRow(frow);
            }else{
                sheet.deleteRow(frow, 1);
            }
        },
        
    };

    window.IBUtil = IBUtil.init();
});



/*
//IBSheet init 초기화 설정.
var version = false;
function IBS_DefaultInitSheet(sheet, info, pAutoFocus, pCfgObj){
    //--------------------------------------------------------
    // 버젼 정보 확인
    //--------------------------------------------------------
    if(!version){
        console.log("IBSHEET VERSION : " , sheet.version());
        version = true;
    }
    //--------------------------------------------------------
    var fvInitSheet = info;
    var fvFocus = (CmmnUtil.isEmpty(pAutoFocus)) ? false: pAutoFocus;
    var fvObj = {};
    if(!CmmnUtil.isEmpty(pCfgObj)){
        fvObj = pCfgObj;
    }
    
    // IBSheet Cfg 는 ibsheet-common.js 에서 구연  CommonOptions 이용
    var fvCfg = {
          // SearchMode 옵션 ======================
          // 0 : 일반 조회 모드
          // 1 : 클라이언트 페이징 모드
          // 2 : LazyLoad 모드
          // 3 : 실시간 서버 처리 스크롤 모드
          // 4 : 실시간 서버 처리 페이징 모드
          // SearchMode 옵션 ======================
           SearchMode           : smLazyLoad
          ,MergeSheet           : msHeaderOnly
          ,ToolTip              : 0
          ,CountPosition        : 4
          ,PagingPosition       : 0
          ,EditableColorDiff    : 2
          ,FalseValue           : 'N'
          ,TrueValue            : 'Y'
          ,NoFocusMode          : !fvFocus
          ,AutoFitColWidth      : "init|search|resize|colresize"
          ,DragMode             : 0
          ,AutoClearHeaderCheck : 1
          //,DataAutoTrim       : 0
          ,MouseHoverMode       : 2 // (by.강민석)
          ,SmartResize          : true
    };
	
	var fvHeader = {Sort:1,ColMove:0,ColResize:1,HeaderCheck:1};
	
	if(!CmmnUtil.isEmpty(fvObj)){
		$.each(fvObj, function(key, val){
			fvCfg[key+''] = val;
			fvHeader[key+''] = val;
		});
	}
	fvInitSheet.Cfg = fvCfg;
	fvInitSheet.HeaderMode = fvHeader;	
  
	//MinimumValue, MaximumValue 속성 을 OnEditValidation 으로 이동처리.
	var fvValiCols = [];
	var fvExtendLastYn = false;	//공백용 empty column 사용여부. 
	var fvTreeColsYn = false;
	if(!CmmnUtil.isEmpty(fvInitSheet.Cols)){
		fvInitSheet.Cols.forEach(function(col, idx) {
			var fvValiCol = {};
			var fvFlag = false;
			fvValiCol.Header = col.Header;
			fvValiCol.SaveName = col.SaveName;
			$.each(col, function(key, val){
				if(key === "MinimumValue"){
						fvFlag = true;
						fvValiCol.MinimumValue = val;
				}
				if(key === "MaximumValue"){
						fvFlag = true;
						fvValiCol.MaximumValue = val;
				}
				//트리col 여부.
				if(key === "TreeCol"){
					fvTreeColsYn = true;
				}
			});
			if(fvFlag){
				fvValiCols.push(fvValiCol);
				delete fvInitSheet.Cols[idx].MinimumValue;
				delete fvInitSheet.Cols[idx].MaximumValue;
			}
			
			//sheet 컬럼의 기본 attribute 처리.
			fvInitSheet.Cols[idx] = IBUtil.setColsDefaultAttr(col);
			
			//sheet del 버튼 클릭 이벤트 생성.
			IBUtil.genColRowDeleteButtonEvent(sheet, col);			
			
		});
		
		
		//sheet 마지막에 공백 col 추가.
		//tree sheet 
		if(!fvTreeColsYn){
			fvInitSheet.Cols.push({SaveName:"_BLANK"	, Header:"" , Width:0, Edit:false	 	} );
			//autofitcol 사용을 하지 않는 경우 공백용 column 을 추가 한다.
			if(CmmnUtil.isEmpty(fvInitSheet.Cfg.AutoFitColWidth) || CmmnUtil.getDefaultString(fvInitSheet.Cfg.AutoFitColWidth,"") == ""){
				fvExtendLastYn = true;
			}
		}
	}
	
	IBS_InitSheet(sheet, fvInitSheet);
	
	sheet.SetDataAutoTrim(0);       데이터의 트림 기능 X 
	sheet.SetEditableColorDiff(2);
	sheet.SetFocusAfterProcess(fvFocus);
	
	if(fvExtendLastYn){
		sheet.SetExtendLastCol(true);
	}
	//컬럼 사이즈 변경시 마지막 col 자동 확장 기능 처리. 전체 적용
	//sheet.SetExtendLastCol(true);
	//sheet.SetFocusEditMode(1);
	
	if(!fvFocus){
	    window[sheet.id + "_OnMouseDown"] = function(button, shift, x, y)  {
	        if (sheet.MouseRow() < sheet.GetDataFirstRow()) return; //헤더행일때는 폼에 반영 안함.
	        sheet.SetConfig({"NoFocusMode":false}, false); 
	        sheet.SelectCell(sheet.MouseRow(), sheet.MouseCol());
	    };
	    window[sheet.id + "_OnMouseMove"] = function(button, shift, x, y)  {
	        if (sheet.MouseRow() < 0 || sheet.MouseCol() < 0){
		        sheet.SetConfig({"NoFocusMode":true}, false);
		        sheet.SetBlur();
	        }
	    };
	}
	
	//MinimumValue, MaximumValue  OnValidation 처리.
	if(fvValiCols.length > 0){
	    window[sheet.id + "_OnValidation"] = function(row, col, value)  {
	        var fvSaveNm = sheet.ColSaveName(col);

	        $.each(fvValiCols,function() {
	        	//validation 대상 col 인경우.
				if(this.SaveName == fvSaveNm){
					if(!CmmnUtil.isEmpty(this.MinimumValue) && this.MinimumValue > value){					
						var fvMsg = this.Header + " 의 최소값은 [" + this.MinimumValue + "] 입니다.";
						CommonUtil.openDialogAlert(fvMsg);
						sheet.ValidateFail(1);
						return false;
					}else if(!CmmnUtil.isEmpty(this.MaximumValue) && parseInt(this.MaximumValue) < parseInt(value)){
						var fvMsg = this.Header + " 의 최대값은 [" + this.MaximumValue + "] 입니다.";
						CommonUtil.openDialogAlert(fvMsg);
						sheet.ValidateFail(1);
						return false;
					}					
				}
			});
	    };
	}
	
	
  window[sheet.id + "_OnFocus"] = function()  {
      if(Grids.length > 0){
      	$.each(Grids, function(){
      		if(sheet.id != this.id){        			
      			window[this.id].SetBlur(); 
      			if(!window[this.id].GetFocusAfterProcess()){
          			window[this.id].SetConfig({"NoFocusMode":true}, false);
          		} 
      			console.log(this.id);
      		}
      	})
      }
  };
  
	//사용자 column size 변경 이벤트
	window[sheet.id + "_OnUserResize"] = function(col, width)  {
		if(!fvTreeColsYn){
			window[sheet.id].SetExtendLastCol(true);
		}
	};
    
    //excel 다운로드 공통함수로 호출 오버라이딩.
    window.window[sheet.id+''].Down2ExcelCsv = function(obj){
    	IBUtil.genDown2Excel(this, obj)
    }
    
    //정렬사용시 정렬정보 저장..
    window[sheet.id + "_OnSort"] = function(col, sortOrder)  {
    	DocIni.putSheetSortInfo(sheet.id, window[sheet.id+''].GetColSortInfo());
    };
    
}


 * IBSheet를 생성(호출 위치에서 동적 생성 height 자동입력)
 
function createIBSheetH(sheetid, width, height, locale) {
	
    var div_str = "";

    if (!locale) locale = "";
    
    Grids.Locale = locale;

    div_str += "<div id='DIV_" + sheetid + "' style='width:" + width + ";height:" + height + ";'>";
    div_str += "<script> IBSheet('<ibsheet Sync=\"1\" Data_Sync=\"0\"> </ibsheet>',\"DIV_" + sheetid + "\", \"" + sheetid + "\"); </script>";
    div_str += "<script> function "+sheetid+"_OnMessage(msg, level, isConfirm) {if (isConfirm == '1'){"+sheetid+".ConfirmOK(1);}else{CommonUtil.openDialogAlert(msg, function(){IBSheetCallBackFocus("+sheetid+")});}}</script>";
    div_str += "<script> "+sheetid+".SetShowMsgMode(0);</script>";
    div_str += "</div>\n";

    //<![CDATA[
    document.write(div_str);
    //]]>
    //Sheet Div 높이 재계산
    if(!$.isEmptyObject($('#DIV_'+sheetid)) && window[sheetid].GetSheetHeight() <= $('#DIV_'+sheetid).height()){
		var vHeight = $(document).height() - $('#DIV_'+sheetid).offset().top - 50;

		if(vHeight > $('#DIV_'+sheetid).height()){
			$('#DIV_'+sheetid).css('height', vHeight);
		}
    }
}


 * IBSheet를 생성(호출 위치에서 동적 생성)
 
function createIBSheetPop(sheetid, width, height, locale) {

    var div_str = "";

    if (!locale) locale = "";

    Grids.Locale = locale;

    div_str += "<div id='DIV_" + sheetid + "' style='width:" + width + ";height:" + height + ";'>";
    div_str += "<script> IBSheet('<ibsheet Sync=\"1\" Data_Sync=\"0\"> </ibsheet>',\"DIV_" + sheetid + "\", \"" + sheetid + "\"); </script>";
    div_str += "<script> function "+sheetid+"_OnDblClick(Row, Col, Value, CellX, CellY, CellW, CellH) {if(Row <= 0 ) return;CommonPopup.setData("+sheetid+".GetRowData(Row));}</script>";
    div_str += "<script> function "+sheetid+"_OnBeforeSearch() {DocIni.putSheetEnterStatus(false);}</script>";
    div_str += "<script> function "+sheetid+"_OnKeyUp(row, col, keyCode, shift) {if(row > 0){if (keyCode == 13 || keyCode == 32) {if(!DocIni.getSheetEnterStatus())return false;CommonPopup.setData("+sheetid+".GetRowData(row));return false;}}}</script>";
    //div_str += "<script> function "+sheetid+"_OnSearchEnd(code, msg, stCode, stMsg, responseText) {"+sheetid+".SetFocus();}</script>";
    div_str += "</div>\n";

    //<![CDATA[
    document.write(div_str);
    //]]>
    //탭클릭시 form 으로 포커스이동.
    window.window[sheetid+'_OnTab'] = function(row, col, oldRow, oldCol, isShift, isLast)  {
    	
    	if(row != oldRow || isLast){
    		window.window[sheetid+''].SetBlur();
    		$("form :input:visible:enabled:not([readonly]):not(select):first").focus();
    		return false;
    	}
    	window.window[sheetid+''].SetSelectCol(oldCol,false);
		window.window[sheetid+''].SetBlur();
		$("form :input:visible:enabled:not([readonly]):not(select):first").focus();
		return false;
    }
}


//alert등 메시지 처리후 callback 으로 Sheet 내 포커스 이동시 사용. 
function IBSheetCallBackFocus(pSheet){
	$(this).focus();
	var vRow = pSheet.GetSelectRow();
	var vCol = pSheet.GetSelectCol();
	pSheet.SelectCell(vRow, vCol, {"Edit":1,"Event":0});
}

//ibsheetinfo.js createIBSheet 함수 override
window.createIBSheet = function(sheetid, width, height, locale){

    var div_str = "";

    if (!locale) locale = "";
    
    Grids.Locale = locale;

    div_str += "<div id='DIV_" + sheetid + "' style='width:" + width + ";height:" + height + ";'>";
    div_str += "<script> IBSheet('<ibsheet Sync=\"1\" Data_Sync=\"0\"> </ibsheet>',\"DIV_" + sheetid + "\", \"" + sheetid + "\"); </script>";
    div_str += "<script> function "+sheetid+"_OnMessage(msg, level, isConfirm) {if (isConfirm == '1'){"+sheetid+".ConfirmOK(1);}else{CommonUtil.openDialogAlert(msg, function(){IBSheetCallBackFocus("+sheetid+")});}}</script>";
    div_str += "<script> "+sheetid+".SetShowMsgMode(0);</script>";
    div_str += "</div>\n";
    
    //<![CDATA[
    document.write(div_str);
}



//ibsheetinfo.js FormQueryStringEnc 함수 override
window.FormQueryStringEnc = function(form, checkRequired) {
    if (typeof form != "object" || form.tagName != "FORM") {
    	if(typeof form == "object"){
    		form = document.forms[$(form).attr('id')]; 
    	}else{
	        IBS_ShowErrMsg("FormQueryStringEnc 함수의 form 인자는 FORM 태그가 아닙니다.");
	        return "";
    	}
    }


    if (checkRequired == null) checkRequired = false;

    var name = new Array(form.elements.length);
    var value = new Array(form.elements.length);
    var j = 0;
    var plain_text = "";

    //사용가능한 컨트롤을 배열로 생성한다.
    len = form.elements.length;
    for (i = 0; i < len; i++) {
        var prev_j = j;
        switch (form.elements[i].type) {
            case "button":
            case "reset":
            case "submit":
                break;
            case "radio":
            case "checkbox":
                if (form.elements[i].checked == true) {
                    name[j] = IBS_getName(form.elements[i]);
                    value[j] = form.elements[i].value;
                    j++;
                }
                break;
            case "select-one":
                name[j] = IBS_getName(form.elements[i]);
                var ind = form.elements[i].selectedIndex;
                if (ind >= 0) {

                    value[j] = form.elements[i].options[ind].value;

                } else {
                    value[j] = "";
                }
                j++;
                break;
            case "select-multiple":
                name[j] = IBS_getName(form.elements[i]);
                var llen = form.elements[i].length;
                var increased = 0;
                for (k = 0; k < llen; k++) {
                    if (form.elements[i].options[k].selected) {
                        name[j] = IBS_getName(form.elements[i]);

                        value[j] = form.elements[i].options[k].value;

                        j++;
                        increased++;
                    }
                }
                if (increased > 0) {
                    j--;
                } else {
                    value[j] = "";
                }
                j++;
                break;
            default:

                name[j] = IBS_getName(form.elements[i]);

        		if(!CmmnUtil.isEmpty($('input[name="'+form.elements[i].name+'_text"]', $(form)))
        				&& !CmmnUtil.isEmpty($('input[name="'+form.elements[i].name+'_text"]', $(form)).data('mask'))){
        			value[j] = $('input[name="'+form.elements[i].name+'_text"]', $(form)).IBMaskEdit('value').replaceAll('-','');
            	}else{
                    value[j] = form.elements[i].value;
            	}
                j++;
        }

        if (checkRequired) {
            //html 컨트롤 태그에 required속성을 설정하면 필수입력을 확인할 수 있다.
            //<input type="text" name="txtName" required="이름">
            if (IBS_RequiredChk(form.elements[i]) && prev_j != j && value[prev_j] == "") {
                if (form.elements[i].getAttribute("required") == "") {
                    alert('"' + IBS_getName(form.elements[i]) + '"' + IBS_MSG_REQUIRED);
                } else {
                    alert('"' + form.elements[i].getAttribute("required") + '"' + IBS_MSG_REQUIRED);
                }
                //컨트롤이 숨겨져 있을수도 있으므로 에러 감싼다.
                try {
                    form.elements[i].focus();
                } catch (ee) {;
                }

                return;
            }
        }
    }

    //QueryString을 조합한다.
    for (i = 0; i < j; i++) {
        if (name[i] != '') plain_text += encodeURIComponent(name[i]) + "=" + encodeURIComponent(value[i]) + "&";
    }

    //마지막에 &를 없애기 위함
    if (plain_text != "") plain_text = plain_text.substr(0, plain_text.length - 1);

    return plain_text;
}*/
