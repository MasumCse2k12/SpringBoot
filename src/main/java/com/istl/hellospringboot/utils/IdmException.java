/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.utils;

/**
 *
 * @author Alauddin
 */
public class IdmException extends Throwable {
    
    public IdmException(String message) {
        super(message);
    }
    
    public IdmException(Throwable t) {
        super(t);
    }
    
    public IdmException(String message,Throwable t) {
        super(message, t);
    }
}
