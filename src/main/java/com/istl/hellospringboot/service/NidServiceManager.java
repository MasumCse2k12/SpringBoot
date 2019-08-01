/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.service;

import com.istl.hellospringboot.dto.NIDUser;
import com.istl.hellospringboot.payload.NidUserRequest;
import com.istl.hellospringboot.payload.NidUserResponse;
import com.istl.hellospringboot.payload.ServiceError;
import com.istl.hellospringboot.utils.NidException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
@Transactional
public class NidServiceManager {

    @Autowired
    private NidService nidService;

    public NidUserResponse createNidUser(NIDUser nIDUser){        

       try {
           return nidService.createNidUser(nIDUser);
        } catch (NidException ex) {
           ex.printStackTrace();
           return new com.istl.hellospringboot.payload.NidUserResponse(new ServiceError(ex.getMessage()));
        }
    }
    
    public Object showNidUser(NidUserRequest nIDUser){        

       try {
           return nidService.showNidUser(nIDUser);
        } catch (NidException ex) {
           ex.printStackTrace();
           return new com.istl.hellospringboot.payload.NidUserResponse(new ServiceError(ex.getMessage()));
        }
    }
    
    public Object verifyNidUser(NidUserRequest nIDUser){        

       try {
           return nidService.verifyNidUser(nIDUser);
        } catch (NidException ex) {
           ex.printStackTrace();
           return new com.istl.hellospringboot.payload.NidUserResponse(new ServiceError(ex.getMessage()));
        }
    }
}
