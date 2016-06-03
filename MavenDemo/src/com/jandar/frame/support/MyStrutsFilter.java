package com.jandar.frame.support;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;


public class MyStrutsFilter extends StrutsPrepareAndExecuteFilter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI();
//		HttpServletRequest servletRequest = (HttpServletRequest) req;
//		HttpServletResponse servletResponse = (HttpServletResponse) res;
		//User loginUser = (User)ActionContext.getContext().getSession().get("LOGINUSER");
		
		/*// 登陆页面无需过滤
		if(url.indexOf("/login.jsp") > -1||url.indexOf(".css") > 0 
				|| url.indexOf(".jpg") > 0
				|| url.indexOf(".gif") > 0
				|| url.indexOf(".png") > 0) {
			super.doFilter(req, res, chain);
			return;
		}

		// 判断如果没有取到登录信息,就跳转到登陆页面
		if (loginUser == null || "".equals(loginUser)) {
			// 跳转到登陆页面
			servletResponse.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			// 已经登陆,继续此次请求
			super.doFilter(req, res, chain);
		}*/
		
		if (url.contains("/vendor/ueditor/jsp/")) {
			chain.doFilter(req, res);
		} else {
			super.doFilter(req, res, chain);
		}
	}
}
