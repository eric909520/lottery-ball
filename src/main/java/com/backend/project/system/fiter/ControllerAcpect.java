package com.backend.project.system.fiter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.StringUtils;
import com.backend.common.utils.security.AesUtil;
import io.sentry.Sentry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
public class ControllerAcpect {

    private Logger logger = LoggerFactory.getLogger(ControllerAcpect.class);

    @Pointcut("execution(public * com.backend.project.system.*.*Controller.*(..))")
    public void pointcut() {
    }


    @Around(value = "pointcut()")
    private void doAround(ProceedingJoinPoint joinPoint) {
        logger.info("进入控制层AOP解码------------------------------------------------------------------------------------------------------");
        try {
            //所有参数
            Object[] args = joinPoint.getArgs();
            if(args != null && args.length > 0 ){
                Object obj = args[0];
                logger.info(obj.toString());
                //记录@RequestBody注解的对象类型
                Class<?> aClass = obj.getClass();
                logger.info(aClass.toString());
                logger.info(JSONObject.toJSONString(obj));
                JSONObject object = JSON.parseObject(JSONObject.toJSONString(obj));
                //提取body，
                String data = object.getString("data");
                if(StringUtils.isNotBlank(data)){
                    //替换参数，并回写
                    data = data.replaceAll("\\\\","");
                    String deodeData = AesUtil.aesDecryptCbc(data);
                    logger.info("解析后的参数："+JSONObject.toJSONString(deodeData));
                    JSONObject bodyStr = JSON.parseObject(deodeData);
                    args[0] = JSONObject.parseObject(bodyStr.toJSONString(), aClass);
                    logger.info("封装入参："+ JSONObject.parseObject(bodyStr.toJSONString(), aClass).toString());
                    logger.info("封装入参："+args[0].toString());
                    joinPoint.proceed(args);
                }
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
        } catch (Throwable throwable) {
            Sentry.captureException(throwable);
            throwable.printStackTrace();
        }
    }

    /**
     * 定义切入点，切入点为com.example.aop下的所有函数
     */
   /* @Pointcut("execution(public * com.example.aop..*.*(..))")
    public void webLog(){}

    *//**
     * 前置通知：在连接点之前执行的通知
     * @param joinPoint
     * @throws Throwable
     *//*
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }*/
}
