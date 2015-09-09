package com.andriiposia.cities.util;

/**
 * Created by Администратор on 9/3/15.
 */
public class JsonResponse {

    public static final String FAIL = "Fail";
    public static final String SUCCESS = "Success";

    private String status = FAIL;
    private Object result = null;

    public JsonResponse() {
    }

    public JsonResponse(String status, Object result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setStatusFail() {
        this.status = FAIL;
    }

    public void setStatusSuccess() {
        this.status = SUCCESS;
    }

    public void setStatusFail(Object result) {
        this.status = FAIL;
        this.result = result;
    }

    public void setStatusSuccess(Object result) {
        this.status = SUCCESS;
        this.result = result;
    }

}
