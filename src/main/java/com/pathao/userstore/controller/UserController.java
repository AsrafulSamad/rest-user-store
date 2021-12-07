package com.pathao.userstore.controller;

import com.pathao.userstore.request.UserPostRequest;
import com.pathao.userstore.request.UserTagPostRequest;
import com.pathao.userstore.response.UserDto;
import com.pathao.userstore.response.UserPostResponse;
import com.pathao.userstore.response.UserTagListResponse;
import com.pathao.userstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/users")
    UserPostResponse createUser(@RequestBody UserPostRequest userPostRequest) {
        return userService.createUser(userPostRequest);
    }

    @GetMapping("/users")
    List<UserDto>  getAllUsers() {
       return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users/{id}/tags")
    void postUserTags(@PathVariable Long id, @RequestBody UserTagPostRequest userPostRequest) {
        userService.createUserTags(id, userPostRequest);
    }

    @GetMapping("/users-by-tags")
    UserTagListResponse getUsersByTags(@RequestParam String[] tags) {
        UserTagListResponse users = userService.getUsersByTags(tags);
        return users;
    }



}
