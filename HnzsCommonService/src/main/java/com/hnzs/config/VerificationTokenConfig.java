package com.hnzs.config;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hnzs.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;



//@Aspect
//@Component
@SuppressWarnings("all")
public class VerificationTokenConfig {

	private static Logger logger = LoggerFactory.getLogger(VerificationTokenConfig.class);


	@Autowired
	private StringRedisTemplate redisTemplate;//spring封装好的


	//声明该方法是一个前置通知：在目标方法开始之前执行
	//@Before("execution(public int com.yl.spring.aop.impl.ArithmeticCalculatorImpl.add(int, int))")
	//@Before("execution(* com.minhao.controller.*.*.*(..)), ")
	public void beforeMethod(JoinPoint joinpoint) {
		try{
			//现在访问接口时间毫秒数
			long now_access_times = System.currentTimeMillis();
			//前置通知的一些方法（可以不做处理）
			String methodName = joinpoint.getSignature().getName();
//			System.out.println(methodName);
			List<Object> args = Arrays.asList(joinpoint.getArgs());
			HttpServletRequest request = (HttpServletRequest) args.get(0);
			String paramToken = request.getParameter("token");
			System.out.println("param token:" + request.getParameter("token") + "\t" + methodName);

			if("login".equalsIgnoreCase(methodName)){
				logger.info("登录成功");
//				session.setAttribute("userName", request.getParameter("username"));
			}else if(!"loginOut".equalsIgnoreCase(methodName)){
				String redisToken = redisTemplate.opsForValue().get(paramToken);
				if(StringUtil.isNull(redisToken)){
					System.out.println("token已过期。。。。。");
				}
			}
//			logger.info("Success VerificationTokenConfig");
		}catch (Exception e){
			logger.error("Faile VerificationTokenConfig by:{}", e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	//后置通知：在目标方法执行后（无论是否发生异常），执行的通知
	//在后置通知中，还不能访问目标方法执行的结果
//	@After("execution(* com.minhao.controller.wlgl.*.*(..))")
//	public void afterMethod(JoinPoint joinPoint) {
//	String methodName = joinPoint.getSignature().getName();
//	List<Object> args = Arrays.asList(joinPoint.getArgs());
//		System.out.println("The method " + methodName + " end with " + args);
//	}
	
	/**
	 * 得到request对象
	 */
//	public HttpServletRequest getRequest() {
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//
//		return request;
//	}
}
