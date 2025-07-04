package ever.support.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

public class ExceptionTransfer implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionTransfer.class);

	private MessageSource messageSource;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.messageSource = (MessageSource) applicationContext.getBean("messageSource");
	}

	public void transfer(JoinPoint thisJoinPoint, Exception exception) throws Exception {
		String pkgName = thisJoinPoint.getTarget().getClass().getName().toLowerCase();
		int lastIndex = pkgName.lastIndexOf(".");
		String className = pkgName.substring(lastIndex + 1);
		String opName = thisJoinPoint.getSignature().getName().toLowerCase();
		Log logger = LogFactory.getLog(thisJoinPoint.getTarget().getClass());
		if (exception instanceof CustomException) {
			CustomException empEx = (CustomException) exception;
			logger.error(empEx.getMessage(), empEx);
			throw empEx;
		}
		if (exception instanceof AjaxMsgCustomException)
			throw exception;
		if (exception.getCause() != null)
			throw new AjaxMsgCustomException(exception.getCause().getMessage());
		throw new AjaxMsgCustomException(exception.toString());
	}

	public void transferException(JoinPoint thisJoinPoint, Exception exception) throws Exception {
		String pkgName = thisJoinPoint.getTarget().getClass().getName().toLowerCase();
		int lastIndex = pkgName.lastIndexOf(".");
		String className = pkgName.substring(lastIndex + 1);
		String opName = thisJoinPoint.getSignature().getName().toLowerCase();
		Log logger = LogFactory.getLog(thisJoinPoint.getTarget().getClass());
		System.out.println("dynamic db rollback start");
	}
}
