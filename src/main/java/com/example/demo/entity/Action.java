package com.example.demo.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import java.util.List;

@Data
@Document(indexName = "myaction",type = "analysis")
@Getter
@Setter
@ToString
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
    private String tag;
    private String action;
    private String userId;
}
