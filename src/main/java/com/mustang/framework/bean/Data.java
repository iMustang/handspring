package com.mustang.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data
 * 用于封装Controller方法的JSON返回结果。
 *
 * @author: xMustang
 * @since: 1.0
 */
@AllArgsConstructor
@Getter
public class Data {
	/**
	 * 模型数据
	 */
	private Object model;
}
