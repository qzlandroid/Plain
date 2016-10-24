package com.example.jit.api;

/**
 * Api响应结果的封装类.
 *
 */
public class ApiResponse<T> {
    private String status;    // 返回码，succes为成功
    private String msg;        //消息
    private T value;           // 对象
    private String url;
    public ApiResponse(String status,String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return status.equals("success");
    }

    public String getStatus() {
        return status;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

