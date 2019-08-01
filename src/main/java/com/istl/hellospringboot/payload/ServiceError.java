/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.payload;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class ServiceError implements Serializable{
    private String errorMsg;
    private Integer errorCode;    

    public ServiceError() {
    } 
    public ServiceError(Integer errorCode, String errorMsg){
        this.errorCode = errorCode;
        String name = ErrorCode.getValue(errorCode).name();
        this.errorMsg = name + " [" + errorMsg + "]";        
        
    }
    public ServiceError(String errorMessage) {
        this.errorMsg = errorMessage;
    }  

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMsg;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMsg = errorMessage;
    }
}
