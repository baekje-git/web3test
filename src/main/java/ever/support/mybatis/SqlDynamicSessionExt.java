package ever.support.mybatis;

import java.util.HashMap;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;

public class SqlDynamicSessionExt {
	private SqlSessionTemplate sqlSession;

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	public SqlSessionTemplate getSqlSession() {
		return this.sqlSession;
	}

	protected Object selectDataSource(HashMap<String, Object> hMap) {
		Object retObj = null;
		String queryId = (String) hMap.get("QUERY_ID");
		retObj = this.sqlSession.selectOne(queryId, hMap);
		return retObj;
	}

	protected String selectDataSource(String queryId) {
		String retString = "";
		retString = (String) this.sqlSession.selectOne(queryId);
		return retString;
	}

	protected Object selectDataSource(String queryId, HashMap<String, Object> hMap) {
		Object retObj = null;
		retObj = this.sqlSession.selectOne(queryId, hMap);
		return retObj;
	}

	protected List<Object> selectListDataSource(HashMap<String, Object> hMap) {
		List<Object> retObj = null;
		String queryId = (String) hMap.get("QUERY_ID");
		retObj = this.sqlSession.selectList(queryId, hMap);
		return retObj;
	}

	protected List<Object> selectListDataSource(String queryId, HashMap<String, Object> hMap) {
		List<Object> retObj = null;
		retObj = this.sqlSession.selectList(queryId, hMap);
		return retObj;
	}

	protected int insertDataSource(HashMap<String, Object> hMap) {
		String queryId = (String) hMap.get("QUERY_ID");
		int retInt = 0;
		retInt = this.sqlSession.insert(queryId, hMap);
		return retInt;
	}

	protected int insertDataSource(String queryId, HashMap<String, Object> hMap) {
		int retInt = 0;
		retInt = this.sqlSession.insert(queryId, hMap);
		return retInt;
	}

	protected int updateDataSource(HashMap<String, Object> hMap) {
		String queryId = (String) hMap.get("QUERY_ID");
		int retInt = 0;
		retInt = this.sqlSession.update(queryId, hMap);
		return retInt;
	}

	protected int updateDataSource(String queryId, HashMap<String, Object> hMap) {
		int retInt = 0;
		retInt = this.sqlSession.update(queryId, hMap);
		return retInt;
	}

	protected int deleteDataSource(HashMap<String, Object> hMap) {
		String queryId = (String) hMap.get("QUERY_ID");
		int retInt = 0;
		retInt = this.sqlSession.delete(queryId, hMap);
		return retInt;
	}

	protected int deleteDataSource(String queryId, HashMap<String, Object> hMap) {
		int retInt = 0;
		retInt = this.sqlSession.delete(queryId, hMap);
		return retInt;
	}

	protected Object callDataSource(HashMap<String, Object> hMap) {
		String queryId = (String) hMap.get("QUERY_ID");
		Object retObj = null;
		retObj = this.sqlSession.selectOne(queryId, hMap);
		return retObj;
	}

	protected Object callDataSource(String queryId, HashMap<String, Object> hMap) {
		Object retObj = null;
		retObj = this.sqlSession.selectOne(queryId, hMap);
		return retObj;
	}
}
