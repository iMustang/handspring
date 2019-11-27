package com.mustang.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * PropsUtil
 * PropsUtil 工具类来读取属性文件
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public final class PropsUtil {

	public static Properties loadProps(String fileName) {
		Properties props = null;
		InputStream is = null;
		try {
			is = ClassUtil.getClassLoader().getResourceAsStream(fileName);
			if (is == null) {
				throw new FileNotFoundException("file is not found, fileName: " + fileName);
			}
			props = new Properties();
			props.load(is);
		} catch (IOException e) {
			log.error("load properties file failure", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("close input stream failuer", e);
				}
			}
		}
		return props;
	}

	/**
	 * 获取 String 类型的属性值（默认值为空字符串）
	 *
	 * @param props
	 * @param key
	 * @return
	 */
	public static String getString(Properties props, String key) {
		return getString(props, key, "");
	}

	/**
	 * 获取 String 类型的属性值（可指定默认值）
	 *
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Properties props, String key, String defaultValue) {
		String value = defaultValue;
		if (props.containsKey(key)) {
			value = props.getProperty(key);
		}
		return value;
	}

	/**
	 * 获取 int 类型的属性值（可指定默认值）
	 *
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Properties props, String key, int defaultValue) {
		int value = defaultValue;
		if (props.containsKey(key)) {
			value = Integer.parseInt(props.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取 int 类型的属性值（默认值为 0）
	 *
	 * @param props
	 * @param key
	 * @return
	 */
	public static int getInt(Properties props, String key) {
		return getInt(props, key, 0);
	}

	/**
	 * 获取 boolean 类型属性（可指定默认值）
	 *
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
		boolean value = defaultValue;
		if (props.containsKey(key)) {
			value = Boolean.parseBoolean(props.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取 boolean 类型属性（默认值为 false）
	 *
	 * @param props
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Properties props, String key) {
		return getBoolean(props, key, false);
	}
}
