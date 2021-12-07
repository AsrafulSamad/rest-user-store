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
        log.info("[REQ] -> User creation request: {}", userPostRequest.toString());
        UserPostResponse userPostResponse = userService.createUser(userPostRequest);
        log.info("[RES] -> User creation response: {}", userPostResponse.toString());
        return userPostResponse;
    }

    @GetMapping("/users")
    List<UserDto>  getAllUsers() {
       log.info("[REQ] -> Get All Users request");
       List<UserDto> userDtos = userService.getAllUsers();
       log.info("[RES] -> Get All Users response: {}", userDtos);
       return userDtos;
    }

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable Long id) {
        log.info("[REQ] -> Get Single User request by id: {}", id);
        UserDto userDto = userService.getUser(id);
        log.info("[RES] -> Single User response: {}", userDto);
        return userDto;
    }

    @PostMapping("/users/{id}/tags")
    void createUserTags(@PathVariable Long id, @RequestBody UserTagPostRequest userPostRequest) {
        log.info("[REQ] -> User tag post request for id: {} and userPostRequest: {}", id, userPostRequest);
        userService.createUserTags(id, userPostRequest);
        log.info("[RES] -> Tags: {} has been posted successfully for user id: {}", userPostRequest.getTags(), id);
    }

    @GetMapping("/users/get-by-tags")
    UserTagListResponse getUsersByTags(@RequestParam String[] tags) {
        log.info("[REQ] -> Get users by tag list: {}", tags);
        UserTagListResponse users = userService.getUsersByTags(tags);
        log.info("[RES] -> User list: {}", users.getUsers());
        return users;
    }

}
