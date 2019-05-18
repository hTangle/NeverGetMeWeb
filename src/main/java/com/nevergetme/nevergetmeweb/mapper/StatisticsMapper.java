package com.nevergetme.nevergetmeweb.mapper;

import com.nevergetme.nevergetmeweb.bean.Tags;
import com.nevergetme.nevergetmeweb.bean.Visitor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create by Alden He on 2019/5/12
 */
@Mapper
public interface StatisticsMapper {
    List<Tags> getTagsOfArticleCountStatistics();
    List<Visitor> getVisitorCountStatistics();
    int setVisitorCountStatistics(Visitor v);
}
