package com.my.task.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.my.task.entity.Account;
import com.my.task.model.TokenPayLoad;
import com.my.task.repository.AccountRepository;
import com.my.task.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String token = null;
        TokenPayLoad payload = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Token")) {
            token = requestTokenHeader.substring(6);
            try {

                payload = jwtTokenUtil.myGetTokenPayload(token);
            } catch (SignatureException e) {
                System.out.println("Invalid token");
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get token");
            } catch (ExpiredJwtException e) {
                System.out.println("Token has expired");
            }

        } else {
            System.out.println("not token");
        }
        if (payload != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Account> accountOptional = accountRepository.findByUsername(payload.getUsername());
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                // if (jwtTokenUtil.validate(token, user)) {
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(account.getUsername(),
                        account.getPassword(), new ArrayList<>());
                UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(userNamePassword);
                // }
            }
        }
        System.out.println(payload);
        filterChain.doFilter(request, response);

    }

}
