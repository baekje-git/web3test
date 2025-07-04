package ever.support.barcode;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class BarcodeView extends AbstractView {
    
    public BarcodeView(){
        setContentType("apllication/download; charset=utf-8");
    }
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model
    		, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Map<String, Object> dataMap = (Map<String, Object>) model.get("data");
    	try {
			BarcodeFormat barFmt = null;
			BitMatrix bitMatrix = null;
			String reqString = new String((dataMap.get("BARCODE").toString()).getBytes("UTF-8"), "ISO-8859-1");
			int barWidth = Integer.parseInt(dataMap.get("w").toString());
			int barHeight = Integer.parseInt(dataMap.get("h").toString());

			if (request.getParameter("TYPE").equals("QR")) {
				barFmt = BarcodeFormat.QR_CODE;
				QRCodeWriter q = new QRCodeWriter();
				bitMatrix = q.encode(reqString, barFmt, barWidth, barHeight);
			} else {
				barFmt = BarcodeFormat.CODE_128;
				Code128Writer q = new Code128Writer();
				bitMatrix = q.encode(reqString, barFmt, barWidth, barHeight);
			}

			ServletOutputStream outputStream = response.getOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			throw new ServletException(e);
		}
    }
    
}
