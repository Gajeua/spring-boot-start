package com.example.board.service;

import com.example.board.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> selectBoardList() throws Exception;

    void insertBoard(BoardDto dto) throws Exception;

    BoardDto selectBoardDetail(int boardIdx) throws Exception;

    void updateBoard(BoardDto dto) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;
}
