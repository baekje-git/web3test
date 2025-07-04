package ever.support.mybatis;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

public class RefreshableSqlSessionFactoryBean extends SqlSessionFactoryBean implements DisposableBean {
	private static final Log log = LogFactory.getLog(RefreshableSqlSessionFactoryBean.class);

	private SqlSessionFactory proxy;

	private int interval = 10000;

	private Timer timer;

	private TimerTask task;

	private Resource[] mapperLocations;

	private boolean running = false;

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private final Lock r = this.rwl.readLock();

	private final Lock w = this.rwl.writeLock();

	public void setMapperLocations(Resource[] mapperLocations) {
		super.setMapperLocations(mapperLocations);
		this.mapperLocations = mapperLocations;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void refresh() throws Exception {
		if (log.isInfoEnabled())
			log.info("refreshing sqlMapClient.");
		this.w.lock();
		try {
			super.afterPropertiesSet();
		} finally {
			this.w.unlock();
		}
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		setRefreshable();
	}

	private void setRefreshable() {
		this.proxy = (SqlSessionFactory) Proxy.newProxyInstance(SqlSessionFactory.class.getClassLoader(),
				new Class[] { SqlSessionFactory.class }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return method.invoke(RefreshableSqlSessionFactoryBean.this.getParentObject(), args);
					}
				});
		this.task = new TimerTask() {
			private Map<Resource, Long> map = new HashMap<>();

			public void run() {
				if (isModified())
					try {
						RefreshableSqlSessionFactoryBean.this.refresh();
					} catch (Exception e) {
						RefreshableSqlSessionFactoryBean.log.error("caught exception", e);
					}
			}

			private boolean isModified() {
				boolean retVal = false;
				if (RefreshableSqlSessionFactoryBean.this.mapperLocations != null)
					for (int i = 0; i < RefreshableSqlSessionFactoryBean.this.mapperLocations.length; i++) {
						Resource mappingLocation = RefreshableSqlSessionFactoryBean.this.mapperLocations[i];
						retVal |= findModifiedResource(mappingLocation);
					}
				return retVal;
			}

			private boolean findModifiedResource(Resource resource) {
				boolean retVal = false;
				List<String> modifiedResources = new ArrayList<>();
				try {
					long modified = resource.lastModified();
					if (this.map.containsKey(resource)) {
						long lastModified = ((Long) this.map.get(resource)).longValue();
						if (lastModified != modified) {
							this.map.put(resource, new Long(modified));
							modifiedResources.add(resource.getDescription());
							retVal = true;
						}
					} else {
						this.map.put(resource, new Long(modified));
					}
				} catch (IOException e) {
					RefreshableSqlSessionFactoryBean.log.error("caught exception", e);
				}
				if (retVal && RefreshableSqlSessionFactoryBean.log.isInfoEnabled())
					RefreshableSqlSessionFactoryBean.log.info("modified files : " + modifiedResources);
				return retVal;
			}
		};
		this.timer = new Timer(true);
		resetInterval();
	}

	private Object getParentObject() throws Exception {
		this.r.lock();
		try {
			return super.getObject();
		} finally {
			this.r.unlock();
		}
	}

	public SqlSessionFactory getObject() {
		return this.proxy;
	}

	public Class<? extends SqlSessionFactory> getObjectType() {
		return (this.proxy != null) ? (Class) this.proxy.getClass() : SqlSessionFactory.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setCheckInterval(int ms) {
		this.interval = ms;
		if (this.timer != null)
			resetInterval();
	}

	private void resetInterval() {
		if (this.running) {
			this.timer.cancel();
			this.running = false;
		}
		if (this.interval > 0) {
			this.timer.schedule(this.task, 0L, this.interval);
			this.running = true;
		}
	}

	public void destroy() throws Exception {
		this.timer.cancel();
	}
}
