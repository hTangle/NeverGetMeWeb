package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by Alden He on 2019/5/11
 */
@Data
public class PublishDateStatistical implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String publishDate;
    private int dailyCount;
    public PublishDateStatistical(){}
}
