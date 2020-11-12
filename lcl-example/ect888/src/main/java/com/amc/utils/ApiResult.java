package com.amc.utils;

/**
 * Description: 统一返回格式
 * Author: Chunliang.Li
 * Date: 2020/11/12 9:32
 **/
public class ApiResult<T> {

    private int code;
    private String message;
    private T data;
    private boolean success;

    public ApiResult(ResultCode code, String message, T data) {
        super();
        this.code = code.getCode();
        this.message = message;
        this.data = data;
    }

    public ApiResult(int code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ApiResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = code == ResultCode.OK.getCode();
    }

    /**
     * @param code 状态码
     * @param msg 消息
     * @param data 内容
     */
    public static <T> ApiResult<T> of(ResultCode code, String msg, T data) {
        return new ApiResult<T>(code, msg, data);
    }
    /**
     * @param code 状态码
     * @param data 内容
     */
    public static <T> ApiResult<T> of(ResultCode code,  T data) {
        return new ApiResult<T>(code, "请求成功", data);
    }
    /**
     * @param code 状态码
     * @param msg 消息
     */
    public static <T> ApiResult<T> of(ResultCode code, String msg) {
        return new ApiResult<T>(code, msg,null);
    }

    /**
     * 代码枚举
     */
    public enum ResultCode {
        /**
         * 成功
         */
        OK(200, ""),
        /**
         * 服务器错误
         */
        INTERNAL_SERVER_ERROR(500, "");

        private int code;
        private String desc;

        ResultCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
