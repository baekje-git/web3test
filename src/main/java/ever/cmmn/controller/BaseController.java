package ever.cmmn.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

@Controller
public class BaseController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected ApplicationContext context;

	@ModelAttribute("webParameterMap")
	protected HashMap getParameterMap() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest req = sra.getRequest();
		Enumeration names = null;
		if (ServletFileUpload.isMultipartContent(req)) {
			if (req instanceof DefaultMultipartHttpServletRequest)
				names = ((DefaultMultipartHttpServletRequest) req).getParameterNames();
		} else {
			names = req.getParameterNames();
		}
		return getParameterMap(names);
	}

	protected HashMap getParameterMap(Map map) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest req = sra.getRequest();
		Enumeration names = null;
		if (ServletFileUpload.isMultipartContent(req)) {
			names = ((DefaultMultipartHttpServletRequest) req).getParameterNames();
		} else {
			names = req.getParameterNames();
		}
		return getParameterMap(names, map);
	}

	protected HashMap getMultipartParameterMap(HttpServletRequest request) {
		Enumeration<String> enumeration = request.getParameterNames();
		Map paramMap = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			paramMap = ((DefaultMultipartHttpServletRequest) request).getParameterMap();
		} else {
			paramMap = request.getParameterMap();
		}
		HashMap<Object, Object> tempMap = new HashMap<>();
		String key = "";
		String value = "";
		for (int i = 0; i < paramMap.size(); i++) {
			key = enumeration.nextElement();
			int size = ((String[]) paramMap.get(key)).length;
			value = ((String[]) paramMap.get(key))[0];
			if (size > 1)
				for (int j = 1; j < size; j++)
					value = String.valueOf(value) + "," + ((String[]) paramMap.get(key))[j];
			tempMap.put(key, value);
		}
		initListPaging(tempMap);
		return tempMap;
	}

	protected HashMap getParameterMap(Enumeration enumeration) {
		return getParameterMap(enumeration, new HashMap<>());
	}

	protected HashMap getParameterMap(Enumeration<String> enumeration, Map<?, ?> map) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		Map paramMap = null;
		HttpServletRequest request = sra.getRequest();
		if (ServletFileUpload.isMultipartContent(request)) {
			if (request instanceof DefaultMultipartHttpServletRequest) {
				paramMap = ((DefaultMultipartHttpServletRequest) request).getParameterMap();
			} else {
				paramMap = request.getParameterMap();
			}
		} else {
			paramMap = request.getParameterMap();
		}
		HashMap<Object, Object> tempMap = new HashMap<>();
		String key = "";
		String value = "";
		for (int i = 0; i < paramMap.size(); i++) {
			if (enumeration != null) {
				key = enumeration.nextElement();
				int size = ((String[]) paramMap.get(key)).length;
				value = ((String[]) paramMap.get(key))[0];
				if (size > 1)
					for (int j = 1; j < size; j++)
						value = String.valueOf(value) + "," + ((String[]) paramMap.get(key))[j];
				tempMap.put(key, value);
			}
		}
		tempMap.putAll(map);
		initListPaging(tempMap);
		return tempMap;
	}

	public void initListPaging(Map<Object, Object> map) {
		if (!map.containsKey("current_page") || map.get("current_page") == null)
			map.put("current_page", "1");
		if (!map.containsKey("once_display_count") || map.get("once_display_count") == null) {
			String ONCE_DISPLAY_COUNT = "10";
			map.put("once_display_count", ONCE_DISPLAY_COUNT);
		}
		int current_page = Integer.parseInt(String.valueOf(map.get("current_page")));
		int once_display_count = Integer.parseInt(String.valueOf(map.get("once_display_count")));
		int startPage = (current_page - 1) * once_display_count;
		map.put("firstNum", Integer.valueOf(startPage));
		map.put("endPage", Integer.valueOf(startPage + once_display_count));
		if (!map.containsKey("current_page2") || map.get("current_page2") == null)
			map.put("current_page2", "1");
		int current_page2 = Integer.parseInt(String.valueOf(map.get("current_page2")));
		int startPage2 = (current_page2 - 1) * once_display_count;
		map.put("firstNum2", Integer.valueOf(startPage2));
		map.put("endPage2", Integer.valueOf(startPage2 + once_display_count));
	}
}
