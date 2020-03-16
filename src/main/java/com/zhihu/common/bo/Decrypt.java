package com.zhihu.common.bo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Decrypt {
	public DecodedJWT deToken(final String token) {
		DecodedJWT jwt = null;
		try {
			System.out.println(JWT.decode(token).getAudience().get(0));
//			JWTVerifier verifier = JWT.require(Algorithm.HMAC256("mysecret")).withIssuer("auth0").build();
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256("tom")).withIssuer("auth0").build();
			jwt = verifier.verify(token);
		} catch (JWTVerificationException exception) {
			exception.printStackTrace();
		}
		return jwt;
	}

	public static void main(String[] args) {
		Decrypt d = new Decrypt();
		DecodedJWT jwt = d.deToken(
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ0b20iLCJpc3MiOiJhdXRoMCJ9.HWbtsY-WC__jNNEdOivrehWsBMtgQDo2J_TucQQErZc");
		System.out.println("issuer: " + jwt.getIssuer());
//		System.out.println("isVip:  " + jwt.getClaim("isVip").asBoolean());
		System.out.println("userid: " + jwt.getClaim("userid").asString());
		System.out.println("username: " + jwt.getClaim("username").asString());
		System.out.println(jwt.getClaims());
//		System.out.println("过期时间：      " + jwt.getExpiresAt());
	}
}
