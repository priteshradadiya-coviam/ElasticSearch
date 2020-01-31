package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AdDTO
{
    String adId;

    String advertiserId;
    String tag;

    String categoryName;

    String userId;
    String description;

    String source;

    String targetUrl;
}
