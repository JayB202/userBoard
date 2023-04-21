package com.sparta.userboard.controller;

import com.sparta.userboard.dto.userDto.SignInRequestDto;
import com.sparta.userboard.dto.userDto.UserMsgResponseDto;
import com.sparta.userboard.dto.userDto.SignUpRequestDto;
import com.sparta.userboard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserMsgResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new UserMsgResponseDto("회원가입 완료", HttpStatus.OK.value()));
    }
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<UserMsgResponseDto> login(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response) {
        userService.login(signInRequestDto, response);
        return ResponseEntity.ok(new UserMsgResponseDto("로그인 완료",HttpStatus.OK.value()));
    }
}