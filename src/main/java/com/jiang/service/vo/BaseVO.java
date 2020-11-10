package com.jiang.service.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @Created jiang
 */
@Data
public class BaseVO<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    private BaseVO() {
        this.code = 200;
        this.message = "success";
    }

    public static BaseVO ok() {
        return new BaseVO();
    }

    public static <T> BaseVO ok(T data) {
        return (new BaseVO()).setData(data);
    }

    public static BaseVO error(String message) {
        return (new BaseVO()).setMessage(message);
    }

    public BaseVO setData(T data) {
        this.data = data;
        return this;
    }

    public BaseVO setMessage(String message) {
        this.message = message;
        return this;
    }


}
