package com.mustang.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * Param
 * 封装Controller方法的参数。
 *
 * @author: xMustang
 * @since: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Param {
    private Map<String, Object> paramMap;

    public boolean isEmpty() {
        return MapUtils.isEmpty(paramMap);
    }
}
