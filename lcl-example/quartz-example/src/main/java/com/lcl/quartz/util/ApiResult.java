package com.lcl.quartz.util;

/**
 * @ClassName: ApiResult
 * @Description: 统一接口返回格式
 * @Author: Chunliang.Li
 * @Date: 2020/6/29 17:40
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class ApiResult<T> {

    private int code;
    private String message;
    private T data;
    private boolean success;

    public ApiResult(ApiResult.ResultCode code, String message, T data) {
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
    public static <T> ApiResult<T> of(ApiResult.ResultCode code, String msg, T data) {
        return new ApiResult<T>(code, msg, data);
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
