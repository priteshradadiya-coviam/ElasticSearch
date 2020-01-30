package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ActionDTO
{
    private String channel;
    private String category;
    private List<String> tag;
    private String action;
    private String userId;

}
