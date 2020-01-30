package com.example.demo.controller;

import com.example.demo.dto.ActionDTO;
import com.example.demo.dto.UIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repo")
@CrossOrigin("*")
public class ActionRepositoryController {

    private static final String TOPIC ="action";

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("/add")
    public String addAction(@RequestBody ActionDTO actionDTO) throws JsonProcessingException {

        ObjectMapper objectMapper=new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(actionDTO));
        return "success";
    }


    @GetMapping("/findActionListByUserId/{id}")
    public List<String> findActionListByUserId(@PathVariable String userId)
    {

    }


}
