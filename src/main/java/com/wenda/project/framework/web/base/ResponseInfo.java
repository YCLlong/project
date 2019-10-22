package com.wenda.project.framework.web.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo {
    private int code;
    private String msg;
    private Object data;

    public static ResponseInfo error(String msg) {
        return new ResponseInfo(-1, msg, null);
    }

    public static ResponseInfo success(Object data) {
        return new ResponseInfo(0, null, data);
    }
}
