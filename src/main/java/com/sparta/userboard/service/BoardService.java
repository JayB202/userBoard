package com.sparta.userboard.service;

import com.sparta.userboard.dto.boardDto.BoardRequestDto;
import com.sparta.userboard.dto.boardDto.BoardResponseDto;
import com.sparta.userboard.dto.boardDto.BoardMsgResponseDto;
import com.sparta.userboard.entity.Board;
import com.sparta.userboard.entity.User;
import com.sparta.userboard.jwt.JwtUtil;
import com.sparta.userboard.repository.BoardRepository;
import com.sparta.userboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, HttpServletRequest httpServletRequest) {

        String token = jwtUtil.resolveToken(httpServletRequest);

        Claims claims;

        //토큰이 있는 경우에만 추가 가능
        if (token != null) {
            //validateToken()를 사용해서, 들어온 토큰이 위조/변조, 만료가 되지 않았는지 검증
            if (jwtUtil.validateToken(token)) {
                //true 라면, 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
                //false 라면,
            } else {
                //매개변수가 의도치 않는 상황 유발시, 해당 메시지 반환
                throw new IllegalArgumentException("Token Error");
            }

            //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            //claims.getSubject(): 우리가 넣어두었던 username 가져오기
            //findByUsername()를 사용해서, UserRepository 에서 user 정보를 가져오기
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    //매개변수가 의도치 않는 상황 유발시
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );


            Board board = boardRepository.saveAndFlush(new Board(boardRequestDto, user.getId()));
            return new BoardResponseDto(board);

        } else {

            return null;
        }
    }


    @Transactional(readOnly = true)

    public List<BoardResponseDto> getListBoards() {

        List<Board> boardList =  boardRepository.findAllByOrderByModifiedAtDesc();      //주의. boards 와 board
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();

        for (Board board : boardList) {
            boardResponseDto.add(new BoardResponseDto(board));
        }
        return boardResponseDto;
    }


    @Transactional(readOnly = true)

    public BoardResponseDto getBoard(Long id) {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
       return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(       //추가: 실수    //id?? id, user.getId()??
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            board.update(requestDto);
            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional
    public BoardMsgResponseDto deleteBoard (Long id, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(       //추가: 실수 findById(id) --> findById(id, user.getId()) --> findByIdAndUserId(id, user.getId())
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            boardRepository.delete(board);
            return new BoardMsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value());

        } else {
            return new BoardMsgResponseDto("게시글 작성자만 삭제 가능", HttpStatus.OK.value());
        }
    }
}