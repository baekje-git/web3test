package ever.cmmn.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/cmmn" })
public class CmmnErrorController {
	private static final Logger logger = LoggerFactory.getLogger(CmmnErrorController.class);

	@RequestMapping({ "/error.do" })
	public String error(HttpServletRequest request) throws Exception {
		Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String errorMessage = "";
		if (errorCode.toString().equals("404")) {
			errorMessage = "요청하신 페이지는 존재하지 않습니다.";
		} else if (errorCode.toString().equals("500")) {
			errorMessage = "서비스 사용에 불편을 끼쳐드려서 대단히 죄송합니다.\n빠른시간내에 문제를 처리하겠습니다.";
		}
		String errorContent = "";
		StringBuilder stackTrace = new StringBuilder();
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		if (exception != null) {
			errorContent = exception.getMessage();
			byte b;
			int i;
			StackTraceElement[] arrayOfStackTraceElement;
			for (i = (arrayOfStackTraceElement = exception.getStackTrace()).length, b = 0; b < i;) {
				StackTraceElement element = arrayOfStackTraceElement[b];
				stackTrace.append(element.toString());
				stackTrace.append("\n");
				b++;
			}
		}
		request.setAttribute("errorCode", errorCode);
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("errorContent", errorContent);
		return "error/error";
	}
}
