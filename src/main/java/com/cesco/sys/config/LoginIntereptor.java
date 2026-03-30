package com.cesco.sys.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginIntereptor implements HandlerInterceptor{
	private static final String SESSION_ID = "userId";
	// static final String[] EXCLUDE_URL_LIST = {
	// 		 "/user/login"
	// 		,"/user/getLogin"
	// 		,"/user/changePassword"
	// 		,"/user/updatePassword"
	// 		,"/user/findPassword"
	// 		,"/user/getEmply"
	// 		,"/user/resetPassword"
	// 		,"/user/logout"
	// };
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String nextURL = request.getRequestURI();
		// log.info("인증 체크", requestURI);
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(SESSION_ID) == null) {

				if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("AJAX")){			
					throw new CommDuplicateException("ERR_AJAX",ErrorCode.USER_ID_DUPLICATION);                
				}
				response.sendRedirect("/user/login?nextURL=" + nextURL);
				return false;
		}
		return true;
		
	}
 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//System.out.println("postHandle executed");
	}
 
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//System.out.println("afterCompletion executed");
		 
	}
}