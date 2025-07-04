package ever.support;

/**
 * 
 * <pre>
 * @FileName : ever.support.Gv.java
 * @Date     : 2019. 12. 3. 
 * @Writer   : KMSDEV
 * @Description :
 * </pre>
 */
public class Gv {
    
    public static final String DATASOURCE                       = "DATASOURCE";
    public static final String NAMESPACE                        = "NAMESPACE";
    public static final String QUERY_ID                         = "QUERY_ID";
    public static final String HTTP_MULTIPART                   = "multipart/form-data";
    public static final String HTTP_GET                         = "GET";
    public static final String HTTP_POST                        = "POST";
    
    public static final String RESULT                           = "RESULT";
    public static final String MSG                              = "MESSAGE";
    public static final String SUCC                             = "SUCCESS";
    public static final String FAIL                             = "FAIL";
    public static final String SUCCMSG                          = "정상적으로 처리되었습니다.";
    public static final String FAILMSG                          = "처리중 오류가 발생하셨습니다.";
    
    public static final String AJAX_HEADER_KEY                  = "X-Requested-With";
    public static final String AJAX_HEADER_VAL                  = "XMLHttpRequest";
    
    // DATE
    public static final String DATE                             = "yyyyMMdd";
    public static final String DATETIME                         = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT                      = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT                  = "yyyy-MM-dd HH:mm:ss";
    
    public static final String TEMP                             = "temp";
    public static final String FILE_DOWNLOAD_VIEW               = "fileDownloadView";
    
    public static final String INDEX_VIEW                       = "/index.do";
    public static final String HOME_VIEW                        = "/home.do";
    public static final String TEST_VIEW                        = "/test.do";
    public static final String MAIN_VIEW                        = "/main.do";
    public static final String LOGIN_VIEW                       = "/login.do";
    public static final String LOGOUT_VIEW                      = "/logout.do";
    public static final String COMMON_PATH                      = "/common/";
    public static final String REDIRECT                         = "redirect:";
    public static final String FORWARD                          = "forward:";
    public static final String SAL1  = "SAL1"; 
    // GRID
    public static final String I                                = "I";
    public static final String U                                = "U";
    public static final String D                                = "D";

    // MESSAGE
    public static final String LOGIN_SUCC_MSG     = "로그인처리가 되었습니다";
    public static final String LOGIN_FAIL_MSG     = "아이디 또는 비밀번호를 정확히 입력하세요";
    public static final String LOGIN_APRV_MSG     = "아직 승인처리되지 않은 사용자입니다";
    public static final String LOGIN_NOPW_MSG     = "비밀번호를 정확히 입력하세요";
    public static final String LOGIN_USE_YN_MSG   = "사용정지된 사용자입니다";
    public static final String LOGIN_NOID_MSG     = "존재하지 않는 아이디 입니다";
    public static final String EXPIRED_TOKEN_MSG  = "만료된 토큰입니다";
    public static final String EXPIRED_REFRESH_TOKEN_MSG  = "만료된 토큰(Refresh)입니다";
    public static final String LOGIN_NOPLAYER_MSG = "구단관계자에게 문의 바랍니다.(미등록 선수)";
    public static final String LOGIN_REGPLAYER_MSG = "구단관계자에게 문의 바랍니다.(가입 선수)";
    public static final String LOGIN_DUPLE_PLAYER_MSG = "구단관계자에게 문의 바랍니다.(중복등록 선수)";
    public static final String CSV_HEADER_FAIL_MSG = "CSV 포멧이 틀립니다.";
    
    
    public static final String ERROR_404                        = "404";
    public static final String ERROR_500                        = "500";
    
    public static final String ERROR_404_MSG                    = "요청하신 페이지는 존재하지 않습니다.";
    public static final String ERROR_500_MSG                    = "서비스 사용에 불편을 끼쳐드려서 대단히 죄송합니다.\n빠른시간내에 문제를 처리하겠습니다.";

    
    //동적DB 연결 기본 키 
    public static final String DEFAULT_SUB_DBKEY                = "DEFAULT_SUB_DBKEY";
    //동적DB 정보 코드그룹키 
    public static final String DYNAMIC_DB_CODEGROUP_KEY         = "[DYDB]_";
    
    //mybatis 설정값 경로 
    public static final String SQLFACTORY_MAPPERLOCATIONS_PATH  = "classpath://sqlmap/mappers/**/*.xml";
    public static final String SQLFACTORY_CONFIG_PATH           = "classpath://sqlmap/config/mybatis-config.xml";
    //세션 타임 아웃 지정
    public static final int SESSION_TIMEOUT                     = 60* 10; //10분 
    //쿠키에 담는 세션id 키값
    public static final String JSESSIONID_COOKIE                = "JSESSIONID_COOKIE";

    //SHA2, 3  
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";
    public static final String SHA3_224 = "SHA3-224";
    public static final String SHA3_256 = "SHA3-256";
    public static final String SHA3_384 = "SHA3-384";
    public static final String SHA3_512 = "SHA3-512";
    
    //AES256키값
    public static final String AES256_KEY                       = "rft6zix8k56s393436104z252739q8zo";
    
    //HMAC키값
    public static final String HMAC_KEY                         = "27s39r10ix548t4z23639qf38k566zzo";

    //Mybatis 쿼리 SUFFIX INSERT
    public static final String QUERY_SUFFIX_INSERT         = "INSERT";
    //Mybatis 쿼리 SUFFIX UPDATE 
    public static final String QUERY_SUFFIX_UPDATE         = "UPDATE";
    //Mybatis 쿼리 SUFFIX DELETE
    public static final String QUERY_SUFFIX_DELETE         = "DELETE";
    
    //IBSHEET 8 INSERT STATE
    public static final String IBSHEET8_ADD_ST         = "Added";
    //IBSHEET 8 UPDATE STATE
    public static final String IBSHEET8_UPD_ST         = "Changed";
    //IBSHEET 8 DELETE STATE
    public static final String IBSHEET8_DEL_ST         = "Deleted";

    // 관리자 로그인/로그아웃
    public static final String ADMIN_LOGIN_VIEW         = "/admin/login.do";
    public static final String ADMIN_LOGIN_PROC         = "/admin/loginProc.do";
    public static final String ADMIN_LOGOUT_VIEW        = "/admin/logout.do";
    public static final String ADMIN_INDEX_VIEW         = "/admin/index.do";
    public static final String ADMIN_LOGIN_APRV_MSG     = "관리자로 승인되지 않은 사용자입니다";
}
