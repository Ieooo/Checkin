package com.leo.checkin.interceptor;

import com.leo.checkin.annotaion.NoNeedLogin;
import com.leo.checkin.exception.AppException;
import com.leo.checkin.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/11 23:32
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String requestPath = request.getRequestURI();
            if (requestPath.contains("/v2/api-docs")
                    || requestPath.contains("/swagger")
                    || handlerMethod.getMethod().isAnnotationPresent(NoNeedLogin.class)
                    || requestPath.contains("/error")
                    || requestPath.contains("/configuration/ui")) {
                return true;
            }
            String token = request.getHeader("tk");
            if (StringUtils.isBlank(token)) {
                throw new AppException("no token");
            }
            // 验证token有效性
            if (StringUtils.isBlank(TokenUtil.getUsernameFromToken(token))) {
                throw new AppException("token非法或过期");
            }
         }
        return true;
    }

}
