package ever.support.excel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.formula.FastFormulaProcessor;
import org.jxls.formula.FormulaProcessor;
import org.jxls.formula.StandardFormulaProcessor;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;

/**
 * SAMPLE
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>khlee<br>
 * <strong>작성일 : </strong>2017. 12. 09. <br>
 * @author       khlee
 * @package      com.util
 * @file         JxlsUtil.java
 * @version      1.0
 * @since        2017. 12. 09.
 */

public class JxlsUtil {
	
	//엑셀다운로드 
	public void download(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap, String templateFile) throws  InvalidFormatException {
		String tempPath = request.getSession().getServletContext().getRealPath("/WEB-INF/excelTemplate");
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(tempPath + "/" + templateFile));
            
            OutputStream os = response.getOutputStream();
            
            Context context = new Context();
            context.putVar("dataList", paramMap.get("dataList"));

            response.setHeader("Content-Transfer-Encoding", "binary");
            
            JxlsHelper.getInstance().processTemplate(is, os, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
