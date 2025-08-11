package com.harrisonmoses.store.filters;

import com.harrisonmoses.store.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       if(request.getHeader("Authorization")==null || !request.getHeader("Authorization").startsWith("Bearer ")){
           filterChain.doFilter(request,response);
           return;
       };

       var token =  request.getHeader("Authorization").replace("Bearer ", "");
       var jwt = jwtService.parseToken(token);

       if(jwt == null || !jwt.isValid()){
           filterChain.doFilter(request,response);
           return;
       }

       var userId =  jwt.getId();
       var userRole =  jwt.getRole();

        var authentication = new UsernamePasswordAuthenticationToken(
              userId,
               null,
               List.of(new SimpleGrantedAuthority("ROLE_"+userRole))
       );

       //We attach the metadata to the request like the IP address
       authentication.setDetails(new WebAuthenticationDetails(request));

       //This helps to get access to the current user
       SecurityContextHolder.getContext().setAuthentication(authentication);

       //Pass control to the next filter in the chain
       filterChain.doFilter(request,response);

    }
}
