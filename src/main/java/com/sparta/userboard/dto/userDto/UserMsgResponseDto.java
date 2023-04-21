package com.sparta.userboard.dto.userDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMsgResponseDto {
    private String msg;
    private int statusCode;
}