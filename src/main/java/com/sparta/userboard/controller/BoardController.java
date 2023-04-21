package com.sparta.userboard.controller;

import com.sparta.userboard.dto.boardDto.BoardMsgResponseDto;
import com.sparta.userboard.dto.boardDto.BoardRequestDto;
import com.sparta.userboard.dto.boardDto.BoardResponseDto;
import com.sparta.userboard.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/posts/new_post")
    public BoardResponseDto newBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest){
        return boardRequestDto.newBoard(boardRequestDto, httpServletRequest);
    }


    @GetMapping("/posts")
    public List<BoardResponseDto> getListBoards(){
        return boardService.getListBoards();
        }

    @GetMapping("/posts/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/posts/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest){
        return boardService.updateBoard(id, boardRequestDto, httpServletRequest);
    }

    @DeleteMapping("/posts/{id}")
    public BoardMsgResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return  boardService.deleteBoard(id, httpServletRequest);
    }


}
