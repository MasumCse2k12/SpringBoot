/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.payload;

import com.istl.hellospringboot.dto.NIDUser;
import java.io.Serializable;

/**
 *
 * @author User
 */
public class NidUserVerifyServiceResopnse extends ServiceResponse implements Serializable {
    
    NIDUser nidUser;
    public NidUserVerifyServiceResopnse() {
    }

    public NidUserVerifyServiceResopnse(ServiceError error) {
        super(error);
    }

    public NidUserVerifyServiceResopnse(NIDUser user) {
        this.nidUser = user;
    }

    public NIDUser getNidUser() {
        return nidUser;
    }

    public void setNidUser(NIDUser nidUser) {
        this.nidUser = nidUser;
    }


    
}
