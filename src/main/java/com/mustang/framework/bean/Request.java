package com.mustang.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Request
 *
 * @author: xMustang
 * @since: 1.0
 */
@AllArgsConstructor
@Getter
public class Request {
	/**
	 * 请求方法
	 */
	private String requestMethod;
	/**
	 * 请求路径
	 */
	private String requestPath;

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + requestMethod.hashCode();
		result = 31 * result + requestPath.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Request)) return false;
		Request request = (Request) obj;
		return request.getRequestPath().equals(this.requestPath)
				&& request.getRequestMethod().equals(this.requestMethod);
	}
}
