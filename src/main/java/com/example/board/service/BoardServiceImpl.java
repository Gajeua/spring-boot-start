package com.example.board.service;

import com.example.board.dto.BoardDto;
import com.example.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
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
    public void insertBoard(BoardDto dto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        if (!ObjectUtils.isEmpty(multipartHttpServletRequest)) {

            // input 태그에서 file 타입을 가진 name 들을 Iterator 로 받을 수 있다.
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            String name;
            while (iterator.hasNext()) {
                name = iterator.next();
                log.debug("file tag name : {}", name);
                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
                for (MultipartFile multipartFile : list) {
                    log.debug("start file information !!");
                    // ex) file name : 1.jpg, size : 249055, contentType : image/jpeg
                    log.debug("file name : {}, size : {}, contentType : {}", multipartFile.getOriginalFilename(), multipartFile.getSize(), multipartFile.getContentType());
                    log.debug("end file informantion !! \n");
                }
            }
        }

//        boardMapper.insertBoard(dto);
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
