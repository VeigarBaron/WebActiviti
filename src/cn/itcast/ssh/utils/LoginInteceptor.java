package cn.itcast.ssh.utils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import cn.itcast.ssh.domain.Employee;


/**
 * 登录验证拦截器
 *
 */
@SuppressWarnings("serial")
public class LoginInteceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	//每次访问Action之前  都要先走intercept类
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//获取访问当前action的  url
		String actionName = invocation.getProxy().getActionName();
		
		//如果当前访问的action的url是loginaction_login表示此时还没有session，需要放行
		if(!"loginAction_login".equals(actionName)){
			//验证session   
			Employee employee = SessionContext.get();
			//如果session不存在，跳回登录页面
			if(employee==null) {
				return "login";
			}else {
				
				//invoke 放行  访问action类中的方法
				return invocation.invoke();
			}
		}else {
			//invoke 放行  访问action类中的方法
			return invocation.invoke();
		}
		
	}

}
