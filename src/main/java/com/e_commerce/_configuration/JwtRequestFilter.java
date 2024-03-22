package com.e_commerce._configuration;

import com.e_commerce._util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        System.out.println(header+"my token.....>>>>>>>>>>>>>>>>>>>>>>>");
        final String cardType = request.getHeader("cardType");
        String jwtToken = null;
        String username = null;
        if (header != null && header.startsWith("Bearer ")) {
            jwtToken = header.substring(7).trim();


            try {
                username = jwtUtil.extractUsername(jwtToken);

            } catch (IllegalArgumentException e) {
                System.out.println("unable to get jwt token!!");
            } catch (ExpiredJwtException e) {
                System.out.println("jwt token is expired!!");
            }
        } else {
            System.out.println("jwt token doesn't start with bearer");
            filterChain.doFilter(request, response);
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailService.userDetailsService().loadUserByUsername(username);//+"-"+cardType

            if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
