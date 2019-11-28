package com.mustang.framework.bean;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * View
 * 封装Controller方法的视图返回结果。
 *
 * @author: xMustang
 * @since: 1.0
 */
@Getter
public class View {
    /**
     * 视图路径
     */
    private String path;
    /**
     * 模型数据
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

}
