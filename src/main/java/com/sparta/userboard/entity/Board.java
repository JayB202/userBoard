package com.sparta.userboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.userboard.dto.boardDto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String contents;
    @JsonIgnore
    @Column
    private String password;
    @Column(nullable = false)
    private Long userId;

    public Board(BoardRequestDto boardRequestDto, Long userId) {
        this.title = boardRequestDto.getTitle();
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
        this.password = boardRequestDto.getPassword();
        this.userId = userId;
    }
    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
        this.password = boardRequestDto.getPassword();
    }
}