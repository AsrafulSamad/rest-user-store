package com.pathao.userstore.request;

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
public class UserTagPostRequest {
    public List<String> tags;
    public Long expiry;
}
