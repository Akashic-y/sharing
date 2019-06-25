package com.yn.common.aspect;

import com.alibaba.fastjson.JSON;
import com.yn.common.annotation.LogAnnotation;
import com.yn.common.util.HttpContextUtils;
import com.yn.common.util.IpUtils;
import com.yn.common.util.UserUtils;
import com.yn.entity.Log;
import com.yn.entity.User;
import com.yn.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面
 *
 * @author yn
 * <p>
 * 2018年4月18日
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.yn.common.annotation.LogAnnotation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);

        if (log != null) {
            log.setModule(logAnnotation.module());
            log.setOperation(logAnnotation.operation());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");

        //请求的参数
        /*Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.setParams(params);*/

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setIp(IpUtils.getIpAddr(request));

        //用户名
        User user = UserUtils.getCurrentUser();

        if (null != user) {
            log.setUserId(user.getId());
            log.setNickname(user.getNickname());
        } else {
            log.setUserId(-1L);
            log.setNickname("获取用户信息为空");
        }

        log.setTime(time);
        log.setCreateDate(new Date());

        logService.saveLog(log);
    }

}
