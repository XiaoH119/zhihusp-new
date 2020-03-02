package com.zhihu.global.auth;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtTokenUtil {

	public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
 
    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "leopard";
    
    // 过期时间是3600秒，既是1个小时
    public static final long EXPIRATION = 3600L;
 
    // 选择了记住我之后的过期时间为7天
    public static final long EXPIRATION_REMEMBER = 604800L;
 
	/**
	 * 创建token
	 * 注：如果是根据可变的唯一值来生成，唯一值变化时，需重新生成token
	 * @param username
	 * @param isRememberMe
	 * @return
	 */
    public static String createToken(String id, String username, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        //可以将基本不重要的对象信息放到claims中，此处信息不多,见简单直接放到配置内
//        HashMap<String,Object> claims = new HashMap<String,Object>();
//        claims.put("id", id);
//        claims.put("username",username);
        //id是重要信息，进行加密下
        String encryId = id;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                // 这里要早set一点，放到后面会覆盖别的字段
//                .setClaims(claims)
                .setIssuer(ISS)
                .setId(encryId)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }
    
    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }
 
    /**
     * 从token中获取ID，同时做解密处理
     * @param token
     * @return
     */
    public static String getObjectId(String token){
        return getTokenBody(token).getId();
    }
 
    
    /**
     * 是否已过期
     * @param token
     * @throws	 过期无法判断，只能通过捕获ExpiredJwtException异常
     * @return
     */
    @Deprecated
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }
    
    /**
     * 获取token信息，同时也做校验处理
     * @param token
     * @throws	 自定义UserLoginException异常处理
     * @return
     */
    public static Claims getTokenBody(String token){
        try{
        	return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException expired){
            //过期
        	throw new UserLoginException("201","登录过期，请重新登录！");
        }catch (SignatureException e){
            //无效
            throw new UserLoginException("202","登录无效，请重新登录！");
        }catch(MalformedJwtException malformedJwt){
            //无效
        	throw new UserLoginException("202","登录无效，请重新登录！");
        }
    }
}
