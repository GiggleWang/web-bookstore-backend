package com.example.bookstorespringboot.service.Impl;

import com.example.bookstorespringboot.controller.ResponseResult;
import com.example.bookstorespringboot.dao.UserAuthDAO;
import com.example.bookstorespringboot.dao.UserSessionDAO;
import com.example.bookstorespringboot.model.LoginRequest;
import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.model.UserSession;
import com.example.bookstorespringboot.service.LoginService;
import com.example.bookstorespringboot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSessionDAO userSessionDAO;

    @Autowired
    private UserAuthDAO userAuthDAO;

    @Override
    public ResponseResult login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Integer type = loginRequest.getType();

        Integer actualType = userAuthDAO.findTypeByEmail(email);
        if (actualType == null) {
            return new ResponseResult(400, "用户不存在", null);
        }

        Boolean disabled = userAuthDAO.findDisabledByEmail(email);

        if (disabled == Boolean.TRUE) {
            return new ResponseResult(400, "用户已被禁用", null);
        }

        if (!actualType.equals(type)) {
            return new ResponseResult(400, "用户类型不匹配", null);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
            if (Objects.isNull(authenticate)) {
                throw new RuntimeException("登录失败");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("登录失败: " + e.getMessage());
        }

        Object principal = authenticate.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new IllegalStateException("Principal is not an instance of UserDetails");
        }

        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        String jwt = JwtUtil.createJWT(username);

        UserSession session = new UserSession();
        session.setUserId(username);
        session.setToken(jwt);
        userSessionDAO.save(session);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200, "登录成功", map);
    }

    @Override
    @Transactional
    public ResponseResult logout() {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            if (authenticationToken != null) {
                UserDetails userDetails = (UserDetails) authenticationToken.getPrincipal();
                if (userDetails != null) {
                    String username = userDetails.getUsername();
                    userSessionDAO.deleteByUserId(username);
                    SecurityContextHolder.clearContext();
                    return new ResponseResult(200, "注销成功");
                } else {
                    return new ResponseResult(500, "用户信息不完整，无法注销");
                }
            } else {
                return new ResponseResult(401, "未认证的用户无法进行注销操作");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(500, "注销失败，内部服务器错误");
        }
    }
}
