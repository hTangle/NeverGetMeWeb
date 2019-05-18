package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.time.LocalDate;

/**
 * Create by Alden He on 2019/5/18
 */
@Data
public class Visitor {
    private Integer id;
    private String visitTime;
    private int pv;
    public Visitor(){}
    public Visitor(int pv){
        this.pv=pv;
        this.visitTime= LocalDate.now().toString();
    }
}
