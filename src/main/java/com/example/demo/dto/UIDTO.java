package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UIDTO
{
    private String channel;
    private String category;
    private String tag;
    private String action;
    private String userId;
    private List<NotificationDTO> notificationDTOList;
}
