/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.utils;

/**
 *
 * @author User
 */
public class NidException extends Throwable{
    public NidException(String message) {
        super(message);
    }
    
    public NidException(Throwable t) {
        super(t);
    }
    
    public NidException(String message,Throwable t) {
        super(message, t);
    }
}
