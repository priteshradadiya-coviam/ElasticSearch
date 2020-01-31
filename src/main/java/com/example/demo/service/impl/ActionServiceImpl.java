package com.example.demo.service.impl;

import com.example.demo.dto.ActionDTO;
import com.example.demo.dto.AdDTO;
import com.example.demo.dto.LoginActionDTO;
import com.example.demo.dto.QuizDTO;
import com.example.demo.entity.Action;
import com.example.demo.entity.Quiz;
import com.example.demo.repositery.ActionRepository;
import com.example.demo.repositery.QuizRepository;
import com.example.demo.searchQueryBuilder.SearchQueryBuilder;
import com.example.demo.service.ActionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    ActionRepository actionRepository;

    @Autowired
    SearchQueryBuilder searchQueryBuilder;

    @Autowired
    QuizRepository quizRepository;


    @KafkaListener(topics = "action", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void addAction(String uidto) {
        ActionDTO action = new ActionDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            action = objectMapper.readValue(uidto,ActionDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Action action1=new Action();
        BeanUtils.copyProperties(action,action1);

        actionRepository.save(action1);
    }

    @KafkaListener(topics = "clicks",groupId = "group-id",containerFactory = "kafkaListenerContainerFactory1")
    public void addAds(String ads)
    {
         AdDTO adDTO = new AdDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            adDTO = objectMapper.readValue(ads,AdDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Action action=new Action();
        action.setUserId(adDTO.getUserId());
        action.setTag(adDTO.getTag());
        action.setChannel(adDTO.getSource());
        action.setAction("ads");

        actionRepository.save(action);
    }

    @KafkaListener(topics = "login", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void addLoginAction(String login) {
        LoginActionDTO loginActionDTO=new LoginActionDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            loginActionDTO = objectMapper.readValue(login,LoginActionDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (String tag:loginActionDTO.getTag())
        {
            Action action1=new Action();
            action1.setAction(loginActionDTO.getAction());
            action1.setChannel(loginActionDTO.getChannel());
            action1.setTag(tag);
            action1.setUserId(loginActionDTO.getUserId());
            actionRepository.save(action1);

        }

    }



    @KafkaListener(topics = "quiz", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void addQuiz(String quizdto)
    {
        QuizDTO quizDTO=new QuizDTO();
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            quizDTO=objectMapper.readValue(quizdto,QuizDTO.class);
        }catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        Quiz quiz=new Quiz();
        BeanUtils.copyProperties(quizDTO,quiz);
        quizRepository.save(quiz);
    }


    @Override
    public List<String> findByUserId(String userId) {
        List<Action> actionList=searchQueryBuilder.getAll(userId,"like");
        List<Action> actionList1=searchQueryBuilder.getAll(userId,"dislike");
        List<Action> actionList2=searchQueryBuilder.getAll(userId,"login");
        List<String> likeTagList=new ArrayList<>();
        List<String> dislikeTagList=new ArrayList<>();
        List<String> intersetTagList=new ArrayList<>();
        for (Action action:actionList)
        {
            likeTagList.add(action.getTag());

        }
        for (Action action:actionList1)
        {

                dislikeTagList.add(action.getTag());

        }
        for (Action action:actionList2)
        {
                intersetTagList.add(action.getTag());

        }
        if(likeTagList!=null && dislikeTagList!=null) {
            likeTagList.removeAll(dislikeTagList);
        }
        if(likeTagList!=null && intersetTagList!=null)
        likeTagList.addAll(intersetTagList);

        if(likeTagList==null && intersetTagList!=null)
        {
            return intersetTagList;
        }

            return likeTagList;
    }


    @Override
    public int findAll() {
        List<Quiz> quizList=(ArrayList<Quiz>)quizRepository.findAll();
        return quizList.size();
    }

    @Override
    public void deleteAll() {
        quizRepository.deleteAll();
    }

//    @Override
//    public String findTopPage() {
//        return ;
//    }

    @Override
    public Map<String, Long> findTopPage()
    {
        return searchQueryBuilder.getTagListCount("pageView");

    }

    @Override
    public Map<String, Long> findNoOfLike() {
        return searchQueryBuilder.getTagListCount("like");
    }

    @Override
    public Map<String, Long> findNoOfDisLike() {
        return searchQueryBuilder.getTagListCount("dislike");
    }

    @Override
    public String findMostPopularTagOnFacebook() {
        return searchQueryBuilder.getMostPopularTag("facebook","like");
    }

    @Override
    public String findMostPopularTagOnQuora() {
        return searchQueryBuilder.getMostPopularTag("quora","like");
    }
}
