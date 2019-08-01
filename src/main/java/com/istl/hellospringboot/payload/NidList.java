/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.payload;

import com.istl.hellospringboot.dto.NIDUser;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author User
 */
public class NidList implements Serializable{
    List<NIDUser> nidUserList;
    int total;

    public NidList(List<NIDUser> nidUserList, int total) {
        this.nidUserList = nidUserList;
        this.total = total;
    }

    public List<NIDUser> getNidUserList() {
        return nidUserList;
    }

    public void setNidUserList(List<NIDUser> nidUserList) {
        this.nidUserList = nidUserList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
