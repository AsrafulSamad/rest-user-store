package com.pathao.userstore.response;

import java.util.List;
import java.util.Set;

public class UserTagDto {
    public Long id;
    public String name;
    public Set<String> tags;

    public Long getId() {
        return id;
    }
}
