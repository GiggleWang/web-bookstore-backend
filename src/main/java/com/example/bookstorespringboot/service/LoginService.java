package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.controller.ResponseResult;
import com.example.bookstorespringboot.model.LoginRequest;
import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.model.UserSession;
import com.example.bookstorespringboot.repository.UserSessionRepository;
import com.example.bookstorespringboot.utils.JwtUtil;
import com.example.bookstorespringboot.utils.RedisCache;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSessionRepository userSessionRepository;

    public ResponseResult login(LoginRequest loginRequest) {
        // From LoginRequest, get email and password
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Create authentication token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        // Perform authentication
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
            if (Objects.isNull(authenticate)) {
                throw new RuntimeException("登录失败");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("登录失败: " + e.getMessage());
        }

        // Check if authentication was successful
        Object principal = authenticate.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new IllegalStateException("Principal is not an instance of UserDetails");
        }

        // Extract username or other identifier from UserDetails
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();  // Assuming the username can serve as a unique identifier

        // Generate JWT token using the username
        String jwt = JwtUtil.createJWT(username);

        // Store or update user session
        UserSession session = new UserSession();
        session.setUserId(username);  // Here, username is used as the identifier
        session.setToken(jwt);
        userSessionRepository.save(session);



        // Prepare and return the response result
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200, "登录成功", map);
    }

    @Transactional
    public ResponseResult logout() {
        try {
            // 获取当前的authenticationToken
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            if (authenticationToken != null) {
                // 安全地获取UserSession对象
                UserSession userSession = (UserSession) authenticationToken.getPrincipal();
                if (userSession != null) {
                    String username = userSession.getUserId();

                    // 从数据库中删除对应的用户会话记录
                    userSessionRepository.deleteByUserId(username);

                    // 清除安全上下文
                    SecurityContextHolder.clearContext();

                    // 返回成功响应
                    return new ResponseResult(200, "注销成功");
                } else {
                    // 如果principal为null，返回错误响应
                    return new ResponseResult(500, "用户信息不完整，无法注销");
                }
            } else {
                return new ResponseResult(401, "未认证的用户无法进行注销操作");
            }
        } catch (Exception e) {
            // 记录日志，方便调试
            e.printStackTrace();

            // 返回错误响应
            return new ResponseResult(500, "注销失败，内部服务器错误");
        }
    }
}

