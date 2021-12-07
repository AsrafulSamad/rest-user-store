package com.pathao.userstore.repo;


import com.pathao.userstore.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag, Long> {

    UserTag findByTagAndUserId(String tag, Long userId);

    UserTag findByTag(String tag);
}
