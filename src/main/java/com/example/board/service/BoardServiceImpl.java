package com.example.board.service;

import com.example.board.dto.BoardDto;
import com.example.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BoardDto> selectBoardList() throws Exception{
        return boardMapper.selectBoardList();
    }

    @Override
    @Transactional
    public void insertBoard(BoardDto dto) throws Exception{
        boardMapper.insertBoard(dto);
    }

    @Override
    @Transactional
    public BoardDto selectBoardDetail(int boardIdx) throws Exception{
        boardMapper.updateHitCount(boardIdx);

        return boardMapper.selectBoardDetail(boardIdx);
    }

    @Override
    @Transactional
    public void updateBoard(BoardDto dto) throws Exception{
        boardMapper.updateBoard(dto);
    }

    @Override
    @Transactional
    public void deleteBoard(int boardIdx) throws Exception{
        boardMapper.deleteBoard(boardIdx);
    }
}
