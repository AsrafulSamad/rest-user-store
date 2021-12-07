package com.pathao.userstore.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize
@JsonDeserialize
@Getter
@Setter
public class UserListResponse {
    List<UserDto> users;
}
