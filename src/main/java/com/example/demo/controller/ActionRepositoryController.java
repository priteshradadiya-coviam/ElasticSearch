package com.example.demo.controller;

//import com.example.demo.client.LoginClient;
import com.example.demo.client.LoginClient;
import com.example.demo.dto.*;
import com.example.demo.service.ActionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repo")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ActionRepositoryController {

    private static final String TOPIC ="action";

    private static final String TOPIC1="quiz";

    private static final String TOPIC2="login";

    @Autowired
    ActionService actionService;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    LoginClient loginClient;

    private String secret="testProject";

    @PostMapping("/add")
    public void addAction(@RequestBody UIDTO uidto,@RequestHeader("accessToken") String accessToken) throws JsonProcessingException {

        String token=accessToken.substring(7);

        Claims claims=this.parseToken(token);
        String userId = String.valueOf(claims.get("userId")).trim();
        ObjectMapper objectMapper=new ObjectMapper();
        ActionDTO actionDTO=new ActionDTO();
        BeanUtils.copyProperties(uidto,actionDTO);
        actionDTO.setUserId(userId);
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(actionDTO));
        //return "success";
    }

    @PostMapping("/addLogin")
    public  void addLoginAction(@RequestBody LoginDTO loginDTO, @RequestHeader("accessToken") String accessToken) throws JsonProcessingException {
        System.out.println(accessToken);
       // String token=accessToken.substring(7);

        Claims claims=this.parseToken(accessToken);
        String userId = String.valueOf(claims.get("userId")).trim();

        System.out.println(userId);
        // String userId=loginClient.getUserIdFromAccessToken(accessToken);
        ObjectMapper objectMapper=new ObjectMapper();
        LoginActionDTO loginActionDTO=new LoginActionDTO();
        BeanUtils.copyProperties(loginDTO,loginActionDTO);

       loginActionDTO.setUserId(userId);
        kafkaTemplate.send(TOPIC2,objectMapper.writeValueAsString(loginActionDTO));
        //return "success";
    }


    @GetMapping("/findActionListByUserId/{id}")
    public List<String> findActionListByUserId(@PathVariable("id") String userId)
    {
        return actionService.findByUserId(userId);

    }

    @PostMapping("/addQuiz")
    public  String addQuiz(@RequestBody QuizDTO quizDTO) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        kafkaTemplate.send(TOPIC1,objectMapper.writeValueAsString(quizDTO));
        return "success";
    }

    @GetMapping("/findAll")
    public int findAll()
    {
        return actionService.findAll();
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll()
    {
        actionService.deleteAll();
    }

    @GetMapping("/findTopPage")
    public Map<String,Long> findTopPage()
    {
        return actionService.findTopPage();
    }

    @GetMapping("/noOfLike")
    public  Map<String,Long> findNoOfLike()
    {
        return actionService.findNoOfLike();
    }

    @GetMapping("/noOfDisLike")
    public  Map<String,Long> findNoOfDisLike()
    {
        return actionService.findNoOfDisLike();
    }

    @GetMapping("/mostPopularTagOnFacebook")
    public String findMostPopularTagOnFacebook()
    {
        return actionService.findMostPopularTagOnFacebook();
    }

    @GetMapping("/mostPopularTagOnQuora")
    public String findMostPopularTagOnQuora()
    {
        return actionService.findMostPopularTagOnQuora();
    }


    public Claims parseToken(String token) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token).getBody();
        return claims;
    }




}
