package com.example.demo.service.impl;

import com.example.demo.configuration.KafkaConsumerConfiguration;
import com.example.demo.dto.UIDTO;
import com.example.demo.entity.Action;
import com.example.demo.repositery.ActionRepository;
import com.example.demo.service.ActionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    ActionRepository actionRepository;



    @KafkaListener(topics = "action", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void addAction(String uidto) {
        Action action = new Action();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            action = objectMapper.readValue(uidto,Action.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        actionRepository.save(action);
    }
}
