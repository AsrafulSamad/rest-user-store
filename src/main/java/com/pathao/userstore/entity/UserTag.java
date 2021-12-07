package com.pathao.userstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTag {
    private @Id
    @GeneratedValue
    Long id;
    private Long userId;
    private String tag;
    private Long expiry;
}
