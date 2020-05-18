package jp.co.sysystem.springWorkout.config.interceptor;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * AOP処理の定義クラス
 * @version 1.0.0 2020/05/13 新規作成
 */
@Aspect
@Component
@Slf4j
public class FunctionInterceptor {

	/**
	* Controller処理が呼び出された際に処理開始および引数、終了のログを出力する
	* @param proceedingJoinPoint
	* @return
	* @throws Throwable
	*/
	@Around("execution(* jp.co.sysystem.springWorkout.web.controller.page..*.*(..))")
	public Object invokeController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object ret = null;
		try {
			if(log.isInfoEnabled()) {
				log.info(
						" {}#{} Start : parameters = {}",
						proceedingJoinPoint.getTarget().getClass(),
						proceedingJoinPoint.getSignature().getName(),
						Arrays.toString(proceedingJoinPoint.getArgs()));
			}

			ret = proceedingJoinPoint.proceed();
			return ret;
		} finally {
			if(log.isInfoEnabled()) {
				log.info(
						" {}#{} End : return = {}",
						proceedingJoinPoint.getTarget().getClass(),
						proceedingJoinPoint.getSignature().getName(),
						ret);
			}

			// レスポンスにエラーが含まれる場合はログに出力する
			//			if (ret != null && ret instanceof ResponseEntity) {
			//				ResponseEntity<?> r = (ResponseEntity<?>) ret;
			//				if (r.getBody() != null && r.getBody() instanceof BaseResponse) {
			//					BaseResponse<?> b = (BaseResponse<?>) r.getBody();
			//					if(b.getErrorMessages() != null && !b.getErrorMessages().isEmpty()){
			//						log.warn(b.getErrorMessages());
			//					}
			//				}
			//			}
		}
	}

	/**
	* Service処理が呼び出された際に処理開始および引数、終了のログを出力する
	* @param proceedingJoinPoint
	* @return
	* @throws Throwable
	*/
	@Around("execution(* jp.co.sysystem.springWorkout.service..*.*(..))")
	public Object invokeService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object ret = null;
		try {
			if(log.isDebugEnabled()) {
				log.trace(
						" {}#{} Start : parameters = {}",
						proceedingJoinPoint.getTarget().getClass(),
						proceedingJoinPoint.getSignature().getName(),
						Arrays.toString(proceedingJoinPoint.getArgs()));
			}

			ret = proceedingJoinPoint.proceed();
			return ret;
		} finally {
			if(log.isDebugEnabled()) {
				log.trace(
						" {}#{} End : return = {}",
						proceedingJoinPoint.getTarget().getClass(),
						proceedingJoinPoint.getSignature().getName(),
						ret);
			}
		}
	}
}
