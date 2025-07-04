package kada.k05.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ever.cmmn.dao.CmmnDao;
import ever.support.session.SessionUtil;
import kada.modoc.ModocApi;
import kada.util.EmailService;
import kada.util.Util;

@Service
public class EditMemberServiceImpl implements EditMemberService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "cmmnDao")
	private CmmnDao cmmnDao;
	
	@Autowired
	private ModocApi modoc;
	
	@Autowired
	private EmailService emailService;
	
	//@Override
	

}
