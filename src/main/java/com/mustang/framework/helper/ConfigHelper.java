package com.mustang.framework.helper;

import java.util.Properties;

import com.mustang.framework.constant.ConfigConstant;
import com.mustang.framework.util.PropsUtil;

/**
 * ConfigHelper
 * 加载用户自定义的配置文件
 *
 * @author: xMustang
 * @since: 1.0
 */
public final class ConfigHelper {
	private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

	/**
	 * 获取 JDBC 驱动
	 *
	 * @return
	 */
	public static String getJdbcDriver() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
	}

	/**
	 * 获取 JDBC URL
	 */
	public static String getJdbcUrl() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}

	/**
	 * 获取 JDBC 用户名
	 */
	public static String getJdbcUsername() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}

	/**
	 * 获取 JDBC 密码
	 */
	public static String getJdbcPassword() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}

	/**
	 * 获取应用基础包名
	 */
	public static String getAppBasePackage() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}

	/**
	 * 获取应用 JSP 路径
	 */
	public static String getAppJspPath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
	}

	/**
	 * 获取应用静态资源路径
	 */
	public static String getAppAssetPath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
	}

	/**
	 * 根据属性名获取 String 类型的属性值
	 */
	public static String getString(String key) {
		return PropsUtil.getString(CONFIG_PROPS, key);
	}

	/**
	 * 根据属性名获取 int 类型的属性值
	 */
	public static int getInt(String key) {
		return PropsUtil.getInt(CONFIG_PROPS, key);
	}

	/**
	 * 根据属性名获取 boolean 类型的属性值
	 */
	public static boolean getBoolean(String key) {
		return PropsUtil.getBoolean(CONFIG_PROPS, key);
	}
}
