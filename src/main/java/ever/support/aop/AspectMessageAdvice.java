package ever.support.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Aspect
public class AspectMessageAdvice {
	private static final Logger logger = LoggerFactory.getLogger(AspectMessageAdvice.class);

	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("--[[  BEGIN   ]]-----------------------------------------------");
		logger.debug(joinPoint.toString());
		StopWatch stopWatch = new StopWatch();
		try {
			stopWatch.start();
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
			logger.debug("query processing time : " + (stopWatch.getTotalTimeMillis() / 1000.0D) + "(sec)");
			logger.debug("---------------------------------------------------[[  END ]]--");
		}
	}
}