/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.dto;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class NIDUser implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Integer id;
    
    private String userName;
    private String nid;
    private String imeiNo;
    private String country;
    
    private byte[] photoFace;
    
    private boolean status;

     public NIDUser() {
        super();
    }
     
     public NIDUser(com.istl.hellospringboot.entity.NIDUser userEO) {
        if(userEO != null) {
            this.id = userEO.getId();
            this.userName = userEO.getUserName();
            this.nid = userEO.getNid();
            this.userName = userEO.getUserName();            
            this.imeiNo = userEO.getImeiNo();
            this.country = userEO.getCountry();
            this.status = userEO.isStatus();
            this.photoFace = userEO.getPhotoFace();
//            this.biometric = userEO.getBiometric();
            
        }
    }
     
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getPhotoFace() {
        return photoFace;
    }

    public void setPhotoFace(byte[] photoFace) {
        this.photoFace = photoFace;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

 

    @Override
    public String toString() {
        return "NIDUser{" + "id=" + id + ", userName=" + userName + ", nid=" + nid + ", imeiNo=" + imeiNo + ", country=" + country + '}';
    }
    
    
    
}
