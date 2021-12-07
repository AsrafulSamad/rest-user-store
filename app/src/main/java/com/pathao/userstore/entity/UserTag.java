package com.pathao.userstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserTag {

    private @Id
    @GeneratedValue
    Long id;
    private Long userId;
    private String tag;
    private Long expiry;

    public UserTag() {}

    UserTag(Long userId, String tag, Long expiry) {
        this.userId = userId;
        this.tag = tag;
        this.expiry = expiry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTag userTags = (UserTag) o;
        return Objects.equals(id, userTags.id) && Objects.equals(userId, userTags.userId) && Objects.equals(tag, userTags.tag) && Objects.equals(expiry, userTags.expiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, tag, expiry);
    }
}
