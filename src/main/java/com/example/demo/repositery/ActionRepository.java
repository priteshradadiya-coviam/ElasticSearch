package com.example.demo.repositery;

import com.example.demo.entity.Action;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends ElasticsearchRepository<Action,String> {
}
