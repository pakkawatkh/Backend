package com.example.backend.mapper;

import com.example.backend.entity.News;
import com.example.backend.model.newsModel.NewsDetailResponse;
import com.example.backend.model.newsModel.NewsListParagraphResponse;
import com.example.backend.model.newsModel.NewsListResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    List<NewsListResponse> toNewsListResponse(List<News> news);

    NewsListParagraphResponse toNewsListParagraphResponse(News news);

    NewsDetailResponse toNewsDetailResponse(News news);

}
