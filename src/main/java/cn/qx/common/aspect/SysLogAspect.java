package cn.qx.common.aspect;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qx.common.annotation.RequestLog;
import cn.qx.common.utils.IPUtils;
import cn.qx.sys.mapper.SysLogMapper;
import cn.qx.sys.entity.Log;
/**
 * 用@Aspect修饰的类表示一个切面对象,此对象中可以封装
 * 我们要织入的扩展功能.
 * <br/>
 * 用@Order指定切面的优先级,数字越小优先级越高.
 * @author hhb
 * @date 2019年2月22日
 */
@Order(1)
@Aspect
@Service
public class SysLogAspect {
	@Autowired
	private SysLogMapper sysLogMapper;
	/**
	 * @Around 注解描述的方法为一个环绕通知(
	 * 用户封装和织入扩展功能),此通知可以在目标
	 * 方法执行之前和之后添加额外的业务操作
	 * 其中@Around注解中的内容为一个切入点表达式
	 * 通过此表达式告诉spring容器,这个通知何时
	 * 会执行.
	 * @param jp 表示连接点对象(封装了具体目标方法信息)
	 * @return 返回目标方法的执行结果
	 * @throws Throwable
	 */
	
	@Around("@annotation(cn.qx.common.annotation.RequestLog)")
	public Object around(ProceedingJoinPoint jp)
	throws Throwable{
		System.out.println("log record around");
		long t1=System.currentTimeMillis();
		System.out.println(t1);
		Object result=jp.proceed();//执行目标方法
		long t2=System.currentTimeMillis();
		System.out.println("方法执行时长:"+(t2-t1));
		saveLog(jp,(t2-t1));
		return result;
	}
	private void saveLog(ProceedingJoinPoint jp,
			long time) throws NoSuchMethodException, SecurityException, JsonProcessingException{
	   //1.获取用户的操作日志
	   //1.1 获取登录用户
		String username=(String)
		SecurityUtils.getSubject().getPrincipal();
	   //1.2获取目标方法的方法名
		Class<?> targetClass=jp.getTarget().getClass();
		String pkgClassName=targetClass.getName();
		MethodSignature ms=//方法签名
		(MethodSignature)jp.getSignature();
		String method=pkgClassName+"."+ms.getName();
	   //1.3获取执行目标方法时传入的实际参数
		Object[] args=jp.getArgs();
		ObjectMapper mapper = new ObjectMapper();
		String params=mapper.writeValueAsString(args);
	   //1.4 获取操作名(这个名字定义在了注解中)
	   //1.4.1获取目标方法对象(业务实现类中的方法)
		Method targetMethod=
		targetClass.getDeclaredMethod(
				ms.getName(),
				ms.getParameterTypes());
	   //1.4.2获取方法对象上注解
		String operation="";
		boolean flag=
		targetMethod.isAnnotationPresent(RequestLog.class);
		if(flag){//判定方法上是否有RequiredLog注解
		RequestLog requiredLog=
		targetMethod.getDeclaredAnnotation(
				RequestLog.class);
		//1.4.3获取注解中定义的操作名
		operation=requiredLog.value();
		}
	   //1.5获取ip地址
		String ip=IPUtils.getIpAddr();
	   //2.封装操作日志
		Log log=new Log();
		log.setUsername(username);
		log.setTime(time);
	    log.setOperation(operation);
		log.setMethod(method);
		log.setParams(params);
		log.setIp(ip);
	    log.setCreatedTime(new Date());
	    //3.保存操作日志(写入到数据库)
	    sysLogMapper.insertObject(log);
	}
}