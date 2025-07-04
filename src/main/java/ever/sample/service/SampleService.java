package ever.sample.service;

import java.util.HashMap;
import java.util.List;

public interface SampleService {

    public void save1() throws Exception;
    public void save2() throws Exception;
    public void saveDynamic(HashMap hMap) throws Exception;
    public void saveBatchDynamic(List<HashMap> listMap) throws Exception;
    public void saveEmp(HashMap<String, Object> hMap) throws Exception;
    public void saveCODE00TB(HashMap<String, Object> hMap) throws Exception;
    public void saveCODE01TB(HashMap<String, Object> hMap) throws Exception;
    public void saveCODE01TB_2(List<HashMap<String, Object>> hMap) throws Exception;
}
