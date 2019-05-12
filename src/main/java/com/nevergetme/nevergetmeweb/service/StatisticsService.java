package com.nevergetme.nevergetmeweb.service;

import com.nevergetme.nevergetmeweb.bean.Tags;

import java.util.List;

/**
 * Create by Alden He on 2019/5/12
 */
public interface StatisticsService {
    List<Tags> getTagsOfArticleCountStatistics();
}
