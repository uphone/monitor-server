package com.rino.monitor.bean;

/**
 * @author zip
 */
public enum ErrorCode {
    ERR_500(-500, "系统内部错误");

    private int status;
    private String message;

    private ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int status() {
        return this.status;
    }

    public String message() {
        return this.message;
    }
}