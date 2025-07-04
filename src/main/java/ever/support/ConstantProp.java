package ever.support;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * <pre>
 * @FileName : ever.support.ConstantProp.java
 * @Date     : 2019. 12. 4. 
 * @Writer   : KMSDEV
 * @Description :
 * </pre>
 */
public class ConstantProp {
    
    private static final Logger logger = LoggerFactory.getLogger(ConstantProp.class);
    
    @Value("${spring.profiles.active}")
    private String activeProfile;
    
    @Value("#{info['FILE.UPLOAD.TEMP_PATH']}")
    private String TEMP_UPLOAD_PATH;
    
    @Value("#{info['FILE.UPLOAD.BASIC_PATH']}")
    private String BASIC_UPLOAD_PATH;
    
    @Value("#{info['FILE.UPLOAD.BOARD_PATH']}")
    private String BOARD_UPLOAD_PATH;
    
    @Value("#{info['FILE.UPLOAD.ATTACH_PATH']}")
    private String ATTACH_UPLOAD_PATH;

    @Value("#{info['MESSAGEQUEUE.RABBITMQ.HOST']}")
    private String RABBITMQ_HOST;
    
    @Value("#{info['MESSAGEQUEUE.RABBITMQ.PORT']}")
    private int RABBITMQ_PORT;
    
    @Value("#{info['MESSAGEQUEUE.RABBITMQ.USER']}")
    private String RABBITMQ_USER;
    
    @Value("#{info['MESSAGEQUEUE.RABBITMQ.PASS']}")
    private String RABBITMQ_PASS;
    
    @Value("#{info['ROW_STATUS_COLUMN']}")
    private String ROW_STATUS_COLUMN;
    
    @Value("#{info['ROW_STATUS_SELECT']}")
    private String ROW_STATUS_SELECT;
    
    @Value("#{info['ROW_STATUS_ADD']}")
    private String ROW_STATUS_ADD;
    
    @Value("#{info['ROW_STATUS_UPDATE']}")
    private String ROW_STATUS_UPDATE;
    
    @Value("#{info['ROW_STATUS_DELETE']}")
    private String ROW_STATUS_DELETE;
    
    @Value("#{info['CACHE.CODE']}")
    private boolean CACHE_CODE;
    
    @Value("#{info['player.roleCode']}")
    private String PLAYER_ROLE_CODE;
    
    @Value("#{info['manager.roleCode']}")
    private String MANAGER_ROLE_CODE;
    
    @Value("#{info['PAYMENT.CRESOTY.IP']}")
    private String paymentCresotyIp;
    
    @Value("#{info['MOBILE.SIGN.PATH']}")
    private String mobileSignPath;
    
    @Value("#{info['SCAN.IMAGE.PATH']}")
    private String scanImagePath;
    
    @Value("#{info['ACCEPTANCE.UPLOAD.PATH']}")
    private String acceptanceUploadPath;
    
    // STATIC
    public static String GV_SPRING_ACTIVE_PROFILE;
    
    public static String GV_TEMP_UPLOAD_PATH;
    public static String GV_BASIC_UPLOAD_PATH;
    public static String GV_BOARD_UPLOAD_PATH;
    public static String GV_ATTACH_UPLOAD_PATH;

    //MQ
    public static String GV_RABBITMQ_HOST;
    public static int GV_RABBITMQ_PORT;
    public static String GV_RABBITMQ_USER;
    public static String GV_RABBITMQ_PASS;
    
    //DATA SET STATUS
    public static String GV_ROW_STATUS_COLUMN;
    public static String GV_ROW_STATUS_SELECT;
    public static String GV_ROW_STATUS_ADD;
    public static String GV_ROW_STATUS_UPDATE;
    public static String GV_ROW_STATUS_DELETE;
    
    //Cache
    public static boolean GV_CACHE_CODE;
    
    // role code
    public static String GV_PLAYER_ROLE_CODE;
    public static String GV_MANAGER_ROLE_CODE;
    
    public static String GV_LOG4J_CONFIGURATIONFILE;
    
    public static String GV_paymentCresotyIp;
    
    public static String GV_mobileSignPath;
    public static String GV_scanImagePath;
    public static String GV_acceptanceUploadPath;
    @PostConstruct
    public void init() throws Exception{
        
    	GV_SPRING_ACTIVE_PROFILE = activeProfile;
    	
        GV_TEMP_UPLOAD_PATH   = TEMP_UPLOAD_PATH;
        GV_BASIC_UPLOAD_PATH  = BASIC_UPLOAD_PATH;
        GV_BOARD_UPLOAD_PATH  = BOARD_UPLOAD_PATH;
        GV_ATTACH_UPLOAD_PATH = ATTACH_UPLOAD_PATH;

        GV_RABBITMQ_HOST   = RABBITMQ_HOST;
        GV_RABBITMQ_PORT  = RABBITMQ_PORT;
        GV_RABBITMQ_USER  = RABBITMQ_USER;
        GV_RABBITMQ_PASS = RABBITMQ_PASS;
        
        GV_ROW_STATUS_COLUMN = ROW_STATUS_COLUMN;
        GV_ROW_STATUS_SELECT = ROW_STATUS_SELECT;
        GV_ROW_STATUS_ADD    = ROW_STATUS_ADD;
        GV_ROW_STATUS_UPDATE = ROW_STATUS_UPDATE;   
        GV_ROW_STATUS_DELETE = ROW_STATUS_DELETE;    
        
        GV_CACHE_CODE = CACHE_CODE;
        
        GV_PLAYER_ROLE_CODE = PLAYER_ROLE_CODE;
        GV_MANAGER_ROLE_CODE = MANAGER_ROLE_CODE;
        
        GV_LOG4J_CONFIGURATIONFILE = System.getProperty("log4j.configurationFile");
        
        GV_paymentCresotyIp = paymentCresotyIp;
        
        GV_mobileSignPath = mobileSignPath;
        GV_scanImagePath = scanImagePath;
        
        this.GV_acceptanceUploadPath  = acceptanceUploadPath;
        logger.info("::::::::::::::::::: GLOBAL STATIC VALUE INITIALIZATION ::::::::::::::::::::");
        logger.info("┌─────────────────────────────────────────────────────────────────────────┐");
        logger.info("│ GV_SPRING_ACTIVE_PROFILE         : " + GV_SPRING_ACTIVE_PROFILE);
        logger.info("│ GV_TEMP_UPLOAD_PATH              : " + GV_TEMP_UPLOAD_PATH);
        logger.info("│ GV_BASIC_UPLOAD_PATH             : " + GV_BASIC_UPLOAD_PATH);
        logger.info("│ GV_BOARD_UPLOAD_PATH             : " + GV_BOARD_UPLOAD_PATH);
        logger.info("│ GV_ATTACH_UPLOAD_PATH            : " + GV_ATTACH_UPLOAD_PATH);
        logger.info("│ GV_ROW_STATUS_COLUMN             : " + GV_ROW_STATUS_COLUMN);
        logger.info("│ GV_ROW_STATUS_SELECT             : " + GV_ROW_STATUS_SELECT);
        logger.info("│ GV_ROW_STATUS_ADD                : " + GV_ROW_STATUS_ADD);
        logger.info("│ GV_ROW_STATUS_UPDATE             : " + GV_ROW_STATUS_UPDATE);
        logger.info("│ GV_ROW_STATUS_DELETE             : " + GV_ROW_STATUS_DELETE);
        logger.info("│ GV_CACHE_CODE                    : " + GV_CACHE_CODE);
        logger.info("│ GV_PLAYER_ROLE_CODE              : " + GV_PLAYER_ROLE_CODE);
        logger.info("│ GV_MANAGER_ROLE_CODE             : " + GV_MANAGER_ROLE_CODE);
        logger.info("│ GV_LOG4J_CONFIGURATIONFILE       : " + GV_LOG4J_CONFIGURATIONFILE);
        logger.info("│ GV_acceptanceUploadPath          : " + GV_acceptanceUploadPath);
        logger.info("└─────────────────────────────────────────────────────────────────────────┘");
        
    }
}
