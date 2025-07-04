package ever.support.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("#{info['JWT_KEY']}")
	private String SECRET_KEY; // 서명에 사용할 secretKey

	@Value("#{info['JWT_EXP_SECOND']}")
	private long TOKEN_VALIDATION_SECOND; // 토큰 사용가능 시간
	
	@Value("#{info['JWT_REFRESH_KEY']}")
	private String REFRESH_SECRET_KEY; // 서명에 사용할 secretKey
	
	@Value("#{info['JWT_REFRESH_SECOND']}")
	private long REFRESH_TOKEN_VALIDATION_SECOND; 
	
	private String DATA_KEY = "userId";
    	

    //토큰 생성 
    public String createToken(String userId) {
        return doCreateToken(userId, TOKEN_VALIDATION_SECOND, SECRET_KEY);
    }

    public String createRefreshToken(String userId) {
        return doCreateToken(userId, REFRESH_TOKEN_VALIDATION_SECOND, REFRESH_SECRET_KEY);
    }

    public String extendExpireDate(String token){
        long curTime = System.currentTimeMillis();
        Jws<Claims> claims =  Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token);
        return claims.getBody().setExpiration(new Date(curTime + TOKEN_VALIDATION_SECOND)).getSubject();
    }
    
    public String extendExpireDateRefresh(String token){
        long curTime = System.currentTimeMillis();
        Jws<Claims> claims =  Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(REFRESH_SECRET_KEY))
                    .build()
                    .parseClaimsJws(token);
        return claims.getBody().setExpiration(new Date(curTime + REFRESH_TOKEN_VALIDATION_SECOND)).getSubject();
    }

    private String doCreateToken(String userId, long expireTime, String secretKey){
        long curTime = System.currentTimeMillis(); 
        String sessionToken =  Jwts.builder()
                 .setHeaderParam("typ", "JWT") // JWT Header가 지닐 정보(default : HS256)
                 .setExpiration(new Date(curTime + expireTime)) // 만료 시각 (+1일)
                 .setIssuedAt(new Date(curTime)) // 발급 시각
                 .claim(DATA_KEY, userId) //Payload에 Private Claims를 담기 위해 claim method를 이용
                 .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256) // 복호화할때 서버만이 알고 있는 비밀키로 해쉬를 하여 생성
                 .compact();
        //세션토큰 발급 완료
        return sessionToken;
    }
    
    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    //session 에 있는지 확인 
    public boolean isUsable(String jwtToken) {
		try{
            Jws<Claims> claims =  Jwts.parserBuilder()
                        .setSigningKey(getSigningKey(SECRET_KEY))
                        .build()
                        .parseClaimsJws(jwtToken);
            //2025-04-10
            //System.out.println(claims.getBody()); 
			return true;
		}catch (Exception e) {
			return false;
		}
	}
    
    // 토큰이 유효한 토큰인지 검사한 후, 토큰에 담긴 Payload 값을 가져온다.
    public Claims extractAllClaims(String jwtToken) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
    
    //만료된 토큰인지 체크 
    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
    
    //만료된 토큰인지 체크(RefreshToken)
    public Boolean isRefreshTokenExpired(String token) {
        final Date expiration = Jwts.parserBuilder()
                                    .setSigningKey(getSigningKey(REFRESH_SECRET_KEY))
					                .build()
					                .parseClaimsJws(token)
					                .getBody()
					                .getExpiration();
        return expiration.before(new Date());
    }

    public String getUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }
    
    public Boolean validateToken(String token, UserInfo user) {
        final String userId = getUserId(token);
        return (userId.equals(user.getUserId()) && !isTokenExpired(token));
    }
}
