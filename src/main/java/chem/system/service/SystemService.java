package chem.system.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ever.cmmn.service.CmmnService;
import ever.support.jwt.JwtUtil;
import ever.support.jwt.UserInfo;

@Service
public class SystemService {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	CmmnService cmmnService;

}
