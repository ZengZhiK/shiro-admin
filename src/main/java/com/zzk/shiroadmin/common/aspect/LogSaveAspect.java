package com.zzk.shiroadmin.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.shiroadmin.common.annotation.LogSave;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.utils.HttpContextUtils;
import com.zzk.shiroadmin.common.utils.IPUtils;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import com.zzk.shiroadmin.mapper.SysLogMapper;
import com.zzk.shiroadmin.model.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * 日志保存切面
 *
 * @author zzk
 * @create 2021-02-12 17:37
 */
@Component
@Aspect
@Slf4j
public class LogSaveAspect {
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 以自定义 @LogSave 注解为切点
     */
    @Pointcut("@annotation(com.zzk.shiroadmin.common.annotation.LogSave)")
    public void logSave() {
    }

    /**
     * 环绕增强
     *
     * @param point
     * @return
     */
    @Around("logSave()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(point, time);
        return result;
    }

    /**
     * 把日志保存
     *
     * @param joinPoint
     * @param time
     * @return void
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        LogSave logSave = method.getAnnotation(LogSave.class);
        if (logSave != null) {
            //注解上的描述
            sysLog.setOperation(logSave.title() + "-" + logSave.action());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        sysLog.setParams(getParams(joinPoint));

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        //用户名
        String token = request.getHeader(JwtConstants.ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token) && JwtTokenUtils.validateToken(token)) {
            String userId = JwtTokenUtils.getUserId(token);
            String username = JwtTokenUtils.getUserName(token);
            sysLog.setUsername(username);
            sysLog.setUserId(userId);
        }
        sysLog.setTime((int) time);
        sysLog.setId(UUID.randomUUID().toString());
        sysLog.setCreateTime(new Date());

        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * 获取参数
     *
     * @param joinPoint 切点
     * @return 参数信息
     */
    private String getParams(JoinPoint joinPoint) {
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                Object arg = joinPoint.getArgs()[i];
                if ((arg instanceof HttpServletResponse) || (arg instanceof HttpServletRequest)
                        || (arg instanceof MultipartFile) || (arg instanceof MultipartFile[])) {
                    continue;
                }
                try {
                    params += JSONObject.toJSONString(joinPoint.getArgs()[i]);
                } catch (Exception e1) {
                    log.error(e1.getMessage());
                }
            }
        }
        return params;
    }
}
