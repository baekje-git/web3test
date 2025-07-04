package ever.cmmn.dao;

import ever.cmmncode.domain.CacheCode;
import ever.cmmncode.service.CodeCacheService;
import ever.support.exception.AjaxMsgCustomException;
import ever.support.mybatis.SqlDynamicSessionExt;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

@Repository("cmmnDynamicDao")
@Scope("prototype")
public class CmmnDynamicDaoImpl extends SqlDynamicSessionExt implements CmmnDynamicDao {
	private static final Logger logger = LoggerFactory.getLogger(CmmnDynamicDaoImpl.class);

	private String dbConnectKey = "DEFAULT_SUB_DBKEY";

	@Autowired
	private CodeCacheService codeCacheService;

	private SqlSessionTemplate sqlSessionTemplate = null;

	public String getDbConnectKey() {
		return this.dbConnectKey;
	}

	public void setDbConnectKey(String dbConnectKey) throws Exception {
		if (dbConnectKey == null) {
			if (this.sqlSessionTemplate == null)
				throw new Exception(
						"할당된 SqlSessionTemplate 가 없습니다. dbConnectKey 를 지정하여 최초  SqlSessionTemplate 를 생성하세요. ");
		} else {
			this.dbConnectKey = dbConnectKey;
			List<CacheCode> cacheCodeList = this.codeCacheService.getCacheCodeListByGroupText(dbConnectKey);
			if (cacheCodeList.size() == 0)
				throw new Exception(String.valueOf(dbConnectKey) + " : 동적 DB연결 그룹키 가 존재하지 않습니다.");
			String DB_CONNECT_DRIVER_CLASSNAME = this.codeCacheService.getValueByCodeKey(cacheCodeList,
					"DB_CONNECT_DRIVER_CLASSNAME");
			String DB_CONNECT_URL = this.codeCacheService.getValueByCodeKey(cacheCodeList, "DB_CONNECT_URL");
			String DB_CONNECT_USERNAME = this.codeCacheService.getValueByCodeKey(cacheCodeList, "DB_CONNECT_USERNAME");
			String DB_CONNECT_PASSWORD = this.codeCacheService.getValueByCodeKey(cacheCodeList, "DB_CONNECT_PASSWORD");
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(DB_CONNECT_DRIVER_CLASSNAME);
			dataSource.setUrl(DB_CONNECT_URL);
			dataSource.setUsername(DB_CONNECT_USERNAME);
			dataSource.setPassword(DB_CONNECT_PASSWORD);
			Log4jdbcProxyDataSource dataSourceLogger = new Log4jdbcProxyDataSource((DataSource) dataSource);
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource((DataSource) dataSourceLogger);
			sqlSessionFactoryBean.setMapperLocations(
					(new PathMatchingResourcePatternResolver()).getResources("classpath://sqlmap/mappers/**/*.xml"));
			sqlSessionFactoryBean.setConfigLocation((new PathMatchingResourcePatternResolver())
					.getResource("classpath://sqlmap/config/mybatis-config.xml"));
			SqlSessionTemplate newSqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
			newSqlSessionTemplate.getConnection().setAutoCommit(false);
			this.sqlSessionTemplate = newSqlSessionTemplate;
			setSqlSession(newSqlSessionTemplate);
		}
		System.out.println("this.sqlSessionTemplate :: " + this.sqlSessionTemplate);
	}

	public void endTransaction() throws Exception {
		if (this.sqlSessionTemplate != null)
			this.sqlSessionTemplate.getConnection().commit();
	}

	public void rollback() throws Exception {
		if (this.sqlSessionTemplate != null)
			this.sqlSessionTemplate.getConnection().rollback();
	}

	public void endBatch() throws Exception {
		if (this.sqlSessionTemplate != null)
			this.sqlSessionTemplate.flushStatements();
	}

	public Object select(String subDbConnectionKey, HashMap<String, Object> hMap) throws Exception {
		Object retObj = null;
		try {
			setDbConnectKey(subDbConnectionKey);
			retObj = selectDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public String select(String subDbConnectionKey, String queryId) throws Exception {
		String retString = "";
		try {
			setDbConnectKey(subDbConnectionKey);
			retString = selectDataSource(queryId);
		} catch (Exception e) {
			logger.error("[" + queryId + "]" + queryId + " [EXCEPTION] => " + e);
			throw e;
		}
		return retString;
	}

	public Object select(String subDbConnectionKey, String queryId, HashMap<String, Object> hMap) throws Exception {
		Object retObj = null;
		try {
			setDbConnectKey(subDbConnectionKey);
			retObj = selectDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public List<Object> selectList(String subDbConnectionKey, HashMap<String, Object> hMap) throws Exception {
		List<Object> retObj = null;
		try {
			setDbConnectKey(subDbConnectionKey);
			retObj = selectListDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public List<Object> selectList(String subDbConnectionKey, String queryId, HashMap<String, Object> hMap)
			throws Exception {
		List<Object> retObj = null;
		try {
			setDbConnectKey(subDbConnectionKey);
			retObj = selectListDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retObj;
	}

	public int insert(String subDbConnectionKey, HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			setDbConnectKey(subDbConnectionKey);
			retCnt = insertDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int insert(String subDbConnectionKey, String queryId, HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			setDbConnectKey(subDbConnectionKey);
			retCnt = insertDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int update(String subDbConnectionKey, HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			setDbConnectKey(subDbConnectionKey);
			retCnt = updateDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int update(String subDbConnectionKey, String queryId, HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			setDbConnectKey(subDbConnectionKey);
			retCnt = updateDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int delete(String subDbConnectionKey, HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			setDbConnectKey(subDbConnectionKey);
			retCnt = deleteDataSource(hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public int delete(String subDbConnectionKey, String queryId, HashMap<String, Object> hMap) throws Exception {
		int retCnt = 0;
		try {
			setDbConnectKey(subDbConnectionKey);
			retCnt = deleteDataSource(queryId, hMap);
		} catch (Exception e) {
			logger.error("[" + hMap.get("QUERY_ID") + "]" + hMap + " [EXCEPTION] => " + e);
			throw e;
		}
		return retCnt;
	}

	public Object callProcedure(String subDbConnectionKey, HashMap<String, Object> hMap) throws Exception {
		setDbConnectKey(subDbConnectionKey);
		callDataSource(hMap);
		if (hMap.containsKey("O_VAL") && hMap.get("O_VAL").toString().indexOf("ERROR") >= 0) {
			logger.debug("[" + hMap.get("QUERY_ID") + "]" + hMap);
			throw new AjaxMsgCustomException("ERR",
					"[" + hMap.get("QUERY_ID") + "] 처리중 오류\n" + hMap.get("O_MSG").toString());
		}
		return hMap;
	}

	public Object callProcedure(String subDbConnectionKey, String queryId, HashMap<String, Object> hMap)
			throws Exception {
		setDbConnectKey(subDbConnectionKey);
		callDataSource(queryId, hMap);
		if (hMap.containsKey("O_VAL") && hMap.get("O_VAL").toString().indexOf("ERROR") >= 0) {
			logger.debug("[" + queryId + "]" + hMap);
			throw new AjaxMsgCustomException("ERR", "[" + queryId + "] 처리중 오류\n" + hMap.get("O_MSG").toString());
		}
		return hMap;
	}
}
