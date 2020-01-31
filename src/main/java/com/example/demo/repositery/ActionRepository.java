package com.example.demo.repositery;

import com.example.demo.entity.Action;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends ElasticsearchRepository<Action,String>
{
    public List<Action> findByUserId(String userId);
}
