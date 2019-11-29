package com.mustang.framework.helper;

import com.mustang.framework.annotation.RequestMapping;
import com.mustang.framework.bean.Handler;
import com.mustang.framework.bean.Request;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ControllerHelper
 *
 * @author: xMustang
 * @since: 1.0
 */
public class ControllerHelper {
	//REQUEST_MAP 就相当于Spring MVC里的映射处理器（HandlerMapping），接收到请求后返回对应的处理器。
	private static final Map<Request, Handler> REQUEST_MAP;

	static {
		REQUEST_MAP = new HashMap<>();

		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if (CollectionUtils.isNotEmpty(controllerClassSet)) {
			for (Class<?> controller : controllerClassSet) {
				Method[] methods = controller.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(methods)) {
					for (Method method : methods) {
						if (method.isAnnotationPresent(RequestMapping.class)) {
							RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
							String requestPath = requestMapping.value();
							String requestMethod = requestMapping.method().name();

							// 封装请求和处理器
							Request request = new Request(requestMethod, requestPath);

							Handler handler = new Handler(controller, method);

							REQUEST_MAP.put(request, handler);
						}
					}
				}
			}
		}
	}

	/**
	 * 获取Handler
	 *
	 * @param requestMethod
	 * @param requestPath
	 * @return
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return REQUEST_MAP.get(request);
	}
}
