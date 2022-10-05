package com.my.task.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.my.task.entity.Account;
import com.my.task.model.TokenPayLoad;
import com.my.task.repository.AccountRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final AccountRepository accountRepository;
    private String secret = "tudda";

    public String generateToken(Account account, long time) {
        Map<String, Object> claims = new HashMap<String, Object>();
        TokenPayLoad payload = TokenPayLoad.builder().username(account.getUsername()).build();
        claims.put("payload", payload);

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public TokenPayLoad getTokenPayload(String token) {
        return getClaimsFromToken(token, (Claims claims) -> {
            Map<String, Object> mapResult = (Map<String, Object>) claims.get("payload");
            return TokenPayLoad.builder()
                    .username((String) mapResult.get("username"))
                    .role((int) mapResult.get("role"))
                    .build();
        });
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimResolver.apply(claims);
    }

    public TokenPayLoad myGetTokenPayload(String token) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Map<String, Object> mapResult = (Map<String, Object>) claims.get("payload");
        return TokenPayLoad.builder()
                .username((String) mapResult.get("username"))
                .role((int) mapResult.get("role"))
                .build();
    }

    public Account getAccountLogin(String token) {
        TokenPayLoad payload = getTokenPayload(token);
        payload.setRole(1);
        Optional<Account> account = accountRepository.findByUsername(payload.getUsername());
        Account acc = null;
        if (account.isPresent()) {
            acc = account.get();
            return acc;
        } else {
            return null;
        }
    }
}
