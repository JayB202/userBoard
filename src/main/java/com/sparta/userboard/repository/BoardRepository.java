package com.sparta.userboard.repository;

import com.sparta.userboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByModifiedAtDesc();
    Optional<Board> findByIdAndUserId(Long id, Long userId);
}