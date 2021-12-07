package com.pathao.userstore.repo;


import com.pathao.userstore.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag, Long> {

    UserTag findByTagAndUserId(String tag, Long userId);

    List<UserTag> findByUserId(Long userId);
    List<UserTag> findByTag(String tag);
}
