package com.example.board.service;

import com.example.board.common.FileUtils;
import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;
import com.example.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private final BoardMapper boardMapper;

    @Autowired
    private final FileUtils fileUtils;

    @Override
    @Transactional(readOnly = true)
    public List<BoardDto> selectBoardList() throws Exception{
        return boardMapper.selectBoardList();
    }

    @Override
    @Transactional
    public void insertBoard(BoardDto dto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        boardMapper.insertBoard(dto);
        List<BoardFileDto> list = fileUtils.parseFileInfo(dto.getBoardIdx(), multipartHttpServletRequest);
        if (!CollectionUtils.isEmpty(list)) boardMapper.insertBoardFileList(list);
    }

    @Override
    @Transactional
    public BoardDto selectBoardDetail(int boardIdx) throws Exception{
        BoardDto board = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
        board.setBoardFileDtoList(fileList);

        boardMapper.updateHitCount(boardIdx);

        return board;
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

    @Override
    @Transactional(readOnly = true)
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception{
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }
}
