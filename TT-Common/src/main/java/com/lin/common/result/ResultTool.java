package com.lin.common.result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultTool {
    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        log.error("process_fail, error_code={},error_msg={}",resultEnum.getCode(),resultEnum.getMessage());
        return new JsonResult(false, resultEnum);
    }
}
