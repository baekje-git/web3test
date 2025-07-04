package ever.support.session;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright    Copyright (c) 2017
 * Company      UBIStorm Co. <br><br>
 * <strong>작성자 : </strong>khlee<br>
 * <strong>작성일 : </strong>2017. 6. 8.오후 2:05:21<br>
 * 설  명 : 컨트롤러나 컨트롤러 안에 있는 메소드에 이 어노테이션을 사용하면 유저 로그인 권한을 피해간다.<br>
 * @author       khlee
 * @package      baekje.com
 * @file         PassPermission.java
 * @version      1.0
 * @since        2017. 6. 8.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassPermission {
	boolean passException() default false;
}