package kada.k05.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ever.cmmn.dao.CmmnDao;
import ever.support.ConstantProp;

@Service
public class CertificateServiceImpl implements CertificateService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="cmmnDao")
    private CmmnDao cmmnDao;

	
}
