package com.inventario.projeto.security.jwt;

import com.inventario.projeto.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtExpirationMs}")
    private Integer jwtExpirationMs;

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtCookie}")
    private String jwtCookie;

    public String getJwtTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String generateTokenFromUsername(String nomeDoUsuario) {
        return Jwts.builder()
                .subject(nomeDoUsuario)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public ResponseCookie generateJwtCookies(UserDetailsImpl userDetails) {
        String jwt = generateTokenFromUsername(userDetails.getUsername());

        return ResponseCookie.from(jwtCookie, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie generateCleanJwtCookies() {
        return ResponseCookie.from(jwtCookie, null)
                .path("/api")
                .build();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirou : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT não é suportado : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT afirma que a string está vazia : {}", e.getMessage());
        }
        return false;
    }
}
