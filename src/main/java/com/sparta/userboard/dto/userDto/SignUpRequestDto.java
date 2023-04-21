package com.sparta.userboard.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@NoArgsConstructor ??
@AllArgsConstructor         //추가?
@Builder      //추가?
public class SignUpRequestDto {

    //필드
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
