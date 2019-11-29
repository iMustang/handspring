package com.mustang.framework.util;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassUtil
 * 通过加载全限定类名得到Class类, 以及获取指定包名下的所有Class类。
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public class ClassUtil {
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 加载类
	 *
	 * @param className
	 * @param isInitialized
	 * @return
	 */
	public static Class<?> loadClass(String className, boolean isInitialized) {
		Class<?> cls;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			log.error("load class failure", e);
			throw new RuntimeException(e);
		}
		return cls;
	}

	/**
	 * 加载类（默认将初始化类），也就是执行 static 初始化代码，初始化 static 字段
	 *
	 * @param className
	 * @return
	 */
	public static Class<?> loadClass(String className) {
		return loadClass(className, true);
	}

	/**
	 * 获取指定包名下的所有类，没有加载类。loadClass(className, false)
	 *
	 * @param packageName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packageName) {
		HashSet<Class<?>> classSet = new HashSet<>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol();
					if ("file".equals(protocol)) {
						String packagePath = url.getPath().replaceAll("%20", " ");
						// packageName是包名，packagePath是包名转化为路径，即.转化为/
						addClass(classSet, packagePath, packageName);
					} else if ("jar".equals(protocol)) {
						JarURLConnection jarURLConnection;

						jarURLConnection = (JarURLConnection) url.openConnection();
						if (jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if (jarFile != null) {
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								while (jarEntries.hasMoreElements()) {
									JarEntry jarEntry = jarEntries.nextElement();
									String jarEntryName = jarEntry.getName();
									if (jarEntryName.endsWith(".class")) {
										String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
										doAddClass(classSet, className);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("get class set failure", e);
			throw new RuntimeException(e);
		}
		return classSet;
	}

	private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
		File[] files = new File(packagePath).listFiles(
				pathname ->
						(pathname.isFile() && pathname.getName().endsWith(".class") || pathname.isDirectory())
		);

		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtils.isNotEmpty(packageName)) {
					className = packageName + "." + className;
				}
				doAddClass(classSet, className);
			} else {
				// 是路径
				String subPackagePath = fileName;
				if (StringUtils.isNotEmpty(packagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}

				// 包名也要加上这个路径
				String subPackageName = fileName;
				if (StringUtils.isNotEmpty(packageName)) {
					subPackageName = packageName + "." + subPackageName;
				}

				addClass(classSet, subPackagePath, subPackageName);
			}
		}
	}

	private static void doAddClass(Set<Class<?>> classSet, String className) {
		Class<?> cls = loadClass(className, false);
		classSet.add(cls);
	}
}
