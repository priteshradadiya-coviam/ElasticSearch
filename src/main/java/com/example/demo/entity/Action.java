package com.example.demo.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;

@Data
@Document(indexName = "myindex",type = "user1")
public class Action
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",

            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String uniqueId;
    private String channel;
    private String category;
    private String tag;
    private String action;
    private String userId;
}
