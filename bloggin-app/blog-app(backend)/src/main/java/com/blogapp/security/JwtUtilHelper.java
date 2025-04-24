package com.blogapp.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtilHelper {
		@Value("${jwt.secret}")
		private String secretKey;
		
		@Value("${jwt.expiration}")
		private long expiration;
		
		public String generateToken(String userName) {
			return Jwts.builder().
					setSubject(userName).
					setIssuedAt(new Date(System.currentTimeMillis())).
					setExpiration(new Date(System.currentTimeMillis()+expiration))
					.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
					.compact();
		}
		public boolean validateToken(String token, UserDetails userDetails) {
		    try {
		        Claims claims = getClaims(token);
		        String username = claims.getSubject();
		        Date expiration = claims.getExpiration();

		        return (username.equals(userDetails.getUsername()) && !expiration.before(new Date()));
		    } catch (ExpiredJwtException ex) {
		        System.out.println("JWT expired: " + ex.getMessage());
		    } catch (UnsupportedJwtException ex) {
		        System.out.println("Unsupported JWT: " + ex.getMessage());
		    } catch (MalformedJwtException ex) {
		        System.out.println("Malformed JWT: " + ex.getMessage());
		    } catch (SignatureException ex) {
		        System.out.println("Invalid signature: " + ex.getMessage());
		    } catch (IllegalArgumentException ex) {
		        System.out.println("Illegal argument token: " + ex.getMessage());
		    }

		    return false;
		}

		public String getUsernameFromToken(String token) {
			return getClaims(token).getSubject();
		}
		private Claims getClaims(String token) {
			return Jwts.parserBuilder().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).
					build().
					parseClaimsJws(token).getBody();
		}
		
}
