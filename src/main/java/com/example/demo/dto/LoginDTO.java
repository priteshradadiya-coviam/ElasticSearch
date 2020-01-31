package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LoginDTO
{
    private String channel;

    private List<String> tag;
    private String action;

}
