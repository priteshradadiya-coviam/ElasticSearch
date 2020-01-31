package com.example.demo.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;


@Data
@Document(indexName = "myquiz",type = "analysis")
public class Quiz
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",

            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String quizId;

    private String userId;
    private String action;

}
