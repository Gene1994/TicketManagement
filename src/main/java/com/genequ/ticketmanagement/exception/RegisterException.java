package com.genequ.ticketmanagement.exception;

public class RegisterException extends Exception {

    /**
     * 错误编码
     */
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public RegisterException(String msg, int errorCode){
        super(msg);
        this.setErrorCode(errorCode);
    }
}
