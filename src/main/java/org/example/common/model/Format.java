package org.example.common.model;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;

/**
 * @Author www.hiai.top
 * @Email goodsking@163.com
 *        自定义json数据
 */

public class Format {
    public static Map<String, Object> layuiPage(Page<?> page) {
        return layuiPage(page, 0, "");
    }

    /**
     * 按照layUI格式分页获取数据
     *
     * @param page
     * @param code
     * @param message
     */
    public static Map<String, Object> layuiPage(Page<?> page, int code, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("msg", message);
        result.put("count", page.getTotalRow());
        result.put("data", page.getList());
        return result;
    }
}
