package com.chonchul.auth.token;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private static final String USER_ID_CLAIM_KEY = "userId";
    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;

    private static final String LECTURE_ID_CLAIM_KEY = "lectureId";
    private final SecretKey qrSecretKey;
    private final Long qrTokenExpireTime;

    public TokenProvider(
            @Value("${jwt.access.secretKey}") String accessSecretKey,
            @Value("${jwt.refresh.secretKey}") String refreshSecretKey,
            @Value("${jwt.access.expireTime}") Long accessTokenExpireTime,
            @Value("${jwt.refresh.expireTime}") Long refreshTokenExpireTime,
            @Value("${jwt.qr.secretKey}") String qrSecretKey,
            @Value("${jwt.qr.expireTime}") Long qrTokenExpireTime
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes());
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.qrSecretKey = Keys.hmacShaKeyFor(qrSecretKey.getBytes());
        this.qrTokenExpireTime = qrTokenExpireTime;
    }

    public String createAccessToken(final Long userId) {
        final Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim(USER_ID_CLAIM_KEY, userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpireTime))
                .signWith(accessSecretKey)
                .compact();
    }

    public String createRefreshToken(final Long userId) {
        final Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim(USER_ID_CLAIM_KEY, userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpireTime))
                .signWith(refreshSecretKey)
                .compact();
    }

    public String createQrToken(final Long lectureId) {
        final Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim(LECTURE_ID_CLAIM_KEY, lectureId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + qrTokenExpireTime))
                .signWith(qrSecretKey)
                .compact();
    }
}
