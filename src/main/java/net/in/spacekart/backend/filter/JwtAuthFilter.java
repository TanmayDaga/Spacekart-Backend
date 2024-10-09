package net.in.spacekart.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.services.JwtService;
import net.in.spacekart.backend.services.entityServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;



    public JwtAuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String token = null;
        String username = null;
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie c : cookie) {
                if (c.getName().equals("auth") && c.getValue().startsWith("Bearer")) {
                    token = c.getValue();
                    token = token.substring(6);
                    username = jwtService.extractPayload(token);
                }
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            UserAuthentication user = (UserAuthentication) userService.loadUserByUsername(username);
            if (user != null && jwtService.validateToken(token, user.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        System.out.println(request);

        filterChain.doFilter(request, response);
    }
}
