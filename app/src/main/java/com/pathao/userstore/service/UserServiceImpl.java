package com.pathao.userstore.service;

import com.pathao.userstore.Exception.UserNotFoundException;
import com.pathao.userstore.entity.User;
import com.pathao.userstore.entity.UserTag;
import com.pathao.userstore.repo.UserRepository;
import com.pathao.userstore.repo.UserTagRepository;
import com.pathao.userstore.request.UserPostRequest;
import com.pathao.userstore.request.UserTagPostRequest;
import com.pathao.userstore.response.UserDto;
import com.pathao.userstore.response.UserPostResponse;
import com.pathao.userstore.response.UserTagDto;
import com.pathao.userstore.response.UserTagListResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;

    UserServiceImpl(UserRepository userRepository, UserTagRepository userTagRepository) {
        this.userRepository = userRepository;
        this.userTagRepository = userTagRepository;
    }

    @Override
    public UserPostResponse createUser(UserPostRequest userPostRequest) {
            User newUser = new User();

            newUser.setFirstName(userPostRequest.firstName);
            newUser.setLastName(userPostRequest.lastName);
            newUser.setPassword(userPostRequest.password);

            User user = userRepository.save(newUser);

            UserPostResponse userPostResponse = new UserPostResponse();
            userPostResponse.setId(user.getId());
            return userPostResponse;
    }

    @Override
    public  List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserDto> list = new ArrayList<>();
        for (User usr : users) {
            UserDto userDto = new UserDto();
            userDto.id = usr.getId();
            userDto.firstName = usr.getFirstName();
            userDto.lastName = usr.getLastName();

            list.add(userDto);
        }
        return list;
    }


    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.firstName = user.getFirstName();
        userDto.lastName = user.getLastName();

        return userDto;
    }


    @Override
    public void createUserTags(Long id, UserTagPostRequest userPostRequest) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        for (String tag : userPostRequest.tags) {
            UserTag userTag = userTagRepository.findByTagAndUserId(tag,id);
            if(userTag == null){
                UserTag tags =  new UserTag();
                tags.setTag(tag);
                tags.setUserId(id);
                tags.setExpiry(userPostRequest.expiry);
                userTagRepository.save(tags);
            } else {
                userTag.setExpiry(userPostRequest.expiry);
                userTagRepository.save(userTag);
            }
        }
    }

    @Override
    public UserTagListResponse getUsersByTags(String[] tags) {
        UserTagListResponse userTagListResponse = new UserTagListResponse();
        List<UserTagDto> users = new ArrayList<UserTagDto>();
        Set<Long> checkSet =  new HashSet<Long>();

        //TODO: Need to refactor to reduce time complexity; Need to use join query
        for(String tag: tags){
            UserTag userTag = userTagRepository.findByTag(tag);
            if(userTag == null) continue;

            //TODO: Check Expiry

            Optional<User> user = userRepository.findById(userTag.getUserId());
            if(user == null) continue;

            if(checkSet.contains(userTag.getUserId())){
                for(UserTagDto userTagDto : users){
                    if(userTagDto.getId() == userTag.getUserId()){
                        userTagDto.tags.add(userTag.getTag());
                        break;
                    }
                }
            } else {
                UserTagDto userTagDto = new UserTagDto();
                userTagDto.id = userTag.getUserId();
                userTagDto.name = user.get().getFirstName() + user.get().getLastName();
                userTagDto.tags = new HashSet<String>();
                userTagDto.tags.add(userTag.getTag());
                users.add(userTagDto);

            }

        }
        userTagListResponse.users = users;

        return userTagListResponse;
    }
}
