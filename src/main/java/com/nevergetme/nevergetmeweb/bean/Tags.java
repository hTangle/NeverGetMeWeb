package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String value;
    private int amount;
    public Tags(){}
}
