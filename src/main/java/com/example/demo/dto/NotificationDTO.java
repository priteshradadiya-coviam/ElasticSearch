package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class NotificationDTO
{
    String message;
    List<String> targetIdList;
}
