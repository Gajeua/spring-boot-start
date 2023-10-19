package com.example.board.repo;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaBoardRepository extends JpaRepository <BoardEntity, Long> {
    List<BoardEntity> findAllByOrderByBoardIdxDesc();

    @Query("select file from BoardFileEntity file where file.idx = :idx")
    BoardFileEntity findBoardFile(Long idx);
}
