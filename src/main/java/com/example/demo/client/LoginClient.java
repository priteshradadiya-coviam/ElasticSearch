package com.example.demo.client;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "login", url = "")
public interface LoginClient {


    @GetMapping("/getUserId/{accessToken}")
    public String getUserIdFromAccessToken(@PathVariable("accessToken") String accessToken);
}
