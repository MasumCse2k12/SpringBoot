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
public class NidUserServiceResopnse extends ServiceResponse implements Serializable {
    
    NidList nidList;
    public NidUserServiceResopnse() {
    }

    public NidUserServiceResopnse(ServiceError error) {
        super(error);
    }

    public NidUserServiceResopnse(NidList list) {
        this.nidList = list;
    }

    public NidList getNidList() {
        return nidList;
    }

    public void setNidList(NidList nidList) {
        this.nidList = nidList;
    }
    
    
}
