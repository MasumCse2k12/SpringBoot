/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.service;

import com.istl.hellospringboot.dto.NIDUser;
import com.istl.hellospringboot.payload.NidUserRequest;
import com.istl.hellospringboot.payload.NidUserResponse;
import com.istl.hellospringboot.utils.NidException;

/**
 *
 * @author User
 */
public interface NidService {
    
   public NidUserResponse createNidUser(NIDUser nIDUser) throws NidException;
   
   public Object showNidUser(NidUserRequest nIDUser) throws NidException;
   
   public Object verifyNidUser(NidUserRequest nIDUser) throws NidException;
    
}
