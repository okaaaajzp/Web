package com.example.demo.exception;

public class customException extends RuntimeException {
    String msg;

    public customException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
