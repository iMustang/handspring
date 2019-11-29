package com.mustang.framework;

import com.alibaba.fastjson.JSON;
import com.mustang.framework.bean.Data;
import com.mustang.framework.bean.Handler;
import com.mustang.framework.bean.Param;
import com.mustang.framework.bean.View;
import com.mustang.framework.helper.BeanHelper;
import com.mustang.framework.helper.ConfigHelper;
import com.mustang.framework.helper.ControllerHelper;
import com.mustang.framework.helper.RequestHelper;
import com.mustang.framework.util.ReflectionUtil;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * DispatcherServlet
 *
 * @author: xMustang
 * @since: 1.0
 */
public class DispatcherServlet extends HttpServlet {

	@Override
	//当DispatcherServlet实例化时, 首先执行 init() 方法, 这时会调用 HelperLoader.init() 方法来加载相关的helper类
	public void init(ServletConfig config) throws ServletException {
		HelperLoader.init();

		//获取ServletContext对象，用于注册Servlet
		ServletContext servletContext = config.getServletContext();
		//注册处理jsp和静态资源的servlet
		registerServlet(servletContext);
	}

	/**
	 * DefaultServlet和JspServlet都是由Web容器创建
	 * org.apache.catalina.servlets.DefaultServlet
	 * org.apache.jasper.servlet.JspServlet
	 *
	 * @param servletContext
	 */
	private void registerServlet(ServletContext servletContext) {
		//动态注册处理JSP的Servlet
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

		//动态注册处理静态资源的默认Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping("/favicon.ico");// 网站头像
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 对于每一次客户端请求都会执行 service() 方法，
		// 这时会首先将请求方法和请求路径封装为Request对象，然后从映射处理器 (REQUEST_MAP) 中获取到处理器。
		// 然后从客户端请求中获取到Param参数对象，执行处理器方法。
		// 最后判断处理器方法的返回值，若为view类型，则跳转到jsp页面；若为data类型，则返回json数据。
		String requestMethod = req.getMethod().toUpperCase();
		String requestPath = req.getPathInfo();

		//这里根据Tomcat的配置路径有两种情况, 一种是 "/userList", 另一种是 "/context地址/userList"
		String[] splits = requestPath.split("/");
		if (splits.length > 2) {
			requestPath = "/" + splits[2];
		}

		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if (handler != null) {
			// 获取控制器实例
			Class<?> controllerClass = handler.getControllerClazz();
			Object controllerBean = BeanHelper.getBean(controllerClass);

			// 初始化参数
			Param param = RequestHelper.createParam(req);

			Object result;
			Method actionMethod = handler.getControllerMethod();
			if (param == null || param.isEmpty()) {
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
			} else {
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			}

			// 这里类似于SpringMVC中的视图解析器
			if (result instanceof View) {//跳转页面
				handleViewResult((View) result, req, resp);
			} else if (result instanceof Data) {//返回json数据
				handleDataResult((Data) result, resp);
			}

		}
	}

	/**
	 * 跳转页面
	 *
	 * @param view
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private static void handleViewResult(View view, HttpServletRequest request,
	                                     HttpServletResponse response) throws IOException, ServletException {
		String path = view.getPath();
		if (StringUtils.isNotEmpty(path)) {
			if (path.startsWith("/")) {// 重定向
				response.sendRedirect(request.getContextPath() + path);
			} else {//请求转发
				Map<String, Object> model = view.getModel();
				for (Map.Entry<String, Object> entry : model.entrySet()) {
					request.setAttribute(entry.getKey(), entry.getValue());
				}
				request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
			}
		}
	}

	/**
	 * 返回JSON数据
	 *
	 * @param data
	 * @param response
	 * @throws IOException
	 */
	private static void handleDataResult(Data data, HttpServletResponse response) throws IOException {
		Object model = data.getModel();
		if (model != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String json = JSON.toJSON(model).toString();
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}
}
