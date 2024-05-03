package com.example.bookstorespringboot.filter;

import com.example.bookstorespringboot.model.UserSession;
import com.example.bookstorespringboot.repository.UserSessionRepository;
import com.example.bookstorespringboot.utils.JwtUtil;
import com.example.bookstorespringboot.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        try{
            Claims claims= JwtUtil.parseJWT(token);
            String email= claims.getSubject();
            // 使用email查询用户会话
            UserSession session = userSessionRepository.findByUserId(email);
            if (session == null || !session.getToken().equals(token)) {
                throw new RuntimeException("无效的token或用户未登录");
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(session, null, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request,response);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token 非法");
        }


    }
}
