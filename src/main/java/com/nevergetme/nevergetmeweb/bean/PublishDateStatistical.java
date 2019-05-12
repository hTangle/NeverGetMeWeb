package com.nevergetme.nevergetmeweb.bean;

import lombok.Data;

/**
 * Create by Alden He on 2019/5/11
 */
@Data
public class PublishDateStatistical {
    private Integer id;
    private String publishDate;
    private int dailyCount;
    public PublishDateStatistical(){}
}
