package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.Tags;
import com.nevergetme.nevergetmeweb.mapper.StatisticsMapper;
import com.nevergetme.nevergetmeweb.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Alden He on 2019/5/12
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Override
    public List<Tags> getTagsOfArticleCountStatistics() {
        return statisticsMapper.getTagsOfArticleCountStatistics();
    }
}
