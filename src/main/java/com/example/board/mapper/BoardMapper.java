package com.example.board.mapper;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoardList() throws Exception;

    void insertBoard(BoardDto dto) throws Exception;

    BoardDto selectBoardDetail(int boardIdx) throws Exception;

    void updateHitCount(int boardIdx) throws Exception;

    void updateBoard(BoardDto dto) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;

    void insertBoardFileList(List<BoardFileDto> boardFileDtoList) throws Exception;

    List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;

    BoardFileDto selectBoardFileInformation(@Param("idx") int idx,@Param("boardIdx") int boardIdx) throws Exception;
}
