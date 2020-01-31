package com.example.demo.repositery;

import com.example.demo.entity.Quiz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends ElasticsearchRepository<Quiz, String> {
}
