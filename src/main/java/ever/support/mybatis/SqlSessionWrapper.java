package ever.support.mybatis;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import ever.support.Gv;

/**
 * 
 * <pre>
 * @FileName : ever.support.mybatis.SqlSessionWrapper.java
 * @Date     : 2021. 8. 31. 
 * @Writer   : 
 * @Description : 
 * </pre>
 */
public class SqlSessionWrapper {
        
    private SqlSessionTemplate sqlSession;    
    
        
    public SqlSessionWrapper(SqlSessionTemplate sqlSession) {
        super();
        this.sqlSession = sqlSession;
    }

    public Object selectDataSource(HashMap<String, Object> hMap){
        Object retObj = null;
        String queryId = (String) hMap.get(Gv.QUERY_ID);
                retObj = sqlSession.selectOne(queryId, hMap);
        return retObj;
    }
    
    public String selectDataSource(String queryId){
        String retString = "";
        retString = sqlSession.selectOne(queryId);
        return retString;
    }
    
    public Object selectDataSource(String queryId, HashMap<String, Object> hMap){
        Object retObj = null;
        retObj = sqlSession.selectOne(queryId, hMap);
        return retObj;
    }
    
    public List<Object> selectListDataSource(HashMap<String, Object> hMap){
        List<Object> retObj = null;
        String queryId = (String) hMap.get(Gv.QUERY_ID);
            retObj = (List<Object>) sqlSession.selectList(queryId, hMap);
        return retObj;
    }
    
    public List<Object> selectListDataSource(String queryId, HashMap<String, Object> hMap){
        List<Object> retObj = null;
        retObj = (List<Object>) sqlSession.selectList(queryId, hMap);
        return retObj;
    }
    
    public int insertDataSource(HashMap<String, Object> hMap){
        String queryId = (String) hMap.get(Gv.QUERY_ID);
        int retInt = 0;
            retInt = sqlSession.insert(queryId, hMap);
        return retInt;
    }
    
    public int insertDataSource(String queryId, HashMap<String, Object> hMap){
        int retInt = 0;
            retInt = sqlSession.insert(queryId, hMap);
        return retInt;
    }
    
    public int updateDataSource(HashMap<String, Object> hMap){
        String queryId = (String) hMap.get(Gv.QUERY_ID);
        int retInt = 0;
        retInt = sqlSession.update(queryId, hMap);
        return retInt;
    }
    
    public int updateDataSource(String queryId, HashMap<String, Object> hMap){
        int retInt = 0;
        retInt = sqlSession.update(queryId, hMap);
        return retInt;
    }
    
    public int deleteDataSource(HashMap<String, Object> hMap){
        String queryId = (String) hMap.get(Gv.QUERY_ID);
        int retInt = 0;
        retInt = sqlSession.delete(queryId, hMap);
        return retInt;
    }
    
    public int deleteDataSource(String queryId, HashMap<String, Object> hMap){
        int retInt = 0;
        retInt = sqlSession.delete(queryId, hMap);
        return retInt;
    }
    
    public Object callDataSource(HashMap<String, Object> hMap){
        String queryId = (String) hMap.get(Gv.QUERY_ID);
        Object retObj = null;
        retObj = sqlSession.selectOne(queryId, hMap);
        return retObj;
    }
    
    public Object callDataSource(String queryId, HashMap<String, Object> hMap){
        Object retObj = null;
        retObj = sqlSession.selectOne(queryId, hMap);
        return retObj;
    }
}
