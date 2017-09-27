package com.resume.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	/**
	 * 获取当前服务器地址 示例：http://localhost:8080/vps
	 * 
	 * @param request
	 * @return 当前服务器地址
	 */
	public static String getCurrentUrl(HttpServletRequest request) {
		if (request == null || request.getServerName() == null) {
			return "";
		}
		String serverName = request.getServerName().trim();
		if (serverName.indexOf("127.0.0.1") >= 0 || serverName.indexOf("localhost") >= 0) {
			return request.getScheme() + "://" + serverName + ":" + request.getServerPort() + request.getContextPath();
		} else {
			return request.getScheme() + "://" + serverName + request.getContextPath();
		}

	}

	/**
	 * 获取请求端IP
	 * 
	 * @param request
	 * @return 请求端IP
	 * @author yangyan42
	 * @date 2017年5月8日
	 */
	public static String getFromIP(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * 获取请求端设备信息
	 * 
	 * @param request
	 * @return 请求端设备信息
	 * @author yangyan42
	 * @date 2017年5月8日
	 */
	public static String getUserAgent(javax.servlet.http.HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		return agent == null ? "" : agent;
	}

}
