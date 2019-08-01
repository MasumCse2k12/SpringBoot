/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.service;

import com.google.gson.Gson;
import com.istl.hellospringboot.dto.NIDUser;
import com.istl.hellospringboot.payload.NidList;
import com.istl.hellospringboot.payload.NidUserRequest;
import com.istl.hellospringboot.payload.NidUserResponse;
import com.istl.hellospringboot.payload.manager.ServiceManager;
import com.istl.hellospringboot.payload.manager.ServiceManagerBean;
import com.istl.hellospringboot.utils.NidException;
import com.istl.hellospringboot.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service
@Transactional
public class NidServiceImpl implements NidService {

    @Autowired
    ServiceManager manager;

    public NidServiceImpl(EntityManager em) {
        System.err.println(" in entry man");
    }

    @Override
    public NidUserResponse createNidUser(NIDUser nIDUser) throws NidException {
        System.err.println(" manager > " + manager.getEm());
        if (manager == null || manager.getEm() == null) {
            throw new NidException("Initialize service first");
        }
        Object[] results = new Object[2];
        String jpql = "select o from NIDUser o where  o.nid=?1 or o.userName=?2 ";
        com.istl.hellospringboot.entity.NIDUser userEO = manager.findSingleResultByJpqlQuery(jpql, com.istl.hellospringboot.entity.NIDUser.class, Arrays.asList(nIDUser.getNid().trim(), nIDUser.getUserName().trim()));
        System.out.println("userEO: " + userEO);
        if (userEO != null) {

            if (nIDUser.getUserName().trim().equalsIgnoreCase(userEO.getUserName())) {
                throw new NidException("Username already exists!");
            }
            if (nIDUser.getNid().trim().equalsIgnoreCase(userEO.getNid())) {
                throw new NidException("Nid already exists!");
            }

        }

        userEO = new com.istl.hellospringboot.entity.NIDUser();
        Utils.copyBean(nIDUser, userEO);
        Gson gson = new Gson();
        System.out.println("DATA: " + gson.toJson(userEO));
        try {
            userEO = manager.persist(userEO);
            results[0] = new NIDUser(userEO);
        } catch (Exception t) {
            t.printStackTrace();
        }
        results[1] = Utils.OPERATION_SUCESS;
        return new NidUserResponse(results);
    }

    @Override
    public Object showNidUser(NidUserRequest nIDUser) throws NidException {
        System.err.println(" manager > " + manager.getEm());
        if (manager == null || manager.getEm() == null) {
            throw new NidException("Initialize service first");
        }

        String jpql = "select o from NIDUser o ";
        List<com.istl.hellospringboot.entity.NIDUser> userEO = null;
        try {
            if (nIDUser.getUserName() != null) {
                jpql = "select o from NIDUser o where   o.userName=?1 ";
                userEO = manager.findByJpqlQuery(jpql, com.istl.hellospringboot.entity.NIDUser.class, Arrays.asList(nIDUser.getUserName().trim()));
            } else {
                userEO = manager.findByJpqlQuery(jpql, com.istl.hellospringboot.entity.NIDUser.class);
            }
        } catch (Exception e) {
            System.err.println(" user error ");
            e.printStackTrace();
        }

        if (userEO != null) {

            Gson gson = new Gson();
            System.out.println("DATA: " + gson.toJson(userEO));

        }

        NidList list = null;
        try {
            if (nIDUser == null) {
                nIDUser = new NidUserRequest();
            }
//            nIDUser = Utils.toUpperCase(nIDUser, true);
            Integer total = 0;

            List<NIDUser> nidUserList = new ArrayList<>();
            if (userEO != null && userEO.size() > 0) {
                for (com.istl.hellospringboot.entity.NIDUser eo : userEO) {
                    nidUserList.add(new NIDUser(eo));
                }

            }

            list = new NidList(nidUserList, total);
        } catch (Throwable t) {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println("Unrecognizable error");
            }
        }

        return list;
    }

    @Override
    public Object verifyNidUser(NidUserRequest nIDUser) throws NidException {
        System.err.println(" manager > " + manager.getEm());
        if (manager == null || manager.getEm() == null) {
            throw new NidException("Initialize service first");
        }
        com.istl.hellospringboot.entity.NIDUser userEO = null;
        try {
            if (nIDUser.getUserName() != null) {
                String jpql = "select o from NIDUser o where   o.userName=?1 ";
                userEO = manager.findSingleResultByJpqlQuery(jpql, com.istl.hellospringboot.entity.NIDUser.class, Arrays.asList(nIDUser.getUserName().trim()));

            } else {
                System.err.println(" verify request failed ");
            }
        } catch (Exception e) {
            System.err.println(" user error ");
            e.printStackTrace();
        }

        if (userEO != null) {
            Gson gson = new Gson();
            System.out.println("DATA: " + gson.toJson(userEO));

        }

        NIDUser user = null;
        try {
            if (nIDUser == null) {
                nIDUser = new NidUserRequest();
            }
      
            if (userEO != null && userEO.getPhotoFace() != null) {
                    user = new NIDUser(userEO);
            }

        } catch (Throwable t) {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println("Unrecognizable error");
            }
        }

        return user;
    }

}
