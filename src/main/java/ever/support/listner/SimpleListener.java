package ever.support.listner;

import ever.cmmncode.domain.CacheCodeGroup;
import ever.cmmncode.service.CodeCacheService;
import ever.support.ConstantProp;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class SimpleListener implements ApplicationListener<ContextRefreshedEvent> {
	private Logger logger = LoggerFactory.getLogger(SimpleListener.class);

	@Autowired
	private CodeCacheService codeCacheService;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if (ConstantProp.GV_CACHE_CODE) {
			logger.debug("캐시 로드 시작...");
			try {
				List<CacheCodeGroup> cacheCodeGroupList = this.codeCacheService.getCacheCodeGroupList();
				if (cacheCodeGroupList.size() == 0)
					this.codeCacheService.refreshAllFromRepository();
				logger.debug(String.valueOf(this.codeCacheService.getCacheCodeGroupList().size()) + "개의 캐시코드그룹 캐시 로드 완료...");
			} catch (Exception e) {
				logger.debug("캐쉬 초기 로드 실패 : " + e.toString());
			}
			logger.debug("캐시 로드 종료...");	
		}
		
	}
}
