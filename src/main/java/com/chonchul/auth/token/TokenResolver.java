package com.chonchul.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

@Component
public class TokenResolver {
    private static final String USER_ID_CLAIM_KEY = "userId";
    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    public TokenResolver(
            @Value("${jwt.access.secretKey}") String accessSecretKey,
            @Value("${jwt.refresh.secretKey}") String refreshSecretKey
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes());
    }

    private Claims getAccessClaims(final String token) {
        return parseClaims(token, accessSecretKey);
    }

    private Claims getRefreshClaims(final String token) {
        return parseClaims(token, refreshSecretKey);
    }

    private Long getClaimValue(Claims claims, String claimKey) {
        return claims.get(claimKey, Long.class);
    }

    public Long getUserDataByAccessToken(final String token) {
        return getClaimValue(getAccessClaims(token), USER_ID_CLAIM_KEY);
    }

//    private Claims parseClaims(final String token, final SecretKey secretKey) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//        } catch (ExpiredJwtException e) {
//            throw new TokenParsingException();
//        } catch (SignatureException e) {
//            throw new TokenParsingException();
//        } catch (Exception e) {
//            throw new TokenParsingException();
//        }
//    }
    private Claims parseClaims(final String token, final SecretKey secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

}
