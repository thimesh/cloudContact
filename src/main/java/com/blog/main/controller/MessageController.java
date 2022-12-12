package com.blog.main.controller;

import com.blog.main.dao.UserDao;
import com.blog.main.model.Message;
import com.blog.main.model.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/message/api/")
public class MessageController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    UserDao userDao;
    @PostMapping("/send")
    public ResponseEntity<Message> getMessage(@RequestParam("messageBody") String message, Principal principal){

        User user = userDao.getUserByUsername(principal.getName());

        log.info("message ::"+ message);
        //log.info(""+message.getSenderId());
      //  log.info("ki");
        return null;

    }
}
