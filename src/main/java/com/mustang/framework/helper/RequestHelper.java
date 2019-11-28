package com.mustang.framework.helper;

import com.mustang.framework.bean.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * RequestHelper
 * 前端控制器接收到HTTP请求后, 从HTTP中获取请求参数, 然后封装到Param对象中。
 *
 * @author: xMustang
 * @since: 1.0
 */
public final class RequestHelper {

    public static Param createParam(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();

        if (!paramNames.hasMoreElements()) {
            return null;
        }

        //get和post参数都能获取到
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String fieldValue = request.getParameter(fieldName);
            paramMap.put(fieldName, fieldValue);
        }

        return new Param(paramMap);
    }
}
