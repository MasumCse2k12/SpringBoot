/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.payload;

import com.istl.hellospringboot.dto.NIDUser;
import com.istl.hellospringboot.utils.Utils;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author User
 */
public class NidUserResponse implements Serializable {

    private NIDUser user;
    private String message;
    private ServiceError error;

    public NidUserResponse() {
        super();
    }

    public NidUserResponse(ServiceError error) {
        this.error = error;
    }

    public NidUserResponse(String message) {
        super();
        this.message = message;
    }

    public NidUserResponse(Object[] results) {
        super();
        if (results != null && results.length >= 2) {
            this.message = (String) results[1];
            this.user = (NIDUser) results[0];

        }
    }

    public NIDUser getUser() {
        return user;
    }

    public void setUser(NIDUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
