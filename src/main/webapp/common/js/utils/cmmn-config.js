var CmmnConfig = {
        AJAX      : {TYPE:{},DATATYPE:{},CONTENTTYPE:{}},
        HTTP_CODE : {},
        PAGE      : {},
        IBS       : {}
};

var _IBS = CmmnConfig.IBS;
var _AJAX  = CmmnConfig.AJAX;

// =====================================================================================================
// AJAX OPTION          
// =====================================================================================================
CmmnConfig.AJAX.TYPE.GET        = "GET";
CmmnConfig.AJAX.TYPE.POST       = "POST";
CmmnConfig.AJAX.TYPE.DELETE     = "DELETE";
CmmnConfig.AJAX.TYPE.PUT        = "PUT";
CmmnConfig.AJAX.DATATYPE.JSON   = "JSON";
CmmnConfig.AJAX.DATATYPE.JSONP  = "JSONP";
CmmnConfig.AJAX.DATATYPE.XML    = "XML";
CmmnConfig.AJAX.DATATYPE.HTML   = "HTML";
CmmnConfig.AJAX.CONTENTTYPE.APPLICATION_DEFAULT = "application/x-www-form-urlencoded; charset=utf-8;";
CmmnConfig.AJAX.CONTENTTYPE.APPLICATION_JSON    = "application/json; charset=utf-8;";
CmmnConfig.AJAX.CONTENTTYPE.APPLICATION_XML     = "application/xml;  charset=utf-8;";

// =====================================================================================================
// HTTP_CODE
// =====================================================================================================
CmmnConfig.HTTP_CODE.R500     = "500";
CmmnConfig.HTTP_CODE.R404     = "404";
CmmnConfig.HTTP_CODE.R401     = "401";
CmmnConfig.HTTP_CODE.R200     = "200";

// =====================================================================================================
// PAGE
// =====================================================================================================
CmmnConfig.PAGE.LOGIN     = "/login.do";

// =====================================================================================================
// IBS
// =====================================================================================================
CmmnConfig.IBS.PATH     = "";
CmmnConfig.IBS.LICENSE  = "";
CmmnConfig.IBS.PARENTID = "IBSDiv";
CmmnConfig.IBS.ID       = "IBS";
CmmnConfig.IBS.NONE     = "none";
// =====================================================================================================
// PROPERTIES OPTION
// =====================================================================================================
CmmnConfig.IBS.COPY     = "copy";
CmmnConfig.IBS.PASTE    = "paste";
CmmnConfig.IBS.ORIGINAL = "original";
CmmnConfig.IBS.COLUMNS  = "columns";
CmmnConfig.IBS.MRG_ROW  = "row";
CmmnConfig.IBS.MRG_COL  = "col";
CmmnConfig.IBS.MRG_RAC  = "rowandcol";
CmmnConfig.IBS.MRG_CAR  = "colandrow";
CmmnConfig.IBS.H_MRG_ROW  = {header : "row"};
CmmnConfig.IBS.H_MRG_COL  = {header : "col"};
CmmnConfig.IBS.H_MRG_RAC  = {header : "rowandcol"};
CmmnConfig.IBS.H_MRG_CAR  = {header : "colandrow"};
CmmnConfig.IBS.D_MRG_ROW  = {data   : "row"};
CmmnConfig.IBS.D_MRG_COL  = {data   : "col"};
CmmnConfig.IBS.D_MRG_RAC  = {data   : "rowandcol"};
CmmnConfig.IBS.D_MRG_CAR  = {data   : "colandrow"};

// =====================================================================================================
// ROWHEADERS OPTION
// =====================================================================================================
CmmnConfig.IBS.NONE     = "none";
CmmnConfig.IBS.SEQ      = "sequence";
CmmnConfig.IBS.REVERSE  = "reverse";
CmmnConfig.IBS.CHECKBOX = "checkbox";
CmmnConfig.IBS.STATE    = "state";
// =====================================================================================================
// SELECTMODE OPTION
// =====================================================================================================
CmmnConfig.IBS.HIGHLIGHTREPEAT  = "highlightrepeat";
CmmnConfig.IBS.HEADER           = "header";
CmmnConfig.IBS.DATA             = "data";
CmmnConfig.IBS.FROZEN           = "frozen";
CmmnConfig.IBS.FROZENBTM        = "frozenbottom";
CmmnConfig.IBS.ROW              = "row";
CmmnConfig.IBS.ROWS             = "rows";
CmmnConfig.IBS.COL              = "col";
CmmnConfig.IBS.COLS             = "cols";
CmmnConfig.IBS.CELL             = "cell";
CmmnConfig.IBS.CELLS            = "cells";
CmmnConfig.IBS.FOCUS            = "focus";
CmmnConfig.IBS.GROUP            = "group";

// =====================================================================================================
// COLUMNS OPTION
// =====================================================================================================
CmmnConfig.IBS.TITLE            = "title";
CmmnConfig.IBS.KEY              = "key";
CmmnConfig.IBS.TYPE             = "type";
CmmnConfig.IBS.OPTIONS          = "options";
CmmnConfig.IBS.REQUIRED         = "required";
CmmnConfig.IBS.ALIGN            = "align";
CmmnConfig.IBS.ALIGND           = "alignD";
CmmnConfig.IBS.ALIGNH           = "alignH";
CmmnConfig.IBS.STYLECLASSNAME   = "styleclassname";
// =====================================================================================================
// OHTER OPTION
// =====================================================================================================
CmmnConfig.IBS.VERTICAL         = "vertical";
CmmnConfig.IBS.HORIZONTAL       = "horizontal";
CmmnConfig.IBS.I                = "i";
CmmnConfig.IBS.U                = "u";
CmmnConfig.IBS.D                = "d";
