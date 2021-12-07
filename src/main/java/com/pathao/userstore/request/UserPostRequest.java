package com.pathao.userstore.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize
@JsonDeserialize
@Getter
public class UserPostRequest {
    public String firstName;
    public String lastName;
    public String password;
}
