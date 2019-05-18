package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.Tags;
import com.nevergetme.nevergetmeweb.bean.Visitor;

import java.util.List;

/**
 * Create by Alden He on 2019/5/12
 */
public interface StatisticsService {
    List<Tags> getTagsOfArticleCountStatistics();
    List<Visitor> getVisitorCountStatistics();
    int setVisitorCountStatistics(Visitor v);
}
