package com.mustang.framework.constant;

/**
 * ConfigConstant
 * <p>
 * 定义该手写框架需要哪些配置项，这些配置项需要用户在使用框架时在配置文件中显式定义。
 *
 * @author: xMustang
 * @since: 1.0
 */
public interface ConfigConstant {
	// 配置文件名称
	String CONFIG_FILE = "handwritten.properties"; // 规定用户使用该框架提供的配置文件名称

	// 数据源
	String JDBC_DRIVER = "handwritten.framework.jdbc.driver";
	String JDBC_URL = "handwritten.framework.jdbc.url";
	String JDBC_USERNAME = "handwritten.framework.jdbc.username";
	String JDBC_PASSWORD = "handwritten.framework.jdbc.password";

	//java源码地址
	String APP_BASE_PACKAGE = "handwritten.framework.app.base_package";
	//jsp页面路径
	String APP_JSP_PATH = "handwritten.framework.app.jsp_path";
	//静态资源路径
	String APP_ASSET_PATH = "handwritten.framework.app.asset_path";
}
