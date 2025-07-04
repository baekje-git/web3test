package org.kada.kadam.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

public class CsvTest {

	public static void main(String[] args) throws Exception {

		List<String> HEADERS = new ArrayList<String>(Arrays.asList(getHeaders("NEG")));
		
		//File initialFile = new File("C:/작업/2021/2021-KADA/프로스포츠(KADAMS)/99_수신자료/(참고)USADA일부시료(MMA) 결과보고 방법/2020.12.11_11_19(771~813)Neg(KADA)_______.csv");
		File initialFile = new File("C:/작업/2021/2021-KADA/프로스포츠(KADAMS)/99_수신자료/KIST CSV 데이터 (2022-03-14)/2022.02.12_14_55(022_046)Neg_____.csv");
	    InputStream targetStream = new FileInputStream(initialFile);
		Reader in =  new InputStreamReader(targetStream);
		
		//CSVParser csvParser = CSVFormat.DEFAULT.withHeader(getHeaders("NEG")).parse(in);
		CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(in);
		System.out.println("HeaderNames===>"+csvParser.getHeaderNames());
                
        if (!csvParser.getHeaderNames().equals(HEADERS)) {
            throw new IllegalStateException("csv 포멧이 틀립니다.");
        }
        
        List<CSVRecord> addData = new ArrayList<CSVRecord>();
        for (CSVRecord csvRecord : csvParser) {
        	//System.out.println(csvRecord.toString());
        	for (String value : HEADERS) {
                if(!StringUtils.isEmpty(csvRecord.get(value))) {                	
                	addData.add(csvRecord);
                	break;                	
                }
            }
        }
        
        for(CSVRecord csvRecord : addData) {
        	System.out.println(csvRecord.toString());
        }
        
	}

	private static String[] getHeaders(String gubun) {
    	List<String> list = new ArrayList<>();
    	if("NEG".equals(gubun)) {
    		list.add("lin");
    		list.add("sample_code");
    		list.add("analysis_attribute");
    		list.add("test_result");
    		list.add("analysis_report_date");
    		list.add("mo_number");
    		list.add("sample_type");
    		list.add("sampleAB");
    		list.add("sample_collection_date");
    		list.add("date_received");
    		list.add("ta");
    		list.add("sca");
    		list.add("rma");
    		list.add("test_type");
    		list.add("sport_code");
    		list.add("discipline_code");
    		list.add("gender");
    		list.add("steroid_profile_variable_code1");
    		list.add("steroid_profile_variable_code2");
    		list.add("steroid_profile_variable_code3");
    		list.add("steroid_profile_variable_code4");
    		list.add("steroid_profile_variable_code5");
    		list.add("steroid_profile_variable_code6");
    		list.add("steroid_profile_variable_code7");
    		list.add("CF_code1");
    		list.add("CF_code2");
    		list.add("CF_code3");
    		list.add("CF_code4");
    		list.add("CF_code5");
    		list.add("CF_code6");
    		list.add("CF_code7");
    		list.add("CF_code8");
    		list.add("CF_code9");
    		list.add("CF_code10");
    		list.add("CF_code11");
    		list.add("CF_code12");
    		list.add("CF_code13");
    		list.add("specific_gravity");
    		list.add("sample_specific_gravity_CP");
    		list.add("ratio_5aand_a");
    		list.add("ratio_5band_etio");
    		list.add("ratio_freet_totalt");
    		list.add("steroid_profile_variable_value1");
    		list.add("steroid_profile_variable_value2");
    		list.add("steroid_profile_variable_value3");
    		list.add("steroid_profile_variable_value4");
    		list.add("steroid_profile_variable_value5");
    		list.add("steroid_profile_variable_value6");
    		list.add("steroid_profile_variable_value7");
    		list.add("CF_presence1");
    		list.add("CF_presence2");
    		list.add("CF_presence3");
    		list.add("CF_presence4");
    		list.add("CF_presence5");
    		list.add("CF_conc5");
    		list.add("CF_presence6");
    		list.add("CF_presence7");
    		list.add("CF_conc7");
    		list.add("CF_presence8");
    		list.add("CF_conc8");
    		list.add("CF_presence9");
    		list.add("CF_conc9");
    		list.add("CF_presence10");
    		list.add("CF_conc10");
    		list.add("CF_presence11");
    		list.add("CF_conc11");
    		list.add("CF_presence12");
    		list.add("CF_conc12");
    		list.add("CF_presence13");
    		list.add("analysis_details");
    		list.add("monitoring");
    	} else if("BLOOD".equals(gubun)) {
    		list.add("lin");	
    		list.add("sample_code");
    		list.add("mo_number");	
    		list.add("sample_type");	
    		list.add("sample_collection_date");	
    		list.add("date_received");	
    		list.add("analysis_report_date");	
    		list.add("ta");	
    		list.add("sca");	
    		list.add("rma");	
    		list.add("test_type");	
    		list.add("sport_code");	
    		list.add("discipline_code");	
    		list.add("gender");	
    		list.add("sampleAB");	
    		list.add("test_result");	
    		list.add("analysis_attribute");
    	} else if("ABP".equals(gubun)) {
    		list.add("lin");
    		list.add("sample_code");
    		list.add("mo_number");
    		list.add("sample_type");
    		list.add("date_results_reported");
    		list.add("ta");
    		list.add("sca");
    		list.add("rma");
    		list.add("test_type");
    		list.add("sport_code");
    		list.add("discipline_code");
    		list.add("gender");
    		list.add("lab");
    		list.add("date_collection");
    		list.add("date_received");
    		list.add("analysis_date");
    		list.add("HGB");
    		list.add("HCT");
    		list.add("IRF");
    		list.add("MCH");
    		list.add("MCHC");
    		list.add("MCV");
    		list.add("OFF-Score");
    		list.add("PLT");
    		list.add("RDW-SD");
    		list.add("RBC");
    		list.add("RET#");
    		list.add("RET%");
    		list.add("WBC");
    		list.add("analyser");
    	}
    	String[] stringArray = list.toArray(new String[0]);
    	return stringArray;
    }
}
