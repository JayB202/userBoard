package com.sparta.userboard.dto.boardDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardMsgResponseDto {
    private String msg;
    private int statusCode;
}