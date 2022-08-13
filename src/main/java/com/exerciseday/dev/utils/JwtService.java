package com.exerciseday.dev.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.secret.secret;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.exerciseday.dev.config.BaseResponseStatus.EMPTY_JWT;
import static com.exerciseday.dev.config.BaseResponseStatus.INVALID_JWT;

@Service
public class JwtService {

    /*
     * JWT 생성
     * 
     * @param userIdx
     * 
     * @return String
     */
    // .claim -> payload 담고 싶은 정보 담기
    public String createJwt(int userIdx) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365)))
                .signWith(SignatureAlgorithm.HS256, secret.JWT_SECRET_KEY)
                .compact();
    }

    /*
     * Header에서 X-ACCESS-TOKEN 으로 JWT 추출
     * 
     * @return String
     */
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
     * JWT에서 userIdx 추출
     * 
     * @return int
     * 
     * @throws BaseException
     */
    public int getUserIdx() throws BaseException {
        // 1. JWT 추출
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("userIdx", Integer.class);
    }

    /*
     * JWT에서 만료일 추출
     */
    public Date getExp() throws BaseException {
        // 1. JWT 추출
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. exp 추출
        return claims.getBody().get("exp", Date.class);
    }

    public boolean isExpiredJWT() throws BaseException {
        Date exp = getExp();
        Date now = new Date();
        System.out.println("만료일 : " + exp);
        System.out.println("현재 : " + now);
        if (exp.before(now)) {
            return true;
        } else {
            return false;
        }
    }
}
