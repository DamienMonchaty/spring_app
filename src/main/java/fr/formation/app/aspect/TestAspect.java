package fr.formation.app.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Component // Injecte un bean dans Spring core
@Aspect
public class TestAspect {

	@Pointcut("execution(* *..*.*ice.*ame(..))")
	public void pointCutPersonService() {
	}

	@Before("@annotation(fr.formation.app.utils.Log)")
	public void beforeLog(JoinPoint joinpoint) {
		System.out.println(" ");
		System.out.println("beforeLog ");
		
		System.out.println("beforeLog Method : " + joinpoint.getSignature().getName());
		System.out.println("beforeLog Args : " + Arrays.toString(joinpoint.getArgs()));
		System.out.println("beforeLog Target class : " + joinpoint.getTarget().getClass().getName());
		System.out.println("Before Log !");
	}

	// @Before : declaration d’un greffon
	// Type de point de jonction : within pour dire toutes les methodes qui sont
	// dans ce package ou un de ses sous-packages
	@Before("pointCutPersonService()")
	public void before(JoinPoint joinpoint) {
		// Code du greffon
		System.out.println(" ");
		System.out.println("before ");
		
		System.out.println("Before Method : " + joinpoint.getSignature().getName());
		System.out.println("Before Args : " + Arrays.toString(joinpoint.getArgs()));
		System.out.println("Before Target class : " + joinpoint.getTarget().getClass().getName());
		System.out.println("Before Method !");
	}

	@After("pointCutPersonService()")
	public void after(JoinPoint joinpoint) {
		System.out.println(" ");
		System.out.println("after ");
		
		System.out.println("After Method : " + joinpoint.getSignature().getName());
		System.out.println("After Args : " + Arrays.toString(joinpoint.getArgs()));
		System.out.println("After Target class : " + joinpoint.getTarget().getClass().getName());
		System.out.println("After Method !");
	}

//	@AfterReturning(pointcut = "execution(* *..*.*ice.performAction(..))", returning = "result")
//	public void logAfterReturning(Object result) {
//		System.out.println("Method returned value: " + result);
//	}

	@AfterThrowing(pointcut = "execution(* *..*.*ice.getPersonLastName(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		System.out.println(" ");
		System.out.println("logAfterThrowing ");

		System.out.println("Exception in method: " + joinPoint.getSignature().getName());
		System.out.println("Exception message: " + error.getMessage());
		System.out.println("Method args: " + Arrays.toString(joinPoint.getArgs()));
		System.out.println("Exception target class: " + joinPoint.getTarget().getClass().getName());
	}
	
//	@Around("execution(* *..*.*ice.performAction(..)) && args(arg)")
//	public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, String arg) {
//		System.out.println(" ");
//		System.out.println("aroundAdvice ");
//		
//		System.out.println("Parametre " + arg);
//		System.out.println("Methode " + proceedingJoinPoint.getSignature());
//		Object value = null;
//		try {
//			value = proceedingJoinPoint.proceed();
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		System.out.println("Valeur de retour : " +  value);
//		return value;
//	}
  

	@Around("execution(* fr.formation.app.services.*ice.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		System.out.println(" ");
		System.out.println("aroundAdvice ");
		
		Object[] args = proceedingJoinPoint.getArgs();
		
		System.out.println("Methode " + proceedingJoinPoint.getSignature());
		System.out.println("Method args: " + Arrays.toString(args));

		Object value = null;
		try {
			value = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("Valeur de retour : " +  value);
		return value;
	}
}
