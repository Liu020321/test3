package org.example.student;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * @Author www.hiai.top
 * @Email goodsking@163.com
 *        此拦截器仅做为示例展示，在本 demo 中并不需要
 */
public class StudentInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		System.out.println("Before invoking " + inv.getActionKey());
		inv.invoke();
		System.out.println("After invoking " + inv.getActionKey());
	}
}
