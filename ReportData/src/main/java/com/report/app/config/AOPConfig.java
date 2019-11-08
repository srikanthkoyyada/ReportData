package com.report.app.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class AOPConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(AOPConfig.class);

	@Before("execution(* com.data.app.*.*(..))")
	public void before(JoinPoint joinPoint) {
		LOGGER.info("Starting method execution: " + joinPoint.getSignature().getName() + " in class:"
				+ joinPoint.getSignature().getDeclaringTypeName());

		LOGGER.info("Exiting method execution: " + joinPoint.getSignature().getName() + " in class:"
				+ joinPoint.getSignature().getDeclaringTypeName());

	}

	@After(value = "execution(* com.data.app.*.*(..))")
	public void after(JoinPoint joinPoint) {
		LOGGER.info("after execution of {}", joinPoint);
	}

	@AfterReturning(value = "execution(* com.data.app.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {

		LOGGER.info("{} returned with value {}", joinPoint, result);
	}

	@AfterThrowing(value = "execution(* com.data.app.*.*(..))", throwing = "ex")
	public void doRecoveryActions(DataAccessException ex) {
		LOGGER.info("after expetions thrown : ", ex.getLocalizedMessage());
	}

	@Around(value = "execution(* com.data.app.*.*(..))")
	public Object aroundMethod(ProceedingJoinPoint jointPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) jointPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();//only class name with out package
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		// Measure method execution time
		stopWatch.start();
		Object result = jointPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		LOGGER.info(
				"Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");

		return result;

	}

}
