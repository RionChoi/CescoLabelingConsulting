package com.cesco.sys.common;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import okhttp3.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Component
public class CommonUtils {
	
	// null Check
	public static String nvl(String defaultValue) {
		return defaultValue == null ? "" : defaultValue;
	}
	
	public static okhttp3.Request addHeaderbyPost(HttpSession session, okhttp3.RequestBody body, String URL, HttpMethod method) {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if(ip == null) {
			ip = req.getRemoteAddr();
		}
		UUID uuid = UUID.randomUUID();
		Request result = null;
		if(method.equals(HttpMethod.POST)) {
			result = new Request.Builder()
				      			.url(URL)
							    .post(body)
							    .addHeader("sys-id", "asd1231231")  // session
							    .addHeader("men-id", "ad24123")		// session
							    .addHeader("us-id", "11059")	// session
							    .addHeader("fun-id", "11059")	// session
							    .addHeader("tran-id", uuid.toString())
							    .addHeader("cl-ip", ip)
							    .addHeader("Content-Type", "application/json")
							    .build();
		}
		if(method.equals(HttpMethod.GET)) {
			result = new Request.Builder()
				      			.url(URL)
				      			.get()
								.addHeader("sys-id", "asd1231231")  // session
								.addHeader("men-id", "ad24123")		// session
								.addHeader("us-id", "11059")	// session
								.addHeader("fun-id", "11059")	// session
								.addHeader("tran-id", uuid.toString())
								.addHeader("cl-ip", ip)
							    .addHeader("Content-Type", "application/json")
							    .build();
		}

		return result;
	}
		/**
	 * UUID를 리턴한다.
	 * @param prefix UUID 앞에 덧 붙일 Prefix
	 * @return prefix를 덧 붙인 UUID
	 */
	public static String getNewId(String prefix) {
		return prefix + UUID.randomUUID().toString().replaceAll("-", "");
	}
}
	