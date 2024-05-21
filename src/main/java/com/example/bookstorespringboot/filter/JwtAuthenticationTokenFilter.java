package com.example.bookstorespringboot.filter;

import com.example.bookstorespringboot.model.UserSession;
import com.example.bookstorespringboot.repository.UserSessionRepository;
import com.example.bookstorespringboot.service.UserAuthService;
import com.example.bookstorespringboot.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UserAuthService userAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 从 header 中获取 token
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {
            // 如果 token 为空，放行
            filterChain.doFilter(request, response);
            return;
        }

        // 解析 token
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String email = claims.getSubject();

            // 使用 email 查询用户会话
            UserSession session = userSessionRepository.findByUserId(email);
            if (session == null || !session.getToken().equals(token)) {
                throw new RuntimeException("无效的 token 或用户未登录");
            }

            // 加载用户详情
            UserDetails userDetails = userAuthService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token 非法");
        }

        // 放行
        filterChain.doFilter(request, response);
    }
}
