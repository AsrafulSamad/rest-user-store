package com.pathao.userstore.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserTagDto {
    Long id;
    String name;
    Set<String> tags;
}
