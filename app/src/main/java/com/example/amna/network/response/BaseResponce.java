package com.example.amna.network.response;

public class BaseResponce<T>{
    private boolean error;

    private String errorMessage;

    private String status;

    private T data;

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
