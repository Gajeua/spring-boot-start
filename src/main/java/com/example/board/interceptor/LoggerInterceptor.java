package com.example.board.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
// spring 5.3 이상에서는 HandlerInterceptor 를 implements 하는 것으로 변경..
// public class LoggerInterceptor extends HandlerInterceptorAdapter {
public class LoggerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        log.debug("=========================== START ==========================");
        log.debug("Request URI \t : {}", request.getRequestURI());
        // impl 하는 방식으로는 true 반환.
        // return super.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        log.debug("=========================== END ==========================");
    }
}
