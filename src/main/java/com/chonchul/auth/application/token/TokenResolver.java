package com.chonchul.auth.application.token;

import com.chonchul.auth.application.exception.TokenParsingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenResolver {
    private static final String USER_ID_CLAIM_KEY = "userId";
    private static final String LECTURE_ID_CLAIM_KEY = "lectureId";
    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final SecretKey qrSecretKey;

    public TokenResolver(
            @Value("${jwt.access.secretKey}") String accessSecretKey,
            @Value("${jwt.refresh.secretKey}") String refreshSecretKey,
            @Value("${jwt.qr.secretKey}") String qrSecretKey
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes());
        this.qrSecretKey = Keys.hmacShaKeyFor(qrSecretKey.getBytes());
    }

    private Claims getAccessClaims(final String token) {
        return parseClaims(token, accessSecretKey);
    }

    private Claims getRefreshClaims(final String token) {
        return parseClaims(token, refreshSecretKey);
    }

    private Claims getQrClaims(final String token) {
        return parseClaims(token, qrSecretKey);
    }

    private Long getClaimValue(Claims claims, String claimKey) {
        return claims.get(claimKey, Long.class);
    }

    public Long getUserDataByRefreshToken(final String token) {
        return getClaimValue(getRefreshClaims(token), USER_ID_CLAIM_KEY);
    }

    public Long getUserDataByAccessToken(final String token) {
        return getClaimValue(getAccessClaims(token), USER_ID_CLAIM_KEY);
    }

    public Long getLectureDataByQrToken(final String token) {
        return getClaimValue(getQrClaims(token), LECTURE_ID_CLAIM_KEY);
    }

    private Claims parseClaims(final String token, final SecretKey secretKey) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenParsingException();
        } catch (SignatureException e) {
            throw new TokenParsingException();
        } catch (Exception e) {
            throw new TokenParsingException();
        }
    }
}
