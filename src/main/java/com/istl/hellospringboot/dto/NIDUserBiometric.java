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
public class NIDUserBiometric  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private byte[] photoFace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getPhotoFace() {
        return photoFace;
    }

    public void setPhotoFace(byte[] photoFace) {
        this.photoFace = photoFace;
    }
    
    
    
    
}
