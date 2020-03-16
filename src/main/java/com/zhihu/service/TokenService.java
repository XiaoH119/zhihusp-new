package com.zhihu.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhihu.common.bean.LoginBean;

import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * @author jinbin
 * @date 2018-07-08 21:04
 */
@Service
public class TokenService {
	public String getToken(LoginBean user) {
		String token = "";
//		token = JWT.create().withIssuer("auth0").withAudience(user.getUserid()).withClaim("userid", user.getUserid())
//				.sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为 token 的密钥
		Date expiresAt = new Date(System.currentTimeMillis() + 24L * 60L * 3600L * 1000L);
		token = JWT.create().withIssuer("auth0").withAudience(user.getUserid()).withClaim("userid", user.getUserid())
				.withClaim("username", user.getUsername()).withExpiresAt(expiresAt)
				.sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为
		// token 的密钥

//		Date expiresAt = new Date(System.currentTimeMillis() + 24L * 60L * 3600L * 1000L);
//		token = JWT.create().withIssuer("auth0").withClaim("isVip", isVip).withClaim("username", username)
//				.withClaim("name", name).withExpiresAt(expiresAt).sign(Algorithm.HMAC256("mysecret"));

//		Date expiresAt = new Date(System.currentTimeMillis() + 24L * 60L * 3600L * 1000L);
//		System.out.println(user.getUserid() + "AAAAAAAAAAAAAAA");
//		token = JWT.create().withIssuer("auth0").withClaim("userid", user.getUserid())
//				.withClaim("username", user.getUsername()).withExpiresAt(expiresAt)
//				.sign(Algorithm.HMAC256(user.getPassword()));
		return token;
	}
}
