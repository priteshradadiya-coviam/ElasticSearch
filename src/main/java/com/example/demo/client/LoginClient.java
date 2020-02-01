package com.example.demo.client;

import org.apache.kafka.common.protocol.types.Field;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "login", url = "http://172.16.20.121:8080/controller")
public interface LoginClient {


    @GetMapping("/getUserId")
    public String getUserIdFromAccessToken(@RequestHeader("accessToken") String accessToken);
}
