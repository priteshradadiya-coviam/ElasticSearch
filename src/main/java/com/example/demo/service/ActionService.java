package com.example.demo.service;

import java.util.List;
import java.util.Map;

public interface ActionService {
    List<String> findByUserId(String userId);

    int findAll();

    void deleteAll();

    Map<String, Long> findTopPage();

    Map<String,Long> findNoOfLike();

    Map<String,Long> findNoOfDisLike();

    String findMostPopularTagOnFacebook();

    String findMostPopularTagOnQuora();
}
