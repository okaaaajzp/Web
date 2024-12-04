package com.example.demo.common;

public class Result {
    private static final int success = 0;
    private static final int error = -1;
    private Object data;
    private String msg;
    private int code;

    public static Result success() {
        Result result = new Result();
        result.setCode(success);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setCode(success);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(error);
        result.setMsg(msg);
        return result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
