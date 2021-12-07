package com.pathao.userstore.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    Long id;
    String firstName;
    String lastName;
}
