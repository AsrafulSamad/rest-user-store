package com.pathao.userstore.service;

import com.pathao.userstore.entity.User;
import com.pathao.userstore.entity.UserTag;
import com.pathao.userstore.exception.UserNotFoundException;
import com.pathao.userstore.repo.UserRepository;
import com.pathao.userstore.repo.UserTagRepository;
import com.pathao.userstore.request.UserPostRequest;
import com.pathao.userstore.request.UserTagPostRequest;
import com.pathao.userstore.response.UserDto;
import com.pathao.userstore.response.UserPostResponse;
import com.pathao.userstore.response.UserTagDto;
import com.pathao.userstore.response.UserTagListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTagRepository userTagRepository;

    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    @Override
    public UserPostResponse createUser(UserPostRequest userPostRequest) {
            User newUser = User.builder()
                    .firstName(userPostRequest.getFirstName())
                    .lastName(userPostRequest.getLastName())
                    .password(userPostRequest.getPassword())
                    .build();

            User user = userRepository.save(newUser);

            UserPostResponse userPostResponse = UserPostResponse.builder()
                    .id(user.getId())
                    .build();

            return userPostResponse;
    }

    @Override
    public  List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        //TODO: Need to use Java Stream
        List<UserDto> list = new ArrayList<>();
        for (User usr : users) {
            UserDto userDto = UserDto.builder()
                    .id(usr.getId())
                    .firstName(usr.getFirstName())
                    .lastName(usr.getLastName())
                    .build();
            list.add(userDto);
        }
        return list;
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            log.error("User not found for id: {}", id);
            throw new UserNotFoundException(id);
        }

        UserDto userDto = UserDto.builder()
                .id(user.get().getId())
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName())
                .build();

        return userDto;
    }


    @Override
    public void createUserTags(Long id, UserTagPostRequest userPostRequest) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            log.error("User not found for id: {}", id);
            throw new UserNotFoundException(id);
        }

        for (String tag : userPostRequest.getTags()) {
            UserTag userTag = userTagRepository.findByTagAndUserId(tag,id);
            if(userTag == null){
                UserTag tags =  UserTag.builder()
                        .tag(tag)
                        .userId(id)
                        .expiry(userPostRequest.getExpiry())
                        .build();

                userTagRepository.save(tags);
            } else {
                userTag.setExpiry(userPostRequest.getExpiry());
                userTagRepository.save(userTag);
            }
        }
    }

    //TODO: This method need to have refactoring to optimize DB calling and to reduce looping multiple times
    @Override
    public UserTagListResponse getUsersByTags(String[] tags) {
        UserTagListResponse userTagListResponse = new UserTagListResponse();
        List<UserTagDto> users = new ArrayList<UserTagDto>();
        Set<Long> checkSet =  new HashSet<Long>();
        for(String tag: tags){
            List<UserTag> userTags = userTagRepository.findByTag(tag);

            if(userTags == null || userTags.isEmpty()) {
                continue;
            }

            for(UserTag userTag : userTags){

                Optional<User> user = userRepository.findById(userTag.getUserId());
                if(!user.isPresent()) {
                    continue;
                }

                if(cal.getTimeInMillis() > userTag.getExpiry()){
                    log.error("User tag[{}] has been expired for userId:{}",userTag.getTag(),userTag.getUserId());
                    continue;
                }

                if(checkSet.contains(userTag.getUserId())){
                    for(UserTagDto userTagDto : users){
                        if(userTagDto.getId() == userTag.getUserId()){
                            userTagDto.getTags().add(userTag.getTag());
                            break;
                        }
                    }
                } else {
                    Set<String> tagSet = new HashSet<String>();
                    tagSet.add(userTag.getTag());

                    UserTagDto userTagDto = UserTagDto.builder()
                            .id(userTag.getUserId())
                            .name(user.get().getFirstName() + " " + user.get().getLastName())
                            .tags(tagSet)
                            .build();

                    users.add(userTagDto);
                }

                checkSet.add(userTag.getUserId());
            }
        }

        addOtherMissingTags(users);
        userTagListResponse.setUsers(users);

        return userTagListResponse;
    }


    private List<UserTagDto>  addOtherMissingTags(List<UserTagDto> users){
        //TODO: Need to remove DB query within for loop
        for(UserTagDto userTagDto : users) {
            List<UserTag> userTags = userTagRepository.findByUserId(userTagDto.getId());
            for (UserTag userTag : userTags){
                if(cal.getTimeInMillis() < userTag.getExpiry()) {
                    userTagDto.getTags().add(userTag.getTag());
                }
            }
        }
        return users;
    }
}
