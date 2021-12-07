package com.pathao.userstore.service;

import com.pathao.userstore.entity.User;
import com.pathao.userstore.request.UserPostRequest;
import com.pathao.userstore.request.UserTagPostRequest;
import com.pathao.userstore.response.UserDto;
import com.pathao.userstore.response.UserPostResponse;
import com.pathao.userstore.response.UserTagListResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

    UserPostResponse createUser(UserPostRequest userPostRequest);

     List<UserDto> getAllUsers();

     UserDto getUser(Long id);

     void createUserTags(Long id, UserTagPostRequest userPostRequest);

    UserTagListResponse getUsersByTags(String[] tags);
}
