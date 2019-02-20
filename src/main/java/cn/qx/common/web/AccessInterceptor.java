package cn.qx.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.qx.common.exception.ServiceException;

public class AccessInterceptor extends HandlerInterceptorAdapter {
    /**
     * 返回值表示是否处理该次请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long time = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 15);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        long start = c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY, 18);
        long end = c.getTimeInMillis();
        if(time <start || time >end) {
            throw new ServiceException("请在每天15:00-18:00进行访问");
        }
        return true;
    }
}
