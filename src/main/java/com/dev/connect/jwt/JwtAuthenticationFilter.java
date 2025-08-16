package com.dev.connect.jwt;

import com.dev.connect.service.Impl.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token=null;
        String userName=null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){

            token=authHeader.substring(7);
            userName=jwtUtil.getUserNameFromToken(token);

        }
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = userDetailService.loadUserByUsername(userName);

            if(jwtUtil.isTokenValid(token,userDetails)){

                UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }
        filterChain.doFilter(request,response);
    }
}
