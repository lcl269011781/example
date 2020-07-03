package com.alibaba.seata.order.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: R
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2019/8/26 13:51
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@ToString
public class R<T> implements Serializable {


    private static final long serialVersionUID = 2591158659117433501L;
    /**
     * 状态码
     */
    private Integer state;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;

    public R() {
        super();
    }

    public R(Integer state) {
        super();
        this.state = state;
    }

    public R(Integer state, String message) {
        super();
        this.state = state;
        this.message = message;
    }

    public R(Integer state, Throwable throwable) {
        super();
        this.state = state;
        this.message = throwable.getMessage();
    }

    public R(Integer state, T data) {
        super();
        this.state = state;
        this.data = data;
    }

    public R(Integer state, String message, T data) {
        super();
        this.state = state;
        this.message = message;
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        R<?> other = (R<?>) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (state == null) {
            if (other.state != null) {
                return false;
            }
        } else if (!state.equals(other.state)) {
            return false;
        }
        return true;
    }
}
